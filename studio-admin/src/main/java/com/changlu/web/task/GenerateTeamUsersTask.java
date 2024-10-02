package com.changlu.web.task;

import com.changlu.common.utils.RedisCache;
import com.changlu.common.utils.StringUtils;
import com.changlu.config.StudioConstant;
import com.changlu.mapper.StudioMUserMapper;
import com.changlu.vo.ShowUserVo;
import com.changlu.enums.StudioRoleEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @ClassName GenerateTeamUsersTask
 * @Author ChangLu
 * @Date 4/9/2022 5:12 PM
 * @Description 生成团队用户信息
 */
@Component
@Slf4j
public class GenerateTeamUsersTask {

    @Resource
    private StudioMUserMapper studioMUserMapper;

    @Autowired
    private RedisCache redisCache;

    public static void main(String[] args) {
        // 创建Timestamp对象
        Timestamp specificTimestamp = Timestamp.valueOf("2023-08-01 16:48:56");

        // 获取时间戳
        long timestamp = specificTimestamp.getTime();

        // 打印时间戳
        System.out.println("Timestamp: " + timestamp);
    }

//    @PostConstruct  //初始化servlet执行一次
    @Async("taskExecutor")  //执行异步任务
    public void doGenerateTask(){
        log.info(Thread.currentThread().getName()+ "commitUserInfo");
        List<Map<String, Object>> result = doGenerateTeamUsers();
        redisCache.setCacheObject(StudioConstant.REDIS_MEMBERS_DATA, result, 3, TimeUnit.MINUTES);
        log.info("已生成用户信息");
    }

    /**
     * 动态生成成员信息
     * @return
     * @throws JsonProcessingException
     */
    public List<Map<String, Object>> doGenerateTeamUsers() {
        synchronized (this) {
            //得到锁之后需要去缓存中再去尝试查询一次
            List<Map<String, Object>> result = redisCache.getCacheObject(StudioConstant.REDIS_MEMBERS_DATA);
            //若是能够从缓存中查询到直接返回
            if (result != null) {
                return result;
            }
            return generateTeamUsers();
        }
    }

    /**
     *
     * @return
     */
    public List<Map<String, Object>> generateTeamUsers() {
        // 1、查询一组展示用户列表记录
        List<ShowUserVo> showUserVos = studioMUserMapper.selectShowUserVoList();
        if (CollectionUtils.isEmpty(showUserVos)) {
            return Collections.emptyList();
        }
        List<Map<String, Object>> teamUsersRes = new ArrayList<>(showUserVos.size());
        // 2、初始用户记录筛选
        showUserVos = beforeFilterUsers(showUserVos);
        // 3、构建老师组
        buildTeacherMembers(teamUsersRes, showUserVos);
        // 4、构建学生组
        buildStudentMembers(teamUsersRes, showUserVos);
        return teamUsersRes;
    }

    /**
     * 过滤情况：1、为null的用户列表。2、真实姓名realName为空的。
     * @param showUserVos 查询出来的展示用户列表
     * @return 过滤完的展示用户列表
     */
    private List<ShowUserVo> beforeFilterUsers(List<ShowUserVo> showUserVos) {
        return showUserVos.stream()
                .filter(Objects::nonNull)
                .filter(user -> StringUtils.isNotEmpty(user.getRealName()))
                .collect(Collectors.toList());
    }

    //构建指导老师列表
    public void buildTeacherMembers(List<Map<String, Object>> result, List<ShowUserVo> showUserVos){
        //获取到指导老师列表（转为ShowTeacherUserVo对象）
        List<ShowUserVo> teachers = showUserVos.stream()
                .filter(user -> user.getRoleName() != null && user.getRoleName().contains(StudioRoleEnum.ROLE_TEACHER.getName())) //只拿到指导老师
                .map((user)->{
                    ShowUserVo.ShowTeacherUserVo showTeacherUserVo = new ShowUserVo.ShowTeacherUserVo();
                    BeanUtils.copyProperties(user, showTeacherUserVo);
                    return showTeacherUserVo;
                })
                .collect(Collectors.toList());
        if (teachers.isEmpty()) {
            return;
        }
        for (ShowUserVo teacher : teachers) {
            //将其转为学生对象
            if (ObjectUtils.isEmpty(teacher.getRoleName()) || //为空
                    teacher.getRoleName().contains(String.valueOf(StudioRoleEnum.ROLE_TEACHER.getName())) //包含"指导老师"
            ) {
                teacher.setRoleName(StudioRoleEnum.ROLE_TEACHER.getName());
            }
            //统一处理额外属性
            teacher.buildUserVo();
        }
        Map<String, Object> teacherGroup = new HashMap<>(1);
        teacherGroup.put("grade", StudioRoleEnum.ROLE_TEACHER.getName());
        teacherGroup.put("members", sortedByShowSort(teachers));
        result.add(teacherGroup);
    }

