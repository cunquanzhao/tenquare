package com.tensquare.friend.controller.client;

import entity.Result;
import entity.StatusCode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("tensquare-user")
public interface UserClient {

    //修改粉丝数
    @RequestMapping(value = "/user/fanscount/{userid}/{x}", method = RequestMethod.POST)
    public Result incFanscount(@PathVariable("userid") String userid,@PathVariable("x") int x);

    //修改关注数
    @RequestMapping(value = "/user/followcount/{userid}/{x}", method = RequestMethod.POST)
    public Result incFollowcount(@PathVariable("userid") String userid,@PathVariable("x") int x);

}
