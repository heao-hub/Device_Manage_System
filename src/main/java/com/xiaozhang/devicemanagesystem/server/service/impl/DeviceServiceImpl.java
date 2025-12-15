package com.xiaozhang.devicemanagesystem.server.service.impl;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xiaozhang.devicemanagesystem.common.constant.ExceptionConstant;
import com.xiaozhang.devicemanagesystem.common.constant.StatusConstant;
import com.xiaozhang.devicemanagesystem.common.exception.DeviceBorrowedException;
import com.xiaozhang.devicemanagesystem.common.exception.DeviceNotFoundException;
import com.xiaozhang.devicemanagesystem.common.result.PageResult;
import com.xiaozhang.devicemanagesystem.pojo.dto.DeviceDTO;
import com.xiaozhang.devicemanagesystem.pojo.dto.DevicePageQueryDTO;
import com.xiaozhang.devicemanagesystem.pojo.dto.InsertDeviceDTO;
import com.xiaozhang.devicemanagesystem.pojo.dto.ScrapDeviceDTO;
import com.xiaozhang.devicemanagesystem.pojo.entity.Device;
import com.xiaozhang.devicemanagesystem.pojo.entity.InsertOrder;
import com.xiaozhang.devicemanagesystem.pojo.entity.Num;
import com.xiaozhang.devicemanagesystem.pojo.entity.ScrapOrder;
import com.xiaozhang.devicemanagesystem.pojo.vo.DeviceVO;
import com.xiaozhang.devicemanagesystem.server.mapper.DeviceMapper;
import com.xiaozhang.devicemanagesystem.server.mapper.NumMapper;
import com.xiaozhang.devicemanagesystem.server.mapper.OrderMapper;
import com.xiaozhang.devicemanagesystem.server.service.DeviceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceMapper deviceMapper;
    @Autowired
    private NumMapper numMapper;
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 设备入库
     * @param insertDeviceDTO
     */
    @Transactional
    public void insert(InsertDeviceDTO insertDeviceDTO) {
        InsertOrder insertOrder = new InsertOrder();
        BeanUtils.copyProperties(insertDeviceDTO,insertOrder);

        // 构造入库单编号
        Num num = numMapper.getNum();
        int insertNum = num.getInsertNum();
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String insertCode = String.format("RK%s%06d", currentDate, insertNum);
        insertOrder.setCode(insertCode);
        insertOrder.setCreateTime(LocalDateTime.now());
        // 插入入库单
        orderMapper.inInsertOrder(insertOrder);
        insertNum++;

        // 插入设备
        int count = insertOrder.getCount();
        int deviceNum = num.getDeviceNum();
        for (int i = 0; i < count; i++) {
            // 构造设备编号
            String deviceCode = String.format("D%s%06d",currentDate,deviceNum);
            deviceNum++;
            Device device = Device.builder()
                    .code(deviceCode)
                    .insertOrderId(insertOrder.getId())
                    .model(insertOrder.getDeviceModel())
                    .name(insertOrder.getDeviceName())
                    .status(StatusConstant.DEVICE_ON_USE)
                    .updateTime(LocalDateTime.now())
                    .build();

            deviceMapper.insert(device);
        }

        // 更新num表
        num.setInsertNum(insertNum);
        num.setDeviceNum(deviceNum);
        numMapper.update(num);
    }

    /**
     * 分页查询设备
     * @param devicePageQueryDTO
     * @return
     */
    public PageResult page(DevicePageQueryDTO devicePageQueryDTO) {
        PageHelper.startPage(devicePageQueryDTO.getPage(),devicePageQueryDTO.getPageSize());
        Page<DeviceVO> list = deviceMapper.page(devicePageQueryDTO);
        int total = (int)list.getTotal();
        List<DeviceVO> records = list.getResult();

        return new PageResult(total,records);
    }

    /**
     * 根据id查询设备信息
     * @param id
     * @return
     */
    public DeviceVO getById(Long id) {
        DeviceVO deviceVO = deviceMapper.getById(id);
        return deviceVO;
    }

    /**
     * 修改设备信息
     * @param deviceDTO
     */
    @Transactional
    public void update(DeviceDTO deviceDTO) {
        String name = deviceDTO.getDeviceName();
        String model = deviceDTO.getDeviceModel();
        Long deptId = deviceDTO.getDeptId();

        DeviceVO deviceVO = deviceMapper.getById(deviceDTO.getId());
        Device device = new Device();
        BeanUtils.copyProperties(deviceDTO,device);
        device.setUpdateTime(LocalDateTime.now());
        device.setName(name);
        device.setModel(model);

        deviceMapper.update(device);

        if (!name.equals(deviceVO.getDeviceName()) || !model.equals(deviceVO.getDeviceModel())) {
            InsertOrder insertOrder = new InsertOrder();
            insertOrder.setId(deviceVO.getInsertOrderId());
            insertOrder.setDeviceName(name);
            insertOrder.setDeviceModel(model);
            insertOrder.setDeptId(deptId);
            orderMapper.updateInsertOrder(insertOrder);
        }
    }

    /**
     * 报废设备
     * @param scrapDeviceDTO
     */
    @Transactional
    public void scrap(ScrapDeviceDTO scrapDeviceDTO) {
        Long deviceId = scrapDeviceDTO.getDeviceId();
        DeviceVO deviceVO = deviceMapper.getById(deviceId);
        if(deviceVO == null){
            throw new DeviceNotFoundException(ExceptionConstant.DEVICE_NOT_FOUND);
        }else if(deviceVO.getStatus() == StatusConstant.DEVICE_OUT_USE){
            throw new DeviceBorrowedException(ExceptionConstant.DEVICE_BORROWED);
        }

        // 更新设备状态
        Device device = new Device();
        device.setId(deviceVO.getId());
        device.setStatus(StatusConstant.DEVICE_SCRAP);
        device.setUpdateTime(LocalDateTime.now());

        deviceMapper.update(device);


        // 构造报废单编号
        Num num = numMapper.getNum();
        int scrapNum = num.getScrapNum();
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String scrapCode = String.format("BF%s%06d",currentDate,scrapNum);
        ScrapOrder scrapOrder = new ScrapOrder();
        scrapOrder.setCode(scrapCode);
        scrapOrder.setDeviceId(device.getId());
        scrapOrder.setReason(scrapDeviceDTO.getReason());
        scrapOrder.setCreateTime(LocalDateTime.now());
        scrapOrder.setAdminId(scrapDeviceDTO.getAdminId());

        // 新增报废单
        orderMapper.inScrapOrder(scrapOrder);

        scrapNum++;
        num.setScrapNum(scrapNum);
        numMapper.update(num);
    }





}
