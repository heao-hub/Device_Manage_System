package com.xiaozhang.devicemanagesystem.server.mapper;

import com.github.pagehelper.Page;
import com.xiaozhang.devicemanagesystem.pojo.dto.*;
import com.xiaozhang.devicemanagesystem.pojo.entity.*;
import com.xiaozhang.devicemanagesystem.pojo.vo.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

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
     * @param borrowOrderPageDTO
     * @return
     */
    Page<BorrowOrderVO> getBorrowOrder(BorrowOrderPageDTO borrowOrderPageDTO);

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

    /**
     * 查询用户反馈信息
     * @param feedBackPageDTO
     * @return
     */
    Page<FeedBackVO> getFeedbacks(FeedBackPageDTO feedBackPageDTO);

    /**
     * 添加用户反馈表
     * @param feedbackOrder
     */
    void inFeedbackOrder(FeedbackOrder feedbackOrder);

    /**
     * 根据id查询借条信息
     * @param id
     * @return
     */
    @Select("select * from borrow_order where id = #{id}")
    BorrowOrder getBorrowOrderById(Long id);

    /**
     * 根据借条id查询借条详细信息
     * @param id
     * @return
     */
    @Select("select * from borrow_detail where borrow_id = #{id}")
    List<BorrowDetail> getBorrowDetailsByBorrowId(Long id);

    /**
     * 修改借条
     * @param borrowOrder
     */
    void updateBorrowOrder(BorrowOrder borrowOrder);

    /**
     * 根据id查询反馈信息
     * @param feedbackId
     * @return
     */
    @Select("select * from feedback_order where id = #{feedbackId} ")
    FeedbackOrder getFeedbackById(Long feedbackId);

    /**
     * 修改反馈表
     * @param feedbackOrder
     */
    void updateFeedbackOrder(FeedbackOrder feedbackOrder);

    /**
     * 分页查询入库单
     * @param insertOrderPageDTO
     * @return
     */
    Page<InsertOrderVO> getInsertOrders(InsertOrderPageDTO insertOrderPageDTO);

    /**
     * 分页查询报废表
     * @param scrapOrderPageDTO
     * @return
     */
    Page<ScrapOrderVO> getScrapOrders(ScrapOrderPageDTO scrapOrderPageDTO);

    /**
     * 统计入库单数据
     * @param map
     * @return
     */
    Integer getInsertCountByMap(Map<String, Object> map);

    /**
     * 统计借条数据
     * @param map
     * @return
     */
    Integer getBorrowCountByMap(Map<String, Object> map);

    /**
     * 统计反馈表数据
     * @param map
     * @return
     */
    Integer getFeedbackCountByMap(Map<String, Object> map);

    /**
     * 统计报废单数据
     * @param map
     * @return
     */
    Integer getScrapCountByMap(Map<String, Object> map);
}
