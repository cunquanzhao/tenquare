package com.tensquare;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Name: JwtTest
 * User: zhaocq
 * Date: 2019/5/11 0011
 * Time: 11:27
 * Description: JWT的入门操作(1.生成令牌（token）  2.解析令牌(token))
 */
public class JwtTest {

    public static void main(String[] args) {
        //创建日期格式化对象   因为DateFormat类为抽象类 所以不能new
        long now = System.currentTimeMillis();//当前时间
        String token = Jwts.builder()
                .setId("666")
                .setSubject("小黄")
                .setIssuedAt(new Date(now))
//                .setExpiration(new Date(new Date().getTime() + 1000*60))  // 设置过期时间
//                .setExpiration(new Date(new Date().getTime()))
                .signWith(SignatureAlgorithm.HS256, "itcast")
                .claim("role","admin")
                .compact()
                ;
        System.out.println(token);
    }

}
