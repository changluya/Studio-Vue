package com.changlu.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.changlu.common.utils.JsonObjectUtil;
import com.changlu.common.utils.RedisCache;
import com.changlu.common.utils.reflect.ReflectUtils;
import com.changlu.config.StudioConstant;
import com.changlu.config.SysUserExtraConstant;
import com.changlu.enums.StudioRoleEnum;
import com.changlu.security.util.SecurityUtils;
import com.changlu.service.*;
import com.changlu.system.mapper.SysUserMapper;
import com.changlu.system.pojo.*;
import com.changlu.vo.InfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName ZfInfoServiceImpl
 * @Author ChangLu
 * @Date 3/31/2022 1:02 PM
 * @Description 信息提交业务层
 */
@Service
public class StudioInfoServiceImpl implements StudioInfoService {


    private static final Logger log = LoggerFactory.getLogger(StudioInfoServiceImpl.class);
    @Autowired
    private SchoolGradeService gradeService;

    @Autowired
    private SchoolMajorService majorService;

    @Autowired
    private SchoolAcademyService schoolAcademyService;

    @Autowired
    private ISysUserService syUserService;

    @Resource
    private SysUserMapper sysUserMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public int commitUserInfo(InfoVo infoVo) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(infoVo, sysUser);
        //JSON序列化
        //1、根据token查询到目前最新的角色
        List<SysRole> roles = SecurityUtils.getUser().getRoles();
        String rolesName = "";
        if (roles != null && roles.size() > 0) {
            rolesName = InfoVo.getRoleNames(roles);
        }
        //根据用户id取出extra字段
        JSONObject extraJSONObject = getUserExtraBySerial(SecurityUtils.getUserId());
        //若是老师则存储老师额外字段
        if (rolesName.contains(StudioRoleEnum.ROLE_TEACHER.getRoleKey())) {
            extraJSONObject.put(SysUserExtraConstant.TEACHER_EXTRA, JSONObject.toJSON(infoVo.getTeacherExtra()));
        }else if ((rolesName.contains(StudioRoleEnum.ROLE_MEMBER.getRoleKey()))){ //若是学生存储学生额外字段
            extraJSONObject.put(SysUserExtraConstant.STUDENT_EXTRA, JSONObject.toJSON(infoVo.getStudentExtra()));
        }else{
            extraJSONObject = null; //其他情况暂时不进行存储处理
        }
        // 将额外字段序列化到extra字段中
        if (extraJSONObject != null) {
            sysUser.setExtra(extraJSONObject.toJSONString());
        }
        //2、更新用户账号
        int res = syUserService.updateSysUser(sysUser, true);
        //删缓存
        redisCache.deleteObject(StudioConstant.REDIS_MEMBERS_DATA);
        return res;
    }

    @Override
    public InfoVo getUserInfo() {
        SysUser sysUser = SecurityUtils.getUser();
        InfoVo infoVo = new InfoVo();
        BeanUtils.copyProperties(sysUser, infoVo);
        //处理角色信息
        List<SysRole> roles = sysUser.getRoles();
        if (roles != null && roles.size() > 0) {
            //集合->string，如最终转为：
            infoVo.setRoleNames(InfoVo.getRoleNames(roles));
        }
        //获取学生或老师的额外字段信息
        JSONObject extraJSONObject = getUserExtraBySerial(SecurityUtils.getUserId());
        //若是角色是老师
        if (infoVo.getRoleNames().contains(StudioRoleEnum.ROLE_TEACHER.getRoleKey())) {
            InfoVo.TeacherExtra teacherExtra = JsonObjectUtil.getUserExtraObjectByKey(extraJSONObject, SysUserExtraConstant.TEACHER_EXTRA, InfoVo.TeacherExtra.class);
            teacherExtra = teacherExtra == null ? new InfoVo.TeacherExtra() : teacherExtra;
            ReflectUtils.nullToEmpty(teacherExtra, 1);
            infoVo.setTeacherExtra(teacherExtra);
        }else if (infoVo.getRoleNames().contains(StudioRoleEnum.ROLE_MEMBER.getRoleKey())){
            InfoVo.StudentExtra studentExtra = JsonObjectUtil.getUserExtraObjectByKey(extraJSONObject, SysUserExtraConstant.STUDENT_EXTRA, InfoVo.StudentExtra.class);
            studentExtra = studentExtra == null ? new InfoVo.StudentExtra() : studentExtra;
            ReflectUtils.nullToEmpty(studentExtra, 1);
            infoVo.setStudentExtra(studentExtra);
        }
        return infoVo;
    }

    @Override
    public Map<String, List> getMenu() {
        Map<String,List> result = new HashMap<>(2);
        //查询出所有专业、年级（降序排列）
        LambdaQueryWrapper<SchoolGradeModel> gradeQueryWrapper = new LambdaQueryWrapper<>();
        gradeQueryWrapper.orderByDesc(SchoolGradeModel::getGradeNum);//按照年级排序
        List<SchoolGradeModel> grades = gradeService.list(gradeQueryWrapper);
        List<SchoolMajorModel> majors = majorService.list();
        List<SchoolAcademyModel> academies = schoolAcademyService.list();
        result.put("grades",grades);
        result.put("majors",majors);
        result.put("academies",academies);
        return result;
    }

    /**
     * 获取用户表中的extra字段并将其转为JSOBObject对象
     * @return JSONObject 不为空
     * @author changlu
     */
    public JSONObject getUserExtraBySerial(Long userId) {
        //查询出来当前用户的extra字段
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<SysUser>()
                .select(SysUser::getExtra)
                .eq(SysUser::getUserId, userId);
        SysUser dbUser = sysUserMapper.selectOne(queryWrapper);
        String extra = null;
        if(dbUser != null) {
            extra = dbUser.getExtra();
        }
        //解析处理为JSONObject对象
        JSONObject extraJSONObject = JsonObjectUtil.parseStringToJsonObject(extra);
        return extraJSONObject;
    }

}
