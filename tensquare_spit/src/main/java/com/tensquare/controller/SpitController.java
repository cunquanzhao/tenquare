package com.tensquare.controller;

import com.tensquare.pojo.Spit;
import com.tensquare.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * Name: SpitController
 * User: zhaocq
 * Date: 2019/5/8 0008
 * Time: 11:50
 * Description:吐槽的控制器
 */
@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {

    @Autowired
    private SpitService spitService;


    @Autowired
    private RedisTemplate redisTemplate;


    /* *
     * @Author zhaocq
     * @Description //TODO 保存
     * @Date 11:52 2019/5/8 0008
     * @Param [spit]
     * @return entity.Result
     **/
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Spit spit){
        spitService.save(spit);
        return new Result(true, StatusCode.OK,"保存成功");
    }


    /* *
     * @Author zhaocq
     * @Description //TODO 根据Id查询
     * @Date 12:08 2019/5/8 0008
     * @Param [spitId]
     * @return entity.Result
     **/
    @RequestMapping(value = "/{spitId}", method = RequestMethod.GET)
    public Result findById(@PathVariable String spitId){
        Spit spit = spitService.findById(spitId);

        return new Result(true, StatusCode.OK,"查询成功",spit);
    }


    /* *
     * @Author zhaocq
     * @Description //TODO 修改
     * @Date 12:10 2019/5/8 0008
     * @Param [spitId, spit]
     * @return entity.Result
     **/
    @RequestMapping(value = "/{spitId}", method = RequestMethod.PUT)
    public Result update(@PathVariable String spitId, @RequestBody Spit spit){
        spit.set_id(spitId);
        spitService.update(spit);
        return new Result(true, StatusCode.OK,"修改成功");
    }


    /* *
     * @Author zhaocq
     * @Description //TODO 根据Id删除
     * @Date 12:11 2019/5/8 0008
     * @Param [spitId]
     * @return entity.Result
     **/
    @RequestMapping(value = "/{spitId}", method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String spitId){
        spitService.deleteById(spitId);
        return new Result(true, StatusCode.OK,"删除成功");
    }


    /* *
     * @Author zhaocq
     * @Description //TODO 分页查询
     * @Date 12:15 2019/5/8 0008
     * @Param [page, size]
     * @return entity.Result
     **/
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.GET)
    public Result findAllByPage(@PathVariable int page,@PathVariable int size){
        Pageable pageable = PageRequest.of(page-1,size);
        Page<Spit> pageData = spitService.findAllByPage(pageable);
        return new Result(true, StatusCode.OK,"查询成功",
                        new PageResult<>(pageData.getTotalElements(),pageData.getContent()));
    }


    /* *
     * @Author zhaocq
     * @Description //TODO 根据父级Id查询吐槽列表
     * @Date 12:25 2019/5/8 0008
     * @Param [parentid, page, size]
     * @return entity.Result
     **/
    @RequestMapping(value = "/comment/{parentid}/{page}/{size}", method = RequestMethod.GET)
    public Result findByParentId(@PathVariable String parentid,@PathVariable int page,@PathVariable int size){
        Pageable pageable = PageRequest.of(page-1,size);
        Page<Spit> pageData = spitService.findByParentId(parentid,pageable);
        return new Result(true, StatusCode.OK,"查询成功",
                new PageResult<>(pageData.getTotalElements(),pageData.getContent()));
    }


    /* *
     * @Author zhaocq
     * @Description //TODO 吐槽点赞
     * @Date 12:30 2019/5/8 0008
     * @Param [spitId]
     * @return entity.Result
     **/
    @RequestMapping(value = "/thumbup/{spitId}", method = RequestMethod.PUT)
    public Result thumbup(@PathVariable String spitId){
        // 获取当前登录的用户id
//        String userId = "2023";
//        Object userId_r = redisTemplate.opsForValue().get("spit_" + spitId);
//        if(userId_r != null && !"".equals(userId_r)){
//            return new Result(false, StatusCode.ERROR,"不能重复点赞");
//        }
        spitService.thumbup(spitId);
//        redisTemplate.opsForValue().set("spit_" + spitId,userId);
        return new Result(true, StatusCode.OK,"点赞成功");
    }
}
