package com.changlu.service.impl;

import com.changlu.mapper.StudioMCcieMapper;
import com.changlu.service.StudioManageCcieService;
import com.changlu.vo.manage.MCcieVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName ZfManageCcieServiceImpl
 * @Author ChangLu
 * @Date 4/7/2022 2:38 PM
 * @Description 管理证书业务实现类
 */
@Service
public class StudioManageCcieServiceImpl implements StudioManageCcieService {

    @Resource
    private StudioMCcieMapper studioMCcieMapper;

    @Override
    public List<MCcieVo> selectZfCcieList(MCcieVo ccieVo) {
        return studioMCcieMapper.selectZfCcieModelList(ccieVo);
    }
}
