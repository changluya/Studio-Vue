package com.changlu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.changlu.system.pojo.StudioThinkModel;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ChangLu
 * @since 2022-03-30
 */
public interface StudioThinkService extends IService<StudioThinkModel> {

    /**
     * 查询ZfThink列表
     *
     * @param studioThinkModel ZfThink
     * @return ZfThink集合
     */
     List<StudioThinkModel> selectZfThinkModelList(StudioThinkModel studioThinkModel);

    /**
     * 根据用户id查询ZfThink列表
     *
     * @param studioThinkModel ZfThink
     * @return ZfThink集合
     */
    List<StudioThinkModel> selectZfThinkModelListByUserId(StudioThinkModel studioThinkModel);

    /**
     * 查询ZfThink
     *
     * @param thinkId ZfThink主键
     * @return ZfThink
     */
    StudioThinkModel selectZfThinkModelByThinkId(Long thinkId);

    /**
     * 新增ZfThink
     *
     * @param studioThinkModel ZfThink
     * @return 结果
     */
    int insertZfThinkModel(StudioThinkModel studioThinkModel);

    /**
     * 修改ZfThink
     *
     * @param studioThinkModel ZfThink
     * @return 结果
     */
    int updateZfThinkModel(StudioThinkModel studioThinkModel);

    /**
     * 批量删除ZfThink
     *
     * @param thinkIds 需要删除的ZfThink主键集合
     * @return 结果
     */
    int deleteZfThinkModelByThinkIds(Long[] thinkIds);

}
