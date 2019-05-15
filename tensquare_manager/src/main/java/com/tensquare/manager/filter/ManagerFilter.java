package com.tensquare.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

@Component
public class ManagerFilter extends ZuulFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();

        //跨域访问放行
        String method = request.getMethod();//获取请求方式
        if("OPTIONS".equals(method)){
            return null;
        }

        //管理员登录放行
        String url = request.getRequestURL().toString();
        if(url.contains("/admin/login")){
            return null;
        }

        String authorization = request.getHeader("Authorization");
        if(authorization!=null&&authorization.startsWith("Bearer ")){
            String token = authorization.substring(7);
            if(!"".equals(token)){
                Claims claims = jwtUtil.parseJWT(token);
                if(claims!=null&&"admin".equals(claims.get("roles"))){
                    currentContext.addZuulRequestHeader("Authorization",authorization);
                    return null; //放行
                }
            }
        }

        currentContext.setSendZuulResponse(false); //阻止放行
        currentContext.setResponseStatusCode(401); //http状态码
        currentContext.setResponseBody("无权访问");
        currentContext.getResponse().setContentType("text/html;charset=UTF-8");


        return null;
    }
}
