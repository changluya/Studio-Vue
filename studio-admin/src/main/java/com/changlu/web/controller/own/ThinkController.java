package com.changlu.web.controller.own;

import com.changlu.common.domain.ResponseResult;
import com.changlu.service.StudioThinkService;
import com.changlu.web.controller.BaseController;
import com.changlu.system.pojo.StudioThinkModel;
import com.changlu.common.utils.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName ThinkController
 * @Author ChangLu
 * @Date 4/4/2022 5:51 PM
 * @Description 个人思考控制器
 */
@RestController
@RequestMapping("/api/own/think")
public class ThinkController extends BaseController {

    @Autowired
    private StudioThinkService studioThinkService;

    /**
     * 查询ZfThink列表
     */
    @GetMapping("/list")
    @PreAuthorize("@ss.hasPerm('own:think:list')")
    public TableDataInfo list(StudioThinkModel studioThinkModel)
    {
        startPage();
        List<StudioThinkModel> list = studioThinkService.selectZfThinkModelListByUserId(studioThinkModel);
        return getDataTable(list);
    }

    /**
     * 获取ZfThink详细信息
     */
    @GetMapping(value = "/{thinkId}")
    @PreAuthorize("@ss.hasPerm('own:think:query')")
    public ResponseResult getInfo(@PathVariable("thinkId") Long thinkId)
    {
        return ResponseResult.success(studioThinkService.selectZfThinkModelByThinkId(thinkId));
    }

    /**
     * 新增ZfThink
     */
    @PostMapping
    @PreAuthorize("@ss.hasPerm('own:think:add')")
    public ResponseResult add(@RequestBody StudioThinkModel studioThinkModel)
    {
        return ResponseResult.toResponse(studioThinkService.insertZfThinkModel(studioThinkModel));
    }

    /**
     * 修改ZfThink
     */
    @PutMapping
    @PreAuthorize("@ss.hasPerm('own:think:edit')")
    public ResponseResult edit(@RequestBody StudioThinkModel studioThinkModel)
    {
        return ResponseResult.toResponse(studioThinkService.updateZfThinkModel(studioThinkModel));
    }

    /**
     * 删除ZfThink
     */
    @DeleteMapping("/{thinkIds}")
    @PreAuthorize("@ss.hasPerm('own:think:remove')")
    public ResponseResult remove(@PathVariable Long[] thinkIds)
    {
        return ResponseResult.toResponse(studioThinkService.deleteZfThinkModelByThinkIds(thinkIds));
    }

}
