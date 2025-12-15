package com.xiaozhang.devicemanagesystem.server.controller;

import com.xiaozhang.devicemanagesystem.common.result.PageResult;
import com.xiaozhang.devicemanagesystem.common.result.Result;
import com.xiaozhang.devicemanagesystem.pojo.dto.DeviceDTO;
import com.xiaozhang.devicemanagesystem.pojo.dto.DevicePageQueryDTO;
import com.xiaozhang.devicemanagesystem.pojo.dto.InsertDeviceDTO;
import com.xiaozhang.devicemanagesystem.pojo.dto.ScrapDeviceDTO;
import com.xiaozhang.devicemanagesystem.pojo.entity.Device;
import com.xiaozhang.devicemanagesystem.pojo.vo.DeviceVO;
import com.xiaozhang.devicemanagesystem.server.service.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 设备管理controller
 */
@RestController
@RequestMapping("/admin/devices")
@Slf4j
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    /**
     * 设备入库
     * @param insertDeviceDTO
     * @return
     */
    @PostMapping
    public Result<String> insertDevices(@RequestBody InsertDeviceDTO insertDeviceDTO){
        log.info("设备入库，{}",insertDeviceDTO);
        deviceService.insert(insertDeviceDTO);
        return Result.success();
    }

    /**
     * 分页查询设备
     * @param devicePageQueryDTO
     * @return
     */
    @GetMapping("/page")
    public Result<PageResult> pageQuery(DevicePageQueryDTO devicePageQueryDTO){
        log.info("分页查询设备，{}",devicePageQueryDTO);

        PageResult result = deviceService.page(devicePageQueryDTO);
        return Result.success(result);
    }

    /**
     * 根据id获取设备信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<DeviceVO> getById(@PathVariable Long id){
        log.info("根据id获取设备信息,{}",id);
        DeviceVO deviceVO = deviceService.getById(id);
        return Result.success(deviceVO);
    }

    @PutMapping
    public Result<String> update(@RequestBody DeviceDTO deviceDTO){
        log.info("修改设备信息，{}",deviceDTO);
        deviceService.update(deviceDTO);
        return Result.success();
    }


    /**
     * 报废设备
     * @param scrapDeviceDTO
     * @return
     */
    @PutMapping("/scrap")
    public Result<String> scrap(@RequestBody ScrapDeviceDTO scrapDeviceDTO){
        log.info("报废设备,{}",scrapDeviceDTO);
        deviceService.scrap(scrapDeviceDTO);
        return Result.success();
    }


}
