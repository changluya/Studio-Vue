package com.changlu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.changlu.system.pojo.StudioMajorModel;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ChangLu
 * @since 2022-03-30
 */
public interface ZfMajorService extends IService<StudioMajorModel> {

    /**
     * 查询ZfMajor列表
     *
     * @param studioMajorModel ZfMajor
     * @return ZfMajor集合
     */
    List<StudioMajorModel> selectZfMajorModelList(StudioMajorModel studioMajorModel);

    /**
     * 查询ZfMajor
     *
     * @param majorId ZfMajor主键
     * @return ZfMajor
     */
    StudioMajorModel selectZfMajorModelByMajorId(Long majorId);

    /**
     * 新增ZfMajor
     *
     * @param studioMajorModel ZfMajor
     * @return 结果
     */
    int insertZfMajorModel(StudioMajorModel studioMajorModel);

    /**
     * 修改ZfMajor
     *
     * @param studioMajorModel ZfMajor
     * @return 结果
     */
    int updateZfMajorModel(StudioMajorModel studioMajorModel);

    /**
     * 批量删除ZfMajor
     *
     * @param majorIds 需要删除的ZfMajor主键集合
     * @return 结果
     */
    int deleteZfMajorModelByMajorIds(Long[] majorIds);
}
