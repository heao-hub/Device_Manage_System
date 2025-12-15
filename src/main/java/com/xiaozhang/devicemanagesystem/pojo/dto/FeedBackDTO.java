package com.xiaozhang.devicemanagesystem.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class FeedBackDTO implements Serializable {
    private Long deviceId;
    private String deviceCode;
    private String deviceName;
    private String deviceModel;
    private String deviceProblem;
    private Long userId;
}
