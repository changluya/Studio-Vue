package com.changlu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.changlu.common.exception.ServiceException;
import com.changlu.common.utils.StringUtils;
import com.changlu.enums.InclusionTypeEnum;
import com.changlu.security.util.SecurityUtils;
import com.changlu.service.IStudioAchievementService;
import com.changlu.system.mapper.StudioAchievementMapper;
import com.changlu.system.mapper.SysUserMapper;
import com.changlu.system.pojo.StudioAchievementModel;
import com.changlu.system.pojo.SysUser;
import com.changlu.system.pojo.dto.StudioAchievementDTO;
import com.changlu.vo.achievement.ShowAchievement;
import com.changlu.vo.achievement.req.AchievementReqVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.compiler.ast.Variable;
import org.springframework.beans.BeanUtils;
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
@Slf4j
public class StudioAchievementServiceImpl implements IStudioAchievementService
{
    @Autowired
    private StudioAchievementMapper studioAchievementMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    /**
     * 我的接口检查：检查是否是个人创建的成果，是个人创建的才可查询、编辑、删除
     * @param id 成果id
     * @return 如果不是我的成果返回true，如果是我的成果返回false
     */
    public boolean checkNotOwnAchievement(Long id) {
        if (id == null) {
            log.error("传递空的成果id，拦截！");
            return false;
        }
        // 走db根据成果id查询成果
        StudioAchievementModel dbAchievement = studioAchievementMapper.selectStudioAchievementById(id);
        // 校验是否是自己的成果
        if (dbAchievement != null && !dbAchievement.getCreateUserId().equals(SecurityUtils.getUserId())) {
            return true;
        }
        return false;
    }


    @Override
    public StudioAchievementModel selectOwnStudioAchievementById(Long id) {
        if (checkNotOwnAchievement(id)) {
            throw new ServiceException("请勿查询非个人的成果信息！");
        }
        return selectStudioAchievementById(id);
    }


    @Override
    public StudioAchievementModel selectStudioAchievementById(Long id)
    {
        return studioAchievementMapper.selectStudioAchievementById(id);
    }

    @Override
    public List<ShowAchievement> showAchievements(AchievementReqVo achievementReqVo) {
        // 查询出来基础成果列表
        StudioAchievementModel query = new StudioAchievementModel();
        // 查询条件1：已收录
        query.setInclusionFlag(InclusionTypeEnum.ALREADY_INCLUSION.getVal());
        // 查询条件2：设置搜索年份
        HashMap<String, Object> params = new HashMap<>();
        params.put("searchYear", achievementReqVo.getSearchYear());
        query.setParams(params);
        // 查询条件3：指定分类
        query.setPocsId(achievementReqVo.getPocsId());
        // 查询出检索过后的成果集合
        List<StudioAchievementDTO> achievementDTOS = selectStudioAchievementList(query);
        // 封装处理结果集
        List<ShowAchievement> res = achievementDTOS.stream()
                .map((o) -> { // 映射对象
                    ShowAchievement showAchievement = new ShowAchievement();
                    BeanUtils.copyProperties(o, showAchievement);
                    showAchievement.build();
                    return showAchievement;
                })
                .sorted((o1, o2) -> { // 根据结束时间排序处理
                    if (o1.getEndTime() != null && o2.getEndTime() != null) {
                        return o2.getEndTime().compareTo(o1.getEndTime());
                    }
                    return 0;
                }).collect(Collectors.toList());
        return res;
    }

    @Override
    public List<StudioAchievementDTO> selectOwnStudioAchievementList(StudioAchievementModel studioAchievement) {
        // 设置创建人为用户本身自己（检索个人创建的成果列表）
        studioAchievement.setCreateUserId(SecurityUtils.getUserId());
        return selectStudioAchievementList(studioAchievement);
    }

