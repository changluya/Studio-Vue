package com.changlu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.changlu.common.domain.ResponseResult;
import com.changlu.system.pojo.StudioCcieModel;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ChangLu
 * @since 2022-03-30
 */
public interface ZfCcieService extends IService<StudioCcieModel> {

    /**
     * 查询ZfCcie列表
     *
     * @param ccieModel
     * @return ZfCcie集合
     */
    List<StudioCcieModel> selectZfCcieList(StudioCcieModel ccieModel);

    /**
     * 查询ZfCcie列表根据用户id
     *
     * @param ccieModel
     * @return ZfCcie集合
     */
    List<StudioCcieModel> selectZfCcieListByUserId(StudioCcieModel ccieModel);

    /**
     * 查询ZfCcie
     *
     * @param ccieId ZfCcie主键
     * @return ZfCcie
     */
    StudioCcieModel selectZfCcieByCcieId(Long ccieId);

    /**
     * 新增ZfCcie
     *
     * @param zfCcie ZfCcie
     * @return 结果
     */
     int insertZfCcie(StudioCcieModel zfCcie);

    /**
     * 修改ZfCcie
     *
     * @param zfCcie ZfCcie
     * @return 结果
     */
    int updateZfCcie(StudioCcieModel zfCcie);

    /**
     * 批量删除ZfCcie
     *
     * @param ccieIds 需要删除的ZfCcie主键集合
     * @return 结果
     */
    int deleteZfCcieByCcieIds(Long[] ccieIds);

    /**
     * 删除ZfCcie信息
     *
     * @param ccieId ZfCcie主键
     * @return 结果
     */
    ResponseResult deleteZfCcieByCcieId(Long ccieId);

}
