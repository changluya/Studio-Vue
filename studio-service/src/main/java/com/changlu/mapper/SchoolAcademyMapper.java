package com.changlu.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.changlu.system.pojo.SchoolAcademyModel;
import com.changlu.system.pojo.StudioCcieModel;

/**
 * 学院Mapper接口
 *
 * @author ruoyi
 * @date 2024-05-28
 */
public interface SchoolAcademyMapper extends BaseMapper<SchoolAcademyModel> {
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
     * 删除学院
     *
     * @param id 学院主键
     * @return 结果
     */
    public int deleteSchoolAcademyById(Long id);

    /**
     * 批量删除学院
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSchoolAcademyByIds(Long[] ids);
}
