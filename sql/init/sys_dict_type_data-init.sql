use studio;
-- sys_dict_type与sys_dict_data一一对应
delete from `sys_dict_type` where `dict_id` = 1;
-- 证书专业资质类别（对应studio_ccie的type字段）
INSERT INTO `sys_dict_type` (`dict_id`, `dict_name`, `dict_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
    VALUES (1, '证书专业资质类别', 'CCIE_TYPE', '0', 'admin', '2024-08-24 22:20:14', '', NULL, '证书专业资质类别');

-- 证书专业资质类别-值
delete from `sys_dict_data` where `dict_code` in (1, 2, 3);
INSERT INTO `sys_dict_data` (`dict_code`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
    VALUES (1, 1, '专利', '1', 'CCIE_TYPE', '', '', 'Y', '0', 'admin', '2024-08-24 22:20:14', '', NULL, '证书类别-专利');
INSERT INTO `sys_dict_data` (`dict_code`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
    VALUES (2, 2, '论文', '2', 'CCIE_TYPE', '', '', 'Y', '0', 'admin', '2024-08-24 22:20:14', '', NULL, '证书类别-论文');
INSERT INTO `sys_dict_data` (`dict_code`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`)
    VALUES (3, 3, '软著', '3', 'CCIE_TYPE', '', '', 'Y', '0', 'admin', '2024-08-24 22:20:14', '', NULL, '证书类别-软著');
