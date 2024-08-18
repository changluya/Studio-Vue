package com.changlu.system.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.changlu.common.utils.StringUtils;
import com.changlu.system.mapper.StudioAchievementMapper;
import com.changlu.system.mapper.SysUserMapper;
import com.changlu.system.pojo.StudioAchievementModel;
import com.changlu.system.pojo.SysUser;
import com.changlu.system.pojo.dto.StudioAchievementDTO;
import com.changlu.system.service.IStudioAchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 成果Service业务层处理
 * 
 * @author changlu
 * @date 2024-08-18
 */
@Service
public class StudioAchievementServiceImpl implements IStudioAchievementService
{
    @Autowired
    private StudioAchievementMapper studioAchievementMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    /**
     * 查询成果
     * 
     * @param id 成果主键
     * @return 成果
     */
    @Override
    public StudioAchievementModel selectStudioAchievementById(Long id)
    {
        return studioAchievementMapper.selectStudioAchievementById(id);
    }

    /**
     * 查询成果列表
     * 
     * @param studioAchievement 成果
     * @return 成果
     */
    @Override
    public List<StudioAchievementDTO> selectStudioAchievementList(StudioAchievementModel studioAchievement)
    {
        List<StudioAchievementDTO> studioAchievementDTOS = studioAchievementMapper.selectStudioAchievementList(studioAchievement);
        if (studioAchievementDTOS == null || studioAchievementDTOS.isEmpty()) {
            return studioAchievementDTOS;
        }
        // 1. 收集所有要查询的用户id，并去除重复
        Set<Long> userIdsSet = studioAchievementDTOS.stream()
                .filter(achievementDTO -> StringUtils.isNotEmpty(achievementDTO.getPartUserIds()))
                .map(achievement -> achievement.getPartUserIds().split(","))
                .flatMap(Arrays::stream)
                .map(Long::parseLong)
                .collect(Collectors.toSet());

        // 2. 查询用户信息，并构建用户映射
        if (!userIdsSet.isEmpty()) {
            List<SysUser> users = sysUserMapper.selectUserByIds(userIdsSet.toArray(new Long[0]));
            Map<Long, String> userMap = users.stream()
                    .collect(Collectors.toMap(SysUser::getUserId, SysUser::getRealName));
            // 3. 构建真实参与者姓名信息
            studioAchievementDTOS.forEach(achievementDTO -> {
                String partUserIds = achievementDTO.getPartUserIds();
                if (StringUtils.isNotEmpty(partUserIds)) {
                    String[] uids = partUserIds.split(","); // 以逗号分隔，忽略空白
                    String partUserNames = Arrays.stream(uids)
                            .mapToLong(Long::parseLong)
                            .mapToObj(userMap::get) // 这里可能会有null值，如果用户ID不存在于userMap中
                            .filter(Objects::nonNull) // 过滤掉null值
                            .collect(Collectors.joining(", "));
                    achievementDTO.setPartUserNames(partUserNames);
                }
            });
        }
        return studioAchievementDTOS;
    }

    /**
     * 新增成果
     * 
     * @param studioAchievement 成果
     * @return 结果
     */
    @Override
    public int insertStudioAchievement(StudioAchievementModel studioAchievement)
    {
        return studioAchievementMapper.insertStudioAchievement(studioAchievement);
    }

    /**
     * 修改成果
     * 
     * @param studioAchievement 成果
     * @return 结果
     */
    @Override
    public int updateStudioAchievement(StudioAchievementModel studioAchievement)
    {
        return studioAchievementMapper.updateStudioAchievement(studioAchievement);
    }

    /**
     * 批量删除成果
     * 
     * @param ids 需要删除的成果主键
     * @return 结果
     */
    @Override
    public int deleteStudioAchievementByIds(Long[] ids)
    {
        return studioAchievementMapper.deleteStudioAchievementByIds(ids);
    }

    /**
     * 删除成果信息
     * 
     * @param id 成果主键
     * @return 结果
     */
    @Override
    public int deleteStudioAchievementById(Long id)
    {
        return studioAchievementMapper.deleteStudioAchievementById(id);
    }
}
