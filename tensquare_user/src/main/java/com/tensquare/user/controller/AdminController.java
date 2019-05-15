package com.tensquare.user.controller;
import java.util.HashMap;
import java.util.Map;

import com.tensquare.user.pojo.User;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tensquare.user.pojo.Admin;
import com.tensquare.user.service.AdminService;

import entity.PageResult;
import entity.Result;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;


	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private HttpServletRequest httpServletRequest;

	/* *
	 * @Author zhaocq
	 * @Description //TODO 登录
	 * @Date 10:11 2019/5/11 0011
	 * @Param [admin]
	 * @return entity.Result
	 **/
	@RequestMapping(value="/login",method= RequestMethod.POST)
	public Result login(@RequestBody Admin admin){
		Admin adminLogin = adminService.login(admin);

		if(adminLogin == null){
			return new Result(false, StatusCode.LOGINERROR,"登录失败");
		}else{
			// 验证密码
			boolean matches = bCryptPasswordEncoder.matches(admin.getPassword(), adminLogin.getPassword());
			if(matches){
  				// 生成token
				String token = jwtUtil.createJWT(admin.getId(), admin.getLoginname(), "admin");
				HashMap<String, String> map = new HashMap<>();
				map.put("token",token);
				map.put("admin","admin");
				return new Result(true,1000,"登录成功",map);
			}else{
				return new Result(false, StatusCode.LOGINERROR,"登录失败");
			}
		}
	}

	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,1000,"查询成功",adminService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,1000,"查询成功",adminService.findById(id));
	}


	/**
	 * 分页查询全部数据
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value="/{page}/{size}",method=RequestMethod.GET)
	public Result findPage(@PathVariable int page,@PathVariable int size){
		Page<Admin> pageList = adminService.findPage(page, size);
		return new Result(true,1000,"查询成功",new PageResult<Admin>(pageList.getTotalElements(), pageList.getContent() ) );
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
		Page<Admin> pageList = adminService.findSearch(searchMap, page, size);
		return  new Result(true,1000,"查询成功",  new PageResult<Admin>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
	 * 增加
	 * @param admin
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Admin admin){
		adminService.add(admin);
		return new Result(true,1000,"增加成功");
	}
	
	/**
	 * 修改
	 * @param admin
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Admin admin, @PathVariable String id ){

		Claims claims = (Claims) httpServletRequest.getAttribute("admin_claims");
		if(claims==null){
			return new Result(false,StatusCode.ACCESSERROR,"权限不足");
		}

		admin.setId(id);
		adminService.update(admin);		
		return new Result(true,1000,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
       /* // 获取Header
		String header = httpServletRequest.getHeader("Authorization");
		boolean flag = header.startsWith("China ");
		if(flag){
			try {
    			// 获取token
				String token = header.substring(6);
				// 解析token
				Claims claims = jwtUtil.parseJWT(token);
				if (claims == null){
					return new Result(false,StatusCode.ACCESSERROR,"权限不足");
				}else{
					// 通过token获取当前用户的角色
					String admin = claims.get("roles").toString();
					if(admin == null || "".equals(admin) || !"admin".equals(admin)){
						return new Result(false,StatusCode.ACCESSERROR,"权限不足");
					}
				}
				adminService.deleteById(id);
				return new Result(true,1000,"删除成功");

			}catch (Exception e){
				e.printStackTrace();
			}
		}
		return new Result(false,StatusCode.ACCESSERROR,"权限不足");*/

		Claims claims = (Claims) httpServletRequest.getAttribute("admin_claims");
		if(claims==null){
			return new Result(false,StatusCode.ACCESSERROR,"权限不足");
		}

		adminService.deleteById(id);
		return new Result(true,1000,"删除成功");
	}
	
}
