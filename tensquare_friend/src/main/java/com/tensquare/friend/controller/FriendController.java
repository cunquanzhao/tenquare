package com.tensquare.friend.controller;

import com.tensquare.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/friend")
@CrossOrigin
public class FriendController {

    @Autowired
    private FriendService friendService;

    @Autowired
    private HttpServletRequest request;

    // /like/{friendid}/{type}
    // 添加好友或非好友
    @RequestMapping(value = "/like/{friendid}/{type}",method = RequestMethod.PUT)
    public Result addFriend(@PathVariable String friendid,@PathVariable String type){

        Claims claims = (Claims) request.getAttribute("user_claims");
        if(claims==null){
            return new Result(false, StatusCode.ACCESSERROR, "权限不足");
        }
        String userid = claims.getId();

        if("1".equals(type)){
            int num = friendService.addFriend(userid, friendid);
            if(num==0){
                return new Result(false, StatusCode.REPERROR, "你已经添加过此好友了");
            }
        }else{
            //添加非好友
            friendService.addNoFriend(userid,friendid);
        }

        return new Result(true, StatusCode.OK, "操作成功");
    }

    /**
     * 删除好友
     * @param friendid
     * @return
     */
    @RequestMapping(value = "/{friendid}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable String friendid){
        Claims claims = (Claims) request.getAttribute("user_claims");
        if(claims==null){
            return new Result(false, StatusCode.ACCESSERROR, "权限不足");
        }
        String userid = claims.getId();

        friendService.delete(userid,friendid);

        return new Result(true, StatusCode.OK, "删除成功");
    }

}
