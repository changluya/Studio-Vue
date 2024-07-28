/**
 * @description TODO
 * @author changlu
 * @date 2024/07/28 16:38
 * @version 1.0
 */
package com.changlu.vo.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @description  时光轴
 * @author changlu
 * @date 2024-07-28 16:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class teamTime implements Serializable {
        private static final long serialVersionUID = 1L;
        private String year;
        private String title;
        private String description;
    }
    private List<teamTime> timeTableData;

}
