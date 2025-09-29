package com.small.fruitandvagetablemallspringboot.result;

import lombok.Getter;

@Getter
public enum ResultCodeEnum {

    SUCCESS(200,"成功"),
    FAIL(404, "操作失败"),
    PERMISSION(403, "没有权限"),
    PARAM_ERROR( 400, "参数不正确"),
    LOGIN_ERROR( 404, "账户名或密码错误");

    private Integer code;
    private String message;

     ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
