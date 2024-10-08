package com.changlu.web.controller.team;

import com.changlu.common.domain.ResponseResult;
import com.changlu.service.SchoolMajorService;
import com.changlu.web.controller.BaseController;
import com.changlu.system.pojo.SchoolMajorModel;
import com.changlu.common.utils.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName MajorController
 * @Author ChangLu
 * @Date 3/31/2022 12:28 PM
 * @Description 专业控制器
 */
@RestController
@RequestMapping("/api/team/major")
public class MajorController extends BaseController {

    @Autowired
    private SchoolMajorService majorService;

    /**
     * 查询ZfMajor列表
     */
    @GetMapping("/list")
    @PreAuthorize("@ss.hasPerm('team:major:list')")
    public TableDataInfo list(SchoolMajorModel zfMajor)
    {
        startPage();
        List<SchoolMajorModel> list = majorService.selectZfMajorModelList(zfMajor);
        return getDataTable(list);
    }

    /**
     * 获取ZfMajor详细信息
     */
    @GetMapping(value = "/{majorId}")
    @PreAuthorize("@ss.hasPerm('team:major:query')")
    public ResponseResult getInfo(@PathVariable("majorId") Long majorId)
    {
        return ResponseResult.success(majorService.selectZfMajorModelByMajorId(majorId));
    }

    /**
     * 新增ZfMajor
     */
    @PostMapping
    @PreAuthorize("@ss.hasPerm('team:major:add')")
    public ResponseResult add(@RequestBody SchoolMajorModel schoolMajorModel)
    {
        return ResponseResult.toResponse(majorService.insertZfMajorModel(schoolMajorModel));
    }

    /**
     * 修改ZfMajor
     */
    @PutMapping
    @PreAuthorize("@ss.hasPerm('team:major:edit')")
    public ResponseResult edit(@RequestBody SchoolMajorModel schoolMajorModel)
    {
        return ResponseResult.toResponse(majorService.updateZfMajorModel(schoolMajorModel));
    }

    /**
     * 删除ZfMajor
     */
    @DeleteMapping("/{majorIds}")
    @PreAuthorize("@ss.hasPerm('team:major:remove')")
    public ResponseResult remove(@PathVariable Long[] majorIds)
    {
        return ResponseResult.toResponse(majorService.deleteZfMajorModelByMajorIds(majorIds));
    }


}
