package com.tensquare.web.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class WebFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre"; //前置过滤
    }

    @Override
    public int filterOrder() {
        return 0; //多个过滤器的执行顺序，值越小优先级越高
    }

    @Override
    public boolean shouldFilter() {
        return true;//开关,true表示启用过滤方法
    }

    //过滤
    public Object run() throws ZuulException {
        System.out.println("过滤方法执行了");

        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String authorization = request.getHeader("Authorization");
        if(authorization!=null){
            currentContext.addZuulRequestHeader("Authorization",authorization);
        }
        return null; //放行
    }
}
