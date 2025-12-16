package com.xiaozhang.devicemanagesystem.server.controller;


import com.xiaozhang.devicemanagesystem.common.result.PageResult;
import com.xiaozhang.devicemanagesystem.common.result.Result;
import com.xiaozhang.devicemanagesystem.pojo.dto.FeedBackDTO;
import com.xiaozhang.devicemanagesystem.pojo.dto.FeedBackPageDTO;
import com.xiaozhang.devicemanagesystem.server.service.FeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/feedbacks")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    /**
     * 分页查询用户的反馈表
     * @param feedBackPageDTO
     * @return
     */
    @GetMapping("/page")
    public Result<PageResult> getUserFeedbacks(FeedBackPageDTO feedBackPageDTO) {
        log.info("分页查询用户的反馈表,{}",feedBackPageDTO);

        PageResult result = feedbackService.getUserFeedbacks(feedBackPageDTO);
        return Result.success(result);
    }

    /**
     * 用户提出设备反馈
     * @param feedBackDTO
     * @return
     */
    @PostMapping
    public Result<String> addFeedback(@RequestBody FeedBackDTO feedBackDTO){
        log.info("用户提出设备反馈,{}",feedBackDTO);
        feedbackService.addFeedback(feedBackDTO);
        return Result.success();
    }
}
