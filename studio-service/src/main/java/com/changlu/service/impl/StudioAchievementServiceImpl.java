package com.changlu.service.impl;

import com.changlu.common.utils.StringUtils;
import com.changlu.enums.InclusionTypeEnum;
import com.changlu.security.util.SecurityUtils;
import com.changlu.service.IStudioAchievementService;
import com.changlu.system.mapper.StudioAchievementMapper;
import com.changlu.system.mapper.SysUserMapper;
import com.changlu.system.pojo.StudioAchievementModel;
import com.changlu.system.pojo.SysUser;
import com.changlu.system.pojo.dto.StudioAchievementDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

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
        // 设置创建人
        studioAchievement.setCreateUserId(SecurityUtils.getUserId());
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
     * 修改收录状态
     * @param id 成果id
     * @param behavior 不同行为情况
     * @return
     */
    @Override
    public int updateInclusion(Long id, int behavior) {
        StudioAchievementModel updateStudioAchievementModel = new StudioAchievementModel();
        // 设置成果id
        updateStudioAchievementModel.setId(id);
        // 情况1、behavior 为 1情况，【申请收录操作】修改状态为申请收录
        if (behavior == 1) {
            updateStudioAchievementModel.setInclusionFlag(InclusionTypeEnum.APPLY_INCLUSION.getVal());
        }else if (behavior == 2) { // 情况2、behavior 为 2情况，【退回收录操作】修改状态为退出收录（未收录状态）
            updateStudioAchievementModel.setInclusionFlag(InclusionTypeEnum.NO_INCLUSION.getVal());
        }
        // 更新成果
        return this.updateStudioAchievement(updateStudioAchievementModel);
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
