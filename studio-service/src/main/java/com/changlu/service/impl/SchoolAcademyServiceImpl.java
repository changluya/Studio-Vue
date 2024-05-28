package com.changlu.service.impl;

import java.util.List;

import com.changlu.system.pojo.SchoolAcademyModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.changlu.mapper.SchoolAcademyMapper;
import com.changlu.service.ISchoolAcademyService;

/**
 * 学院Service业务层处理
 *
 * @author ruoyi
 * @date 2024-05-28
 */
@Service
public class SchoolAcademyServiceImpl implements ISchoolAcademyService {
    @Autowired
    private SchoolAcademyMapper schoolAcademyMapper;

    /**
     * 查询学院
     *
     * @param id 学院主键
     * @return 学院
     */
    @Override
    public SchoolAcademyModel selectSchoolAcademyById(Long id)
    {
        return schoolAcademyMapper.selectSchoolAcademyById(id);
    }

    /**
     * 查询学院列表
     *
     * @param SchoolAcademyModel 学院
     * @return 学院
     */
    @Override
    public List<SchoolAcademyModel> selectSchoolAcademyList(SchoolAcademyModel schoolAcademy)
    {
        return schoolAcademyMapper.selectSchoolAcademyList(schoolAcademy);
    }

    /**
     * 新增学院
     *
     * @param SchoolAcademyModel 学院
     * @return 结果
     */
    @Override
    public int insertSchoolAcademy(SchoolAcademyModel schoolAcademy)
    {
        return schoolAcademyMapper.insertSchoolAcademy(schoolAcademy);
    }

    /**
     * 修改学院
     *
     * @param schoolAcademy 学院
     * @return 结果
     */
    @Override
    public int updateSchoolAcademy(SchoolAcademyModel schoolAcademy)
    {
        return schoolAcademyMapper.updateSchoolAcademy(schoolAcademy);
    }

    /**
     * 批量删除学院
     *
     * @param ids 需要删除的学院主键
     * @return 结果
     */
    @Override
    public int deleteSchoolAcademyByIds(Long[] ids)
    {
        return schoolAcademyMapper.deleteSchoolAcademyByIds(ids);
    }

    /**
     * 删除学院信息
     *
     * @param id 学院主键
     * @return 结果
     */
    @Override
    public int deleteSchoolAcademyById(Long id)
    {
        return schoolAcademyMapper.deleteSchoolAcademyById(id);
    }
}
