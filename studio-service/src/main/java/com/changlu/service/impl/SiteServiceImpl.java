package com.changlu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.changlu.common.utils.DateUtils;
import com.changlu.enums.ConfigTypeEnum;
import com.changlu.enums.InclusionTypeEnum;
import com.changlu.enums.UserStatusEnum;
import com.changlu.enums.StudioRaceTypeEnum;
import com.changlu.mapper.IndexCountMapper;
import com.changlu.mapper.StudioCcieMapper;
import com.changlu.mapper.SchoolMajorMapper;
import com.changlu.mapper.StudioRaceMapper;
import com.changlu.service.*;
import com.changlu.system.mapper.StudioAchievementMapper;
import com.changlu.system.mapper.SysUserMapper;
import com.changlu.system.pojo.*;
import com.changlu.vo.config.BasicConfig;
import com.changlu.vo.config.ConfigVo;
import com.changlu.vo.site.BasicSiteStatisticsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName IndexServiceImpl
 * @Author ChangLu
 * @Date 4/9/2022 2:23 PM
 * @Description 首页展示业务层
 */
@Service
public class SiteServiceImpl implements SiteService {

    @Autowired
    private SiteConfigService siteConfigService;

    @Resource
    private SysUserMapper userMapper;

    @Autowired
    private ISysUserService sysUserService;

    @Resource
    private StudioRaceMapper studioRaceMapper;

    @Resource
    private StudioCcieMapper studioCcieMapper;

    @Resource
    private SchoolMajorMapper schoolMajorMapper;

    @Resource
    private IndexCountMapper indexCountMapper;

    @Resource
    private IStudioAchievementService studioAchievementService;

    @Autowired
    private StudioCcieService studioCcieService;

    @Autowired
    private StudioRaceService studioRaceService;


