package com.changlu.system.mapper;

import com.changlu.system.pojo.StudioAchievementModel;
import com.changlu.system.pojo.dto.StudioAchievementDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 成果Mapper接口
 * 
 * @author changlu
 * @date 2024-08-18
 */
public interface StudioAchievementMapper {
    /**
     * 查询成果
     * 
     * @param id 成果主键
     * @return 成果
     */
    public StudioAchievementModel selectStudioAchievementById(Long id);

    /**
     * 查询成果列表
     * 
     * @param studioAchievementModel 成果
     * @return 成果集合
     */
    public List<StudioAchievementDTO> selectStudioAchievementList(StudioAchievementModel studioAchievementModel);

    /**
     * 新增成果
     * 
     * @param studioAchievementModel 成果
     * @return 结果
     */
    public int insertStudioAchievement(StudioAchievementModel studioAchievementModel);

    /**
     * 修改成果
     * 
     * @param studioAchievementModel 成果
     * @return 结果
     */
    public int updateStudioAchievement(StudioAchievementModel studioAchievementModel);

    /**
     * 删除成果
     * 
     * @param id 成果主键
     * @return 结果
     */
    public int deleteStudioAchievementById(Long id);

    /**
     * 批量删除成果
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStudioAchievementByIds(@Param("ids") Long[] ids,@Param("createUserId") Long createUserId);
}
