package com.xiaozhang.devicemanagesystem.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationLog {
    private Long id;
    private Long deviceId;
    private String operationType;
    private Long operatorId;
    private String operationDesc;
    private LocalDateTime operationTime;
}
