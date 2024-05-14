package com.changlu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.changlu.mapper.StudioGradeMapper;
import com.changlu.service.ZfGradeService;
import com.changlu.system.pojo.StudioGradeModel;
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
public class ZfGradeServiceImpl extends ServiceImpl<StudioGradeMapper, StudioGradeModel> implements ZfGradeService {

    @Autowired
    private StudioGradeMapper gradeMapper;


    /**
     * 查询ZfGrade列表
     *
     * @param studioGradeModel ZfGrade
     * @return ZfGrade
     */
    @Override
    public List<StudioGradeModel> selectZfGradeModelList(StudioGradeModel studioGradeModel)
    {
        return gradeMapper.selectZfGradeModelList(studioGradeModel);
    }

    /**
     * 查询ZfGrade
     *
     * @param gradeId ZfGrade主键
     * @return ZfGrade
     */
    @Override
    public StudioGradeModel selectZfGradeModelByGradeId(Long gradeId)
    {
        return gradeMapper.selectZfGradeModelByGradeId(gradeId);
    }

    /**
     * 新增ZfGrade
     *
     * @param studioGradeModel ZfGrade
     * @return 结果
     */
    @Override
    public int insertZfGradeModel(StudioGradeModel studioGradeModel)
    {
        return gradeMapper.insertZfGradeModel(studioGradeModel);
    }

    /**
     * 修改ZfGrade
     *
     * @param studioGradeModel ZfGrade
     * @return 结果
     */
    @Override
    public int updateZfGradeModel(StudioGradeModel studioGradeModel)
    {
        return gradeMapper.updateZfGradeModel(studioGradeModel);
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
