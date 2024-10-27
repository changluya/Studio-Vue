package com.changlu.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.changlu.system.pojo.StudioCcieModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ChangLu
 * @since 2022-03-30
 */
public interface StudioCcieMapper extends BaseMapper<StudioCcieModel> {

    /**
     * 查询ZfCcie列表
     *
     * @param zfCcie ZfCcie
     * @return ZfCcie集合
     */
    public List<StudioCcieModel> selectCcieList(StudioCcieModel zfCcie);

    /**
     * 查询Ccie列表根据，获奖日期降序排序
     *
     * @param ccie 获奖类型
     * @return Ccie集合
     */
    public List<StudioCcieModel> selectShowCcieList(@Param("type") Integer type,@Param("searchYear") String searchYear);

    /**
     * 根据用户id查询ZfCcie列表
     *
     * @param zfCcie ZfCcie
     * @return ZfCcie集合
     */
    public List<StudioCcieModel> selectZfCcieListByUserId(StudioCcieModel zfCcie);

    /**
     * 查询ZfCcie
     *
     * @param ccieId ZfCcie主键
     * @return ZfCcie
     */
    public StudioCcieModel selectZfCcieByCcieId(Long ccieId);

    /**
     * 新增ZfCcie
     *
     * @param zfCcie ZfCcie
     * @return 结果
     */
    public int insertZfCcie(StudioCcieModel zfCcie);

    /**
     * 修改ZfCcie
     *
     * @param zfCcie ZfCcie
     * @return 结果
     */
    public int updateZfCcie(StudioCcieModel zfCcie);

    /**
     * 删除ZfCcie
     *
     * @param ccieId ZfCcie主键
     * @return 结果
     */
    public int deleteZfCcieByCcieId(Long ccieId);


    /**
     * 批量删除ZfCcie
     *
     * @param ccieIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteZfCcieByCcieIds(@Param("ccieIds") Long[] ccieIds,@Param("createUserId") Long createUserId);

    /**
     * 根据用户id删除ZfCcie
     *
     * @param ccieId ZfCcie主键
     * @return 结果
     */
    public int deleteZfCcieByUserId(Long userId);

}
