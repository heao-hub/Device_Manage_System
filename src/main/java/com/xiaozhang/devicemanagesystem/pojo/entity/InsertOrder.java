package com.xiaozhang.devicemanagesystem.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsertOrder {
    private Long id;
    private String code;
    private String deviceName;
    private String deviceModel;
    private int count;
    private double price;
    private String manufacturer;
    private Long deptId;
    private LocalDateTime createTime;
    private Long adminId;


}
