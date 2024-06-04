package com.changlu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.changlu.mapper.StudioResourceMapper;
import com.changlu.service.StudioResourceService;
import com.changlu.vo.race.ResourceVo;
import com.changlu.enums.StudioResourceEnum;
import com.changlu.system.pojo.StudioResourceModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
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
public class StudioResourceServiceImpl extends ServiceImpl<StudioResourceMapper, StudioResourceModel> implements StudioResourceService {

    @Resource
    private StudioResourceMapper studioResourceMapper;

    /**
     * 插入多条资源
     * @param resFlag 资源的标识（指定哪张表，目前关联的表有竞赛表其标识为1）
     * @param tableId 关联对应的表记录id
     * @param pics 要保存的图片资源
     * @return
     */
    @Override
    public boolean insertResources(StudioResourceEnum resFlag, Long tableId, List<ResourceVo> pics) {
        //1、批量进行创建对象
        List<StudioResourceModel> resourceModels = new ArrayList<>(pics.size());
        pics.stream().forEach((pic)->{
            StudioResourceModel studioResourceModel = new StudioResourceModel();
            BeanUtils.copyProperties(pic, studioResourceModel);//拷贝资源原名、URL
            studioResourceModel.setResFlag(String.valueOf(resFlag.value()));//设置资源标识
            studioResourceModel.setTableId(tableId);//设置关联表的id（这里是竞赛记录id）
            studioResourceModel.setCreateTime(new Date());//设置创建时间
            resourceModels.add(studioResourceModel);
        });
        //2、批量更新
        return this.saveBatch(resourceModels);
    }

    @Override
    public int deleteResources(StudioResourceEnum resFlag, Long[] tableIds) {
        return studioResourceMapper.deleteResources(String.valueOf(resFlag.value()),tableIds);
    }
}
