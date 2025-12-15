package com.xiaozhang.devicemanagesystem.server.handler;

import com.xiaozhang.devicemanagesystem.common.exception.BaseException;
import com.xiaozhang.devicemanagesystem.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器，处理抛出的业务异常
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }
}
