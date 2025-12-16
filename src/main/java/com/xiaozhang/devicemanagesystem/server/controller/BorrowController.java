package com.xiaozhang.devicemanagesystem.server.controller;

import com.xiaozhang.devicemanagesystem.common.result.PageResult;
import com.xiaozhang.devicemanagesystem.common.result.Result;
import com.xiaozhang.devicemanagesystem.pojo.dto.*;
import com.xiaozhang.devicemanagesystem.server.service.BorrowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 借用、归还设备controller
 */
@Slf4j
@RequestMapping("/borrow")
@RestController
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    /**
     * 用户借用设备
     * @param borrowDTO
     * @return
     */
    @PostMapping("/devices")
    public Result<String> borrowDevices(@RequestBody BorrowDTO borrowDTO){
        log.info("用户借用设备，{}",borrowDTO);

        borrowService.borrowDevices(borrowDTO);
        return Result.success();
    }

    /**
     * 查询用户的借条信息
     * @param borrowOrderPageDTO
     * @return
     */
    @GetMapping("/user-order/page")
    public Result<PageResult> getBorrowOrdersByUserId(BorrowOrderPageDTO borrowOrderPageDTO){
        log.info("查询用户的借条,{}",borrowOrderPageDTO);

        PageResult pageResult = borrowService.getUserBorrowOrders(borrowOrderPageDTO);
        return Result.success(pageResult);
    }

    /**
     * 根据借条id查询借用的设备
     * @return
     */
    @GetMapping("/devices/{borrowOrderId}")
    public Result<PageResult> getDevicesByBorrowId(@PathVariable Long borrowOrderId){
        log.info("根据借条id查询借用的设备，{}",borrowOrderId);

        PageResult result = borrowService.getDevicesByBorrowId(borrowOrderId);
        return Result.success(result);
    }

    /**
     * 根据用户id和设备归还状态查询用户借用的设备
     * @param userDevicesPageDTO
     * @return
     */
    @GetMapping("/user-devices/page")
    public Result<PageResult> getDevicesByUserIdAndStatus(UserDevicesPageDTO userDevicesPageDTO){
        log.info("根据用户id和设备归还状态查询用户借用的设备，{}",userDevicesPageDTO);
        PageResult result = borrowService.getDevicesByUserIdAndStatus(userDevicesPageDTO);
        return Result.success(result);
    }

    /**
     * 归还设备
     * @param backDeviceDTO
     * @return
     */
    @PostMapping("/user-back-device")
    public Result<String> returnDevice(@RequestBody BackDeviceDTO backDeviceDTO){
        log.info("归还设备，{}",backDeviceDTO);
        borrowService.returnDevice(backDeviceDTO);
        return Result.success();
    }


}
