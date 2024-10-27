package com.changlu.web.controller.team;

import com.changlu.common.domain.ResponseResult;
import com.changlu.common.exception.ServiceException;
import com.changlu.common.utils.ExcelUtil;
import com.changlu.service.StudioCcieService;
import com.changlu.service.StudioManageCcieService;
import com.changlu.system.pojo.StudioAchievementModel;
import com.changlu.system.pojo.StudioCcieModel;
import com.changlu.web.controller.BaseController;
import com.changlu.vo.manage.MCcieVo;
import com.changlu.common.utils.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @ClassName ManageCcieController
 * @Author ChangLu
 * @Date 4/7/2022 2:35 PM
 * @Description 管理证书控制器
 */
@RestController
@RequestMapping("/api/team/ccie")
public class ManageCciesController extends BaseController {

    @Autowired
    private StudioManageCcieService studioManageCcieService;

    @Autowired
    private StudioCcieService ccieService;

    /**
     * 查询证书列表
     */
    @GetMapping("/list")
    @PreAuthorize("@ss.hasPerm('team:ccie:list')")
    public TableDataInfo list(MCcieVo mCcieVo)
    {
        startPage();
        List<MCcieVo> list = studioManageCcieService.selectZfCcieList(mCcieVo);
        return getDataTable(list);
    }

    @PostMapping("/export")
    @PreAuthorize("@ss.hasPerm('team:ccie:export')")
    public void export(MCcieVo mCcieVo, HttpServletResponse response) {
        List<MCcieVo> list = studioManageCcieService.selectZfCcieList(mCcieVo);
        ExcelUtil<MCcieVo> util = new ExcelUtil<>(MCcieVo.class);
        util.exportExcel(response, list, "证书列表");
    }

    /**
     * 审核通过收录
     */
//    @PreAuthorize("@ss.hasPerm('own:achievement:apply')")
    @PutMapping("/approved")
    public ResponseResult approvedInclusion(@RequestBody StudioCcieModel ccieModel)
    {
        // 校验参数
        Long id = ccieModel.getCcieId();
        if (id == null) {
            throw new ServiceException("成果id为空，请传递正确参数！");
        }
        ccieService.updateInclusion(id, 3);
        return ResponseResult.success();
    }

    /**
     * 取消收录
     */
//    @PreAuthorize("@ss.hasPerm('team:achievement:cancel')")
    @PutMapping("/cancel")
    public ResponseResult cancelInclusion(@RequestBody StudioCcieModel ccieModel)
    {
        // 校验参数
        Long id = ccieModel.getCcieId();
        if (id == null) {
            throw new ServiceException("成果id为空，请传递正确参数！");
        }
        ccieService.updateInclusion(id, 2);
        return ResponseResult.success();
    }

}
