/**
 * @description TODO
 * @author changlu
 * @date 2024/07/28 16:16
 * @version 1.0
 */
package com.changlu.vo.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description  配置类Vo
 * @author changlu
 * @date 2024-07-28 16:16
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * {
     *    configId: xx      //id表示指定某条配置记录id
     *    configKey: '',    //key用于定位是保存哪个配置
     *    configValue: ''   //value指的是具体配置
     * }
     */
    private Long configId;
    private String configKey;
    private Object configValue;
}
