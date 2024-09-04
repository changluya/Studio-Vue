package com.changlu.vo;

import com.changlu.system.pojo.SysRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.management.relation.Role;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName InfoVo
 * @Author ChangLu
 * @Date 3/31/2022 12:55 PM
 * @Description 用于接收Info层传来的对象
 */



@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfoVo implements Serializable {



    /** 真实姓名 */
    private String realName;

    /** 个人描述 */
    private String description;

    /** 个人照片 */
    private String perImg;

    /** 学院id */
    private Long academyId;

    /** 专业id */
    private Long majorId;

    /** 年纪id */
    private Long gradeId;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TeacherExtra implements Serializable {
        private static final long serialVersionUID = 1L;
        private String teacherMainHref;
    }

    private TeacherExtra teacherExtra;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StudentExtra implements Serializable {
        private static final long serialVersionUID = 1L;
        private Integer directionType;
        private String directionName;
        private String logoImg;
    }

    private StudentExtra studentExtra;

    //用于查询
    /** 角色 */
    private String roleNames;

    public static String getRoleNames(List<SysRole> roles) {
        String rolesNames = roles.stream()
                .map(SysRole::getRoleKey)
                .collect(Collectors.joining(","));
        return rolesNames;
    }


}
