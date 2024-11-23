use studio;

-- 网站基础配置 'site.basicConfig'
delete from `sys_config` where config_id = 1;
INSERT INTO `studio`.`sys_config` (`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (1, '网站基础配置', 'site.basicConfig', 'eyJzaXRlVGl0bGUiOiLku7/nlJ/lrp7pqozlrqQiLCJ0ZWFtVGl0bGUiOiLku7/nlJ/lrp7pqozlrqQiLCJ0ZWFtTG9nbyI6Imh0dHA6Ly9waWN0dXJlZC1iZWR0ZXN0Lm9zcy1jbi1iZWlqaW5nLmFsaXl1bmNzLmNvbS90ZXN0L3N0dWRpby81MjYzYmVlNS1kOGNjLTQzYTctYTE5Yy05NGEyMGFlMmNiMTIucG5nIiwiSVNQTiI6IuS6rOWFrOe9keWuieWkhzExMDEyMzIxMjAwMDAwMeWPtyIsInNpdGVDcmVhdGVUaW1lIjoiMjAxNi0xMC0xNCIsInVuaXRMb2dvIjoiaHR0cDovL3BpY3R1cmVkLWJlZHRlc3Qub3NzLWNuLWJlaWppbmcuYWxpeXVuY3MuY29tL3Rlc3Qvc3R1ZGlvLzlmOGI1MzEyLTIzMTgtNGY3MC04OTJmLTRiZTM4YzliNzg1Mi5wbmciLCJ1bml0TmFtZSI6IuWNl+S6rOW3peeoi+WtpumZoiJ9', 'N', 'admin', '2024-07-28 20:01:49', 'admin', '2024-10-01 01:49:53', NULL);

-- 网站参数配置 'site.params'
delete from `sys_config` where config_id = 9;
INSERT INTO `studio`.`sys_config` (`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (9, '网站参数-邀请码', 'site.params.invitecode', 'IkVNYipTeTYi', 'Y', 'admin', '2024-11-23 12:09:30', '', NULL, NULL);

-- 网站页面级初始化配置
-- 网站主页配置 'site.page.mainConfig'
delete from `sys_config` where config_id = 2;
INSERT INTO `studio`.`sys_config` (`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (2, '网站底部栏目配置', 'site.page.footerConfig', 'eyJicmllZkRlc2NyaXB0aW9uIjoi5Y675bm05Zyo5bel5L2c5a6k5YG254S25ZCs5Yiw5YW25LuW5bel5L2c5a6k6KaB5YGa5LiA5Liq5a6Y572R77yM5o6l552A6LaB552A5LiA5pe25YW06LW354S25ZCO5Lmf5om+5LqG5Liq5qih5p2/5p2l5YGa5LqG5LiA5Liq6Ieq5bex55qE5a6Y572R5Lul5Y+K5LiA5Liq5ZCO5Y+w566h55CG57O757uf77yM5b2T5pe255qE5ZCO5Y+w57O757uf5Y+q5pyJ5LiA5Liq55m75b2V44CB5rOo5YaM6L+Y5pyJ5LiA5Liq5o+Q5Lqk5Liq5Lq65L+h5oGv55qE6aG16Z2i77yM5Li76KaB5Li65LqG5pa55L6/5bGV56S65Yqo5oCB5rGH5oC75Zui6Zif5oiQ5ZGY44CC6K+l5bel5L2c5a6k57O757uf5Li76KaB6Z2i5ZCR5qCh5Zut5bel5L2c5a6k77yM5Y+v5L6b5Liq5Lq65Y+K5bel5L2c5a6k5Zui6Zif5a2m5Lmg5L2/55So44CCIiwiY29udGFjdFBlcnNvbiI6IumVv+i3r+iAgeW4iCIsImNvbnRhY3RBZGRyZXNzIjoieHjlpKflraZ4eOWkp+alvHh4eOWxgiIsImNvbnRhY3RFbWFpbCI6IjkzOTk3NDg4M0BxcS5jb20ifQ==', 'N', 'admin', '2024-07-30 23:12:37', 'admin', '2024-07-31 07:51:10', NULL);
-- 网站底部栏目配置 'site.page.footerConfig'
delete from `sys_config` where config_id = 3;
INSERT INTO `studio`.`sys_config` (`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (3, '网站主页配置', 'site.page.mainConfig', 'eyJ0ZWFtRGVzY3JpcHRpb24iOiLov5nph4zmmK/ku7/nlJ/lrp7pqozlrqTvvIzlnKjov5nph4zkuI3ku4XmnInlrabkuaDnoazku7bvvIzov5jmnInlrabkuaDova/ku7bnmoTlsI/kvJnkvLTku6zvvIzmiJHku6zpg73lnKjliY3ov5vnmoTot6/kuIrvvIzmnKrmnaXlgLzlvpfmnJ/lvoXvvIFcbuW3peS9nOWupOacieedgOS4sOWvjOeahOWtpuS5oOi1hOa6kO+8jOacieedgOWPr+S7peW4ruWKqeS9oOino+WGs+mXrumimOeahOWtpumVv+WtpuWnkOS7rOS7peWPiuS4k+S4muaMh+WvvOiAgeW4iO+8jOiuqeS9oOS4jeaWreWcqOS4k+S4mumihuWfn+i/m+ihjOaOoue0ouWSjOaMluaOmOefpeivhuWuneiXj+OAgiIsInRlYW1NaXNzaW9uIjoi5oqK5oiR5Lus55qE5Yqq5Yqb5ZKM55+l6K+G77yM5YyW6Lqr5oiQ5pyJ5Lu35YC855qE5Lqn5ZOB77yM5Zyo5pyq5p2l54mp6IGU572R6aKG5Z+f5byA5ouT5Ye65LiA54mH5paw55qE5aSp5ZywLiIsInRlYW1QbGFuIjoi5aSa5pa55L2N55qE5omp5bGV5oqA5pyv5pa56Z2i55qE5Lq65omN77yM5bCG5oOz5rOV6L+b6KGM6JC95a6eLlxu5LiA5YiH5Lul5ZCE5L2N5ZCM5a2m55qE5Liq5Lq65Y+R5bGV5Li65YeG77yB77yB77yBIiwidGVhbVZpc2lvbiI6Iuacn+acm+aJgOacieeahOaIkOWRmOiDveWkn+WtpuacieaJgOaIkO+8jOWcqOWQhOiHqueahOmihuWfn+WPkeWFieWPkeS6ru+8jOWPluW+l+S8mOengOeahOaIkOe7qS4iLCJiYW5uZXJUYWJsZURhdGEiOlt7ImJhbm5lckltZyI6Imh0dHBzOi8vcGljdHVyZWQtYmVkLm9zcy1jbi1iZWlqaW5nLmFsaXl1bmNzLmNvbS9pbWcvMjAyNC8wNzEucG5nIiwibWFpblRpdGxlIjoi5qyi6L+O5p2l5Yiw5Lu/55Sf5a6e6aqM5a6kIiwic3ViVGl0bGUiOiLkuIDnvqTlv5flkIzpgZPlkIjnmoTkurrvvIzkuIDotbfliqDmsrnvvIzkuIDotbflpZTot5HlnKjnkIbmg7PnmoTot6/kuIouLi4ifSx7ImJhbm5lckltZyI6Imh0dHBzOi8vcGljdHVyZWQtYmVkLm9zcy1jbi1iZWlqaW5nLmFsaXl1bmNzLmNvbS9pbWcvMjAyNC8wNzIucG5nIiwibWFpblRpdGxlIjoi5YWz5LqO5oiR5LusIiwic3ViVGl0bGUiOiLmnIDlpb3nmoTlm6LpmJ/vvIzmnIDlpb3nmoTmiJHku6zvvIzkuI3otJ/pn7bljY7vvIzliqrlipvlpYvmlpfjgIIifSx7ImJhbm5lckltZyI6Imh0dHBzOi8vcGljdHVyZWQtYmVkLm9zcy1jbi1iZWlqaW5nLmFsaXl1bmNzLmNvbS9pbWcvMjAyNC8wNzMucG5nIiwibWFpblRpdGxlIjoi5pe25YWJ6L20Iiwic3ViVGl0bGUiOiLml7bpl7TmmK/muKnmn5TnmoTnvr3mr5vvvIzmiorov4flvoDnmoTngbDlsJjovbvovbvlvLnljrvjgIIifSx7ImJhbm5lckltZyI6Imh0dHBzOi8vcGljdHVyZWQtYmVkLm9zcy1jbi1iZWlqaW5nLmFsaXl1bmNzLmNvbS9pbWcvMjAyNC8wNzQucG5nIiwibWFpblRpdGxlIjoi5Zui6ZifIiwic3ViVGl0bGUiOiLmi43nhaflj6rpnIDopoHkuInnp5LvvIzlj6/plIHkvY/nmoTmmK/miJHku6zkuInlubTpnZLmmKXvvIzmhJ/osKLpgYfop4HvvIEifV19', 'N', 'admin', '2024-07-30 22:44:24', 'admin', '2024-09-15 21:57:06', NULL);
-- 时光轴配置 'site.page.timeConfig'
delete from `sys_config` where config_id = 4;
INSERT INTO `studio`.`sys_config` (`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (4, '时光轴配置', 'site.page.timeConfig', 'W3siZGVzY3JpcHRpb24iOiLojrflvpfkuobnianogZTnvZHlpKfotZvkuInnrYnlpZbjgIHorqHnrpfmnLrorr7orqHlpKfotZvkuIDnrYnlpZbkuIDnrYnlpZbnrYnorrjlpJrlpZbpobkiLCJ0aXRsZSI6IuWwj+acieaIkOWwsSIsInllYXIiOiIyMDE55bm0In0seyJkZXNjcmlwdGlvbiI6IuWcqHh456ue6LWb44CB5aSn5a2m55Sf5Yib5paw5Yib5Lia5aSn6LWb562J5q+U6LWb5Lit5byA5aeL5bSt6Zyy5aS06KeSIiwidGl0bGUiOiLliJ3lh7rojIXlupAiLCJ5ZWFyIjoiMjAxNy0yMDE45bm0In0seyJkZXNjcmlwdGlvbiI6IuWtpuagoeivhOWuoeWPluW+l+W3peS9je+8jOiOt+ecgeeJqeiBlOe9keWkp+i1m+KAnOaWsOWkp+mZhuadr+KAneS6jOetieWlluOAgeecgee6p+iuoeeul+acuuiuvuiuoeWkp+i1m+S4ieetieWlluetiSIsInRpdGxlIjoi5YaN6L+b5LiA5q2lIiwieWVhciI6IjIwMjDlubQifSx7ImRlc2NyaXB0aW9uIjoi5Y+C5LiO5LqG54mp6IGU572R55yB57qn5oqA6IO956ue6LWb77yM5aSn5a2m55Sf5Yib5paw5Yib5Lia5aSn6LWb77yM5Lq65bel5pm66IO95oqA5pyv5pyN5Yqh5oqA6IO95aSn6LWb77yM5LqS6IGU572RK+etieWkmumhueavlOi1m+OAguiOt+WPlk5CLUlPVOS/oeWPt+ajgOa1i+S7qui9r+iRl++8jOaxn+iLj+ecgeWkp+WtpueUn+WIm+aWsOWIm+S4muiuree7g+iuoeWIkumhueebruS8mOengOWlluetiSIsInRpdGxlIjoi5Yid6Zyy6ZSL6IqSIiwieWVhciI6IjIwMjHlubQifSx7ImRlc2NyaXB0aW9uIjoi55ar5oOF5omT5Ye777yM57uE57uH57q/5LiK5a2m5Lmg44CC5Y+C5LiO5LqGeHh455yB57qn5oqA6IO956ue6LWb77yM5aSn5a2m55Sf5Yib5paw5Yib5Lia5aSn6LWb77yM5rGf6IuP55yB5aSn5a2m55Sf5Yib5paw5Yib5Lia6K6t57uD6K6h5YiS6aG555uu77yM5LqS6IGU572RK+etieOAguiOt+WPluiNo+iqieacie+8muWFqOWbveWkp+WtpueUn+aVsOWtpuW7uuaooeernui1m+axn+iLj+ecgeS4gOetieWllu+8jOWQjuWPsHdlYuOAgWFwcOi9r+iRl++8jOWkmuS4queglOeptuaAp+ivvumimOe7k+mimOivgeS5pu+8jOaxn+iLj+ecgeWkp+WtpueUn+WIm+aWsOWIm+S4muiuree7g+iuoeWIkumhueebruS8mOengOe7k+mimOetieOAgiIsInRpdGxlIjoi5YaN5LiL5LiA5Z+OIiwieWVhciI6IjIwMjLlubQifSx7ImRlc2NyaXB0aW9uIjoi5LiA6Lev6auY5q2MIiwidGl0bGUiOiLlhrLlirLljYHotrMiLCJ5ZWFyIjoiMjAyM+W5tCJ9XQ==', 'N', 'admin', '2024-07-30 23:19:23', 'admin', '2024-09-30 12:46:33', NULL);

-- 网站上传配置
-- 文件上传配置选项 'site.upload.option'
delete from `sys_config` where config_id = 5;
INSERT INTO `studio`.`sys_config` (`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (5, '文件上传配置选项', 'site.upload.option', 'InNpdGUudXBsb2FkLmZpbGUi', 'N', 'admin', '2024-07-31 07:54:20', 'admin', '2024-08-01 00:13:53', NULL);
-- 本地文件上传配置 'site.upload.file'
delete from `sys_config` where config_id = 6;
INSERT INTO `studio`.`sys_config` (`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (6, '本地文件上传配置', 'site.upload.file', 'eyJmaWxlVXBsb2FkRGlyIjoiL3RtcC9zdGF0aWMvIiwiaXAiOiIxOTIuNDYuOTIuMTM2IiwicG9ydCI6IiIsInByb3RvY29sIjoiaHR0cCIsInZpc2l0UGF0aCI6Ii9zdHVkaW8vc3RhdGljLyJ9', 'N', 'admin', '2024-08-01 00:12:18', 'admin', '2024-08-01 00:13:17', NULL);
-- OSS资源上传配置 'site.upload.oss'
delete from `sys_config` where config_id = 7;
INSERT INTO `studio`.`sys_config` (`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (7, 'OSS资源上传配置', 'site.upload.oss', 'eyJlbmRwb2ludCI6IjEiLCJhY2Nlc3NLZXlJZCI6IjEiLCJhY2Nlc3NLZXlTZWNyZXQiOiIxIiwiYnVja2V0TmFtZSI6IjEiLCJrZXkiOiIxIn0=', 'N', 'admin', '2024-08-01 00:16:44', 'admin', '2024-08-01 00:18:45', NULL);
-- 技术栈配置 'site.page.skillConfig'
delete from `sys_config` where config_id = 8;
INSERT INTO `studio`.`sys_config` (`config_id`, `config_name`, `config_key`, `config_value`, `config_type`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (8, '技术栈配置', 'site.page.skillConfig', 'W3sic2tpbGxOYW1lIjoib3BlbkNWIiwic2tpbGxQZXJjZW50YWdlIjoxMDB9LHsic2tpbGxOYW1lIjoiSmF2YSIsInNraWxsUGVyY2VudGFnZSI6NjB9LHsic2tpbGxOYW1lIjoi572R56uZ5byA5Y+RIiwic2tpbGxQZXJjZW50YWdlIjozMH0seyJza2lsbE5hbWUiOiLkuIrkvY3mnLoiLCJza2lsbFBlcmNlbnRhZ2UiOjYwfV0=', 'N', 'admin', '2024-09-15 20:27:11', 'admin', '2024-09-15 22:33:43', NULL);