package com.changlu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.changlu.system.pojo.StudioGradeModel;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ChangLu
 * @since 2022-03-30
 */
public interface StudioGradeMapper extends BaseMapper<StudioGradeModel> {

    /**
     * 查询ZfGrade列表
     *
     * @param studioGradeModel ZfGrade
     * @return ZfGrade集合
     */
    public List<StudioGradeModel> selectZfGradeModelList(StudioGradeModel studioGradeModel);

    /**
     * 查询ZfGrade
     *
     * @param gradeId ZfGrade主键
     * @return ZfGrade
     */
    public StudioGradeModel selectZfGradeModelByGradeId(Long gradeId);

    /**
     * 新增ZfGrade
     *
     * @param studioGradeModel ZfGrade
     * @return 结果
     */
    public int insertZfGradeModel(StudioGradeModel studioGradeModel);

    /**
     * 修改ZfGrade
     *
     * @param studioGradeModel ZfGrade
     * @return 结果
     */
    public int updateZfGradeModel(StudioGradeModel studioGradeModel);

    /**
     * 批量删除ZfGrade
     *
     * @param gradeIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteZfGradeModelByGradeIds(Long[] gradeIds);

}
