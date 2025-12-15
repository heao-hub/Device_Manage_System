package com.xiaozhang.devicemanagesystem.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class DeviceDTO implements Serializable {
    private Long id;
    private String code;
    private String deviceName;
    private String deviceModel;
    private int status;
    private Long deptId;
}
