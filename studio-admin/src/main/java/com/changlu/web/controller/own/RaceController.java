package com.changlu.web.controller.own;

import com.changlu.common.domain.ResponseResult;
import com.changlu.mapper.StudioMUserMapper;
import com.changlu.service.StudioRaceService;
import com.changlu.web.controller.BaseController;
import com.changlu.vo.race.RaceVo;
import com.changlu.system.pojo.StudioRaceModel;
import com.changlu.common.utils.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName OwnRaceController
 * @Author ChangLu
 * @Date 4/4/2022 2:16 PM
 * @Description 个人竞赛控制器
 */
@RestController
@RequestMapping("/api/own/race")
public class RaceController extends BaseController {

    @Autowired
    private StudioRaceService raceService;

    @Resource
    private StudioMUserMapper studioMUserMapper;

    /**
     * 查询个人竞赛列表
     */
    @GetMapping("/list")
    @PreAuthorize("@ss.hasPerm('own:race:list')")
    public TableDataInfo list(StudioRaceModel studioRaceModel)
    {
        startPage();
        List<RaceVo> list = raceService.selectZfRaceModelListByUserId(studioRaceModel);
        return getDataTable(list);
    }

    /**
     * 根据竞赛id来获取详细的竞赛内容
     */
    @GetMapping(value = "/{raceId}")
    @PreAuthorize("@ss.hasPerm('own:race:query')")
    public ResponseResult getInfo(@PathVariable("raceId") Long raceId)
    {
        return ResponseResult.success(raceService.selectZfRaceModelByRaceId(raceId));
    }

    /**
     * 根据竞赛id来查询出所有的参赛成员
     */
    @GetMapping("/members/{raceId}")
    @PreAuthorize("@ss.hasPerm('own:race:members')")
    public ResponseResult getRaceMembers(@PathVariable("raceId") Long raceId){
        return ResponseResult.success(studioMUserMapper.selectRaceUsersByRaceId(raceId));
    }

    /**
     * 新增ZfRace
     */
    @PostMapping
    @PreAuthorize("@ss.hasPerm('own:race:add')")
    public ResponseResult add(@RequestBody RaceVo raceVo)
    {
        return ResponseResult.toResponse(raceService.insertZfRaceModel(raceVo));
    }


    /**
     * 修改ZfRace
     */
    @PutMapping
    @PreAuthorize("@ss.hasPerm('own:race:edit')")
    public ResponseResult edit(@RequestBody RaceVo raceVo)
    {
        return ResponseResult.toResponse(raceService.updateZfRaceModel(raceVo));
    }

    /**
     * 删除ZfRace
     */
    @DeleteMapping("/{raceIds}")
    @PreAuthorize("@ss.hasPerm('own:race:remove')")
    public ResponseResult remove(@PathVariable Long[] raceIds)
    {
        return ResponseResult.toResponse(raceService.deleteZfRaceModelByRaceIds(raceIds));
    }


}
