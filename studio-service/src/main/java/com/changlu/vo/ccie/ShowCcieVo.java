package com.changlu.vo.ccie;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @description  证书展示
 * @author changlu
 * @date 2024-08-31 10:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowCcieVo implements Serializable {
    private static final long serialVersionUID = 1L;

    // 来源于StudioCcieModel
    /** 获奖证书id */
    private Long ccieId;

    /** 获奖证书名称 */
    private String ccieName;

    /** 获奖证书图片 */
    private String ccieImg;

    /** 获奖获奖时间 */
    @JsonFormat(pattern="yyyy年MM月dd日")
    private Date ccieGetTime;

    /** 证书拥有者用户id */
    private Long userId;

    // 其他需要单独构建
    /** 拥有者用户姓名 */
    private String userName;



}
