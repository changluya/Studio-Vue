package com.changlu.web.controller.team;

import javax.servlet.http.HttpServletResponse;

import com.changlu.common.domain.ResponseResult;
import com.changlu.common.utils.ExcelUtil;
import com.changlu.common.utils.page.TableDataInfo;
import com.changlu.system.pojo.StudioPocsModel;
import com.changlu.system.service.IStudioPocsService;
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
 * 成果分类Controller
 * 
 * @author 长路
 * @date 2024-08-18
 */
@RestController
@RequestMapping("/api/team/pocs")
public class StudioPocsController extends BaseController {

    @Autowired
    private IStudioPocsService studioPocsService;

    @GetMapping("/menu")
    public ResponseResult menu() {
        List<StudioPocsModel> res = studioPocsService.selectStudioPocsList(null);
        return ResponseResult.success(res);
    }

    /**
     * 查询成果分类列表
     */
    @PreAuthorize("@ss.hasPerm('team:pocs:list')")
    @GetMapping("/list")
    public TableDataInfo list(StudioPocsModel studioPocs)
    {
        startPage();
        List<StudioPocsModel> list = studioPocsService.selectStudioPocsList(studioPocs);
        return getDataTable(list);
    }

    /**
     * 导出成果分类列表
     */
    @PreAuthorize("@ss.hasPerm('team:pocs:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, StudioPocsModel studioPocs)
    {
        List<StudioPocsModel> list = studioPocsService.selectStudioPocsList(studioPocs);
        ExcelUtil<StudioPocsModel> util = new ExcelUtil<StudioPocsModel>(StudioPocsModel.class);
        util.exportExcel(response, list, "成果分类数据");
    }

    /**
     * 获取成果分类详细信息
     */
    @PreAuthorize("@ss.hasPerm('team:pocs:query')")
    @GetMapping(value = "/{id}")
    public ResponseResult getInfo(@PathVariable("id") Long id)
    {
        return ResponseResult.success(studioPocsService.selectStudioPocsById(id));
    }

    /**
     * 新增成果分类
     */
    @PreAuthorize("@ss.hasPerm('team:pocs:add')")
    @PostMapping
    public ResponseResult add(@RequestBody StudioPocsModel studioPocs)
    {
        return ResponseResult.toResponse (studioPocsService.insertStudioPocs(studioPocs));
    }

    /**
     * 修改成果分类
     */
    @PreAuthorize("@ss.hasPerm('team:pocs:edit')")
    @PutMapping
    public ResponseResult edit(@RequestBody StudioPocsModel studioPocs)
    {
        return ResponseResult.toResponse(studioPocsService.updateStudioPocs(studioPocs));
    }

    /**
     * 删除成果分类
     */
    @PreAuthorize("@ss.hasPerm('team:pocs:remove')")
	@DeleteMapping("/{ids}")
    public ResponseResult remove(@PathVariable Long[] ids)
    {
        return ResponseResult.toResponse(studioPocsService.deleteStudioPocsByIds(ids));
    }
}
