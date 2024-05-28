package com.changlu.service;

import com.changlu.system.pojo.SchoolAcademyModel;

import java.util.List;

/**
 * 学院Service接口
 *
 * @author 长路
 * @date 2024-05-28
 */
public interface ISchoolAcademyService {
    /**
     * 查询学院
     *
     * @param id 学院主键
     * @return 学院
     */
    public SchoolAcademyModel selectSchoolAcademyById(Long id);

    /**
     * 查询学院列表
     *
     * @param schoolAcademy 学院
     * @return 学院集合
     */
    public List<SchoolAcademyModel> selectSchoolAcademyList(SchoolAcademyModel schoolAcademy);

    /**
     * 新增学院
     *
     * @param schoolAcademy 学院
     * @return 结果
     */
    public int insertSchoolAcademy(SchoolAcademyModel schoolAcademy);

    /**
     * 修改学院
     *
     * @param schoolAcademy 学院
     * @return 结果
     */
    public int updateSchoolAcademy(SchoolAcademyModel schoolAcademy);

    /**
     * 批量删除学院
     *
     * @param ids 需要删除的学院主键集合
     * @return 结果
     */
    public int deleteSchoolAcademyByIds(Long[] ids);

    /**
     * 删除学院信息
     *
     * @param id 学院主键
     * @return 结果
     */
    public int deleteSchoolAcademyById(Long id);
}
