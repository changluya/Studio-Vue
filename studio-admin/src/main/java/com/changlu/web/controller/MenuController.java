package com.changlu.web.controller;

import com.changlu.common.domain.MenuOption;
import com.changlu.common.domain.ResponseResult;
import com.changlu.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @Autowired
    private IMenuService iMenuService;

    /**
     * 根据类别来获取菜单栏目数据
     * @return
     */
    @GetMapping("/{type}")
    public ResponseResult getCcieType(@PathVariable String type){
        List<MenuOption> menuOptions = iMenuService.getMenuOptionsByDictData(type);
        return ResponseResult.success(menuOptions);
    }


}
