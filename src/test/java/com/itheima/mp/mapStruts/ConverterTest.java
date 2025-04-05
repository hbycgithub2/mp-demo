package com.itheima.mp.mapStruts;

import com.itheima.mp.converter.UserConverter;
import com.itheima.mp.converter.UserDemoConverter;
import com.itheima.mp.domain.dto.UserDemoDTO;
import com.itheima.mp.domain.dto.UserFormDTO;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.po.UserDemo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@SpringBootTest
public class ConverterTest {
    @Resource
    UserDemoConverter userDemoConverter;
    @Resource
    private UserConverter userConverter;

    /**
     * MapStutrs 将 PO 转为 DTO
     */
    @Test
    void userDemConverter() {
        UserDemo userDemo = new UserDemo();
        userDemo.setId(11L);
        userDemo.setUsername("张三");
        userDemo.setEmail("1311768852");
        userDemo.setCreateTime(LocalDateTime.now());
        userDemo.setStatus(1);
        userDemo.setInfo( "{\"age\":21,\"gender\":\"男\",\"intro\":\"hello world\"}");

        UserDemoDTO dto = userDemoConverter.toDto(userDemo);
        System.out.println(dto);
    }

    /**
     * User(id=null, username=张三, password=123, phone=15911130186,
     * info=UserInfo(age=21, intro=hello world, gender=男),
     * status=null, balance=1000, createTime=null, updateTime=null)
     *
     */
    @Test
    void Converter() {
        UserFormDTO userDTO = new UserFormDTO();
        userDTO.setUsername("张三");
        userDTO.setPassword("123");
        userDTO.setPhone("15911130186");
        userDTO.setInfo("{\"age\":21,\"gender\":\"男\",\"intro\":\"hello world\"}");
        userDTO.setBalance(1000);
        User user = userConverter.toUser(userDTO);
        System.out.println(user);
    }
}
