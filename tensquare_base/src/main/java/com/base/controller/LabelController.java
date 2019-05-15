package com.base.controller;

import com.base.domain.Label;
import com.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Name: LabelController
 * User: zhaocq
 * Date: 2019/5/5 0005
 * Time: 11:45
 * Description: 标签的控制器
 */
@RestController
@RequestMapping("/label")
@CrossOrigin
public class LabelController {

    @Autowired
    private LabelService labelService;

    @Autowired
    private HttpServletRequest request;

    /* *
     * @Author zhaocq
     * @Description //TODO 保存
     * @Date 11:52 2019/5/5 0005
     * @Param [label]
     * @return entity.Result
     **/
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Label label){
        labelService.save(label);
        return new Result(true, StatusCode.OK,"保存成功");
    }



    /* *
     * @Author zhaocq
     * @Description //TODO 根据Id查询
     * @Date 12:02 2019/5/5 0005
     * @Param [labelId]
     * @return entity.Result
     **/
    @RequestMapping(value = "/{labelId}", method = RequestMethod.GET)
    public Result findById(@PathVariable String labelId){
        String authorization = request.getHeader("Authorization");
        System.out.println("获取的头信息是："+authorization);

        Label label = labelService.findById(labelId);
        return new Result(true, StatusCode.OK,"查询成功",label);
    }

    /* *
     * @Author zhaocq
     * @Description //TODO 根据Id删除
     * @Date 12:05 2019/5/5 0005
     * @Param [labelId]
     * @return entity.Result
     **/
    @RequestMapping(value = "/{labelId}", method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String labelId){
        labelService.deleteById(labelId);
        return new Result(true, StatusCode.OK,"删除成功");
    }


    /* *
     * @Author zhaocq
     * @Description //TODO 修改
     * @Date 12:07 2019/5/5 0005
     * @Param [labelId, label]
     * @return entity.Result
     **/
    @RequestMapping(value = "/{labelId}", method = RequestMethod.PUT)
    public Result save(@PathVariable String labelId, @RequestBody Label label){
        label.setId(labelId);
        labelService.update(label);
        return new Result(true, StatusCode.OK,"修改成功");
    }

    /* *
    * @Author zhaocq
    * @Description //TODO 查询全部
    * @Date 12:07 2019/5/5 0005
    * @Param [labelId, label]
    * @return entity.Result
    **/
    @RequestMapping(method = RequestMethod.GET)
    public Result save(){
        List<Label> labelList = labelService.findAll();
        return new Result(true, StatusCode.OK,"查询成功",labelList);
    }


    /* *
     * @Author zhaocq
     * @Description //TODO 按条件查询
     * @Date 8:49 2019/5/6 0006
     * @Param [label]
     * @return entity.Result
     **/
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result search(@RequestBody Label label){
        List<Label> labelList = labelService.search(label);
        return new Result(true, StatusCode.OK,"查询成功",labelList);
    }


    /* *
     * @Author zhaocq
     * @Description //TODO 按条件查询(分页)
     * @Date 8:49 2019/5/6 0006
     * @Param [label]
     * @return entity.Result
     **/
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result searchPage(@RequestBody Label label, @PathVariable int page, @PathVariable int size){
        Page<Label> pageData = labelService.search(label,page,size);
        return new Result(true, StatusCode.OK,"查询成功",
                            new PageResult<>(pageData.getTotalElements(),pageData.getContent()));
    }
}
