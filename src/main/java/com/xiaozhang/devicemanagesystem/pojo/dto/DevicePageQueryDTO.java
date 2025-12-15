package com.xiaozhang.devicemanagesystem.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class DevicePageQueryDTO implements Serializable {
    //设备名称
    private String deviceName;

    // 设备状态
    private int deviceStatus;

    //页码
    private int page;

    //每页显示记录数
    private int pageSize;

}
