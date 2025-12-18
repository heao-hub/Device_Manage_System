package com.xiaozhang.devicemanagesystem.server.service.impl;

import com.xiaozhang.devicemanagesystem.common.constant.StatusConstant;
import com.xiaozhang.devicemanagesystem.pojo.vo.DeviceReportVO;
import com.xiaozhang.devicemanagesystem.pojo.vo.DeviceStatusReportVO;
import com.xiaozhang.devicemanagesystem.pojo.vo.ScrapOrderReportVO;
import com.xiaozhang.devicemanagesystem.server.mapper.DeviceMapper;
import com.xiaozhang.devicemanagesystem.server.service.StatisticsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    private DeviceMapper deviceMapper;

    /**
     * 统计设备数据
     * @param begin
     * @param end
     * @return
     */
    public DeviceReportVO getDeviceStatistics(LocalDate begin, LocalDate end) {
        // 创建日期列表
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);
        while(!begin.equals(end)){
            begin = begin.plusDays(1);
            dateList.add(begin);
        }

        // 创建设备列表
        List<Integer> newDeviceList = new ArrayList<>();
        List<Integer> totalDeviceList = new ArrayList<>();

        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            Map<String, Object> map = new HashMap<>();
            map.put("endTime",endTime);
            Integer totalCount = deviceMapper.getDeviceCountByMap(map);

            map.put("beginTime",beginTime);
            Integer newCount = deviceMapper.getDeviceCountByMap(map);
            totalDeviceList.add(totalCount);
            newDeviceList.add(newCount);
        }

        return DeviceReportVO
                .builder()
                .dateList(StringUtils.join(dateList,","))
                .newDeviceList(StringUtils.join(newDeviceList,","))
                .totalDeviceList(StringUtils.join(totalDeviceList,","))
                .build();
    }

    /**
     * 统计不同状态的设备数据
     * @param begin
     * @param end
     * @return
     */
    public DeviceStatusReportVO getDeviceStatusStatistics(LocalDate begin, LocalDate end) {
        List<Integer> countList = deviceMapper.getCountByStatus();

        Integer onUseCount = countList.get(StatusConstant.DEVICE_ON_USE - 1);
        Integer outUseCount = countList.get(StatusConstant.DEVICE_OUT_USE - 1);
        Integer repairCount = countList.get(StatusConstant.DEVICE_REPAIR - 1);
        Integer scrapCount = countList.get(StatusConstant.DEVICE_SCRAP - 1);
        Integer totalCount = onUseCount+outUseCount+repairCount+scrapCount;

        return DeviceStatusReportVO
                .builder()
                .onUseDeviceCount(onUseCount)
                .outUseDeviceCount(outUseCount)
                .repairDeviceCount(repairCount)
                .scrapDeviceCount(scrapCount)
                .totalDeviceCount(totalCount)
                .build();
    }
}
