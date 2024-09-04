package com.changlu.vo;

import com.alibaba.fastjson.JSONObject;
import com.changlu.common.utils.JsonObjectUtil;
import com.changlu.common.utils.StringUtils;
import com.changlu.common.utils.reflect.ReflectUtils;
import com.changlu.config.SysUserExtraConstant;
import com.changlu.enums.DirectionTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName ShowUserVo
 * @Author ChangLu
 * @Date 4/9/2022 5:36 PM
 * @Description 展示用户信息实体类
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowUserVo implements Serializable {

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 角色姓名
     */
    private String roleName;

    /**
     *  个人照片
     */
    private String perImg;

    /**
     * 年级名
     */
    private Integer gradeNum;

    /**
     * 专业名
     */
    private String majorName;

    /**
     * 学院名
     */
    private String academyName;

    /**
     * 描述
     */
    private String description;

    @JsonIgnore
    private String extra;

    public void buildUserVo(){
        //其他字段处理
        //填充null为""，处理两层
        ReflectUtils.nullToEmpty(this, 2);
        //extra字段
        JSONObject jsonObject = JsonObjectUtil.parseStringToJsonObject(this.extra);
        this.executeBuildUserVo(jsonObject);
    }

    protected void executeBuildUserVo(JSONObject jsonObject) {
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ShowStudentUserVo extends ShowUserVo implements Serializable {
        private String directionTypeName;
        private String directionName;
        private String logoImg;

        @Override
        protected void executeBuildUserVo(JSONObject extraJSONObject) {
            InfoVo.StudentExtra studentExtra = JsonObjectUtil.getJSONObjectByKey(extraJSONObject, SysUserExtraConstant.STUDENT_EXTRA, InfoVo.StudentExtra.class);
            String directionTypeName = DirectionTypeEnum.ENROLLED.directionTypeName;
            // 若是有额外字段表示当前已编辑过
            if (studentExtra != null){
                Integer directionType = studentExtra.getDirectionType();
                if (directionType != null){
                    directionTypeName = DirectionTypeEnum.getDirectionTypeName(directionType);
                }
                this.setDirectionTypeName(directionTypeName);
                this.setDirectionName(studentExtra.getDirectionName());
                this.setLogoImg(studentExtra.getLogoImg());
            } else {
                // 若是没有额外字段，默认设置去向为在校
                this.setDirectionTypeName(directionTypeName);
            }
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ShowTeacherUserVo extends ShowUserVo implements Serializable {
        private String teacherMainHref;

        @Override
        protected void executeBuildUserVo(JSONObject extraJSONObject) {
            InfoVo.TeacherExtra teacherExtra = JsonObjectUtil.getJSONObjectByKey(extraJSONObject, SysUserExtraConstant.TEACHER_EXTRA, InfoVo.TeacherExtra.class);
            if (teacherExtra != null){
                this.setTeacherMainHref(teacherExtra.getTeacherMainHref());
            }
        }
    }

}
