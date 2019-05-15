package com.tensquare.qa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{

    /* *
     * @Author zhaocq
     * @Description //TODO 最新的问题列表
     * @Date 11:00 2019/5/6 0006
     * @Param [label, pageable]
     * @return org.springframework.data.domain.Page<com.tensquare.qa.pojo.Problem>
     **/
    @Query(value = "SELECT * FROM tb_problem,tb_pl WHERE labelid = problemid AND labelid = ? ORDER BY replytime DESC",nativeQuery = true)
    Page<Problem> newlist(String label, Pageable pageable);

    /* *
     * @Author zhaocq
     * @Description //TODO 最热回答
     * @Date 11:17 2019/5/6 0006
     * @Param [label, pageable]
     * @return org.springframework.data.domain.Page<com.tensquare.qa.pojo.Problem>
     **/
    @Query(value = "SELECT * FROM tb_problem,tb_pl WHERE labelid = problemid AND labelid = ? ORDER BY reply DESC",nativeQuery = true)
    Page<Problem> hotlist(String label, Pageable pageable);

    /* *
     * @Author zhaocq
     * @Description //TODO 等待回答的列表
     * @Date 11:20 2019/5/6 0006
     * @Param [label, pageable]
     * @return org.springframework.data.domain.Page<com.tensquare.qa.pojo.Problem>
     **/
    @Query(value = "SELECT * FROM tb_problem,tb_pl WHERE labelid = problemid AND labelid = ? AND reply = 0",nativeQuery = true)
    Page<Problem> waitlist(String label, Pageable pageable);
}
