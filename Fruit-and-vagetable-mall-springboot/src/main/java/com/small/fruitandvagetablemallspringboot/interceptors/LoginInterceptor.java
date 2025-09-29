package com.small.fruitandvagetablemallspringboot.interceptors;

import com.alibaba.fastjson.JSON;
import com.small.fruitandvagetablemallspringboot.result.Result;
import com.small.fruitandvagetablemallspringboot.result.ResultCodeEnum;
import com.small.fruitandvagetablemallspringboot.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录验证拦截器
 *
 * @author chengyuan
 * @since 2022-03-16 14:32
 */

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtService jwtService;

    /**
     * 验签功能实现
     *
     * @author 程元
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        // 修改响应类型编码，防止前台显示乱码
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setHeader("Content-Type", "application/json;charset=UTF-8");

        ServletOutputStream output = response.getOutputStream();
        /*
            为什么不用 response.getWriter();     程序会正常返回结果，但是控制台会报错
            java.lang.IllegalStateException: getWriter() has already been called for this response
            解决办法参考：https://blog.csdn.net/li396864285/article/details/78122296
        */
        String token = request.getHeader("Authorization");
        System.out.println("token=====>"+token);

        // 验签，失败或异常会返回false
        if (jwtService.checkToken(token)) {
//            output.write(JSON.toJSONString(Result.success(jwtService.getChaimData())).getBytes());
            return true;
        }
        // token过期，不合法等都会导致验签不通过，这里一律视为登录态失效
        output.write(JSON.toJSONString(Result.error(ResultCodeEnum.PERMISSION)).getBytes());
        return false;
    }
}
