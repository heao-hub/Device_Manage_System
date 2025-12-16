package com.xiaozhang.devicemanagesystem.server.controller;

import com.github.pagehelper.Page;
import com.xiaozhang.devicemanagesystem.common.result.PageResult;
import com.xiaozhang.devicemanagesystem.common.result.Result;
import com.xiaozhang.devicemanagesystem.pojo.dto.*;
import com.xiaozhang.devicemanagesystem.server.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/admin/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 分页查询所有借条信息
     * @param borrowOrderPageDTO
     * @return
     */
    @GetMapping("/borrow/page")
    public Result<PageResult> borrowPage( BorrowOrderPageDTO borrowOrderPageDTO){
        log.info("分页查询所有借条信息,{}",borrowOrderPageDTO);

        PageResult result = orderService.borrowPage(borrowOrderPageDTO);
        return Result.success(result);
    }

    /**
     * 处理借用申请
     * @param handleBorrowDTO
     * @return
     */
    @PutMapping("/borrow/handle")
    public Result<String> handleBorrowOrder(@RequestBody HandleBorrowDTO handleBorrowDTO){
        log.info("处理借用申请，{}",handleBorrowDTO);

        orderService.handleBorrowOrder(handleBorrowDTO);
        return Result.success();
    }

    /**
     * 分页查询反馈信息
     * @param feedBackPageDTO
     * @return
     */
    @GetMapping("/feedback/page")
    public Result<PageResult> feedbackPage(FeedBackPageDTO feedBackPageDTO){
        log.info("分页查询反馈信息，{}",feedBackPageDTO);

        PageResult result = orderService.feedbackPage(feedBackPageDTO);
        return Result.success(result);
    }

    /**
     * 处理反馈信息
     * @param handleFeedbackDTO
     * @return
     */
    @PutMapping("/feedback/handle")
    public Result<String> handleFeedback(@RequestBody HandleFeedbackDTO handleFeedbackDTO){
        log.info("处理反馈信息，{}",handleFeedbackDTO);

        orderService.handleFeedback(handleFeedbackDTO);
        return Result.success();
    }

    /**
     * 分页查询入库单
     * @param insertOrderPageDTO
     * @return
     */
    @GetMapping("/insert/page")
    public Result<PageResult> insertPage(InsertOrderPageDTO insertOrderPageDTO){
        log.info("分页查询入库单，{}",insertOrderPageDTO);
        PageResult result = orderService.insertPage(insertOrderPageDTO);
        return Result.success(result);
    }

    /**
     * 分页查询报废单
     * @param scrapOrderPageDTO
     * @return
     */
    @GetMapping("/scrap/page")
    public Result<PageResult> scrapPage(ScrapOrderPageDTO scrapOrderPageDTO){
        log.info("分页查询报废单，{}",scrapOrderPageDTO);

        PageResult result = orderService.scrapPage(scrapOrderPageDTO);
        return Result.success(result);
    }

}
