package com.xiaozhang.devicemanagesystem.server.service;

import com.xiaozhang.devicemanagesystem.pojo.vo.DeviceReportVO;
import com.xiaozhang.devicemanagesystem.pojo.vo.DeviceStatusReportVO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public interface StatisticsService {
    /**
     * 统计设备数据
     * @param begin
     * @param end
     * @return
     */
    DeviceReportVO getDeviceStatistics(LocalDate begin, LocalDate end);

    /**
     * 统计不同状态的设备数据
     * @param begin
     * @param end
     * @return
     */
    DeviceStatusReportVO getDeviceStatusStatistics(LocalDate begin, LocalDate end);
}