    @Override
    public List<StudioAchievementDTO> selectStudioAchievementList(StudioAchievementModel studioAchievement)
    {
        List<StudioAchievementDTO> studioAchievementDTOS = studioAchievementMapper.selectStudioAchievementList(studioAchievement);
        if (studioAchievementDTOS == null || studioAchievementDTOS.isEmpty()) {
            return studioAchievementDTOS;
        }
        // 1. 收集所有要查询的参与者用户id【partUserIds】，并去除重复
        Set<Long> userIdsSet = studioAchievementDTOS.stream()
                .filter(achievementDTO -> StringUtils.isNotEmpty(achievementDTO.getPartUserIds()))
                .map(achievement -> achievement.getPartUserIds().split(","))
                .flatMap(Arrays::stream)
                .map(Long::parseLong)
                .collect(Collectors.toSet());

        // 收集创建者用户id【createUserId】
        studioAchievementDTOS.forEach(achievementDTO -> {
            if (achievementDTO.getCreateUserId() != null) {
                userIdsSet.add(achievementDTO.getCreateUserId());
            }
        });

        // 2. 查询用户信息，并构建用户映射
        if (!userIdsSet.isEmpty()) {
            List<SysUser> users = sysUserMapper.selectUserByIds(userIdsSet.toArray(new Long[0]));
            Map<Long, String> userMap = users.stream()
                    .collect(Collectors.toMap(SysUser::getUserId, SysUser::getRealName));
            // 3. 构建关联信息
            studioAchievementDTOS.forEach(achievementDTO -> {
                Long createUserId = achievementDTO.getCreateUserId();
                String partUserIds = achievementDTO.getPartUserIds();
                // 创建成果者姓名
                achievementDTO.setCreateUserName(userMap.get(createUserId));
                // 参与者姓名构建
                // 参与者添加创建者
                List<String> participantIds = new ArrayList<>();
                participantIds.add(String.valueOf(createUserId));
                if(StringUtils.isNotEmpty(partUserIds)) {
                    participantIds.addAll(Arrays.asList(partUserIds.split(",")));
                }
                // 匹配获取所有参与者姓名
                String partUserNames = participantIds.stream()
                        .mapToLong(Long::parseLong)
                        .mapToObj(userMap::get) // 这里可能会有null值，如果用户ID不存在于userMap中
                        .filter(Objects::nonNull) // 过滤掉null值
                        .collect(Collectors.joining(", "));
                achievementDTO.setPartUserNames(partUserNames);
            });
        }
        return studioAchievementDTOS;
    }

    @Override
    public void insertStudioAchievement(StudioAchievementModel studioAchievement)
    {
        // 设置创建人
        studioAchievement.setCreateUserId(SecurityUtils.getUserId());
        studioAchievementMapper.insertStudioAchievement(studioAchievement);
    }

    @Override
    public void updateOwnStudioAchievement(StudioAchievementModel studioAchievement) {
        if (checkNotOwnAchievement(studioAchievement.getId())) {
            throw new ServiceException("请勿编辑非个人的成果信息！");
        }
        updateStudioAchievement(studioAchievement);
    }

    /**
     * 修改成果
     * 
     * @param studioAchievement 成果
     * @return 结果
     */
    @Override
    public void updateStudioAchievement(StudioAchievementModel studioAchievement)
    {
        studioAchievementMapper.updateStudioAchievement(studioAchievement);
    }

    /**
     * 修改收录状态
     * @param id 成果id
     * @param behavior 不同行为情况
     *        情况1、behavior 为 1情况，【申请收录操作】修改状态为申请收录
     *        情况2、behavior 为 2情况，【退回收录操作】修改状态为退出收录（未收录状态）
     *        情况3、behavior 为 3情况，【审核通过收录操作】修改状态为通过收录（已收录状态）
     * @return
     */
    @Override
    public void updateInclusion(Long id, int behavior) {
        StudioAchievementModel updateStudioAchievementModel = new StudioAchievementModel();
        // 设置成果id
        updateStudioAchievementModel.setId(id);
        if (behavior == 1) {
            updateStudioAchievementModel.setInclusionFlag(InclusionTypeEnum.APPLY_INCLUSION.getVal());
        }else if (behavior == 2) {
            updateStudioAchievementModel.setInclusionFlag(InclusionTypeEnum.NO_INCLUSION.getVal());
        }else if (behavior == 3) {
            updateStudioAchievementModel.setInclusionFlag(InclusionTypeEnum.ALREADY_INCLUSION.getVal());
        }
        // 更新成果
        this.updateStudioAchievement(updateStudioAchievementModel);
    }

    @Override
    public void deleteOwnStudioAchievementByIds(Long[] ids) {
        Long createUserId = SecurityUtils.getUserId();
        studioAchievementMapper.deleteStudioAchievementByIds(ids, createUserId);
    }


    /**
     * 批量删除成果
     * 
     * @param ids 需要删除的成果主键
     * @return 结果
     */
    @Override
    public void deleteStudioAchievementByIds(Long[] ids)
    {
        studioAchievementMapper.deleteStudioAchievementByIds(ids, null);
    }

    @Override
    public int countAlreadyInclusionAchievement() {
        LambdaQueryWrapper<StudioAchievementModel> queryAchievement = new LambdaQueryWrapper<StudioAchievementModel>()
                .eq(StudioAchievementModel::getInclusionFlag, InclusionTypeEnum.ALREADY_INCLUSION.getVal());
        int achievementsNum = this.studioAchievementMapper.selectCount(queryAchievement);
        return achievementsNum;
    }
}
