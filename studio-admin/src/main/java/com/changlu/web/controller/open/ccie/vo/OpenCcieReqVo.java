package com.changlu.web.controller.open.ccie.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpenCcieReqVo implements Serializable {

    private static final long serialVersionUID = 1L;

    // 证书类型
    private Integer type;

}
