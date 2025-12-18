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
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.ognl.Ognl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 统计入库单数据
     * @param begin
     * @param end
     * @return
     */
    public InsertOrderReportVO getInsertStatistics(LocalDate begin, LocalDate end) {
        // 创建日期列表
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);
        while(!begin.equals(end)){
            begin = begin.plusDays(1);
            dateList.add(begin);
        }

        // 创建入库单列表
        List<Integer> newInsertOrderList = new ArrayList<>();
        List<Integer> totalInsertOrderList = new ArrayList<>();

        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            Map<String, Object> map = new HashMap<>();
            map.put("endTime",endTime);
            Integer totalCount = orderMapper.getInsertCountByMap(map);

            map.put("beginTime",beginTime);
            Integer newCount = orderMapper.getInsertCountByMap(map);
            totalInsertOrderList.add(totalCount);
            newInsertOrderList.add(newCount);
        }

        return InsertOrderReportVO
                .builder()
                .dateList(StringUtils.join(dateList,","))
                .newInsertOrderList(StringUtils.join(newInsertOrderList,","))
                .totalInsertOrderList(StringUtils.join(totalInsertOrderList,","))
                .build();
    }

    /**
     * 统计借条数据
     * @param begin
     * @param end
     * @return
     */
    public BorrowOrderReportVO getBorrowStatistics(LocalDate begin, LocalDate end) {
        // 创建日期列表
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);
        while(!begin.equals(end)){
            begin = begin.plusDays(1);
            dateList.add(begin);
        }

        // 创建借条列表
        List<Integer> newBorrowOrderList = new ArrayList<>();
        List<Integer> totalBorrowOrderList = new ArrayList<>();

        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            Map<String, Object> map = new HashMap<>();
            map.put("endTime",endTime);
            Integer totalCount = orderMapper.getBorrowCountByMap(map);

            map.put("beginTime",beginTime);
            Integer newCount = orderMapper.getBorrowCountByMap(map);
            totalBorrowOrderList.add(totalCount);
            newBorrowOrderList.add(newCount);
        }

        return BorrowOrderReportVO
                .builder()
                .dateList(StringUtils.join(dateList,","))
                .newBorrowOrderList(StringUtils.join(newBorrowOrderList,","))
                .totalBorrowOrderList(StringUtils.join(totalBorrowOrderList,","))
                .build();
    }

    /**
     * 统计反馈单数据
     * @param begin
     * @param end
     * @return
     */
    public FeedbackOrderReportVO getFeedbackStatistics(LocalDate begin, LocalDate end) {
        // 创建日期列表
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);
        while(!begin.equals(end)){
            begin = begin.plusDays(1);
            dateList.add(begin);
        }

        // 创建反馈单列表
        List<Integer> newFeedbackOrderList = new ArrayList<>();
        List<Integer> totalFeedbackOrderList = new ArrayList<>();

        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            Map<String, Object> map = new HashMap<>();
            map.put("endTime",endTime);
            Integer totalCount = orderMapper.getFeedbackCountByMap(map);

            map.put("beginTime",beginTime);
            Integer newCount = orderMapper.getFeedbackCountByMap(map);
            totalFeedbackOrderList.add(totalCount);
            newFeedbackOrderList.add(newCount);
        }

        return FeedbackOrderReportVO
                .builder()
                .dateList(StringUtils.join(dateList,","))
                .newFeedbackOrderList(StringUtils.join(newFeedbackOrderList,","))
                .totalFeedbackOrderList(StringUtils.join(totalFeedbackOrderList,","))
                .build();
    }

    /**
     * 统计报废单数据
     * @param begin
     * @param end
     * @return
     */
    public ScrapOrderReportVO getScrapStatistics(LocalDate begin, LocalDate end) {
        // 创建日期列表
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);
        while(!begin.equals(end)){
            begin = begin.plusDays(1);
            dateList.add(begin);
        }

        // 创建报废单列表
        List<Integer> newScrapOrderList = new ArrayList<>();
        List<Integer> totalScrapOrderList = new ArrayList<>();

        for (LocalDate date : dateList) {
            LocalDateTime beginTime = LocalDateTime.of(date, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.MAX);

            Map<String, Object> map = new HashMap<>();
            map.put("endTime",endTime);
            Integer totalCount = orderMapper.getScrapCountByMap(map);

            map.put("beginTime",beginTime);
            Integer newCount = orderMapper.getScrapCountByMap(map);
            totalScrapOrderList.add(totalCount);
            newScrapOrderList.add(newCount);
        }

        return ScrapOrderReportVO
                .builder()
                .dateList(StringUtils.join(dateList,","))
                .newScrapOrderList(StringUtils.join(newScrapOrderList,","))
                .totalScrapOrderList(StringUtils.join(totalScrapOrderList,","))
                .build();
    }


}
