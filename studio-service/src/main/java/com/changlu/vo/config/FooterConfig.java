/**
 * @description TODO
 * @author changlu
 * @date 2024/07/28 17:12
 * @version 1.0
 */
package com.changlu.vo.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description  网站底部栏目配置类
 * @author changlu
 * @date 2024-07-28 17:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FooterConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    //团队简要描述
    private String briefDescription;
    //联系人
    private String contactPerson;
    //联系地址
    private String contactAddress;
    //联系邮箱
    private String contactEmail;
}
