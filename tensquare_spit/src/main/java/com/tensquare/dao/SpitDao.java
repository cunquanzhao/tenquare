package com.tensquare.dao;

import com.tensquare.pojo.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Name: SpitDao
 * User: zhaocq
 * Date: 2019/5/8 0008
 * Time: 11:46
 * Description: 吐槽的持久层接口
 */
public interface SpitDao extends MongoRepository<Spit,String>{
    
    /* *
     * @Author zhaocq
     * @Description //TODO 根据父级Id查询吐槽列表
     * @Date 12:26 2019/5/8 0008
     * @Param [parentid, pageable]
     * @return org.springframework.data.domain.Page<com.tensquare.pojo.Spit>
     **/
    Page<Spit> findByParentid(String parentid, Pageable pageable);
}
