package com.tensquare.user.filter;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String authorization = request.getHeader("Authorization");
        if(authorization!=null&&!"".equals(authorization)){
            if(authorization.startsWith("Bearer ")){
                String token = authorization.substring(7);
                Claims claims = jwtUtil.parseJWT(token);
                if(claims!=null){
                    if("admin".equals(claims.get("roles"))){
                        request.setAttribute("admin_claims",claims);
                    }else{
                        request.setAttribute("user_claims",claims);
                    }
                }
            }
        }
        return true; //放行
    }
}
