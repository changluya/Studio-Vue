package com.changlu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.changlu.common.domain.ResponseResult;
import com.changlu.enums.InclusionTypeEnum;
import com.changlu.mapper.StudioCcieMapper;
import com.changlu.security.util.SecurityUtils;
import com.changlu.service.ISysUserService;
import com.changlu.service.StudioCcieService;
import com.changlu.system.pojo.StudioCcieModel;
import com.changlu.system.pojo.SysUser;
import com.changlu.vo.ccie.ShowCcieVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ChangLu
 * @since 2022-03-30
 */
@Service
public class StudioCcieServiceImpl extends ServiceImpl<StudioCcieMapper, StudioCcieModel> implements StudioCcieService {

    @Resource
    private StudioCcieMapper studioCcieMapper;
    @Autowired
    private ISysUserService iSysUserService;


    @Override
    public List<StudioCcieModel> selectCcieList(StudioCcieModel ccieModel) {
        return studioCcieMapper.selectCcieList(ccieModel);
    }

    @Override
    public List<ShowCcieVo> selectShowCcieList(Integer type) {
        // db查询证书列表
        List<StudioCcieModel> studioCcieModels = studioCcieMapper.selectShowCcieList(type);
        if (CollectionUtils.isEmpty(studioCcieModels)) {
            return Collections.emptyList();
        }
        // 获取用户id的map集合
        Set<Long> userIds = studioCcieModels.stream()
                .map(StudioCcieModel::getUserId)
                .collect(Collectors.toSet());
        Map<Long, SysUser> userMap = iSysUserService.selectUserMap(userIds.toArray(new Long[0]));
        // 构建证书vo集合响应
        List<ShowCcieVo> showCcyVos = studioCcieModels.stream().map(studioCcieModel -> {
            ShowCcieVo showCcieVo = new ShowCcieVo();
            BeanUtils.copyProperties(studioCcieModel, showCcieVo);
            showCcieVo.setUserName(userMap.get(studioCcieModel.getUserId()).getRealName());
            return showCcieVo;
        }).collect(Collectors.toList());
        return showCcyVos;
    }

    @Override
    public List<StudioCcieModel> selectOwnCcieList(StudioCcieModel ccieModel) {
        //设置用户id
        ccieModel.setUserId(SecurityUtils.getUserId());
        return studioCcieMapper.selectZfCcieListByUserId(ccieModel);
    }

    @Override
    public StudioCcieModel selectOwnCcieByCcieId(Long ccieId){
        return studioCcieMapper.selectZfCcieByCcieId(ccieId);
    }

    @Override
    public int insertZfCcie(StudioCcieModel zfCcie)
    {
        // 设置创建者
        zfCcie.setUserId(SecurityUtils.getUserId());
        return studioCcieMapper.insertZfCcie(zfCcie);
    }

    /**
     * 修改ZfCcie
     *
     * @param zfCcie ZfCcie
     * @return 结果
     */
    @Override
    public int updateOwnCcie(StudioCcieModel zfCcie) {
        // todo 个人更新逻辑判断处理
        return studioCcieMapper.updateZfCcie(zfCcie);
    }

    @Override
    public int updateCcie(StudioCcieModel zfCcie) {
        return studioCcieMapper.updateZfCcie(zfCcie);
    }

    @Override
    public int deleteOwnCcieByCcieIds(Long[] ccieIds) {
        Long userId = SecurityUtils.getUserId();
        return studioCcieMapper.deleteZfCcieByCcieIds(ccieIds, userId);
    }

    /**
     * 批量删除ZfCcie
     *
     * @param ccieIds 需要删除的ZfCcie主键
     * @return 结果
     */
    @Override
    public int deleteZfCcieByCcieIds(Long[] ccieIds) {
        return studioCcieMapper.deleteZfCcieByCcieIds(ccieIds, null);
    }

    @Override
    public ResponseResult deleteZfCcieByCcieId(Long ccieId) {
        int result = studioCcieMapper.deleteZfCcieByCcieId(ccieId);
        if (result > 0) {
            return ResponseResult.success("删除成功！");
        }
        return ResponseResult.error("删除失败！");
    }


    @Override
    public void updateInclusion(Long id, int behavior) {
        StudioCcieModel updateCcieModel = new StudioCcieModel();
        // 设置证书id
        updateCcieModel.setCcieId(id);
        if (behavior == 1) {
            updateCcieModel.setInclusionFlag(InclusionTypeEnum.APPLY_INCLUSION.getVal());
        }else if (behavior == 2) {
            updateCcieModel.setInclusionFlag(InclusionTypeEnum.NO_INCLUSION.getVal());
        }else if (behavior == 3) {
            updateCcieModel.setInclusionFlag(InclusionTypeEnum.ALREADY_INCLUSION.getVal());
        }
        // 更新证书
        this.updateCcie(updateCcieModel);
    }

}
