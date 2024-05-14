package com.changlu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.changlu.common.utils.DateUtils;
import com.changlu.mapper.StudioThinkMapper;
import com.changlu.security.util.SecurityUtils;
import com.changlu.service.ZfThinkService;
import com.changlu.system.pojo.StudioThinkModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
public class ZfThinkServiceImpl extends ServiceImpl<StudioThinkMapper, StudioThinkModel> implements ZfThinkService {

    @Resource
    private StudioThinkMapper zfThinkModelMapper;

    /**
     * 查询ZfThink列表
     *
     * @param studioThinkModel ZfThink
     * @return ZfThink
     */
    @Override
    public List<StudioThinkModel> selectZfThinkModelList(StudioThinkModel studioThinkModel)
    {
        return zfThinkModelMapper.selectZfThinkModelList(studioThinkModel);
    }

    /**
     * 根据用户id来查询ZfThink列表
     *
     * @param studioThinkModel ZfThink
     * @return ZfThink
     */
    @Override
    public List<StudioThinkModel> selectZfThinkModelListByUserId(StudioThinkModel studioThinkModel) {
        studioThinkModel.setUserId(SecurityUtils.getUserId());
        return zfThinkModelMapper.selectZfThinkModelListByUserId(studioThinkModel);
    }

    /**
     * 查询ZfThink
     *
     * @param thinkId ZfThink主键
     * @return ZfThink
     */
    @Override
    public StudioThinkModel selectZfThinkModelByThinkId(Long thinkId)
    {
        return zfThinkModelMapper.selectZfThinkModelByThinkId(thinkId);
    }

    /**
     * 新增ZfThink
     *
     * @param studioThinkModel ZfThink
     * @return 结果
     */
    @Override
    public int insertZfThinkModel(StudioThinkModel studioThinkModel)
    {
        studioThinkModel.setCreateTime(DateUtils.getNowDate());
        //设置用户id
        studioThinkModel.setUserId(SecurityUtils.getUserId());
        return zfThinkModelMapper.insertZfThinkModel(studioThinkModel);
    }

    /**
     * 修改ZfThink
     *
     * @param studioThinkModel ZfThink
     * @return 结果
     */
    @Override
    public int updateZfThinkModel(StudioThinkModel studioThinkModel)
    {
        studioThinkModel.setUpdateTime(DateUtils.getNowDate());
        return zfThinkModelMapper.updateZfThinkModel(studioThinkModel);
    }

    /**
     * 批量删除ZfThink
     *
     * @param thinkIds 需要删除的ZfThink主键
     * @return 结果
     */
    @Override
    public int deleteZfThinkModelByThinkIds(Long[] thinkIds)
    {
        return zfThinkModelMapper.deleteZfThinkModelByThinkIds(thinkIds);
    }

}
