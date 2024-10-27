package com.changlu.web.controller.team;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.changlu.common.domain.ResponseResult;
import com.changlu.common.utils.ExcelUtil;
import com.changlu.common.utils.page.TableDataInfo;
import com.changlu.service.SchoolAcademyService;
import com.changlu.system.pojo.SchoolAcademyModel;
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

/**
 * 学院Controller
 *
 * @author 长路
 * @date 2024-05-28
 */
@RestController
@RequestMapping("/api/team/academy")
public class ManageAcademyController extends BaseController {
    @Autowired
    private SchoolAcademyService schoolAcademyService;

    /**
     * 查询学院列表
     */
    @PreAuthorize("@ss.hasPerm('team:academy:list')")
    @GetMapping("/list")
    public TableDataInfo list(SchoolAcademyModel schoolAcademy)
    {
        startPage();
        List<SchoolAcademyModel> list = schoolAcademyService.selectSchoolAcademyList(schoolAcademy);
        return getDataTable(list);
    }

    /**
     * 导出学院列表
     */
    @PreAuthorize("@ss.hasPerm('team:academy:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SchoolAcademyModel schoolAcademy)
    {
        List<SchoolAcademyModel> list = schoolAcademyService.selectSchoolAcademyList(schoolAcademy);
        ExcelUtil<SchoolAcademyModel> util = new ExcelUtil<SchoolAcademyModel>(SchoolAcademyModel.class);
        util.exportExcel(response, list, "学院数据");
    }

    /**
     * 获取学院详细信息
     */
    @PreAuthorize("@ss.hasPerm('team:academy:query')")
    @GetMapping(value = "/{id}")
    public ResponseResult getInfo(@PathVariable("id") Long id)
    {
        return ResponseResult.success(schoolAcademyService.selectSchoolAcademyById(id));
    }

    /**
     * 新增学院
     */
    @PreAuthorize("@ss.hasPerm('team:academy:add')")
    @PostMapping
    public ResponseResult add(@RequestBody SchoolAcademyModel schoolAcademy)
    {
        return ResponseResult.success(schoolAcademyService.insertSchoolAcademy(schoolAcademy));
    }

    /**
     * 修改学院
     */
    @PreAuthorize("@ss.hasPerm('team:academy:edit')")
    @PutMapping
    public ResponseResult edit(@RequestBody SchoolAcademyModel schoolAcademy)
    {
        return ResponseResult.success(schoolAcademyService.updateSchoolAcademy(schoolAcademy));
    }

    /**
     * 删除学院
     */
    @PreAuthorize("@ss.hasPerm('team:academy:remove')")
	@DeleteMapping("/{ids}")
    public ResponseResult remove(@PathVariable Long[] ids)
    {
        return ResponseResult.success(schoolAcademyService.deleteSchoolAcademyByIds(ids));
    }
}
