package com.changlu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.changlu.mapper.SchoolMajorMapper;
import com.changlu.service.ZfMajorService;
import com.changlu.system.pojo.SchoolMajorModel;
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
public class ZfMajorServiceImpl extends ServiceImpl<SchoolMajorMapper, SchoolMajorModel> implements ZfMajorService {


    @Autowired
    private SchoolMajorMapper majorMapper;

    /**
     * 查询ZfMajor列表
     *
     * @param schoolMajorModel ZfMajor
     * @return ZfMajor
     */
    @Override
    public List<SchoolMajorModel> selectZfMajorModelList(SchoolMajorModel schoolMajorModel)
    {
        return majorMapper.selectZfMajorModelList(schoolMajorModel);
    }

    /**
     * 查询ZfMajor
     *
     * @param majorId ZfMajor主键
     * @return ZfMajor
     */
    @Override
    public SchoolMajorModel selectZfMajorModelByMajorId(Long majorId)
    {
        return majorMapper.selectZfMajorModelByMajorId(majorId);
    }

    /**
     * 新增ZfMajor
     *
     * @param schoolMajorModel ZfMajor
     * @return 结果
     */
    @Override
    public int insertZfMajorModel(SchoolMajorModel schoolMajorModel)
    {
        return majorMapper.insertZfMajorModel(schoolMajorModel);
    }

    /**
     * 修改ZfMajor
     *
     * @param schoolMajorModel ZfMajor
     * @return 结果
     */
    @Override
    public int updateZfMajorModel(SchoolMajorModel schoolMajorModel)
    {
        return majorMapper.updateZfMajorModel(schoolMajorModel);
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