    /**
     * 获取统计数据
     */
    @Override
    public List<Integer> getCounts() {
        //1、获取用户总数
        LambdaQueryWrapper<SysUser> userWrapper = new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getStatus, UserStatusEnum.ACTIVE.value())
                .ne(SysUser::getUserId, 1L);//系统管理员不计入总数
        Integer userCount = this.userMapper.selectCount(userWrapper);
        //2、获取项目总数(团队竞赛)
        LambdaQueryWrapper<StudioRaceModel> teamRaceWrapper = new LambdaQueryWrapper<StudioRaceModel>()
                .eq(StudioRaceModel::getRaceFlag, StudioRaceTypeEnum.RACE_TYPE_TEAM.value());
        Integer projectCount = studioRaceMapper.selectCount(teamRaceWrapper);
        //3、获取竞赛总数(个人竞赛)
        LambdaQueryWrapper<StudioRaceModel> ownRaceWrapper = new LambdaQueryWrapper<StudioRaceModel>()
                .eq(StudioRaceModel::getRaceFlag, StudioRaceTypeEnum.RACE_TYPE_OWN.value());
        Integer raceCount = studioRaceMapper.selectCount(ownRaceWrapper);
        //4、获取获奖总数
        Integer ccieCount = studioCcieMapper.selectCount(null);
        List<Integer> counts = Arrays.asList(userCount, projectCount, raceCount, ccieCount);
        return counts;
    }

    @Override
    public BasicSiteStatisticsVo getBasicSiteStatistics() {
        // 1、统计成立天数
        long establishmentDays = getEstablishmentDays();
        // 2、统计团队成员数
        int temMembersNum = sysUserService.countTeamUser();
        // 3、统计网站成果数（已收录）
        int achievementsNum = studioAchievementService.countAlreadyInclusionAchievement();
        // 4、统计荣誉证书数（已收录）
        int cciesNum = studioCcieService.countAlreadyInclusionCcie();
        // 5、统计竞赛（已收录）
        int racesNum = studioRaceService.countAlreadyInclusionRace();
        // 构建统计对象
        BasicSiteStatisticsVo statisticsVo = BasicSiteStatisticsVo.builder()
                .establishmentDaysNum(establishmentDays)
                .teamMembersNum(temMembersNum)
                .achievementsNum(achievementsNum)
                .cciesNum(cciesNum)
                .racesNum(racesNum).build();
        return statisticsVo;
    }

    /**
     * 获取成立天数，动态计算成立以来截止到今天的天数
     * @return 成立天数
     */
    @Override
    public long getEstablishmentDays() {
        ConfigVo configVo = siteConfigService.selectConfigValueByConfigKey(ConfigTypeEnum.SITE_BASICCONFIG.getConfigKey());
        BasicConfig configValue = (BasicConfig) configVo.getConfigValue();
        // 获取网站配置中的成立时间 yyyy-MM-dd
        String siteCreateTime = configValue.getSiteCreateTime();
        LocalDate creationDate = LocalDate.parse(siteCreateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate today = LocalDate.now();
        // 使用 ChronoUnit获取更精确的天数计算
        long establishmentDays = ChronoUnit.DAYS.between(creationDate, today);
        return  establishmentDays;
    }

    /**
     * 获取echarts的options数据，数据格式如下：
     *  testJSON: {
     *    "grades":{
     *      names: ["2019级", "2020级", "2021级", "2022级"],
     *      values:{
     *        races: [5, 20, 36, 10],
     *        ccies: [5, 20, 36, 10]
     *      }
     *    },
     *    "majors":{
     *      names: ["计算机应用技术", "物联网应用技术", "大数据技术", "移动应用开发","软件技术","计算机网络技术","VR虚拟现实"],
     *      values:{
     *        races: [5, 20, 36, 10, 10,15,20],
     *        ccies: [5, 20, 100, 10, 10,15,20]
     *      }
     *    }
     *  }
     **/
    @Override
    public Map<String, Object> getOptions() {
        Map<String,Object> optionsResult = new HashMap<>(2);
        //1、统计年级
        //1.1、names
        SimpleDateFormat format = new SimpleDateFormat("YYYY");
        Integer beginYear = Integer.valueOf(format.format(DateUtils.minusDate(new Date(), Calendar.YEAR, 3)));
        Integer endYear = Integer.valueOf(format.format(new Date()));
        List<String> gradeNames = new ArrayList<>(endYear - beginYear);
        for (int YEAR = beginYear; YEAR <= endYear; YEAR++) {
            gradeNames.add(YEAR + "年");
        }
        Map<String,Object> grades = new HashMap<>(2);
        optionsResult.put("grades", grades);
        grades.put("names", gradeNames);
        Map<String,Object> gradeValues = new HashMap<>(2);
        grades.put("values", gradeValues);
        //1.2、values：races
        gradeValues.put("races", indexCountMapper.countRacesByGrade(beginYear, endYear));
        //1.2、values：ccies
        gradeValues.put("ccies", indexCountMapper.countCciesByGrade(beginYear, endYear));

        //2、统计专业
        //2.1、names
        LambdaQueryWrapper<SchoolMajorModel> majorWrapper = new LambdaQueryWrapper<>(SchoolMajorModel.class)
                .select(SchoolMajorModel::getMajorName)
                .orderByAsc(SchoolMajorModel::getMajorId);//根据专业id来进行排序
        List<SchoolMajorModel> majorModels = schoolMajorMapper.selectList(majorWrapper);
        List<String> majorNames = majorModels.stream().map((major) -> major.getMajorName()).collect(Collectors.toList());
        Map<String,Object> majors = new HashMap<>(2);
        optionsResult.put("majors", majors);
        majors.put("names", majorNames);
        Map<String,Object> majorValues = new HashMap<>(2);
        majors.put("values", majorValues);
        //2.2、values：races
        majorValues.put("races", indexCountMapper.countRacesByMajor());
        //2.2、values：ccies
        majorValues.put("ccies", indexCountMapper.countCciesByMajor());
        return optionsResult;
    }
}
