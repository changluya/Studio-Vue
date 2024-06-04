package com.changlu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.changlu.common.exception.ServiceException;
import com.changlu.common.utils.DateUtils;
import com.changlu.mapper.StudioRaceMapper;
import com.changlu.security.util.SecurityUtils;
import com.changlu.service.StudioRaceService;
import com.changlu.service.StudioResourceService;
import com.changlu.vo.race.RaceVo;
import com.changlu.vo.race.ResourceVo;
import com.changlu.enums.StudioRaceTypeEnum;
import com.changlu.enums.StudioResourceEnum;
import com.changlu.system.pojo.StudioRaceModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ChangLu
 * @since 2022-03-30
 */
@Service
public class StudioRaceServiceImpl extends ServiceImpl<StudioRaceMapper, StudioRaceModel> implements StudioRaceService {

    @Resource
    private StudioRaceMapper studioRaceMapper;

    @Resource
    private StudioResourceService studioResourceService;

    @Override
    public List<RaceVo> selectZfRaceModelList(StudioRaceModel studioRaceModel) {
        return studioRaceMapper.selectZfRaceModelList(studioRaceModel);
    }

    @Override
    public List<RaceVo> selectZfRaceModelListByUserId(StudioRaceModel studioRaceModel) {
        //设置用户id
        studioRaceModel.setRaceMembers(String.valueOf(SecurityUtils.getUserId()));
        //由于使用mybatis的分页，所以这里要自己手动去查一遍（让自动的分页查询生效）
        studioRaceMapper.selectCount(new LambdaQueryWrapper<StudioRaceModel>().eq(StudioRaceModel::getRaceMembers, studioRaceModel.getRaceMembers()));
        return studioRaceMapper.selectZfRaceModelListByUserId(studioRaceModel);
    }

    /**
     * 根据比赛主键查询ZfRace
     *
     * @param raceId ZfRace主键
     * @return ZfRace
     */
    @Override
    public RaceVo selectZfRaceModelByRaceId(Long raceId) {
        return studioRaceMapper.selectZfRaceModelByRaceId(raceId);
    }

    /**
     * 新增ZfRace（个人、团队）
     *      涉及：竞赛表以及资源表
     *
     * @param raceVo 比赛对象
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean insertZfRaceModel(RaceVo raceVo) {
        //1、插入竞赛表
        raceVo.setCreateTime(DateUtils.getNowDate());  // 设置创建时间
        // 根据是否设置了UserId来判断个人or团队
        if (raceVo.getRaceMembers() == null){
            raceVo.setRaceMembers(String.valueOf(SecurityUtils.getUserId()));  // 设置用户id
            raceVo.setRaceFlag(StudioRaceTypeEnum.RACE_TYPE_OWN.value());  // 个人
        }else {
            raceVo.setRaceFlag(StudioRaceTypeEnum.RACE_TYPE_TEAM.value());  // 团队
        }
        if (studioRaceMapper.insertZfRaceModel(raceVo) > 0) {
            Long raceId = studioRaceMapper.getLastInsertId();//获取新插入比赛记录的id
            List<ResourceVo> pics = raceVo.getPics();
            if (pics.size() == 0) {
                return true;
            }
            //2、资源表插入（根据传入的多个资源）
            boolean result = studioResourceService.insertResources(StudioResourceEnum.RACE_FLAG, raceId, pics);
            if(result){
                return true;
            }
        }
        throw new ServiceException("新增竞赛失败");
    }

    /**
     * 更新fRace（个人、团队）
     *      三步骤：1、更新竞赛表。2、删除资源表相关资源记录。3、重新插入资源记录
     *
     * @param raceVo 比赛对象
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateZfRaceModel(RaceVo raceVo) {
        raceVo.setUpdateTime(DateUtils.getNowDate());
        //1、更新竞赛表
        if (studioRaceMapper.updateZfRaceModel(raceVo) > 0){
            //2、删除相关竞赛的资源记录
            Long[] raceIds = {raceVo.getRaceId()};//将其设置为竞赛id数组传入（目的是直接复用接口）
            studioResourceService.deleteResources(StudioResourceEnum.RACE_FLAG, raceIds);
            //todo 异步去删除远程OSS对象存储的文件
            List<ResourceVo> pics = raceVo.getPics();
            if (pics.size() == 0) {
                return true;
            }
            //3、重新插入新的竞赛资源记录（注意这里是获取raceVo中的raceId）
            if (studioResourceService.insertResources(StudioResourceEnum.RACE_FLAG, raceVo.getRaceId(), pics)) {
                return true;
            }
        }
        throw new ServiceException("修改竞赛失败");
    }

    /**
     * 删除竞赛记录（个人、团队）
     *      2步骤：1、删除竞赛表记录。2、删除资源表
     *
     * @param raceIds 竞赛记录id
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteZfRaceModelByRaceIds(Long[] raceIds) {
        //1、删除竞赛表记录
        if (!doDeleteZfRaceModelByRaceIds(raceIds)){
            throw new ServiceException("删除竞赛失败");
        }
        return true;
    }

    /**
     * 根据竞赛id来批量删除资源
     * @param raceIds
     * @return
     */
    public boolean doDeleteZfRaceModelByRaceIds(Long[] raceIds){
        if (ObjectUtils.isEmpty(raceIds)){
            return true;
        }
        //1、删除竞赛表记录
        if (studioRaceMapper.deleteZfRaceModelByRaceIds(raceIds) > 0){
            //2、删除多条竞赛记录关联的资源表（直接批量删除）
//            for (int i = 0; i < raceIds.length; i++) {
//                zfResourceService.deleteResources(ZfResourceEnum.RACE_FLAG, raceIds[i]);
//            }
            studioResourceService.deleteResources(StudioResourceEnum.RACE_FLAG, raceIds);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteZfRaceModelByUserId(Long userId) {
        //1、查询出所有用户的所有竞赛记录id
        List<Long> tempRaceIds = studioRaceMapper.selectRaceIdsByUserId(userId);
        Long[] raceIds = tempRaceIds.toArray(new Long[tempRaceIds.size()]);
        //2、删除个人竞赛记录中的所有参赛记录
        if (doDeleteZfRaceModelByRaceIds(raceIds)) {
            //todo 3、删除团队竞赛记录及相关联的
            return true;
        }
        return false;
    }




}
