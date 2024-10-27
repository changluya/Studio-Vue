package com.changlu.web.controller.team;

import com.changlu.common.domain.ResponseResult;
import com.changlu.common.exception.ServiceException;
import com.changlu.common.utils.page.TableDataInfo;
import com.changlu.security.util.SecurityUtils;
import com.changlu.service.IStudioAchievementService;
import com.changlu.system.pojo.StudioAchievementModel;
import com.changlu.system.pojo.dto.StudioAchievementDTO;
import com.changlu.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 团队成果Controller
 * 
 * @author changlu
 * @date 2024-08-18
 */
@RestController
@RequestMapping("/api/team/achievement")
public class TeamAchievementController extends BaseController
{
    @Autowired
    private IStudioAchievementService studioAchievementService;

    /**
     * 查询成果列表
     */
//    @PreAuthorize("@ss.hasPerm('team:achievement:list')")
    @GetMapping("/list")
    public TableDataInfo list(StudioAchievementModel studioAchievement)
    {
        startPage();
        List<StudioAchievementDTO> list = studioAchievementService.selectStudioAchievementList(studioAchievement);
        return getDataTable(list);
    }

    /**
     * 获取成果详细信息
     */
//    @PreAuthorize("@ss.hasPerm('team:achievement:query')")
    @GetMapping(value = "/{id}")
    public ResponseResult getInfo(@PathVariable("id") Long id)
    {
        return ResponseResult.success(studioAchievementService.selectStudioAchievementById(id));
    }

    /**
     * 新增成果
     */
//    @PreAuthorize("@ss.hasPerm('team:achievement:add')")
    @PostMapping
    public ResponseResult add(@RequestBody StudioAchievementModel studioAchievement)
    {
        studioAchievementService.insertStudioAchievement(studioAchievement);
        return ResponseResult.success();
    }

    /**
     * 修改成果
     */
//    @PreAuthorize("@ss.hasPerm('team:achievement:edit')")
    @PutMapping
    public ResponseResult edit(@RequestBody StudioAchievementModel studioAchievement)
    {
        studioAchievementService.updateStudioAchievement(studioAchievement);
        return ResponseResult.success();
    }

    /**
     * 审核通过收录
     */
//    @PreAuthorize("@ss.hasPerm('own:achievement:apply')")
    @PutMapping("/approved")
    public ResponseResult approvedInclusion(@RequestBody StudioAchievementModel studioAchievement)
    {
        // 校验参数
        Long id = studioAchievement.getId();
        if (id == null) {
            throw new ServiceException("成果id为空，请传递正确参数！");
        }
        studioAchievementService.updateInclusion(id, 3);
        return ResponseResult.success();
    }

    /**
     * 取消收录
     */
//    @PreAuthorize("@ss.hasPerm('team:achievement:cancel')")
    @PutMapping("/cancel")
    public ResponseResult cancelInclusion(@RequestBody StudioAchievementModel studioAchievement)
    {
        // 校验参数
        Long id = studioAchievement.getId();
        if (id == null) {
            throw new ServiceException("成果id为空，请传递正确参数！");
        }
        studioAchievementService.updateInclusion(id, 2);
        return ResponseResult.success();
    }


    /**
     * 删除成果
     */
//    @PreAuthorize("@ss.hasPerm('team:achievement:remove')")
	@DeleteMapping("/{ids}")
    public ResponseResult remove(@PathVariable Long[] ids)
    {
        studioAchievementService.deleteStudioAchievementByIds(ids);
        return ResponseResult.success();
    }
}
