package com.tensquare.service;

import com.tensquare.dao.SpitDao;
import com.tensquare.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import java.util.Date;
import java.util.Optional;

/**
 * Name: SpitService
 * User: zhaocq
 * Date: 2019/5/8 0008
 * Time: 11:47
 * Description: 吐槽的业务层接口
 */
@Service
@Transactional
public class SpitService {

    @Autowired
    private SpitDao spitDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private MongoTemplate mongoTemplate;




    /* *
     * @Author zhaocq
     * @Description //TODO 保存(发布吐槽)
     * @Date 11:49 2019/5/8 0008
     * @Param [spit]
     * @return void
     **/
    public void save(Spit spit){
        spit.set_id( idWorker.nextId()+"" );
        spit.setPublishtime(new Date());//发布日期
        spit.setVisits(0);//浏览量
        spit.setShare(0);//分享数
        spit.setThumbup(0);//点赞数
        spit.setComment(0);//回复数
        spit.setState("1");//状态

        if(spit.getParentid() != null && !"".equals(spit.getParentid())){
            // 证明当前是评论信息
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            Update update = new Update();
            update.inc("comment",1);
            mongoTemplate.updateFirst(query,update,"spit");
        }
        spitDao.save(spit);
    }

    /* *
     * @Author zhaocq
     * @Description //TODO 根据Id查询
     * @Date 11:56 2019/5/8 0008
     * @Param [spitId]
     * @return com.tensquare.pojo.Spit
     **/
    public Spit findById(String spitId) {
        return spitDao.findById(spitId).get();
    }

    /* *
     * @Author zhaocq
     * @Description //TODO 修改
     * @Date 12:09 2019/5/8 0008
     * @Param [spit]
     * @return void
     **/
    public void update(Spit spit) {
        spitDao.save(spit);
    }

    /* *
     * @Author zhaocq
     * @Description //TODO 删除
     * @Date 12:11 2019/5/8 0008
     * @Param [spitId]
     * @return void
     **/
    public void deleteById(String spitId) {
        spitDao.deleteById(spitId);
    }

    /* *
     * @Author zhaocq
     * @Description //TODO 分页
     * @Date 12:15 2019/5/8 0008
     * @Param [pageable]
     * @return org.springframework.data.domain.Page<com.tensquare.pojo.Spit>
     **/
    public Page<Spit> findAllByPage(Pageable pageable) {
        return spitDao.findAll(pageable);
    }

    /* *
     * @Author zhaocq
     * @Description //TODO 根据父级Id查询吐槽列表
     * @Date 12:26 2019/5/8 0008
     * @Param [parentid, pageable]
     * @return org.springframework.data.domain.Page<com.tensquare.pojo.Spit>
     **/
    public Page<Spit> findByParentId(String parentid, Pageable pageable) {
        return spitDao.findByParentid(parentid,pageable);
    }

    /* *
     * @Author zhaocq
     * @Description //TODO 吐槽点赞
     * @Date 12:30 2019/5/8 0008
     * @Param [spitId]
     * @return void
     **/
    public void thumbup(String spitId) {

//        Spit spit = spitDao.findById(spitId).get();
//        spit.setThumbup((spit.getThumbup() == null?0:spit.getThumbup())+ 1);
//        spitDao.save(spit);

        // 方式二： 使用原生的MongoDB操作 db.spit.update({"_id":"1"},{$inc:{"thumbup":NumberInt(1)}})
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(spitId));
        Update update = new Update();
        update.inc("thumbup",1);
        mongoTemplate.updateFirst(query,update,"spit");

    }


}
