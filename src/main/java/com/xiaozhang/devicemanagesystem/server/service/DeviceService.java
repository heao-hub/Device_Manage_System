package com.xiaozhang.devicemanagesystem.server.service;

import com.xiaozhang.devicemanagesystem.common.result.PageResult;
import com.xiaozhang.devicemanagesystem.pojo.dto.DeviceDTO;
import com.xiaozhang.devicemanagesystem.pojo.dto.DevicePageQueryDTO;
import com.xiaozhang.devicemanagesystem.pojo.dto.InsertDeviceDTO;
import com.xiaozhang.devicemanagesystem.pojo.dto.ScrapDeviceDTO;
import com.xiaozhang.devicemanagesystem.pojo.vo.DeviceVO;
import org.springframework.stereotype.Service;

@Service
public interface DeviceService {
    /**
     * 设备入库
     * @param insertDeviceDTO
     */
    void insert(InsertDeviceDTO insertDeviceDTO);

    /**
     * 分页查询设备
     * @param devicePageQueryDTO
     * @return
     */
    PageResult page(DevicePageQueryDTO devicePageQueryDTO);

    /**
     * 根据id查询设备信息
     * @param id
     * @return
     */
    DeviceVO getById(Long id);

    /**
     * 修改设备信息
     * @param deviceDTO
     */
    void update(DeviceDTO deviceDTO);

    /**
     * 报废设备
     * @param scrapDeviceDTO
     */
    void scrap(ScrapDeviceDTO scrapDeviceDTO);
}