    //构建学生
    private void buildStudentMembers(List<Map<String, Object>> result, List<ShowUserVo> showUserVos){
        //1、对user字段进行替换（转为ShowStudentUserVo对象）
        List<ShowUserVo> studentUserVo = showUserVos.stream()
                .filter(user -> user.getRoleName() != null && !user.getRoleName().contains(StudioRoleEnum.ROLE_TEACHER.getName())) //去除指导老师
                .map((user)->{
                    ShowUserVo.ShowStudentUserVo showStudentUserVo = new ShowUserVo.ShowStudentUserVo();
                    BeanUtils.copyProperties(user, showStudentUserVo);
                    return showStudentUserVo;
                })
                .collect(Collectors.toList());
        for (ShowUserVo user: studentUserVo) {
            // 若是角色为空
            if (ObjectUtils.isEmpty(user.getRoleName())) {
                user.setRoleName("成员");
                continue;
            }
            if (user.getRoleName().contains(String.valueOf(StudioRoleEnum.ROLE_MANAGE.getName()))) {
                user.setRoleName(StudioRoleEnum.ROLE_MANAGE.getName());
            }
            user.buildUserVo();
        }
        //2、根据年级进行分组以及降序处理
        //key是年级，根据年级来进行降序排序
        TreeMap<Integer, List<ShowUserVo>> sortedMap = new TreeMap<>((o1, o2)-> o2-o1);//降序排序
        Map<Integer, List<ShowUserVo>> map = studentUserVo.stream()
                .filter(userVo -> userVo.getGradeNum() != null) //过滤掉年级为null的
                .collect(Collectors.groupingBy(ShowUserVo::getGradeNum));//根据年级来进行分组
        sortedMap.putAll(map);
        //3、构造并添加学生
        for (Map.Entry<Integer, List<ShowUserVo>> entry : sortedMap.entrySet()) {
            Map<String,Object> gradeMap = new HashMap<>(2);
            Integer gradeNum = entry.getKey();
            List<ShowUserVo> members = sortedStudent(entry.getValue());
            gradeMap.put("grade", gradeNum + "级");
            gradeMap.put("members", members);
            result.add(gradeMap);
        }
    }

    /**
     * 将每个年级的负责人进行重新排序
     * @param members 年级的一组成员
     * @return
     */
    private List<ShowUserVo> sortedStudent(List<ShowUserVo> members) {
        List<ShowUserVo> result = new ArrayList<>(members.size());
        // 获取到管理员、学生一组列表
        List<ShowUserVo> manageUsers = new ArrayList<>();
        List<ShowUserVo> memberUsers = new ArrayList<>();
        members.forEach(member->{
            String roleName = member.getRoleName();
            if (roleName != null && roleName.contains(StudioRoleEnum.ROLE_MANAGE.getName())) {
                manageUsers.add(member);
            }else {
                memberUsers.add(member);
            }
        });
        // 优先添加管理员，其次添加团队成员
        result.addAll(sortedByShowSort(manageUsers));
        result.addAll(sortedByShowSort(memberUsers));
        return result;
    }

    /**
     * 根据showSort由高到低依次排序
     * @param members 所有成员
     * @return
     */
    private List<ShowUserVo> sortedByShowSort(List<ShowUserVo> members) {
        if (CollectionUtils.isEmpty(members)) {
            return Collections.emptyList();
        }
        // 过程：过滤为null的 =》由showSort由低到高排序反转（即高到低排序）=》收集list返回
        // 总结：根据showSort，越高越优先排列
        return members.stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparingInt(ShowUserVo::getShowSort).reversed())
                .collect(Collectors.toList());
    }

}
