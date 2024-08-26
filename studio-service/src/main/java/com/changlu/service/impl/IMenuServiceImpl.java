package com.changlu.service.impl;

import com.changlu.common.domain.MenuOption;
import com.changlu.common.exception.ServiceException;
import com.changlu.common.utils.StringUtils;
import com.changlu.enums.DictTypeEnum;
import com.changlu.enums.InclusionTypeEnum;
import com.changlu.service.IMenuService;
import com.changlu.system.pojo.SysDictData;
import com.changlu.system.service.ISysDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IMenuServiceImpl implements IMenuService {

    @Autowired
    private ISysDictDataService iSysDictDataService;


    @Override
    public List<MenuOption> getMenuOptionsByDictData(String type) {
        if (StringUtils.isEmpty(type)) {
            return Collections.emptyList();
        }
        List<MenuOption> res = new ArrayList<>();
        // 若是类别为ccie时
        if ("ccie".equals(type)) {
            // 查询dict_type
            res = this.getMenuOptionsByDictType(DictTypeEnum.CCIE_TYPE.getDictType());
        }else if ("inclusionType".equals(type)) {
            // 查询收录枚举类
            res = Arrays.stream(InclusionTypeEnum.values())
                    .map(inclusionTypeEnum -> new MenuOption(inclusionTypeEnum.getInclusionName(), inclusionTypeEnum.getVal()))
                    .collect(Collectors.toList());
        }
        return res;
    }

    public List<MenuOption> getMenuOptionsByDictType(String dictType) {
        // 校验dictType类型
        List<String> dictTypes = Arrays.stream(DictTypeEnum.values())
                .map(DictTypeEnum::getDictType)
                .collect(Collectors.toList());
        if (!dictTypes.contains(dictType)) {
            throw new ServiceException(String.format("DictTypeEnum not exist dictType %s", dictType));
        }
        // 构建查询条件
        List<MenuOption> res = new ArrayList<>();
        SysDictData queryDictData = new SysDictData();
        queryDictData.setDictType(dictType);
        // 查询到结果之后去构建为MenuOption集合
        List<SysDictData> sysDictDatas = iSysDictDataService.selectDictDataList(queryDictData);
        if (sysDictDatas != null && !sysDictDatas.isEmpty()) {
            res = sysDictDatas.stream()
                    .map((sysDictData) -> new MenuOption(sysDictData.getDictLabel(), Integer.parseInt(sysDictData.getDictValue())))
                    .collect(Collectors.toList());
        }
        return res;
    }
}
