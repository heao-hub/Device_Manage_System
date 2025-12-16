package com.xiaozhang.devicemanagesystem.server.service;

import com.xiaozhang.devicemanagesystem.common.result.PageResult;
import com.xiaozhang.devicemanagesystem.pojo.dto.*;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    /**
     * 查询所有借条信息
     * @param borrowOrderPageDTO
     * @return
     */
    PageResult borrowPage(BorrowOrderPageDTO borrowOrderPageDTO);

    /**
     * 处理借用申请
     * @param handleBorrowDTO
     */
    void handleBorrowOrder(HandleBorrowDTO handleBorrowDTO);

    /**
     * 查询所有反馈信息
     * @param feedBackPageDTO
     * @return
     */
    PageResult feedbackPage(FeedBackPageDTO feedBackPageDTO);

    /**
     * 处理反馈信息
     * @param handleFeedbackDTO
     */
    void handleFeedback(HandleFeedbackDTO handleFeedbackDTO);

    /**
     * 分页查询入库单
     * @param insertOrderPageDTO
     * @return
     */
    PageResult insertPage(InsertOrderPageDTO insertOrderPageDTO);

    /**
     * 分页查询报废单
     * @param scrapOrderPageDTO
     * @return
     */
    PageResult scrapPage(ScrapOrderPageDTO scrapOrderPageDTO);
}
