package com.changlu.service;


import com.changlu.common.domain.MenuOption;

import java.util.List;

public interface IMenuService {

    List<MenuOption> getMenuOptionsByDictData(String type);

}
