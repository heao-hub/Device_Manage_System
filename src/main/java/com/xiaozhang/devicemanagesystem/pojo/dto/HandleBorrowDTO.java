package com.xiaozhang.devicemanagesystem.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class HandleBorrowDTO implements Serializable {
    private Long id;
    private String code;
    private int status;
    private Long adminId;
}
