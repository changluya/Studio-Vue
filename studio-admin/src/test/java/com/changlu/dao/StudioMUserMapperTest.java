package com.changlu.dao;
import com.changlu.mapper.StudioMUserMapper;
import com.changlu.vo.ShowUserVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class StudioMUserMapperTest {

    @Autowired
    private StudioMUserMapper studioMUserMapper;

    /**
     * 示例：
     * ShowUserVo(realName=茅津菁, roleName=成员,指导老师, perImg=http://127.0.0.1:8999/studio/static/6f442080-e147-4fd3-a3c9-ba7472eb0966.jpg, gradeNum=2023, majorName=计算机科学, academyName=计算机工程学院, description=, extra={"teacherExtra":{"teacherMainHref":"https://www.tsinghua.edu.cn/info/1333/108942.htm"}}, showSort=0)
     */
    @Test
    public void select() {
        List<ShowUserVo> showUserVos = studioMUserMapper.selectShowUserVoList();
        showUserVos.forEach(System.out::println);
    }

}
