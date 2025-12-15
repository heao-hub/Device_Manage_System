package com.xiaozhang.devicemanagesystem.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class HandleFeedbackDTO implements Serializable {
    private Long id;
    private Long deviceId;
    private int status;
    private Long adminId;
}
