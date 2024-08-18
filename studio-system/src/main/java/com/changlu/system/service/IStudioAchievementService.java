package com.changlu.system.service;

import com.changlu.system.pojo.StudioAchievementModel;
import com.changlu.system.pojo.dto.StudioAchievementDTO;

import java.util.List;

/**
 * 成果Service接口
 * 
 * @author changlu
 * @date 2024-08-18
 */
public interface IStudioAchievementService {

    public StudioAchievementModel selectStudioAchievementById(Long id);

    /**
     * 查询成果列表
     * 
     * @param studioAchievement 成果
     * @return 成果集合
     */
    public List<StudioAchievementDTO> selectStudioAchievementList(StudioAchievementModel studioAchievement);

    /**
     * 新增成果
     * 
     * @param studioAchievement 成果
     * @return 结果
     */
    public int insertStudioAchievement(StudioAchievementModel studioAchievement);

    /**
     * 修改成果
     * 
     * @param studioAchievement 成果
     * @return 结果
     */
    public int updateStudioAchievement(StudioAchievementModel studioAchievement);

    /**
     * 批量删除成果
     * 
     * @param ids 需要删除的成果主键集合
     * @return 结果
     */
    public int deleteStudioAchievementByIds(Long[] ids);

    /**
     * 删除成果信息
     * 
     * @param id 成果主键
     * @return 结果
     */
    public int deleteStudioAchievementById(Long id);
}
