package com.changlu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.changlu.vo.race.RaceVo;
import com.changlu.system.pojo.StudioRaceModel;
import com.changlu.vo.race.ShowRaceVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ChangLu
 * @since 2022-03-30
 */
public interface StudioRaceService extends IService<StudioRaceModel> {

    /**
     * 查询ZfRace
     *
     * @param raceModel ZfRace主键
     * @return ZfRace
     */
    List<RaceVo> selectRaceModelList(StudioRaceModel raceModel);

    /**
     * 查询展示竞赛列表
     * @return 竞赛vo实体类
     */
    List<ShowRaceVo> selectShowRaceList();

    /**
     * 根据用户id查询ZfRace列表
     *
     * @param studioRaceModel ZfRace
     * @return ZfRace集合
     */
    List<RaceVo> selectZfRaceModelListByUserId(StudioRaceModel studioRaceModel);

    /**
     * 查询ZfRace
     *
     * @param raceId ZfRace主键
     * @return ZfRace
     */
    RaceVo selectZfRaceModelByRaceId(Long raceId);

    /**
     * 新增ZfRace(个人)
     *
     * @param raceVo ZfRace
     * @return 结果
     */
    boolean insertZfRaceModel(RaceVo raceVo);

    /**
     * 修改ZfRace
     *
     * @param raceVo ZfRace
     * @return 结果
     */
    boolean updateZfRaceModel(RaceVo raceVo);

    /**
     * 修改ZfRace
     *
     * @param raceVo ZfRace
     * @return 结果
     */
    void updateZfRaceModel(StudioRaceModel studioRaceModel);


    /**
     * 删除ZfRace信息
     *
     * @param raceIds ZfRace主键
     * @return 结果
     */
    boolean deleteZfRaceModelByRaceIds(Long[] raceIds);


    /**
     * 根据用户删除竞赛信息
     *
     * @param raceIds ZfRace主键
     * @return 结果
     */
    boolean deleteZfRaceModelByUserId(Long userId);

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
}
