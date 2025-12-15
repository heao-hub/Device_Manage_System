package com.xiaozhang.devicemanagesystem.server.service;

import com.xiaozhang.devicemanagesystem.common.result.PageResult;
import com.xiaozhang.devicemanagesystem.pojo.dto.BackDeviceDTO;
import com.xiaozhang.devicemanagesystem.pojo.dto.BorrowDTO;
import com.xiaozhang.devicemanagesystem.pojo.dto.UserBorrowOrderPageDTO;
import com.xiaozhang.devicemanagesystem.pojo.dto.UserDevicesPageDTO;
import org.springframework.stereotype.Service;

@Service
public interface BorrowService {

    /**
     * 用户借用设备
     * @param borrowDTO
     */
    void borrowDevices(BorrowDTO borrowDTO);

    /**
     * 查询用户借条信息
     * @param userBorrowOrderPageDTO
     * @return
     */
    PageResult getUserBorrowOrders(UserBorrowOrderPageDTO userBorrowOrderPageDTO);

    /**
     * 根据借条id查询设备
     * @param borrowOrderId
     * @return
     */
    PageResult getDevicesByBorrowId(Long borrowOrderId);

    /**
     * 查询用户借用的设备
     * @param userDevicesPageDTO
     * @return
     */
    PageResult getDevicesByUserIdAndStatus(UserDevicesPageDTO userDevicesPageDTO);

    /**
     * 归还设备
     * @param backDeviceDTO
     */
    void returnDevice(BackDeviceDTO backDeviceDTO);
}
