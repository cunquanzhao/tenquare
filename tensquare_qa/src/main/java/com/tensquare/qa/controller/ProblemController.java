package com.tensquare.qa.controller;
import java.util.List;
import java.util.Map;

import com.netflix.discovery.converters.Auto;
import com.tensquare.qa.controller.client.BaseClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tensquare.qa.pojo.Problem;
import com.tensquare.qa.service.ProblemService;

import entity.PageResult;
import entity.Result;
/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/problem")
public class ProblemController {

	@Autowired
	private ProblemService problemService;

	@Autowired
	private BaseClient baseClient;

	@RequestMapping(value = "/label/{labelid}",method = RequestMethod.GET)
	public Result findLableById(@PathVariable String labelid){
		return baseClient.findById(labelid);
	}
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,1000,"查询成功",problemService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,1000,"查询成功",problemService.findById(id));
	}


	/**
	 * 分页查询全部数据
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/{page}/{size}",method=RequestMethod.GET)
	public Result findPage(@PathVariable int page,@PathVariable int size){
		Page<Problem> pageList = problemService.findPage(page, size);
		return new Result(true,1000,"查询成功",new PageResult<Problem>(pageList.getTotalElements(), pageList.getContent() ) );
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<Problem> pageList = problemService.findSearch(searchMap, page, size);
		return  new Result(true,1000,"查询成功",  new PageResult<Problem>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
	 * 增加
	 * @param problem
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Problem problem  ){
		problemService.add(problem);
		return new Result(true,1000,"增加成功");
	}
	
	/**
	 * 修改
	 * @param problem
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Problem problem, @PathVariable String id ){
		problem.setId(id);
		problemService.update(problem);		
		return new Result(true,1000,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		problemService.deleteById(id);
		return new Result(true,1000,"删除成功");
	}

	/* *
	 * @Author zhaocq
	 * @Description //TODO 最新的问题列表
	 * @Date 10:53 2019/5/6 0006
	 * @Param [label, page, size]
	 * @return entity.Result
	 **/
	@RequestMapping(value="/newlist/{label}/{page}/{size}",method= RequestMethod.GET)
	public Result newlist(@PathVariable String label, @PathVariable int page, @PathVariable int size){
		Page<Problem> pageData = problemService.newlist(label, page, size);
		return new Result(true,1000,"查询成功",
							new PageResult<>(pageData.getTotalElements(),pageData.getContent()));
	}

	/* *
	 * @Author zhaocq
	 * @Description //TODO 最热回答
	 * @Date 11:17 2019/5/6 0006
	 * @Param [label, page, size]
	 * @return entity.Result
	 **/
	@RequestMapping(value="/hotlist/{label}/{page}/{size}",method= RequestMethod.GET)
	public Result hotlist(@PathVariable String label, @PathVariable int page, @PathVariable int size){
		Page<Problem> pageData = problemService.hotlist(label, page, size);
		return new Result(true,1000,"查询成功",
				new PageResult<>(pageData.getTotalElements(),pageData.getContent()));
	}


	/* *
	 * @Author zhaocq
	 * @Description //TODO 等待回答列表
	 * @Date 11:19 2019/5/6 0006
	 * @Param [label, page, size]
	 * @return entity.Result
	 **/
	@RequestMapping(value="/waitlist/{label}/{page}/{size}",method= RequestMethod.GET)
	public Result waitlist(@PathVariable String label, @PathVariable int page, @PathVariable int size){
		Page<Problem> pageData = problemService.waitlist(label, page, size);
		return new Result(true,1000,"查询成功",
				new PageResult<>(pageData.getTotalElements(),pageData.getContent()));
	}
}
