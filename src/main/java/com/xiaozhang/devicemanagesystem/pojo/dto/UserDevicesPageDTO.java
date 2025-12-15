package com.xiaozhang.devicemanagesystem.pojo.dto;

import lombok.Data;

@Data
public class UserDevicesPageDTO {
    private int page;
    private int pageSize;
    private Long userId;;
    private int returnStatus;
}
