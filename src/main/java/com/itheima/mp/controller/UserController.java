package com.itheima.mp.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.ConverterRegistry;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.itheima.mp.converter.UserConverter;
import com.itheima.mp.domain.dto.PageDTO;
import com.itheima.mp.domain.dto.UserFormDTO;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.po.UserInfo;
import com.itheima.mp.domain.query.UserQuery;
import com.itheima.mp.domain.vo.UserVO;
import com.itheima.mp.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "用户管理接口")
@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UserController {
    @Resource
    private UserConverter userConverter;
    private final IUserService userService;
    @ApiOperation("新增用户接口")
    @PostMapping
    public void saveUser(@RequestBody UserFormDTO userDTO){
        User user = userConverter.toUser(userDTO);
        // 2.新增
        userService.save(user);
    }

    @ApiOperation("新增用户接口2")
    @PostMapping
    public void saveUser2(@RequestBody UserFormDTO userDTO){
        UserInfo info = new UserInfo();
        if (null != userDTO && StrUtil.isNotBlank(userDTO.getInfo())) {
            info = JSONUtil.parseObj(userDTO.getInfo()).toBean(UserInfo.class);
        }
        // 1.把DTO拷贝到PO
        User user = BeanUtil.copyProperties(userDTO, User.class,"info");
        user.setInfo(info);
        // 2.新增
        userService.save(user);
    }

    @ApiOperation("删除用户接口")
    @DeleteMapping("{id}")
    public void deleteUserById(@ApiParam("用户id") @PathVariable("id") Long id){
        userService.removeById(id);
    }

    @ApiOperation("根据id查询用户接口")
    @GetMapping("{id}")
    public UserVO queryUserById(@ApiParam("用户id") @PathVariable("id") Long id){
        return userService.queryUserAndAddressById(id);
    }

    @ApiOperation("根据id批量查询用户接口")
    @GetMapping
    public List<UserVO> queryUserByIds(@ApiParam("用户id集合") @RequestParam("ids") List<Long> ids){
        return userService.queryUserAndAddressByIds(ids);
    }

    @ApiOperation("扣减用户余额接口")
    @PutMapping("/{id}/deduction/{money}")
    public void deductBalance(
            @ApiParam("用户id") @PathVariable("id") Long id,
            @ApiParam("扣减的金额") @PathVariable("money") Integer money){
        userService.deductBalance(id, money);
    }

    @ApiOperation("根据复杂条件查询用户接口")
    @GetMapping("/list")
    public List<UserVO> queryUsers(UserQuery query){
        // 1.查询用户PO
        List<User> users = userService.queryUsers(
                query.getName(), query.getStatus(), query.getMinBalance(), query.getMaxBalance());
        // 2.把PO拷贝到VO
        return BeanUtil.copyToList(users, UserVO.class);
    }

    @ApiOperation("根据条件分页查询用户接口")
    @GetMapping("/page")
    public PageDTO<UserVO> queryUsersPage(UserQuery query){
       return userService.queryUsersPage(query);
    }
}
