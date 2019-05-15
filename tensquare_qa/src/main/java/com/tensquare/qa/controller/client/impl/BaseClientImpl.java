package com.tensquare.qa.controller.client.impl;

import com.tensquare.qa.controller.client.BaseClient;
import entity.Result;
import entity.StatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
public class BaseClientImpl implements BaseClient {

    @ResponseBody
    public Result findById(String labelId) {
        return new Result(false, StatusCode.ERROR,"服务器繁忙！");
    }
}
