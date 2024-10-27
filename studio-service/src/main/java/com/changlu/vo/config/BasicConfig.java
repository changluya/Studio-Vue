/**
 * @description TODO
 * @author changlu
 * @date 2024/07/28 16:33
 * @version 1.0
 */
package com.changlu.vo.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description  基础配置类
 * @author changlu
 * @date 2024-07-28 16:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    private String siteTitle;
    private String teamTitle;
    private String teamLogo;
    private String unitName;
    private String unitLogo;
    private String ISPN;
    private String siteCreateTime;

}
