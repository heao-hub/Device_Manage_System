package com.xiaozhang.devicemanagesystem.common.exception;

/**
 * 密码错误异常
 */
public class PasswordWrongException extends RuntimeException {
    public PasswordWrongException(String message) {
        super(message);
    }
}
