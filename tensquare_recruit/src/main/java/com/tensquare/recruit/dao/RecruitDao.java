package com.tensquare.recruit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.recruit.pojo.Recruit;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface RecruitDao extends JpaRepository<Recruit,String>,JpaSpecificationExecutor<Recruit>{

    /* *
     * @Author zhaocq
     * @Description //TODO 获取推荐职位
     * @Date 10:11 2019/5/6 0006
     * @Param [state]
     * @return java.util.List<com.tensquare.recruit.pojo.Recruit>
     **/
    List<Recruit> findTop4ByStateOrderByCreatetimeDesc(String state);

    /* *
     * @Author zhaocq
     * @Description //TODO 最新职位
     * @Date 10:14 2019/5/6 0006
     * @Param [state]
     * @return java.util.List<com.tensquare.recruit.pojo.Recruit>
     **/
    List<Recruit> findTop12ByStateNotOrderByCreatetimeDesc(String state);
}
