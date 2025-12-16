package com.xiaozhang.devicemanagesystem.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackOrder {
    private Long id;
    private String code;
    private Long deviceId;
    private String deviceProblem;
    private Long userId;
    private int status;
    private Long adminId;
    private LocalDateTime createTime;
    private LocalDateTime handleTime;
}
