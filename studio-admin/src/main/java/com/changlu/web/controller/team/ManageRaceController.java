package com.changlu.web.controller.team;

import com.changlu.common.domain.ResponseResult;
import com.changlu.common.exception.ServiceException;
import com.changlu.common.utils.ExcelUtil;
import com.changlu.mapper.StudioMUserMapper;
import com.changlu.service.StudioRaceService;
import com.changlu.system.pojo.StudioRaceModel;
import com.changlu.web.controller.BaseController;
import com.changlu.vo.race.RaceVo;
import com.changlu.common.utils.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @ClassName ManageRaceController
 * @Author ChangLu
 * @Date 4/7/2022 6:55 PM
 * @Description 管理竞赛控制器
 */
@RestController
@RequestMapping("/api/team/race")
public class ManageRaceController extends BaseController {

    @Resource
    private StudioMUserMapper studioMUserMapper;

    @Autowired
    private StudioRaceService raceService;

    /**
     * 查询ZfRace列表
     */
    @GetMapping("/list")
    @PreAuthorize("@ss.hasPerm('team:race:list')")
    public TableDataInfo list(RaceVo raceVo)
    {
        startPage();
        List<RaceVo> list = raceService.selectRaceModelList(raceVo);
        return getDataTable(list);
    }

    /**
     * 获取用户选项（id与姓名）
     */
    @GetMapping("/memberoptions")
    @PreAuthorize("@ss.hasPerm('team:race:memberoptions')")
    public ResponseResult getUserIdAndRealName(){
        return ResponseResult.success(studioMUserMapper.selectSysUserIdAndRealName());
    }

//    /**
//     * 查询用户的年级、专业信息（暂未使用）
//     */
//    @GetMapping("/member/{userIds}")
//    public ResponseResult membersGradeMajor(@PathVariable(name = "userIds") Long[] userIds)
//    {
//        return ResponseResult.success(zfMUserMapper.selectSysUserByUserIds(userIds));
//    }

    /**
     * 导出竞赛记录
     */
    @PostMapping("/export")
    @PreAuthorize("@ss.hasPerm('team:race:export')")
    public void export(RaceVo raceVo, HttpServletResponse response){
        List<RaceVo> list = raceService.selectRaceModelList(raceVo);
        ExcelUtil<RaceVo> util = new ExcelUtil<>(RaceVo.class);
        util.exportExcel(response, list, "竞赛记录");
    }

    /**
     * 审核通过收录
     */
//    @PreAuthorize("@ss.hasPerm('own:achievement:apply')")
    @PutMapping("/approved")
    public ResponseResult approvedInclusion(@RequestBody StudioRaceModel raceModel)
    {
        // 校验参数
        Long id = raceModel.getRaceId();
        if (id == null) {
            throw new ServiceException("成果id为空，请传递正确参数！");
        }
        raceService.updateInclusion(id, 3);
        return ResponseResult.success();
    }

    /**
     * 取消收录
     */
//    @PreAuthorize("@ss.hasPerm('team:achievement:cancel')")
    @PutMapping("/cancel")
    public ResponseResult cancelInclusion(@RequestBody StudioRaceModel raceModel)
    {
        // 校验参数
        Long id = raceModel.getRaceId();
        if (id == null) {
            throw new ServiceException("成果id为空，请传递正确参数！");
        }
        raceService.updateInclusion(id, 2);
        return ResponseResult.success();
    }


}
