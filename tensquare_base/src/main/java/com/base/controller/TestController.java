package com.base.controller;

import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class TestController {

    @Value("${sms.ip}")
    private String ip;

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public Result getIp(){
        return new Result(true, StatusCode.OK, ip);
    }
}
