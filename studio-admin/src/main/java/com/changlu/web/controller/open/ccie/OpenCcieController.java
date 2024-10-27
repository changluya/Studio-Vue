package com.changlu.web.controller.open.ccie;

import com.changlu.common.domain.ResponseResult;
import com.changlu.service.StudioCcieService;
import com.changlu.vo.ccie.ShowCcieVo;
import com.changlu.vo.ccie.vo.CcieReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/open/ccie")
public class OpenCcieController {

    @Autowired
    private StudioCcieService studioCcieService;

    @GetMapping("/list")
    public ResponseResult list(CcieReqVo ccieReqVo){
        List<ShowCcieVo> showCcieVos = studioCcieService.selectShowCcieList(ccieReqVo);
        return ResponseResult.success(showCcieVos);
    }

}
