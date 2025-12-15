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
public class DeviceVO implements Serializable {
    private Long id;
    private String code;
    private String deviceName;
    private String deviceModel;
    private int status;
    private double price;
    private String manufacturer;
    private Long insertOrderId;
    private String deptName;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
