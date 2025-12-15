package com.xiaozhang.devicemanagesystem.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class InsertDeviceDTO implements Serializable {
    private String deviceName;
    private String deviceModel;
    private int count;
    private double price;
    private String manufacturer;
    private Long deptId;
    private Long adminId;
}
