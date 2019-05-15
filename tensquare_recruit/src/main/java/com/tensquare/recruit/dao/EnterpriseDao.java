package com.tensquare.recruit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.recruit.pojo.Enterprise;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface EnterpriseDao extends JpaRepository<Enterprise,String>,JpaSpecificationExecutor<Enterprise>{

    /* *
     * @Author zhaocq
     * @Description //TODO 获取热门企业
     * @Date 10:05 2019/5/6 0006
     * @Param [isHot]
     * @return java.util.List<com.tensquare.recruit.pojo.Enterprise>
     **/
    List<Enterprise> findByIshot(String isHot);
}
