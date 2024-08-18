package com.changlu.system.service;

import com.changlu.system.pojo.StudioPocsModel;

import java.util.List;

/**
 * 成果分类Service接口
 * 
 * @author 长路
 * @date 2024-08-18
 */
public interface IStudioPocsService {
    /**
     * 查询成果分类
     * 
     * @param id 成果分类主键
     * @return 成果分类
     */
    public StudioPocsModel selectStudioPocsById(Long id);

    /**
     * 查询成果分类列表
     * 
     * @param studioPocs 成果分类
     * @return 成果分类集合
     */
    public List<StudioPocsModel> selectStudioPocsList(StudioPocsModel studioPocs);

    /**
     * 新增成果分类
     * 
     * @param studioPocs 成果分类
     * @return 结果
     */
    public int insertStudioPocs(StudioPocsModel studioPocs);

    /**
     * 修改成果分类
     * 
     * @param studioPocs 成果分类
     * @return 结果
     */
    public int updateStudioPocs(StudioPocsModel studioPocs);

    /**
     * 批量删除成果分类
     * 
     * @param ids 需要删除的成果分类主键集合
     * @return 结果
     */
    public int deleteStudioPocsByIds(Long[] ids);

    /**
     * 删除成果分类信息
     * 
     * @param id 成果分类主键
     * @return 结果
     */
    public int deleteStudioPocsById(Long id);
}
