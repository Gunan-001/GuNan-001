package com.small.fruitandvagetablemallspringboot.exception;

import com.small.fruitandvagetablemallspringboot.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 异常处理工具类
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandle {

    /**
     *  捕获404异常
     * @return
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public Result notFoundException(NoHandlerFoundException e){
        log.error("资源未找到",e);
        Result result = new Result(404,("资源未找到"+e.getMessage()),null);
        return result;
    }

    /**
     * 400——Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result handleHttpMessageNotReadableException(HttpMessageNotReadableException  e) {
        log.error("参数解析失败", e);
        Result result = new Result(400,("参数解析失败,"+e.getMessage()),null);
        return result;
    }

    /**
     *  405——Method Not Allowed
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException  e){
        log.error("不支持当前请求方法",e);
        Result result = new Result(405,("不支持当前请求方法,"+e.getMessage()),null);
        return result;
    }

    /**
     * 415——Unsupported Media Type
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Result handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e){
        log.error("不支持当前媒体",e);
        Result result = new Result(415,("不支持当前媒体"+e.getMessage()),null);
        return result;
    }

    /**
     * 500：服务器内部异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public Result internalServerError(Exception e){
        log.error("服务器内部异常",e);
        Result result = new Result(500,("服务器内部异常"+e.getMessage()),null);
        return result;
    }
}
