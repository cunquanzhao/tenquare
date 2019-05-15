package com.tensquare.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.user.pojo.Admin;
/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface AdminDao extends JpaRepository<Admin,String>,JpaSpecificationExecutor<Admin>{

    /* *
     * @Author zhaocq
     * @Description //TODO 登录验证
     * @Date 1:45 2019/5/11 0011
     * @Param [loginname]
     * @return com.tensquare.user.pojo.Admin
     **/
    Admin findByLoginname(String loginname);
}
