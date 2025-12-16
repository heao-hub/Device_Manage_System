package com.xiaozhang.devicemanagesystem.server.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xiaozhang.devicemanagesystem.common.constant.ExceptionConstant;
import com.xiaozhang.devicemanagesystem.common.constant.StatusConstant;
import com.xiaozhang.devicemanagesystem.common.exception.BorrowDetailNotFoundException;
import com.xiaozhang.devicemanagesystem.common.exception.DeviceReturnStatusException;
import com.xiaozhang.devicemanagesystem.common.result.PageResult;
import com.xiaozhang.devicemanagesystem.common.result.Result;
import com.xiaozhang.devicemanagesystem.pojo.dto.*;
import com.xiaozhang.devicemanagesystem.pojo.entity.BorrowDetail;
import com.xiaozhang.devicemanagesystem.pojo.entity.BorrowOrder;
import com.xiaozhang.devicemanagesystem.pojo.entity.Device;
import com.xiaozhang.devicemanagesystem.pojo.entity.Num;
import com.xiaozhang.devicemanagesystem.pojo.vo.BorrowDetailVO;
import com.xiaozhang.devicemanagesystem.pojo.vo.BorrowOrderVO;
import com.xiaozhang.devicemanagesystem.pojo.vo.DeviceVO;
import com.xiaozhang.devicemanagesystem.server.mapper.DeviceMapper;
import com.xiaozhang.devicemanagesystem.server.mapper.NumMapper;
import com.xiaozhang.devicemanagesystem.server.mapper.OrderMapper;
import com.xiaozhang.devicemanagesystem.server.service.BorrowService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class BorrowServiceImpl implements BorrowService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private NumMapper numMapper;
    @Autowired
    private BorrowService borrowService;

    /**
     * 用户提出借用申请
     * @param borrowDTO
     */
    @Transactional
    public void borrowDevices(BorrowDTO borrowDTO) {
        // 构造借条编号
        Num num = numMapper.getNum();
        int borrowNum = num.getBorrowNum();
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String borrowCode = String.format("JT%s%06d",currentDate,borrowNum);
        borrowNum++;
        BorrowOrder borrowOrder = BorrowOrder.builder()
                .code(borrowCode)
                .adminId(null)
                .status(StatusConstant.BORROW_WAITING)
                .description(borrowDTO.getDescription())
                .createTime(LocalDateTime.now())
                .handleTime(null)
                .userId(borrowDTO.getUserId())
                .build();

        orderMapper.inBorrowOrder(borrowOrder);

        List<DeviceDTO> devices = borrowDTO.getDevices();
        for (DeviceDTO device : devices) {
            BorrowDetail borrowDetail = new BorrowDetail();
            borrowDetail.setBorrowId(borrowOrder.getId());
            borrowDetail.setDeviceId(device.getId());
            borrowDetail.setUserId(borrowDTO.getUserId());

            orderMapper.inBorrowDetail(borrowDetail);
        }

        num.setBorrowNum(borrowNum);
        numMapper.update(num);
    }

    /**
     * 查询用户借条信息
     * @param borrowOrderPageDTO
     * @return
     */
    public PageResult getUserBorrowOrders(BorrowOrderPageDTO borrowOrderPageDTO) {
        PageHelper.startPage(borrowOrderPageDTO.getPage(),borrowOrderPageDTO.getPageSize());
        Page<BorrowOrderVO> list = orderMapper.getBorrowOrder(borrowOrderPageDTO);
        long total = list.getTotal();
        List<BorrowOrderVO> records = list.getResult();
        return new PageResult((int)total,records);
    }

    /**
     * 根据借条id查询设备信息
     * @param borrowOrderId
     * @return
     */
    public PageResult getDevicesByBorrowId(Long borrowOrderId) {
        List<DeviceVO> list = orderMapper.getDevicesByBorrowId(borrowOrderId);

        return new PageResult(list.size(),list);
    }

    /**
     * 查询用户借用的设备
     * @param userDevicesPageDTO
     * @return
     */
    public PageResult getDevicesByUserIdAndStatus(UserDevicesPageDTO userDevicesPageDTO) {
        PageHelper.startPage(userDevicesPageDTO.getPage(),userDevicesPageDTO.getPageSize());
        Page<BorrowDetailVO> list = orderMapper.getUserBorrowDevices(userDevicesPageDTO);
        long total = list.getTotal();
        List<BorrowDetailVO> records = list.getResult();
        return new PageResult((int) total,records);
    }

    /**
     * 归还设备
     * @param backDeviceDTO
     */
    @Transactional
    public void returnDevice(BackDeviceDTO backDeviceDTO) {
        BorrowDetail borrowDetail = new BorrowDetail();
        BeanUtils.copyProperties(backDeviceDTO,borrowDetail);

        borrowDetail = orderMapper.getBorrowDetail(borrowDetail);

        if (borrowDetail == null){
            throw new BorrowDetailNotFoundException(ExceptionConstant.BORROW_DETAIL_NOT_FOUND);
        }else if(borrowDetail.getReturnStatus() != StatusConstant.RETURN_NO){
            throw new DeviceReturnStatusException(ExceptionConstant.DEVICE_RETURN_STATUS_ERROR);
        }

        // 修改borrow_detail表中内容
        borrowDetail.setReturnStatus(StatusConstant.RETURN_YES);
        borrowDetail.setReturnTime(LocalDateTime.now());
        orderMapper.updateBorrowDetail(borrowDetail);

        // 修改device中设备的状态
        Device device = new Device();
        device.setId(backDeviceDTO.getDeviceId());
        device.setStatus(StatusConstant.DEVICE_ON_USE);
        deviceMapper.update(device);

    }


}
