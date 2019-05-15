package com.tensquare;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * Name: JwtTest
 * User: zhaocq
 * Date: 2019/5/11 0011
 * Time: 11:27
 * Description: JWT的入门操作(1.生成令牌（token）  2.解析令牌(token))
 */
public class JwtTest1 {

    public static void main(String[] args) {

        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2NjYiLCJzdWIiOiLlsI_pu4QiLCJ" +
                "pYXQiOjE1NTc1NDc2ODksInJvbGUiOiJhZG1pbiJ9.wVCVCOA_jobiTi4K9rDlh5es_BnurXnA7ux_4p3r4z0";

        Claims claims = Jwts.parser().setSigningKey("itcast").parseClaimsJws(token).getBody();
        System.out.println("id:" + claims.getId());
        System.out.println("用户名:" + claims.getSubject());
        System.out.println("创建日期" + claims.getIssuedAt());
        System.out.println("角色" + claims.get("role"));

    }
    
}
