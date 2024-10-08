package com.changlu.web.controller.own;

import com.changlu.common.domain.ResponseResult;
import com.changlu.common.exception.ServiceException;
import com.changlu.service.StudioCcieService;
import com.changlu.system.pojo.StudioAchievementModel;
import com.changlu.web.controller.BaseController;
import com.changlu.system.pojo.StudioCcieModel;
import com.changlu.common.utils.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName CcieController
 * @Author ChangLu
 * @Date 4/3/2022 2:27 PM
 * @Description 获奖证书控制器
 */
@RestController
@RequestMapping("/api/own/ccie")
public class OwnCcieController extends BaseController {

    @Autowired
    private StudioCcieService ccieService;

    /**
     * 列表、分页、关键字段搜索
     */
    @GetMapping("/list")
    @PreAuthorize("@ss.hasPerm('own:ccie:list')")
    public TableDataInfo list(StudioCcieModel ccieModel){
        startPage();//开启分页
        List<StudioCcieModel> list = ccieService.selectOwnCcieList(ccieModel);
        return getDataTable(list);
    }

    /**
     * 查询详细信息
     */
    @GetMapping("/{ccieId}")
    @PreAuthorize("@ss.hasPerm('own:ccie:query')")
    public ResponseResult getInfo(@PathVariable("ccieId") Long ccieId){
        return ResponseResult.success(ccieService.selectOwnCcieByCcieId(ccieId));
    }

    /**
     * 新增获奖证书
     */
    @PostMapping
    @PreAuthorize("@ss.hasPerm('own:ccie:add')")
    public ResponseResult add(@RequestBody StudioCcieModel ccieModel) {
        return ResponseResult.toResponse(ccieService.insertZfCcie(ccieModel));
    }

    /**
     * 修改信息
     */
    @PutMapping
    @PreAuthorize("@ss.hasPerm('own:ccie:edit')")
    public ResponseResult edit(@RequestBody StudioCcieModel ccieModel){
        return ResponseResult.toResponse(ccieService.updateOwnCcie(ccieModel));
    }

    /**
     * 删除获奖证书：若是/1,2，那么就会封装到一个long数组中。
     * 注意一定要加ccieIds，否则就会抛出转换Long类型异常。
     */
    @DeleteMapping("/{ccieIds}")
    @PreAuthorize("@ss.hasPerm('own:ccie:remove')")
    public ResponseResult remove(@PathVariable(name = "ccieIds") Long[] ccieIds){
        return ResponseResult.toResponse(ccieService.deleteZfCcieByCcieIds(ccieIds));
    }

    /**
     * 申请收录
     */
//    @PreAuthorize("@ss.hasPerm('own:achievement:apply')")
    @PutMapping("/apply")
    public ResponseResult applyInclusion(@RequestBody StudioCcieModel ccieModel)
    {
        // 校验参数
        Long id = ccieModel.getCcieId();
        if (id == null) {
            throw new ServiceException("成果id为空，请传递正确参数！");
        }
        ccieService.updateInclusion(id, 1);
        return ResponseResult.success();
    }

    /**
     * 取消收录
     */
//    @PreAuthorize("@ss.hasPerm('own:achievement:cancel')")
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

    /**
     * 导出获奖证书
     */
//    @PostMapping("/export")
//    public void export(HttpServletResponse response, ZfCcieModel ccieModel) {
//        List<ZfCcieModel> list = ccieService.selectZfCcieList(ccieModel);
//        ExcelUtil<ZfCcieModel> util = new ExcelUtil<>(ZfCcieModel.class);
//        util.exportExcel(response,list, "获奖证书数据");
//    }

}
