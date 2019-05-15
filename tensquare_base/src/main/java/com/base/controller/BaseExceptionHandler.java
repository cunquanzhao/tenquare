package com.base.controller;

import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Name: BaseExceptionHandler
 * User: zhaocq
 * Date: 2019/5/5 0005
 * Time: 12:24
 * Description: 公共异常的处理类
 */
@ControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    public Result error(Exception e) {
        e.printStackTrace();
        return new Result(false, StatusCode.ERROR, e.getMessage());
    }

}
