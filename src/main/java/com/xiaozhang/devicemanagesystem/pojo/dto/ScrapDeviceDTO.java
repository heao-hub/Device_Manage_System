package com.xiaozhang.devicemanagesystem.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ScrapDeviceDTO implements Serializable {
    private String reason;
    private Long deviceId;
    private Long adminId;
}
