package com.changlu.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.changlu.system.pojo.StudioThinkModel;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ChangLu
 * @since 2022-03-30
 */
public interface StudioThinkMapper extends BaseMapper<StudioThinkModel> {

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
     * @param thinkIds 需要删除的数据主键集合
     * @return 结果
     */
    int deleteZfThinkModelByThinkIds(Long[] thinkIds);


    /**
     * 根据用户id删除个人心得记录
     *
     * @param thinkId ZfThink主键
     * @return 结果
     */
    int deleteZfThinkModelByUserId(Long userId);
}
