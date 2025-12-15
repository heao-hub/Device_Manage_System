package com.xiaozhang.devicemanagesystem.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScrapOrder {
    private Long id;
    private String code;
    private Long deviceId;
    private String reason;
    private Long adminId;
    private LocalDateTime createTime;
}
