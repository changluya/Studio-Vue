package com.changlu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.changlu.vo.race.ResourceVo;
import com.changlu.enums.StudioResourceEnum;
import com.changlu.system.pojo.StudioResourceModel;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ChangLu
 * @since 2022-03-30
 */
public interface StudioResourceService extends IService<StudioResourceModel> {

    /**
     * 插入多条资源记录
     */
    boolean insertResources(StudioResourceEnum resFlag, Long tableId, List<ResourceVo> pics);

    /**
     * 删除指定关联表id的资源记录
     */
    int deleteResources(StudioResourceEnum resFlag, Long[] tableIds);

}
