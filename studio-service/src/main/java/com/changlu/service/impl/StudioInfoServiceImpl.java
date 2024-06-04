package com.changlu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.changlu.common.utils.RedisCache;
import com.changlu.config.StudioConstant;
import com.changlu.security.util.SecurityUtils;
import com.changlu.service.*;
import com.changlu.system.pojo.SchoolAcademyModel;
import com.changlu.system.pojo.SysUser;
import com.changlu.vo.InfoVo;
import com.changlu.system.pojo.SchoolGradeModel;
import com.changlu.system.pojo.SchoolMajorModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ZfInfoServiceImpl
 * @Author ChangLu
 * @Date 3/31/2022 1:02 PM
 * @Description 信息提交业务层
 */
@Service
public class StudioInfoServiceImpl implements StudioInfoService {


    @Autowired
    private SchoolGradeService gradeService;

    @Autowired
    private SchoolMajorService majorService;

    @Autowired
    private SchoolAcademyService schoolAcademyService;

    @Autowired
    private ISysUserService syUserService;

    @Autowired
    private RedisCache redisCache;

    @Override
    public int commitUserInfo(InfoVo infoVo) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(infoVo, sysUser);
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
}
