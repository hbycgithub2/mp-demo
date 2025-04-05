package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.po.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    /**
     * INSERT INTO tb_user ( id, username, password, phone, info, balance, create_time, update_time ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ? )
     * 5(Long), Tom(String), 123(String), 18688990013(String), {"age":24,"intro":"英文老师","gender":"female"}(String), 200(Integer), 2025-04-04T12:13:55.661884300(LocalDateTime), 2025-04-04T12:13:55.661884300(LocalDateTime)
     */
    @Test
    void testInsert() {
        User user = new User();
         user.setId(5L);
        user.setUsername("Tom");
        user.setPassword("123");
        user.setPhone("18688990013");
        user.setBalance(200);
        user.setInfo(UserInfo.of(24, "英文老师", "female"));
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.insert(user);
    }

    /**
     * SELECT id,username,password,phone,info,status,balance,create_time,update_time FROM tb_user WHERE id=?
     * 5(Long)
     */
    @Test
    void testSelectById() {
        User user = userMapper.selectById(5L);
        System.out.println("user = " + user);
    }

    /**
     * SELECT id,username,password,phone,info,status,balance,create_time,update_time FROM tb_user WHERE id IN ( ? , ? , ? , ? )
     * 1(Long), 2(Long), 3(Long), 4(Long)
     */
    @Test
    void testQueryByIds() {
        List<User> users = userMapper.selectBatchIds(List.of(1L, 2L, 3L, 4L));
        users.forEach(System.out::println);
    }

    /**
     * SELECT * FROM tb_user WHERE id IN ( ? , ? , ? , ? ) LIMIT 10
     * 1(Long), 2(Long), 3(Long), 4(Long)
     */
    @Test
    void testQueryByIds2() {
        List<User> users = userMapper.queryUserByIds(List.of(1L, 2L, 3L, 4L));
        users.forEach(System.out::println);
    }

    /**
     * UPDATE tb_user SET balance=? WHERE id=?
     * 200(Integer), 5(Long)
     */
    @Test
    void testUpdateById() {
        User user = new User();
        user.setId(5L);
        user.setBalance(20000);
        userMapper.updateById(user);
    }

    /**
     * DELETE FROM tb_user WHERE id=?
     * 5(Long)
     */
    @Test
    void testDeleteUser() {
        userMapper.deleteById(5L);
    }

    /**
     * SELECT id,username,info,balance FROM tb_user WHERE (username LIKE ? AND balance >= ?)
     * %o%(String), 1000(Integer)
     */
    @Test
    void testQueryWrapper() {
        // 1.构建查询条件
        QueryWrapper<User> wrapper = new QueryWrapper<User>()
                .select("id", "username", "info", "balance")
                .like("username", "o")
                .ge("balance", 1000);
        // 2.查询
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    /**
     * SELECT id,username,info,balance FROM tb_user WHERE (username LIKE ? AND balance >= ?)
     * %o%(String), 1000(Integer)
     */
    @Test
    void testLambdaQueryWrapper() {
        // 1.构建查询条件
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>()
                .select(User::getId, User::getUsername, User::getInfo, User::getBalance)
                .like(User::getUsername, "o")
                .ge(User::getBalance, 1000);
        // 2.查询
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    /**
     * UPDATE tb_user SET balance=? WHERE (username = ?)
     * 2000(Integer), jack(String)
     */
    @Test
    void testUpdateByQueryWrapper() {
        // 1.要更新的数据
        User user = new User();
        user.setBalance(2000);
        // 2.更新的条件
        QueryWrapper<User> wrapper = new QueryWrapper<User>().eq("username", "jack");
        // 3.执行更新
        userMapper.update(user, wrapper);
    }

    /**
     * UPDATE tb_user SET balance = balance - 200 WHERE (id IN (?,?,?))
     * 1(Long), 2(Long), 4(Long)
     */
    @Test
    void testUpdateWrapper() {
        List<Long> ids = List.of(1L, 2L, 4L);
        UpdateWrapper<User> wrapper = new UpdateWrapper<User>()
                .setSql("balance = balance - 200")
                .in("id", ids);
        userMapper.update(null, wrapper);
    }

    /**
     * UPDATE tb_user SET balance = balance - ? WHERE (id IN (?,?,?))
     * 200(Integer), 1(Long), 2(Long), 4(Long)
     */
    @Test
    void testCustomSqlUpdate() {
        // 1.更新条件
        List<Long> ids = List.of(1L, 2L, 4L);
        int amount = 200;
        // 2.定义条件
        QueryWrapper<User> wrapper = new QueryWrapper<User>().in("id", ids);
        // 3.调用自定义SQl方法
        userMapper.updateBalanceByIds(wrapper, amount);
    }
}