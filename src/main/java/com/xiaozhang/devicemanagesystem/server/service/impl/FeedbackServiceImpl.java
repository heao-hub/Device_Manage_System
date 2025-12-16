package com.xiaozhang.devicemanagesystem.server.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xiaozhang.devicemanagesystem.common.constant.StatusConstant;
import com.xiaozhang.devicemanagesystem.common.result.PageResult;
import com.xiaozhang.devicemanagesystem.pojo.dto.FeedBackDTO;
import com.xiaozhang.devicemanagesystem.pojo.dto.FeedBackPageDTO;
import com.xiaozhang.devicemanagesystem.pojo.entity.FeedbackOrder;
import com.xiaozhang.devicemanagesystem.pojo.entity.Num;
import com.xiaozhang.devicemanagesystem.pojo.vo.FeedBackVO;
import com.xiaozhang.devicemanagesystem.server.mapper.NumMapper;
import com.xiaozhang.devicemanagesystem.server.mapper.OrderMapper;
import com.xiaozhang.devicemanagesystem.server.service.FeedbackService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private NumMapper numMapper;

    /**
     * 分页查询用户数据
     * @param feedBackPageDTO
     * @return
     */
    public PageResult getUserFeedbacks(FeedBackPageDTO feedBackPageDTO) {
        PageHelper.startPage(feedBackPageDTO.getPage(), feedBackPageDTO.getPageSize());

        Page<FeedBackVO> list = orderMapper.getFeedbacks(feedBackPageDTO);
        long total = list.getTotal();
        List<FeedBackVO> result = list.getResult();
        return new PageResult((int)total,list);
    }

    /**
     * 添加用户反馈
     * @param feedBackDTO
     */
    @Transactional
    public void addFeedback(FeedBackDTO feedBackDTO) {
        FeedbackOrder feedbackOrder = new FeedbackOrder();
        BeanUtils.copyProperties(feedBackDTO, feedbackOrder);
        feedbackOrder.setStatus(StatusConstant.FEEDBACK_WAITING);
        feedbackOrder.setCreateTime(LocalDateTime.now());

        // 构造反馈表的顺序号
        Num num = numMapper.getNum();
        int feedbackNum = num.getFeedbackNum();
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String feedbackCode = String.format("FK%s%06d",currentDate,feedbackNum);
        feedbackOrder.setCode(feedbackCode);

        orderMapper.inFeedbackOrder(feedbackOrder);

        feedbackNum++;
        num.setFeedbackNum(feedbackNum);
        numMapper.update(num);
    }
}
