package com.changlu.web.controller.open;

import com.changlu.common.domain.ResponseResult;
import com.changlu.system.pojo.StudioPocsModel;
import com.changlu.system.service.IStudioPocsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/open/pocs")
public class OpenPocsController {

    @Autowired
    private IStudioPocsService studioPocsService;

    @GetMapping("/list")
    public ResponseResult showPocs(){
        List<StudioPocsModel> res = studioPocsService.selectStudioPocsList(null);
        return ResponseResult.success(res);
    }

}
