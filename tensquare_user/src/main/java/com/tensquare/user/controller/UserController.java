package com.tensquare.user.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tensquare.user.pojo.Admin;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tensquare.user.pojo.User;
import com.tensquare.user.service.UserService;

import entity.PageResult;
import entity.Result;
import util.JwtUtil;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtUtil jwtUtil;


	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	//修改粉丝数
	@RequestMapping(value = "/fanscount/{userid}/{x}",method = RequestMethod.POST)
	public Result incFanscount(@PathVariable String userid,@PathVariable int x){
		userService.incFanscount(userid,x);
		return new Result(true, StatusCode.OK,"修改成功");
	}

	//修改关注数
	@RequestMapping(value = "/followcount/{userid}/{x}",method = RequestMethod.POST)
	public Result incFollowcount(@PathVariable String userid,@PathVariable int x){
		userService.incFollowcount(userid,x);
		return new Result(true, StatusCode.OK,"修改成功");
	}

	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,1000,"查询成功",userService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,1000,"查询成功",userService.findById(id));
	}


	/**
	 * 分页查询全部数据
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/{page}/{size}",method=RequestMethod.GET)
	public Result findPage(@PathVariable int page,@PathVariable int size){
		Page<User> pageList = userService.findPage(page, size);
		return new Result(true,1000,"查询成功",new PageResult<User>(pageList.getTotalElements(), pageList.getContent() ) );
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
		Page<User> pageList = userService.findSearch(searchMap, page, size);
		return  new Result(true,1000,"查询成功",  new PageResult<User>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
	 * 增加
	 * @param user
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody User user  ){
		userService.add(user);
		return new Result(true,1000,"增加成功");
	}
	
	/**
	 * 修改
	 * @param user
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody User user, @PathVariable String id ){
		user.setId(id);
		userService.update(user);		
		return new Result(true,1000,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		userService.deleteById(id);
		return new Result(true,1000,"删除成功");
	}


	/* *
	 * @Author zhaocq
	 * @Description //TODO 发送短信验证码
	 * @Date 12:23 2019/5/9 0009
	 * @Param [imobiled]
	 * @return entity.Result
	 **/
	@RequestMapping(value="/sendsms/{mobile}",method= RequestMethod.POST)
	public Result sendsms(@PathVariable String mobile){
		userService.sendsms(mobile);
		return new Result(true,1000,"发送成功");
	}



	/* *
	 * @Author zhaocq
	 * @Description //TODO 注册
	 * @Date 12:44 2019/5/9 0009
	 * @Param [code]
	 * @return entity.Result
	 **/
	@RequestMapping(value="/register/{code}",method= RequestMethod.POST)
	public Result register(@RequestBody User user, @PathVariable String code){
		String newPassWord = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(newPassWord);
		userService.register(user,code);
		return new Result(true,1000,"注册成功");
	}


	/* *
	 * @Author zhaocq
	 * @Description //TODO 用户登录
	 * @Date 10:27 2019/5/11 0011
	 * @Param [admin]
	 * @return entity.Result
	 **/
	@RequestMapping(value="/login",method= RequestMethod.POST)
	public Result login(@RequestBody User user){
		User userLogin = userService.login(user);
		if(userLogin == null){
			return new Result(false, StatusCode.LOGINERROR,"登录失败");
		}else{
			// 验证密码
			boolean matches = bCryptPasswordEncoder.matches(user.getPassword(), userLogin.getPassword());
			if(matches){
				String token = jwtUtil.createJWT(userLogin.getId(), userLogin.getNickname(), "user");
				Map map = new HashMap();
				map.put("token",token);

				// 给当前用户鉴权
				return new Result(true,1000,"登录成功",map);
			}else{
				return new Result(false, StatusCode.LOGINERROR,"登录失败");
			}
		}
	}
	
}
