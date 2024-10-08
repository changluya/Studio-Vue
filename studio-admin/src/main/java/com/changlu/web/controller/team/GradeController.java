package com.changlu.web.controller.team;

import com.changlu.common.domain.ResponseResult;
import com.changlu.service.SchoolGradeService;
import com.changlu.web.controller.BaseController;
import com.changlu.system.pojo.SchoolGradeModel;
import com.changlu.common.utils.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName GradeController
 * @Author ChangLu
 * @Date 4/5/2022 3:51 PM
 * @Description 专业控制器
 */
@RestController
@RequestMapping("/api/team/grade")
public class GradeController extends BaseController {

    @Autowired
    private SchoolGradeService gradeService;

    /**
     * 查询ZfGrade列表
     */
    @GetMapping("/list")
    @PreAuthorize("@ss.hasPerm('team:grade:list')")
    public TableDataInfo list(SchoolGradeModel schoolGradeModel)
    {
        startPage();
        List<SchoolGradeModel> list = gradeService.selectZfGradeModelList(schoolGradeModel);
        return getDataTable(list);
    }

    /**
     * 获取ZfGrade详细信息
     */
    @GetMapping(value = "/{gradeId}")
    @PreAuthorize("@ss.hasPerm('team:grade:query')")
    public ResponseResult getInfo(@PathVariable("gradeId") Long gradeId)
    {
        return ResponseResult.success(gradeService.selectZfGradeModelByGradeId(gradeId));
    }

    /**
     * 新增ZfGrade
     */
    @PostMapping
    @PreAuthorize("@ss.hasPerm('team:grade:add')")
    public ResponseResult add(@RequestBody SchoolGradeModel schoolGradeModel)
    {
        return ResponseResult.toResponse(gradeService.insertZfGradeModel(schoolGradeModel));
    }

    /**
     * 修改ZfGrade
     */
    @PutMapping
    @PreAuthorize("@ss.hasPerm('team:grade:edit')")
    public ResponseResult edit(@RequestBody SchoolGradeModel schoolGradeModel)
    {
        return ResponseResult.toResponse(gradeService.updateZfGradeModel(schoolGradeModel));
    }

    /**
     * 删除ZfGrade
     */
    @DeleteMapping("/{gradeIds}")
    @PreAuthorize("@ss.hasPerm('team:grade:remove')")
    public ResponseResult remove(@PathVariable Long[] gradeIds)
    {
        return ResponseResult.toResponse(gradeService.deleteZfGradeModelByGradeIds(gradeIds));
    }

}
