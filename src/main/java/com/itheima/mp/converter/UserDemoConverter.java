package com.itheima.mp.converter;

import com.itheima.mp.domain.dto.UserDemoDTO;
import com.itheima.mp.domain.po.UserDemo;
import com.itheima.mp.utill.JsonConverter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

//@Mapper(componentModel = "spring", uses = StatusMapper.class)
@Mapper(componentModel = "spring", uses = {StatusMapper.class, JsonConverter.class})  // 使用数组形式添加多个转换器)
public interface UserDemoConverter{

    // 基础映射（自动匹配同名属性）
      @Mapping(source = "username",target = "nickname")
      @Mapping(source = "createTime", target = "createTimeFormat", dateFormat = "yyyy-MM-dd HH:mm")
      @Mapping(target = "info", source = "info", qualifiedByName = "jsonToUserInfo")
      UserDemoDTO toDto(UserDemo user);

    // 集合映射（自动生成循环转换）
    List<UserDemoDTO> toDtoList(List<UserDemo> users);
}

// 状态码转换器（独立类）
class StatusMapper {
    public String statusToDesc(Integer status) {
        return status == 1 ? "启用" : "禁用";
    }
}