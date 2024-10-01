package com.changlu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.changlu.common.config.Constants;
import com.changlu.common.domain.LoginBody;
import com.changlu.common.exception.ServiceException;
import com.changlu.common.utils.JwtUtil;
import com.changlu.common.utils.RedisCache;
import com.changlu.common.utils.RsaUtil;
import com.changlu.common.utils.uuid.IdUtils;
import com.changlu.domain.LoginUser;
import com.changlu.enums.StudioRoleEnum;
import com.changlu.security.service.TokenService;
import com.changlu.security.util.SecurityUtils;
import com.changlu.service.LoginService;
import com.changlu.system.mapper.SysUserMapper;
import com.changlu.system.mapper.SysUserRoleMapper;
import com.changlu.system.pojo.SysUser;
import com.changlu.system.pojo.SysUserRole;
import com.changlu.vo.user.req.UserAccountReqVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName LoginServiceImpl
 * @Author ChangLu
 * @Date 3/25/2022 2:50 PM
 * @Description 登录业务处理器
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Map<String,String> doLogin(LoginBody loginBody) {
        //1、校验验证码
        verifyCaptcha(loginBody.getCode(),loginBody.getUuid());
        //2、非对称解密密码
        loginBody.setPassword(decrypt(loginBody.getPassword()));
        //3、进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginBody.getUsername(), loginBody.getPassword());
        Authentication authenticate = null;
        try {
            authenticate = authenticationManager.authenticate(authenticationToken);
        }catch (Exception e){  //注意在这里可以捕捉到BadCredentialsException异常
            log.error(String.format("用户认证异常，此次用户登陆用户名为：%s", loginBody.getUsername()), e);
            if (e instanceof BadCredentialsException){
                throw new ServiceException("您的密码有误，请重新输入");  //抛出密码错误异常
            }else{
                throw new ServiceException(e.getMessage());
            }
        }
        // 若是认证没有通过，抛出异常
        if (ObjectUtils.isEmpty(authenticate)) {
            throw new ServiceException("用户认证失败");
        }
        // 认证通过，将用户信息取出存储到redis（默认存储时间）
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        //以前使用的是id来作为标识，应该使用一个uuid，并存储到用户对象中
//        String userId = loginUser.getUser().getUserId().toString();
        String uuid = IdUtils.simpleUUID();
        loginUser.setToken(uuid);
        tokenService.refreshToken(loginUser);
        //4、根据UserId来生成Token进行返回
        String token = JwtUtil.createJWT(uuid);
        Map<String,String> result = new HashMap<String,String>(1);
        result.put("token",token);
        return result;
    }

    @Override
    public void verifyCaptcha(String code, String uuid) {
        //redis获取验证码后删除
        String redisCode = redisCache.getCacheObject(Constants.CAPTCHA_CODE_KEY + uuid);
        redisCache.deleteObject(Constants.CAPTCHA_CODE_KEY + uuid);
        //情况一：验证码已失效
        if (ObjectUtils.isEmpty(redisCode)){
            throw new ServiceException("验证码已过期，已失效！");
        }
        //情况二：验证码有误
        if (!redisCode.equalsIgnoreCase(code)) {
            throw new ServiceException("验证码有误，请重新输入！");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean registerUser(LoginBody loginBody) {
        //对密码进行非对称解密
        loginBody.setPassword(decrypt(loginBody.getPassword()));
        //1、查询用户名是否存在
        if (checkUserNameIsExist(loginBody.getUsername())) {
            throw new ServiceException("用户名已存在，请重新输入！");
        }
        //2、注册用户（对外注册，目前只提供成员角色）
        String username = loginBody.getUsername();
        String password = loginBody.getPassword();
        List<Long> userRoleIds = new ArrayList<>(1);
        userRoleIds.add(
                StudioRoleEnum.ROLE_MEMBER.value()
        );
        return coreCreateAccount(username, password, userRoleIds);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean createAccount(UserAccountReqVo userAccountReqVo) {
        //1、查询用户名是否存在
        if (checkUserNameIsExist(userAccountReqVo.getUsername())) {
            throw new ServiceException("用户名已存在，请重新输入！");
        }
        // 2、过滤非授权角色
        // 目前提供创建账号的角色仅仅只有成员、老师角色账号
        List<Long> supportCreateRoleIds = Arrays.asList(
                StudioRoleEnum.ROLE_MEMBER.value(),
                StudioRoleEnum.ROLE_TEACHER.value()
        );
        // 构建基础信息
        String username = userAccountReqVo.getUsername(); // 用户名
        String password = decrypt(userAccountReqVo.getPassword()); // 密码
        List<Long> userRoleIds = new ArrayList<>(1); // 构建角色列表
        Long roleId = userAccountReqVo.getRoleId();
        if (!supportCreateRoleIds.contains(roleId)) {
            log.error(String.format("不支持创建非成员、老师角色的账号，当前创建的角色id为：%s，目前仅支持创建的角色id为：%s", roleId, supportCreateRoleIds));
            return false;
        }
        // 针对老师角色 =》 成员、老师角色
        if (StudioRoleEnum.ROLE_TEACHER.value().equals(roleId)) {
            userRoleIds.add(StudioRoleEnum.ROLE_MEMBER.value());
            userRoleIds.add(roleId);
        }else { // 针对成员角色 =》 成员角色
            userRoleIds.add(roleId);
        }
        // 3、创建账号
        return coreCreateAccount(username, password, userRoleIds);
    }

    /**
     * 检查用户名是否存在
     * @param username 用户名
     * @return
     */
    private boolean checkUserNameIsExist(String username) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUserName, username);    // WHERE (user_name = xx);
        if (sysUserMapper.selectCount(queryWrapper) > 0){
            return true;
        }
        return false;
    }

    /**
     * 创建用户账号以及给予的角色权限
     * @param username 用户名（明文）
     * @param password 密码（明文）
     * @param roleIds 角色id列表
     * @return
     */
    private boolean coreCreateAccount(String username, String password, List<Long> roleIds) {
        // 密码加密存储到数据库
        String bcryptPasswd = SecurityUtils.bCryptPasswordEncoder.encode(password);
        SysUser registerUser = new SysUser(username, username, "{bcrypt}" + bcryptPasswd);
        // 1、注册用户
        if (sysUserMapper.insertUser(registerUser) > 0){
            // 获取到注册的用户id
            Long userId = sysUserMapper.getLastInsertId();
            // 2、用户授权角色
            HashSet<Long> roleIdSet = new HashSet<>(roleIds);
            List<SysUserRole> userRoles = roleIdSet.stream()
                    .map((roleId) -> new SysUserRole(userId, roleId))
                    .collect(Collectors.toList());
            return sysUserRoleMapper.batchUserRole(userRoles) > 0;
        }
        throw new ServiceException("注册失败！");
    }

    private String decrypt(String rsaPasswd) {
        try {
            return RsaUtil.decryptByPrivateKey(rsaPasswd);
        } catch (Exception e) {
            log.error(String.format("非对称解密密码异常！传输的加密密码为：%s", rsaPasswd), e);
            throw new ServiceException("请不要尝试篡改加密密码！");
        }
    }

}
