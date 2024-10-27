package com.changlu.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/***
 * 菜单选项
 */
@Data
@NoArgsConstructor
public class MenuOption implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private Integer val;

    public MenuOption(String name, Integer val) {
        this.name = name;
        this.val = val;
    }
}
