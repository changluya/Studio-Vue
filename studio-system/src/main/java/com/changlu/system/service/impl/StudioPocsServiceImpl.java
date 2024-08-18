package com.changlu.system.service.impl;

import com.changlu.system.mapper.StudioPocsMapper;
import com.changlu.system.pojo.StudioPocsModel;
import com.changlu.system.service.IStudioPocsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 成果分类Service业务层处理
 * 
 * @author 长路
 * @date 2024-08-18
 */
@Service
public class StudioPocsServiceImpl implements IStudioPocsService {
    @Autowired
    private StudioPocsMapper studioPocsMapper;

    /**
     * 查询成果分类
     * 
     * @param id 成果分类主键
     * @return 成果分类
     */
    @Override
    public StudioPocsModel selectStudioPocsById(Long id)
    {
        return studioPocsMapper.selectStudioPocsById(id);
    }

    /**
     * 查询成果分类列表
     * 
     * @param studioPocs 成果分类
     * @return 成果分类
     */
    @Override
    public List<StudioPocsModel> selectStudioPocsList(StudioPocsModel studioPocs)
    {
        return studioPocsMapper.selectStudioPocsList(studioPocs);
    }

    /**
     * 新增成果分类
     * 
     * @param studioPocs 成果分类
     * @return 结果
     */
    @Override
    public int insertStudioPocs(StudioPocsModel studioPocs)
    {
        return studioPocsMapper.insertStudioPocs(studioPocs);
    }

    /**
     * 修改成果分类
     * 
     * @param studioPocs 成果分类
     * @return 结果
     */
    @Override
    public int updateStudioPocs(StudioPocsModel studioPocs)
    {
        return studioPocsMapper.updateStudioPocs(studioPocs);
    }

    /**
     * 批量删除成果分类
     * 
     * @param ids 需要删除的成果分类主键
     * @return 结果
     */
    @Override
    public int deleteStudioPocsByIds(Long[] ids)
    {
        return studioPocsMapper.deleteStudioPocsByIds(ids);
    }

    /**
     * 删除成果分类信息
     * 
     * @param id 成果分类主键
     * @return 结果
     */
    @Override
    public int deleteStudioPocsById(Long id)
    {
        return studioPocsMapper.deleteStudioPocsById(id);
    }
}
