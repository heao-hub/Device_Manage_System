package com.xiaozhang.devicemanagesystem.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowDetail {
    private Long id;
    private Long borrowId;
    private Long deviceId;
    private Long userId;
    private LocalDateTime borrowTime;
    private LocalDateTime returnTime;
    private int returnStatus;  // 1--未归还，2--已归还
}
