package com.itheima.mp.converter;

import com.itheima.mp.domain.dto.UserFormDTO;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.utill.JsonConverter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {JsonConverter.class})  // 使用数组形式添加多个转换器)
public interface UserConverter {
    @Mapping(source = "username",target = "username")
    @Mapping(target = "info", source = "info", qualifiedByName = "jsonToUserInfo")
    User toUser(UserFormDTO userDTO);
}
