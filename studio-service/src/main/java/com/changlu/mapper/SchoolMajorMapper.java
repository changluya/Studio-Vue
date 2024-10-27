package com.changlu.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.changlu.system.pojo.SchoolMajorModel;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ChangLu
 * @since 2022-03-30
 */
public interface SchoolMajorMapper extends BaseMapper<SchoolMajorModel> {

    /**
     * 查询ZfMajor列表
     *
     * @param schoolMajorModel ZfMajor
     * @return ZfMajor集合
     */
    List<SchoolMajorModel> selectZfMajorModelList(SchoolMajorModel schoolMajorModel);;

    /**
     * 查询ZfMajor
     *
     * @param majorId ZfMajor主键
     * @return ZfMajor
     */
    public SchoolMajorModel selectZfMajorModelByMajorId(Long majorId);

    /**
     * 新增ZfMajor
     *
     * @param schoolMajorModel ZfMajor
     * @return 结果
     */
    public int insertZfMajorModel(SchoolMajorModel schoolMajorModel);

    /**
     * 修改ZfMajor
     *
     * @param schoolMajorModel ZfMajor
     * @return 结果
     */
    public int updateZfMajorModel(SchoolMajorModel schoolMajorModel);

    /**
     * 批量删除ZfMajor
     *
     * @param majorIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteZfMajorModelByMajorIds(Long[] majorIds);
}
