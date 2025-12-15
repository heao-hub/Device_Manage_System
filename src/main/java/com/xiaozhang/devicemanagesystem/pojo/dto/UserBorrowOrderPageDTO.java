package com.xiaozhang.devicemanagesystem.pojo.dto;

import lombok.Data;

@Data
public class UserBorrowOrderPageDTO {
    private int page;
    private int pageSize;
    private Long userId;
}
