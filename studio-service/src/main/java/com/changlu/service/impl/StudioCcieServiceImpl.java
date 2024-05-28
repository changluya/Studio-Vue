package com.changlu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.changlu.common.domain.ResponseResult;
import com.changlu.mapper.StudioCcieMapper;
import com.changlu.security.util.SecurityUtils;
import com.changlu.service.StudioCcieService;
import com.changlu.system.pojo.StudioCcieModel;
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
public class StudioCcieServiceImpl extends ServiceImpl<StudioCcieMapper, StudioCcieModel> implements StudioCcieService {

    @Resource
    private StudioCcieMapper studioCcieMapper;

    @Override
    public List<StudioCcieModel> selectZfCcieList(StudioCcieModel ccieModel) {
        return studioCcieMapper.selectZfCcieList(ccieModel);
    }

    @Override
    public List<StudioCcieModel> selectZfCcieListByUserId(StudioCcieModel ccieModel) {
        //设置用户id
        ccieModel.setUserId(SecurityUtils.getUserId());
        return studioCcieMapper.selectZfCcieListByUserId(ccieModel);
    }

    @Override
    public StudioCcieModel selectZfCcieByCcieId(Long ccieId){
        return studioCcieMapper.selectZfCcieByCcieId(ccieId);
    }

    @Override
    public int insertZfCcie(StudioCcieModel zfCcie)
    {
        zfCcie.setUserId(SecurityUtils.getUserId());
        return studioCcieMapper.insertZfCcie(zfCcie);
    }

    /**
     * 修改ZfCcie
     *
     * @param zfCcie ZfCcie
     * @return 结果
     */
    @Override
    public int updateZfCcie(StudioCcieModel zfCcie)
    {
        return studioCcieMapper.updateZfCcie(zfCcie);
    }

    /**
     * 批量删除ZfCcie
     *
     * @param ccieIds 需要删除的ZfCcie主键
     * @return 结果
     */
    @Override
    public int deleteZfCcieByCcieIds(Long[] ccieIds)
    {
        return studioCcieMapper.deleteZfCcieByCcieIds(ccieIds);
    }

    @Override
    public ResponseResult deleteZfCcieByCcieId(Long ccieId) {
        int result = studioCcieMapper.deleteZfCcieByCcieId(ccieId);
        if (result > 0) {
            return ResponseResult.success("删除成功！");
        }
        return ResponseResult.error("删除失败！");
    }

}
