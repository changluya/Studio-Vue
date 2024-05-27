package com.changlu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.changlu.system.pojo.SchoolGradeModel;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ChangLu
 * @since 2022-03-30
 */
public interface ZfGradeService extends IService<SchoolGradeModel> {

    /**
     * 查询ZfGrade列表
     *
     * @param schoolGradeModel ZfGrade
     * @return ZfGrade集合
     */
    List<SchoolGradeModel> selectZfGradeModelList(SchoolGradeModel schoolGradeModel);

    /**
     * 查询ZfGrade
     *
     * @param gradeId ZfGrade主键
     * @return ZfGrade
     */
    SchoolGradeModel selectZfGradeModelByGradeId(Long gradeId);

    /**
     * 新增ZfGrade
     *
     * @param schoolGradeModel ZfGrade
     * @return 结果
     */
    int insertZfGradeModel(SchoolGradeModel schoolGradeModel);


    /**
     * 修改ZfGrade
     *
     * @param schoolGradeModel ZfGrade
     * @return 结果
     */
    int updateZfGradeModel(SchoolGradeModel schoolGradeModel);

    /**
     * 批量删除ZfGrade
     *
     * @param gradeIds 需要删除的ZfGrade主键集合
     * @return 结果
     */
    int deleteZfGradeModelByGradeIds(Long[] gradeIds);


}
