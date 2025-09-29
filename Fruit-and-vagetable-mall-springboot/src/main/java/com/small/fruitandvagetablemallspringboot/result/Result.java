package com.small.fruitandvagetablemallspringboot.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于返回json数据的工具类
 * @author GH
 * @date 2022-04-09 14:42
 */
@Data
@AllArgsConstructor
@Component

public class Result {

    private Integer code;

    private String message;

    private Map<String,Object> data = new HashMap<String ,Object>();

    private Result(){}

    public static Result ok(){
        Result r = new Result();
        r.setCode(ResultCodeEnum.SUCCESS.getCode());
        r.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return r;
    }

    public static Result error(){
        Result r = new Result();
        r.setCode(ResultCodeEnum.FAIL.getCode());
        r.setMessage(ResultCodeEnum.FAIL.getMessage());
        return r;
    }

    public static Result ok(ResultCodeEnum codeEnum){
        Result r = new Result();
        r.setCode(codeEnum.getCode());
        r.setMessage(codeEnum.getMessage());
        return r;
    }

    public static Result error(ResultCodeEnum codeEnum){
        Result r = new Result();
        r.setCode(codeEnum.getCode());
        r.setMessage(codeEnum.getMessage());
        return r;
    }
    public Result data(Map<String,Object> map){
        this.setData(map);
        return this;
    }

    public Result data(String key,Object value){
        this.data.put(key,value);
        return this;
    }

    public Result message(String message){
        this.setMessage(message);
        return this;
    }

    public Result code(Integer code){
        this.setCode(code);
        return this;
    }

}
