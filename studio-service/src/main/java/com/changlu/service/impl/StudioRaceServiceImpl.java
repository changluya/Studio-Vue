package com.changlu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.changlu.common.exception.ServiceException;
import com.changlu.common.utils.DateUtils;
import com.changlu.enums.InclusionTypeEnum;
import com.changlu.mapper.StudioCcieMapper;
import com.changlu.mapper.StudioMUserMapper;
import com.changlu.mapper.StudioRaceMapper;
import com.changlu.security.util.SecurityUtils;
import com.changlu.service.ISysUserService;
import com.changlu.service.StudioRaceService;
import com.changlu.service.StudioResourceService;
import com.changlu.system.pojo.StudioCcieModel;
import com.changlu.system.pojo.SysUser;
import com.changlu.vo.manage.MUserVo;
import com.changlu.vo.race.RaceVo;
import com.changlu.vo.race.ResourceVo;
import com.changlu.enums.StudioRaceTypeEnum;
import com.changlu.enums.StudioResourceEnum;
import com.changlu.system.pojo.StudioRaceModel;
import com.changlu.vo.race.ShowRaceVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

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

    @Resource
    private StudioMUserMapper studioMUserMapper;

    @Resource
    private ISysUserService sysUserService;

    @Override
    public List<RaceVo> selectRaceModelList(StudioRaceModel raceModel) {
        //1、查询到所有竞赛集合
        Long[] raceIds = studioRaceMapper.selectZfRaceIds(); //  解决分页插件给我们查询记录总数问题，所以这里走一遍去查询对应页数的竞赛id
        List<RaceVo> raceVos = new ArrayList<>(raceIds.length);
        if (!ObjectUtils.isEmpty(raceIds)) {
            raceVos = studioRaceMapper.selectZfRaceModelListByRaceIds(raceModel, raceIds);
        }
        //2、查询出所有的用户记录（id、真实姓名）
        List<MUserVo> mUserVos = studioMUserMapper.selectSysUserIdAndRealName();
        Map<Long, String> userMap = mUserVos.stream().collect(Collectors.toMap(MUserVo::getUserId, MUserVo::getRealName));
        //3、遍历所有竞赛，根据对应member_ids来进行合成对应的姓名
        raceVos.stream().forEach(raceVo -> {
            String[] memIds = raceVo.getRaceMembers().split(",");
            StringBuilder teamMemberNames = new StringBuilder("");
            for (int i = 0; i < memIds.length; i++) {
                Long id = Long.valueOf(memIds[i]);
                teamMemberNames.append(userMap.get(id));
                if (i != memIds.length -1 ){
                    teamMemberNames.append(",");
                }
            }
            //生成个人or团队的用户名单
            raceVo.setTeamMemberNames(teamMemberNames.toString());
        });
        return raceVos;
    }

    @Override
    public List<ShowRaceVo> selectShowRaceList() {
        // 筛选竞赛列表
        StudioRaceModel query = new StudioRaceModel();
        query.setInclusionFlag(InclusionTypeEnum.ALREADY_INCLUSION.getVal());//选择已收录
        List<StudioRaceModel> studioRaceModels = studioRaceMapper.selectRaceModelList(query);
        // 查询竞赛列表中包含的所有参与者字符串
        String[] partUserStrArr = studioRaceModels.stream().map(StudioRaceModel::getRaceMembers).toArray(String[]::new);
        Map<String, String> partUsersRealNameMap = sysUserService.selectpartUsersRealNameMap(partUserStrArr);
        // 构建展示竞赛列表记录
        List<ShowRaceVo> showRaceVos = studioRaceModels.stream()
                .map((raceModel -> {
                    ShowRaceVo showRaceVo = new ShowRaceVo();
                    BeanUtils.copyProperties(raceModel, showRaceVo);
                    // 构建参与者姓名集合
                    showRaceVo.setTeamMemberRealNames(partUsersRealNameMap.get(showRaceVo.getRaceMembers()));
                    return showRaceVo;
                }))
                .collect(Collectors.toList());
        return showRaceVos;
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
        // 构建更新对象
        StudioRaceModel newStudioRaceModel = new StudioRaceModel();
        BeanUtils.copyProperties(raceVo, newStudioRaceModel);
        //1、更新竞赛表
        if (studioRaceMapper.updateZfRaceModel(newStudioRaceModel) > 0){
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

    @Override
    public void updateZfRaceModel(StudioRaceModel studioRaceModel) {
        studioRaceMapper.updateZfRaceModel(studioRaceModel);
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

    @Override
    public void updateInclusion(Long id, int behavior) {
        StudioRaceModel updateRaceModel = new StudioRaceModel();
        // 设置竞赛id
        updateRaceModel.setRaceId(id);
        if (behavior == 1) {
            updateRaceModel.setInclusionFlag(InclusionTypeEnum.APPLY_INCLUSION.getVal());
        }else if (behavior == 2) {
            updateRaceModel.setInclusionFlag(InclusionTypeEnum.NO_INCLUSION.getVal());
        }else if (behavior == 3) {
            updateRaceModel.setInclusionFlag(InclusionTypeEnum.ALREADY_INCLUSION.getVal());
        }
        // 更新竞赛
        this.updateZfRaceModel(updateRaceModel);
    }

}
