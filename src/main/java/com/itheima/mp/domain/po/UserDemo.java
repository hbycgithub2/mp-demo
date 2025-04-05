package com.itheima.mp.domain.po;

import lombok.Data;

import java.time.LocalDateTime;
// 用户实体（对应数据库表）
@Data
public class UserDemo {
    private Long id;
    private String username;
    private String email;
    private LocalDateTime createTime;
    private Integer status;  // 0-禁用，1-启用
    private String info;
}
