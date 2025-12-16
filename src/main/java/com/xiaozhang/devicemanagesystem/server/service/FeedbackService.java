package com.xiaozhang.devicemanagesystem.server.service;

import com.xiaozhang.devicemanagesystem.common.result.PageResult;
import com.xiaozhang.devicemanagesystem.pojo.dto.FeedBackDTO;
import com.xiaozhang.devicemanagesystem.pojo.dto.FeedBackPageDTO;
import org.springframework.stereotype.Service;

@Service
public interface FeedbackService {

    /**
     *分页查询用户反馈
     * @param feedBackPageDTO
     * @return
     */
    PageResult getUserFeedbacks(FeedBackPageDTO feedBackPageDTO);

    /**
     * 添加用户反馈
     * @param feedBackDTO
     */
    void addFeedback(FeedBackDTO feedBackDTO);
}
