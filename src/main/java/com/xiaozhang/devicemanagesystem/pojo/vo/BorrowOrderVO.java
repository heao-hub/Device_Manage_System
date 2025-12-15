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
public class BorrowOrderVO implements Serializable {
    private Long id;
    private String code;
    private String description;
    private int status;
    private LocalDateTime createTime;
    private LocalDateTime handleTime;
    private Long userId;
    private String userCode;
    private String userName;
    private Long adminId;
    private String adminCode;
    private String adminName;
}
