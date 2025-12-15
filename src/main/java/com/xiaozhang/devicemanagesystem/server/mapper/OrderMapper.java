package com.xiaozhang.devicemanagesystem.server.mapper;

import com.github.pagehelper.Page;
import com.xiaozhang.devicemanagesystem.pojo.dto.UserBorrowOrderPageDTO;
import com.xiaozhang.devicemanagesystem.pojo.dto.UserDevicesPageDTO;
import com.xiaozhang.devicemanagesystem.pojo.entity.BorrowDetail;
import com.xiaozhang.devicemanagesystem.pojo.entity.BorrowOrder;
import com.xiaozhang.devicemanagesystem.pojo.entity.InsertOrder;
import com.xiaozhang.devicemanagesystem.pojo.entity.ScrapOrder;
import com.xiaozhang.devicemanagesystem.pojo.vo.BorrowDetailVO;
import com.xiaozhang.devicemanagesystem.pojo.vo.BorrowOrderVO;
import com.xiaozhang.devicemanagesystem.pojo.vo.DeviceVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {
    /**
     * 添加入库单
     * @param insertOrder
     */
    void inInsertOrder(InsertOrder insertOrder);

    /**
     * 修改入库单
     * @param insertOrder
     */
    void updateInsertOrder(InsertOrder insertOrder);

    /**
     * 添加报废单
     * @param scrapOrder
     */
    void inScrapOrder(ScrapOrder scrapOrder);

    /**
     * 添加借用申请
     * @param borrowOrder
     */
    void inBorrowOrder(BorrowOrder borrowOrder);

    /**
     * 添加借条详细信息
     * @param borrowDetail
     */
    void inBorrowDetail(BorrowDetail borrowDetail);

    /**
     * 查询用户借条信息
     * @param userBorrowOrderPageDTO
     * @return
     */
    Page<BorrowOrderVO> getUserBorrowOrder(UserBorrowOrderPageDTO userBorrowOrderPageDTO);

    /**
     * 根据借条查询借用的设备
     * @param borrowOrderId
     * @return
     */
    List<DeviceVO> getDevicesByBorrowId(Long borrowOrderId);

    /**
     * 查询用户借用的设备
     * @param userDevicesPageDTO
     * @return
     */
    Page<BorrowDetailVO> getUserBorrowDevices(UserDevicesPageDTO userDevicesPageDTO);

    /**
     * 查找借条详细信息
     * @param borrowDetail
     * @return
     */
    BorrowDetail getBorrowDetail(BorrowDetail borrowDetail);

    /**
     * 修改借条详细信息
     * @param borrowDetail
     */
    void updateBorrowDetail(BorrowDetail borrowDetail);
}
