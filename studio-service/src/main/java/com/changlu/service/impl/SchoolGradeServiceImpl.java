package com.changlu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.changlu.mapper.SchoolGradeMapper;
import com.changlu.service.SchoolGradeService;
import com.changlu.system.pojo.SchoolGradeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ChangLu
 * @since 2022-03-30
 */
@Service
public class SchoolGradeServiceImpl extends ServiceImpl<SchoolGradeMapper, SchoolGradeModel> implements SchoolGradeService {

    @Autowired
    private SchoolGradeMapper gradeMapper;


    /**
     * 查询ZfGrade列表
     *
     * @param schoolGradeModel ZfGrade
     * @return ZfGrade
     */
    @Override
    public List<SchoolGradeModel> selectZfGradeModelList(SchoolGradeModel schoolGradeModel)
    {
        return gradeMapper.selectZfGradeModelList(schoolGradeModel);
    }

    /**
     * 查询ZfGrade
     *
     * @param gradeId ZfGrade主键
     * @return ZfGrade
     */
    @Override
    public SchoolGradeModel selectZfGradeModelByGradeId(Long gradeId)
    {
        return gradeMapper.selectZfGradeModelByGradeId(gradeId);
    }

    /**
     * 新增ZfGrade
     *
     * @param schoolGradeModel ZfGrade
     * @return 结果
     */
    @Override
    public int insertZfGradeModel(SchoolGradeModel schoolGradeModel)
    {
        return gradeMapper.insertZfGradeModel(schoolGradeModel);
    }

    /**
     * 修改ZfGrade
     *
     * @param schoolGradeModel ZfGrade
     * @return 结果
     */
    @Override
    public int updateZfGradeModel(SchoolGradeModel schoolGradeModel)
    {
        return gradeMapper.updateZfGradeModel(schoolGradeModel);
    }

    /**
     * 批量删除ZfGrade
     *
     * @param gradeIds 需要删除的ZfGrade主键
     * @return 结果
     */
    @Override
    public int deleteZfGradeModelByGradeIds(Long[] gradeIds)
    {
        return gradeMapper.deleteZfGradeModelByGradeIds(gradeIds);
    }

}
