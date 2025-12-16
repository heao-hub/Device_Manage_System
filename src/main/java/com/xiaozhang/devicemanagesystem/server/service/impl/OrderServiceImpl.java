package com.xiaozhang.devicemanagesystem.server.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xiaozhang.devicemanagesystem.common.constant.ExceptionConstant;
import com.xiaozhang.devicemanagesystem.common.constant.StatusConstant;
import com.xiaozhang.devicemanagesystem.common.exception.BorrowNotFoundException;
import com.xiaozhang.devicemanagesystem.common.exception.DeviceBorrowedException;
import com.xiaozhang.devicemanagesystem.common.exception.DeviceNotFoundException;
import com.xiaozhang.devicemanagesystem.common.exception.FeedbackNotFoundException;
import com.xiaozhang.devicemanagesystem.common.result.PageResult;
import com.xiaozhang.devicemanagesystem.pojo.dto.*;
import com.xiaozhang.devicemanagesystem.pojo.entity.*;
import com.xiaozhang.devicemanagesystem.pojo.vo.*;
import com.xiaozhang.devicemanagesystem.server.mapper.DeviceMapper;
import com.xiaozhang.devicemanagesystem.server.mapper.NumMapper;
import com.xiaozhang.devicemanagesystem.server.mapper.OrderMapper;
import com.xiaozhang.devicemanagesystem.server.service.BorrowService;
import com.xiaozhang.devicemanagesystem.server.service.DeviceService;
import com.xiaozhang.devicemanagesystem.server.service.FeedbackService;
import com.xiaozhang.devicemanagesystem.server.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private DeviceMapper deviceMapper;
    @Autowired
    private OrderService orderService;
    @Autowired
    private NumMapper numMapper;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private FeedbackService feedbackService;

    /**
     * 查询所有借条信息
     * @param borrowOrderPageDTO
     * @return
     */
    public PageResult borrowPage(BorrowOrderPageDTO borrowOrderPageDTO) {
        PageHelper.startPage(borrowOrderPageDTO.getPage(),borrowOrderPageDTO.getPageSize());
        Page<BorrowOrderVO> list = orderMapper.getBorrowOrder(borrowOrderPageDTO);
        long total = list.getTotal();
        List<BorrowOrderVO> result = list.getResult();
        return new PageResult((int) total,result);
    }

    /**
     * 处理借用申请
     * @param handleBorrowDTO
     */
    @Transactional
    public void handleBorrowOrder(HandleBorrowDTO handleBorrowDTO) {
        // 查询借条信息
        BorrowOrder borrowOrder = orderMapper.getBorrowOrderById(handleBorrowDTO.getId());
        if(borrowOrder == null){
            throw new BorrowNotFoundException(ExceptionConstant.BORROW_NOT_FOUND);
        }
        // 查询借条关联的设备信息
        List<DeviceVO> devices = orderMapper.getDevicesByBorrowId(handleBorrowDTO.getId());
        List<BorrowDetail> borrowDetails = orderMapper.getBorrowDetailsByBorrowId(handleBorrowDTO.getId());

        // 如果申请通过
        if(handleBorrowDTO.getStatus() == StatusConstant.BORROW_SUCCESS){
            // 将设备的状态设为借出
            for (DeviceVO device : devices) {
                int status = device.getStatus();

                if(status == StatusConstant.DEVICE_OUT_USE){
                    throw new DeviceBorrowedException(ExceptionConstant.DEVICE_BORROWED);
                }else if(status == StatusConstant.DEVICE_REPAIR){
                    throw new DeviceBorrowedException(ExceptionConstant.DEVICE_REPAIR);
                }else if(status == StatusConstant.DEVICE_SCRAP){
                    throw new DeviceBorrowedException(ExceptionConstant.DEVICE_SCRAPED);
                }else{
                    Device newDevice = new Device();
                    newDevice.setId(device.getId());
                    newDevice.setStatus(StatusConstant.DEVICE_OUT_USE);
                    newDevice.setUpdateTime(LocalDateTime.now());
                    deviceMapper.update(newDevice);
                }
            }
            // 修改借条详细信息表
            for (BorrowDetail borrowDetail : borrowDetails) {
                borrowDetail.setBorrowTime(LocalDateTime.now());
                borrowDetail.setReturnStatus(StatusConstant.RETURN_NO);
                orderMapper.updateBorrowDetail(borrowDetail);
            }
        }

        // 修改借条
        borrowOrder.setStatus(handleBorrowDTO.getStatus());
        borrowOrder.setHandleTime(LocalDateTime.now());
        borrowOrder.setAdminId(handleBorrowDTO.getAdminId());

        orderMapper.updateBorrowOrder(borrowOrder);
    }

    /**
     * 查询所有反馈信息
     * @param feedBackPageDTO
     * @return
     */
    public PageResult feedbackPage(FeedBackPageDTO feedBackPageDTO) {
        PageHelper.startPage(feedBackPageDTO.getPage(),feedBackPageDTO.getPageSize());

        Page<FeedBackVO> list = orderMapper.getFeedbacks(feedBackPageDTO);
        long total = list.getTotal();
        List<FeedBackVO> records = list.getResult();
        return new PageResult((int) total,records);
    }

    /**
     * 处理反馈信息
     * @param handleFeedbackDTO
     */
    @Transactional
    public void handleFeedback(HandleFeedbackDTO handleFeedbackDTO) {
        // 获取反馈信息
        Long feedbackId = handleFeedbackDTO.getId();
        FeedbackOrder feedbackOrder = orderMapper.getFeedbackById(feedbackId);

        if(feedbackOrder == null){
            throw new FeedbackNotFoundException(ExceptionConstant.FEEDBACK_ORDER_NOT_FOUNT);
        }

        int status = handleFeedbackDTO.getStatus();
        DeviceVO deviceVO = deviceMapper.getById(feedbackOrder.getDeviceId());
        if(deviceVO == null){
            throw new DeviceNotFoundException(ExceptionConstant.DEVICE_NOT_FOUND);
        }

        if(status == StatusConstant.FEEDBACK_REPAIR){
            // 如果处理结果是维修
            Device device = new Device();
            device.setId(deviceVO.getId());
            device.setStatus(StatusConstant.DEVICE_REPAIR);
            device.setUpdateTime(LocalDateTime.now());
            deviceMapper.update(device);
        }else if(status == StatusConstant.FEEDBACK_SCRAP){
            ScrapDeviceDTO scrapDeviceDTO = new ScrapDeviceDTO();
            scrapDeviceDTO.setDeviceId(deviceVO.getId());
            scrapDeviceDTO.setAdminId(handleFeedbackDTO.getAdminId());
            scrapDeviceDTO.setReason(feedbackOrder.getDeviceProblem());
            deviceService.scrap(scrapDeviceDTO);
        }

        // 修改feedback
        feedbackOrder.setStatus(status);
        feedbackOrder.setHandleTime(LocalDateTime.now());
        feedbackOrder.setAdminId(handleFeedbackDTO.getAdminId());
        orderMapper.updateFeedbackOrder(feedbackOrder);

    }

    /**
     * 分页查询入库单
     * @param insertOrderPageDTO
     * @return
     */
    public PageResult insertPage(InsertOrderPageDTO insertOrderPageDTO) {
        PageHelper.startPage(insertOrderPageDTO.getPage(),insertOrderPageDTO.getPageSize());
        Page<InsertOrderVO> list = orderMapper.getInsertOrders(insertOrderPageDTO);
        long total = list.getTotal();
        List<InsertOrderVO> records = list.getResult();
        return new PageResult((int) total,records);
    }

    /**
     * 分页查询报废单
     * @param scrapOrderPageDTO
     * @return
     */
    public PageResult scrapPage(ScrapOrderPageDTO scrapOrderPageDTO) {
        PageHelper.startPage(scrapOrderPageDTO.getPage(),scrapOrderPageDTO.getPageSize());
        Page<ScrapOrderVO> list = orderMapper.getScrapOrders(scrapOrderPageDTO);
        long total = list.getTotal();
        List<ScrapOrderVO> records = list.getResult();
        return new PageResult((int) total,records);
    }


}
