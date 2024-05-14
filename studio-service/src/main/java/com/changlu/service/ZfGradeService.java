package com.changlu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.changlu.system.pojo.StudioGradeModel;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ChangLu
 * @since 2022-03-30
 */
public interface ZfGradeService extends IService<StudioGradeModel> {

    /**
     * 查询ZfGrade列表
     *
     * @param studioGradeModel ZfGrade
     * @return ZfGrade集合
     */
    List<StudioGradeModel> selectZfGradeModelList(StudioGradeModel studioGradeModel);

    /**
     * 查询ZfGrade
     *
     * @param gradeId ZfGrade主键
     * @return ZfGrade
     */
    StudioGradeModel selectZfGradeModelByGradeId(Long gradeId);

    /**
     * 新增ZfGrade
     *
     * @param studioGradeModel ZfGrade
     * @return 结果
     */
    int insertZfGradeModel(StudioGradeModel studioGradeModel);


    /**
     * 修改ZfGrade
     *
     * @param studioGradeModel ZfGrade
     * @return 结果
     */
    int updateZfGradeModel(StudioGradeModel studioGradeModel);

    /**
     * 批量删除ZfGrade
     *
     * @param gradeIds 需要删除的ZfGrade主键集合
     * @return 结果
     */
    int deleteZfGradeModelByGradeIds(Long[] gradeIds);


}
