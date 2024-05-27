package com.changlu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.changlu.system.pojo.SchoolMajorModel;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ChangLu
 * @since 2022-03-30
 */
public interface ZfMajorService extends IService<SchoolMajorModel> {

    /**
     * 查询ZfMajor列表
     *
     * @param schoolMajorModel ZfMajor
     * @return ZfMajor集合
     */
    List<SchoolMajorModel> selectZfMajorModelList(SchoolMajorModel schoolMajorModel);

    /**
     * 查询ZfMajor
     *
     * @param majorId ZfMajor主键
     * @return ZfMajor
     */
    SchoolMajorModel selectZfMajorModelByMajorId(Long majorId);

    /**
     * 新增ZfMajor
     *
     * @param schoolMajorModel ZfMajor
     * @return 结果
     */
    int insertZfMajorModel(SchoolMajorModel schoolMajorModel);

    /**
     * 修改ZfMajor
     *
     * @param schoolMajorModel ZfMajor
     * @return 结果
     */
    int updateZfMajorModel(SchoolMajorModel schoolMajorModel);

    /**
     * 批量删除ZfMajor
     *
     * @param majorIds 需要删除的ZfMajor主键集合
     * @return 结果
     */
    int deleteZfMajorModelByMajorIds(Long[] majorIds);
}
