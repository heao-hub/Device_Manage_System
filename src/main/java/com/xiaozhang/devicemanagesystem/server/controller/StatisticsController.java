package com.xiaozhang.devicemanagesystem.server.controller;

import com.xiaozhang.devicemanagesystem.common.result.Result;
import com.xiaozhang.devicemanagesystem.pojo.vo.DeviceReportVO;
import com.xiaozhang.devicemanagesystem.pojo.vo.DeviceStatusReportVO;
import com.xiaozhang.devicemanagesystem.pojo.vo.ScrapOrderReportVO;
import com.xiaozhang.devicemanagesystem.server.service.StatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@Slf4j
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    /**
     * 统计设备数据
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("/device")
    public Result<DeviceReportVO> deviceStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        log.info("统计设备数据,{},{}",begin,end);
        DeviceReportVO  report = statisticsService.getDeviceStatistics(begin,end);
        return Result.success(report);
    }

    /**
     * 统计不同状态的设备数据
     * @param begin
     * @param end
     * @return
     */
    @GetMapping("/status/device")
    public Result<DeviceStatusReportVO> deviceStatusStatistics(){
        log.info("统计不同状态的设备数据");
        DeviceStatusReportVO  report = statisticsService.getDeviceStatusStatistics();
        return Result.success(report);
    }
}
