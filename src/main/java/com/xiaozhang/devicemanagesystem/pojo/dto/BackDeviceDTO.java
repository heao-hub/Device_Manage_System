package com.xiaozhang.devicemanagesystem.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BackDeviceDTO implements Serializable {
    private Long userId;
    private Long deviceId;
    private Long borrowId;
}
