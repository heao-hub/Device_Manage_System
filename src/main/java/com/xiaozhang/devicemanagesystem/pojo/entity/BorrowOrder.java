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
public class BorrowOrder {
    private Long id;
    private String code;
    private String description;
    private LocalDateTime createTime;
    private Long userId;
    private int status;
    private Long adminId;
    private LocalDateTime handleTime;
}
