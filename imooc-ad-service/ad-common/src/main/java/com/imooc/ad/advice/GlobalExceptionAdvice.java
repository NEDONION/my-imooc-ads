package com.imooc.ad.advice;

import ch.qos.logback.classic.pattern.ClassNameOnlyAbbreviator;
import com.imooc.ad.exception.AdException;
import com.imooc.ad.vo.CommonResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//异常处理方法
@RestControllerAdvice
public class GlobalExceptionAdvice {

    // 只对AdException的异常进行处理
    @ExceptionHandler(value = AdException.class)
    public CommonResponse<String> handlerAdException(HttpServletRequest req,
            AdException ex) {
        CommonResponse<String> response = new CommonResponse<>(-1, "business error");
        response.setData(ex.getMessage());
        return response;
    }
}
