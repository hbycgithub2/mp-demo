package com.itheima.mp.domain.test;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.Data;
import lombok.NoArgsConstructor;


public class ConversionExample {
    public static void main(String[] args) {
        // 源对象初始化
        SourceObject source = new SourceObject();
        source.setName("李四");
        source.setAge(30);
        source.setUserJson("{\"username\":\"张三\", \"score\":85}");

        // JSON转嵌套对象（带字段名映射）
        JSONObject jsonObj = JSONUtil.parseObj(source.getUserJson());
       // CopyOptions options = CopyOptions.create()
              //  .setFieldMapping(Collections.singletonMap("username", "name"));
        User user = jsonObj.toBean(User.class);


        // 属性复制到目标对象
        TargetObject target = new TargetObject();
        BeanUtil.copyProperties(source, target, "userJson");
        target.setUser(user);

        // 输出验证
        System.out.println(JSONUtil.toJsonPrettyStr(target));
    }
}
@Data
@NoArgsConstructor
// 源对象
class SourceObject {
    private String name;
    private Integer age;
    private String userJson; // 示例JSON："{\"username\":\"张三\", \"score\":85}"
}
@Data
@NoArgsConstructor
// 目标对象
class TargetObject {
    private String name;
    private Integer age;
    private User user; // 嵌套对象
}
@Data
@NoArgsConstructor
// JSON对应的嵌套对象
class User {
    private String username;
    private Integer score;
}
