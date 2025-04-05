package com.itheima.mp.utill;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.mp.domain.po.UserInfo;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component // 关键：Spring 托管此类

public class JsonConverter {
    // 关键：使用 @Named 注解标记方法，并指定名称
   @Named("jsonToUserInfo")
    public UserInfo jsonToUserInfo(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, UserInfo.class);
    }
}
