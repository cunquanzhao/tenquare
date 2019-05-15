package com.tensquare.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.user.pojo.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface UserDao extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

    /* *
     * @Author zhaocq
     * @Description //TODO 登录查询
     * @Date 10:28 2019/5/11 0011
     * @Param [mobile]
     * @return com.tensquare.user.pojo.User
     **/
    User findByMobile(String mobile);

    /**
     * 更新粉丝数
     *
     * @param userid 用户ID
     * @param x      粉丝数
     */
    @Modifying
    @Query("update User u set u.fanscount=u.fanscount+?2  where u.id=?1")
    public void incFanscount(String userid, int x);

    /**
     * 更新关注数
     * @param userid 用户ID
     * @param x      粉丝数
     */
    @Modifying
    @Query("update User u set u.followcount=u.followcount+?2  where  u.id=?1")
    public void incFollowcount(String userid, int x);
}
