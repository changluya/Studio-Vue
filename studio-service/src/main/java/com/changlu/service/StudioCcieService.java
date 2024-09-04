package com.changlu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.changlu.common.domain.ResponseResult;
import com.changlu.system.pojo.StudioCcieModel;
import com.changlu.vo.ccie.ShowCcieVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ChangLu
 * @since 2022-03-30
 */
public interface StudioCcieService extends IService<StudioCcieModel> {

    /**
     * 查询ZfCcie列表
     *
     * @param ccieModel
     * @return ZfCcie集合
     */
    List<StudioCcieModel> selectCcieList(StudioCcieModel ccieModel);

    /**
     * 查询证书列表，用于展示
     *
     * @param ccieModel
     * @return Ccie集合
     */
    List<ShowCcieVo> selectShowCcieList(Integer type) ;

    /**
     * 查询ZfCcie列表根据用户id
     *
     * @param ccieModel
     * @return ZfCcie集合
     */
    List<StudioCcieModel> selectOwnCcieList(StudioCcieModel ccieModel);

    /**
     * 查询ZfCcie
     *
     * @param ccieId ZfCcie主键
     * @return ZfCcie
     */
    StudioCcieModel selectOwnCcieByCcieId(Long ccieId);

    /**
     * 新增ZfCcie
     *
     * @param zfCcie ZfCcie
     * @return 结果
     */
     int insertZfCcie(StudioCcieModel zfCcie);

    /**
     * 修改个人的ZfCcie
     *
     * @param zfCcie ZfCcie
     * @return 结果
     */
    int updateOwnCcie(StudioCcieModel zfCcie);

    /**
     * 修改ZfCcie
     *
     * @param zfCcie ZfCcie
     * @return 结果
     */
    int updateCcie(StudioCcieModel zfCcie);

    /**
     * 批量删除个人的Ccie
     *
     * @param ccieIds 需要删除的Ccie主键集合
     * @return 结果
     */
    int deleteOwnCcieByCcieIds(Long[] ccieIds);

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

    /**
     * 修改收录状态
     * @param id 证书id
     * @param behavior 不同行为情况
     *        情况1、behavior 为 1情况，【申请收录操作】修改状态为申请收录
     *        情况2、behavior 为 2情况，【退回收录操作】修改状态为退出收录（未收录状态）
     *        情况3、behavior 为 3情况，【审核通过收录操作】修改状态为通过收录（已收录状态）
     * @return
     */
    public void updateInclusion(Long id, int behavior);

    /**
     * 统计已收录的荣誉证书
     * @return 已收录的荣誉证书数量
     */
    public int countAlreadyInclusionCcie();

}
