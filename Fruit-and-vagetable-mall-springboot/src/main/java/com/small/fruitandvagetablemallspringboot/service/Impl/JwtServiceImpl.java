package com.small.fruitandvagetablemallspringboot.service.Impl;


import com.small.fruitandvagetablemallspringboot.service.JwtService;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 验签功能实现类
 *
 * @author rest
 */
@Service
public class JwtServiceImpl implements JwtService {

    private final String jwtKey = "GJDSJWDK*#*#4636#*#*";
    private Jwt jwt;

    /**
     * 登录成功返回token字符串
     */
    @Override
    public String createToken(String userName) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("username", userName);


        JwtBuilder jwtBuilder = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, jwtKey) // 签发算法，秘钥为key
                .setClaims(claims)          // body数据，要唯一，自行设置
                .setIssuedAt(new Date())    // 设置签发时间
                .setExpiration(new Date(System.currentTimeMillis() + 3600 * 3));    // 3小时有效时间

        return jwtBuilder.compact();
    }

    /**
     * 验签功能实现类
     *
     * @param token 登录时创建的token字符串
     * @return 验签成功或失败
     */
    @Override
    public boolean checkToken(String token) {
        try {
            jwt = Jwts.parser().setSigningKey(jwtKey).parse(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取token中的载荷上的数据
     */
    @Override
    public Map<String, Object> getChaimData() {
        return (Map<String, Object>) jwt.getBody();
    }
}
