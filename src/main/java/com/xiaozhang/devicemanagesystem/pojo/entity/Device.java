package com.xiaozhang.devicemanagesystem.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Device {
    private Long id;
    private String code;
    private String name;
    private String model;
    private int status; //1--正常可用，2--借出，3--维修中，4--报废
    private Long insertOrderId;
    private LocalDateTime updateTime;
}
