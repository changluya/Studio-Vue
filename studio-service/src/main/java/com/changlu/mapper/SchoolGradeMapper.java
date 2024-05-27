package com.changlu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.changlu.system.pojo.SchoolGradeModel;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ChangLu
 * @since 2022-03-30
 */
public interface SchoolGradeMapper extends BaseMapper<SchoolGradeModel> {

    /**
     * 查询ZfGrade列表
     *
     * @param schoolGradeModel ZfGrade
     * @return ZfGrade集合
     */
    public List<SchoolGradeModel> selectZfGradeModelList(SchoolGradeModel schoolGradeModel);

    /**
     * 查询ZfGrade
     *
     * @param gradeId ZfGrade主键
     * @return ZfGrade
     */
    public SchoolGradeModel selectZfGradeModelByGradeId(Long gradeId);

    /**
     * 新增ZfGrade
     *
     * @param schoolGradeModel ZfGrade
     * @return 结果
     */
    public int insertZfGradeModel(SchoolGradeModel schoolGradeModel);

    /**
     * 修改ZfGrade
     *
     * @param schoolGradeModel ZfGrade
     * @return 结果
     */
    public int updateZfGradeModel(SchoolGradeModel schoolGradeModel);

    /**
     * 批量删除ZfGrade
     *
     * @param gradeIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteZfGradeModelByGradeIds(Long[] gradeIds);

}
