package com.xiaozhang.devicemanagesystem.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserVO implements Serializable {
    private Long id;
    private String code;
    private String username;
    private String password;
    private int type;    //1--管理员，2--普通用户
    private int deptId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String deptName;

}
