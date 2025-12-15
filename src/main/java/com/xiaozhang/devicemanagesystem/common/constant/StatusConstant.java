package com.xiaozhang.devicemanagesystem.common.constant;

/**
 * 与状态有关的常量
 */
public class StatusConstant {

    // 设备状态
    public static final int DEVICE_ON_USE = 1;
    public static final int DEVICE_OUT_USE = 2;
    public static final int DEVICE_REPAIR = 3;
    public static final int DEVICE_SCRAP = 4;

    // 借用申请状态
    public static final int BORROW_WAITING = 1;
    public static final int BORROW_SUCCESS = 2;
    public static final int BORROW_REFUSE = 3;

    // 设备归还状态
    public static final int RETURN_NO = 1;
    public static final int RETURN_YES = 2;
    
    // 反馈处理状态
    public static final int FEEDBACK_WAITING = 1;
    public static final int FEEDBACK_DONE = 2;
    public static final int FEEDBACK_REPAIR = 3;
    public static final int FEEDBACK_SCRAP = 4;





}
