package com.xiaozhang.devicemanagesystem.pojo.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UserDTO implements Serializable {
    private Long id;
    private String username;
    private String password;
    private int type;
    private Long deptId;
}
