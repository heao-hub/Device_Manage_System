package com.xiaozhang.devicemanagesystem.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceStatusReportVO implements Serializable {
    private Integer onUseDeviceCount;
    private Integer outUseDeviceCount;
    private Integer repairDeviceCount;
    private Integer scrapDeviceCount;
    private Integer totalDeviceCount;
}
