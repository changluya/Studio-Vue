package com.changlu.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.changlu.system.pojo.StudioMajorModel;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ChangLu
 * @since 2022-03-30
 */
public interface StudioMajorMapper extends BaseMapper<StudioMajorModel> {

    /**
     * 查询ZfMajor列表
     *
     * @param studioMajorModel ZfMajor
     * @return ZfMajor集合
     */
    List<StudioMajorModel> selectZfMajorModelList(StudioMajorModel studioMajorModel);;

    /**
     * 查询ZfMajor
     *
     * @param majorId ZfMajor主键
     * @return ZfMajor
     */
    public StudioMajorModel selectZfMajorModelByMajorId(Long majorId);

    /**
     * 新增ZfMajor
     *
     * @param studioMajorModel ZfMajor
     * @return 结果
     */
    public int insertZfMajorModel(StudioMajorModel studioMajorModel);

    /**
     * 修改ZfMajor
     *
     * @param studioMajorModel ZfMajor
     * @return 结果
     */
    public int updateZfMajorModel(StudioMajorModel studioMajorModel);

    /**
     * 批量删除ZfMajor
     *
     * @param majorIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteZfMajorModelByMajorIds(Long[] majorIds);
}
