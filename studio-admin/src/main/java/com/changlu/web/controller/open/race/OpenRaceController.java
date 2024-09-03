package com.changlu.web.controller.open.race;

import com.changlu.common.domain.ResponseResult;
import com.changlu.service.StudioRaceService;
import com.changlu.vo.race.ShowRaceVo;
import com.changlu.web.controller.open.race.vo.OpenRaceReqVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/open/race")
public class OpenRaceController {

    @Autowired
    private StudioRaceService studioRaceService;

    @GetMapping("/list")
    public ResponseResult list(OpenRaceReqVo openRaceReqVo){
        List<ShowRaceVo> showRaceVos = studioRaceService.selectShowRaceList();
        return ResponseResult.success(showRaceVos);
    }


}
