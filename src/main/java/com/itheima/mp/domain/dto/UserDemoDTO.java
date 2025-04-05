package com.itheima.mp.domain.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.itheima.mp.domain.po.UserInfo;
import lombok.Data;

@Data
// 用户 DTO（返回给前端的对象）
public class UserDemoDTO {
    private Long id;
    private String nickname;  // 与实体字段名不一致
    private String email;
    private String createTimeFormat;  // 需要日期格式化
    private String statusDesc;  // 状态码转文字
    @TableField(typeHandler = JacksonTypeHandler.class)
    private UserInfo info;
}
