package com.changlu.web.controller.own;

import javax.servlet.http.HttpServletResponse;

import com.changlu.common.domain.ResponseResult;
import com.changlu.common.utils.ExcelUtil;
import com.changlu.common.utils.page.TableDataInfo;
import com.changlu.system.pojo.StudioAchievementModel;
import com.changlu.system.pojo.dto.StudioAchievementDTO;
import com.changlu.system.service.IStudioAchievementService;
import com.changlu.web.controller.BaseController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 成果Controller
 * 
 * @author changlu
 * @date 2024-08-18
 */
@RestController
@RequestMapping("/api/own/achievement")
public class StudioAchievementController extends BaseController
{
    @Autowired
    private IStudioAchievementService studioAchievementService;

    /**
     * 查询成果列表
     */
    @PreAuthorize("@ss.hasPerm('own:achievement:list')")
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
    @PreAuthorize("@ss.hasPerm('own:achievement:query')")
    @GetMapping(value = "/{id}")
    public ResponseResult getInfo(@PathVariable("id") Long id)
    {
        return ResponseResult.success(studioAchievementService.selectStudioAchievementById(id));
    }

    /**
     * 新增成果
     */
    @PreAuthorize("@ss.hasPerm('own:achievement:add')")
    @PostMapping
    public ResponseResult add(@RequestBody StudioAchievementModel studioAchievement)
    {
        return ResponseResult.toResponse(studioAchievementService.insertStudioAchievement(studioAchievement));
    }

    /**
     * 修改成果
     */
    @PreAuthorize("@ss.hasPerm('own:achievement:edit')")
    @PutMapping
    public ResponseResult edit(@RequestBody StudioAchievementModel studioAchievement)
    {
        return ResponseResult.toResponse(studioAchievementService.updateStudioAchievement(studioAchievement));
    }

    /**
     * 删除成果
     */
    @PreAuthorize("@ss.hasPerm('own:achievement:remove')")
	@DeleteMapping("/{ids}")
    public ResponseResult remove(@PathVariable Long[] ids)
    {
        return ResponseResult.toResponse(studioAchievementService.deleteStudioAchievementByIds(ids));
    }
}
