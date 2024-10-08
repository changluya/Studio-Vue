package com.changlu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.changlu.system.pojo.StudioRaceModel;
import com.changlu.vo.race.RaceVo;
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
public interface StudioRaceMapper extends BaseMapper<StudioRaceModel> {

    /**
     * 查询Race列表：带有竞赛资源列表
     *
     * @param studioRaceModel ZfRace
     * @return ZfRace集合
     */
    List<RaceVo> selectRaceVoList(StudioRaceModel studioRaceModel);

    /**
     * 查询Race列表
     *
     * @param studioRaceModel 竞赛模型
     * @return Race集合
     */
    List<StudioRaceModel> selectRaceModelList(StudioRaceModel studioRaceModel);

    /**
     * 查询ZfRace
     *
     * @param raceId ZfRace主键
     * @return ZfRace
     */
    RaceVo selectZfRaceModelByRaceId(Long raceId);

    /**
     * 查询所有竞赛id
     * @return
     */
    Long[] selectZfRaceIds();

    /**
     * 查询ZfRace
     *
     * @param raceIds ZfRace主键 Map<String,Object> map
     * @return ZfRace
     */
    List<RaceVo> selectZfRaceModelListByRaceIds(@Param("raceModel") StudioRaceModel studioRaceModel, @Param("raceIds") Long[] raceIds);

    /**
     * 查询指定用户的所有竞赛记录id
     * @param userId
     * @return
     */
    public List<Long> selectRaceIdsByUserId(Long userId);

    /**
     * 根据用户id查询ZfRace列表
     *
     * @param studioRaceModel ZfRace
     * @return ZfRace集合
     */
    public List<RaceVo> selectZfRaceModelListByUserId(StudioRaceModel studioRaceModel);

    /**
     * 获取刚刚创建用户自增的id
     * @return
     */
    Long getLastInsertId();

    /**
     * 新增ZfRace
     *
     * @param raceVo ZfRace
     * @return 结果
     */
    int insertZfRaceModel(RaceVo raceVo);

    /**
     * 修改ZfRace
     *
     * @param studioRaceModel StudioRaceModel
     * @return 结果
     */
    int updateZfRaceModel(StudioRaceModel studioRaceModel);

    /**
     * 批量删除ZfRace
     *
     * @param raceIds 需要删除的数据主键集合
     * @return 结果
     */
    int deleteZfRaceModelByRaceIds(Long[] raceIds);

    /**
     * 根据用户id删除ZfRace
     *
     * @param userId ZfRace主键
     * @return 结果
     */
    public int deleteZfRaceModelByUserId(Long userId);

}
