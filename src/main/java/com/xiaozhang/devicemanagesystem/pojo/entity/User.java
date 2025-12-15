package com.xiaozhang.devicemanagesystem.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private Long id;
    private String code;
    private String username;
    private String password;
    private int type;    //1--管理员，2--普通用户
    private Long deptId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
