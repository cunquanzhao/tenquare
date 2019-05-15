package com.tensquare.friend.service;

import com.netflix.discovery.converters.Auto;
import com.tensquare.friend.controller.client.UserClient;
import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.dao.NoFriendDao;
import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FriendService {
    @Autowired
    private FriendDao friendDao;

    @Autowired
    private NoFriendDao noFriendDao;

    @Autowired
    private UserClient userClient;


    public int addFriend(String userid,String friendid){

        //如果查询结果>0,说明此记录已经存在
        if(friendDao.count(userid,friendid)>0){
            return 0;
        }

        Friend friend = new Friend();
        friend.setUserid(userid);
        friend.setFriendid(friendid);
        friend.setIslike("0");

        friendDao.save(friend);

        if(friendDao.count(friendid,userid)>0){
            friendDao.update(userid,friendid,"1");
            friendDao.update(friendid,userid,"1");
        }

        userClient.incFollowcount(userid, 1); //当前用户的关注数+1
        userClient.incFanscount(friendid, 1); //好友的粉丝数+1

        return 1;
    }

    /**
     * 添加非好友
     * @param userid
     * @param friendid
     */
    public void addNoFriend(String userid, String friendid) {
        NoFriend noFriend = new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);

        noFriendDao.save(noFriend);
    }

    /**
     * 删除好友
     * @param userid
     * @param friendid
     */
    public void delete(String userid, String friendid) {
        friendDao.deleteFriend(userid,friendid);
        friendDao.update(friendid,userid,"0");
        addNoFriend(userid,friendid);

        userClient.incFollowcount(userid, -1);//当前用户的关注数-1
        userClient.incFanscount(friendid, -1);//好友的粉丝数-1
    }

}
