package com.changlu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.changlu.mapper.StudioMajorMapper;
import com.changlu.service.ZfMajorService;
import com.changlu.system.pojo.StudioMajorModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class ZfMajorServiceImpl extends ServiceImpl<StudioMajorMapper, StudioMajorModel> implements ZfMajorService {


    @Autowired
    private StudioMajorMapper majorMapper;

    /**
     * 查询ZfMajor列表
     *
     * @param studioMajorModel ZfMajor
     * @return ZfMajor
     */
    @Override
    public List<StudioMajorModel> selectZfMajorModelList(StudioMajorModel studioMajorModel)
    {
        return majorMapper.selectZfMajorModelList(studioMajorModel);
    }

    /**
     * 查询ZfMajor
     *
     * @param majorId ZfMajor主键
     * @return ZfMajor
     */
    @Override
    public StudioMajorModel selectZfMajorModelByMajorId(Long majorId)
    {
        return majorMapper.selectZfMajorModelByMajorId(majorId);
    }

    /**
     * 新增ZfMajor
     *
     * @param studioMajorModel ZfMajor
     * @return 结果
     */
    @Override
    public int insertZfMajorModel(StudioMajorModel studioMajorModel)
    {
        return majorMapper.insertZfMajorModel(studioMajorModel);
    }

    /**
     * 修改ZfMajor
     *
     * @param studioMajorModel ZfMajor
     * @return 结果
     */
    @Override
    public int updateZfMajorModel(StudioMajorModel studioMajorModel)
    {
        return majorMapper.updateZfMajorModel(studioMajorModel);
    }

    /**
     * 批量删除ZfMajor
     *
     * @param majorIds 需要删除的ZfMajor主键
     * @return 结果
     */
    @Override
    public int deleteZfMajorModelByMajorIds(Long[] majorIds)
    {
        return majorMapper.deleteZfMajorModelByMajorIds(majorIds);
    }

}
