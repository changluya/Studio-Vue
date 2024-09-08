package com.changlu.service;

import com.changlu.system.pojo.StudioAchievementModel;
import com.changlu.system.pojo.dto.StudioAchievementDTO;
import com.changlu.vo.achievement.ShowAchievement;
import com.changlu.vo.achievement.req.AchievementReqVo;

import java.util.List;

/**
 * 成果Service接口
 * 
 * @author changlu
 * @date 2024-08-18
 */
public interface IStudioAchievementService {

    /**
     * 查询我的成果
     *
     * @param id 根据成果id查询
     * @return 成果
     */
    public StudioAchievementModel selectOwnStudioAchievementById(Long id);

    /**
     * 查询成果
     *
     * @param id 成果主键
     * @return 成果
     */
    public StudioAchievementModel selectStudioAchievementById(Long id);

     /**
     * 对外展示的查询成果列表
     * 核心：筛选已收录，根据时间倒序
     * where inclusion_flag = 3
     * order by end_time desc
     */
    public List<ShowAchievement> showAchievements(AchievementReqVo achievementReqVo);

    /**
     * 查询我的成果列表
     *
     * @param studioAchievement 成果
     * @return 成果集合
     */
    public List<StudioAchievementDTO> selectOwnStudioAchievementList(StudioAchievementModel studioAchievement);

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
    public void insertStudioAchievement(StudioAchievementModel studioAchievement);

    /**
     * 修改我的成果信息
     *
     * @param studioAchievement 成果
     * @return 结果
     */
    public void updateOwnStudioAchievement(StudioAchievementModel studioAchievement);

    /**
     * 修改成果
     * 
     * @param studioAchievement 成果
     * @return 结果
     */
    public void updateStudioAchievement(StudioAchievementModel studioAchievement);

    /**
     * 修改收录状态
     * @param id 成果id
     * @param behavior 不同行为情况
     * @return
     */
    public void updateInclusion(Long id, int behavior);

    /**
     * 批量删除我的成果
     *
     * @param ids 需要删除的成果主键集合
     * @return 结果
     */
    public void deleteOwnStudioAchievementByIds(Long[] ids);

    /**
     * 批量删除成果
     * 
     * @param ids 需要删除的成果主键集合
     * @return 结果
     */
    public void deleteStudioAchievementByIds(Long[] ids);

    /**
     * 统计已收录的成果
     * @return 已收录的成果数量
     */
    public int countAlreadyInclusionAchievement();
}
