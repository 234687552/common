package com.maxrocky.common.exception;

import com.maxrocky.common.view.ApiResult;
import lombok.extern.log4j.Log4j;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Iterator;
import java.util.List;


/**
 * @author lzd--liangzidi@maxrocky.com
 * @date 2017/11/16 0016  16:50
 * @desc rest接口异常处理
 */
@RestControllerAdvice
@Log4j
public class ExceptionController {


    @Resource
    ExceptionManager exceptionManager;

    /**
     * 其他错误处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ApiResult exception(Exception e) {
        log.error("系统异常", e);
        return ApiResult.error("SYS_EXCEPTION", "系统异常");
    }

    /**
     * 自定义错误处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public ApiResult customException(CustomException e) {
        log.error("自定义的异常", e);
        return ApiResult.error(e.getCode(), e.getMsg());
    }

    /**
     * 违反约束异常处理 主要表现为数据库异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResult constraintViolationException(ConstraintViolationException e) {
        log.error("校验异常", e);
        String code = "";
        ConstraintViolationException constraintViolationException = (ConstraintViolationException) e;
        Iterator<ConstraintViolation<?>> iterator = constraintViolationException.getConstraintViolations().iterator();
        if (iterator.hasNext()) {
            code = (iterator.next()).getMessageTemplate();
        }
        CustomException exception = exceptionManager.create(code);
        return ApiResult
                .error(exception.getCode(), exception.getMsg());
    }

    /**
     * 校验异常
     * 对于@NotNull,@NotBlank,@NotEmpty等校验(记住需要结合@Valid才起效)
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResult methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("校验失败", e);
        BindingResult result = ((MethodArgumentNotValidException) e).getBindingResult();
        List<ObjectError> errors = result.getAllErrors();
        String message = errors.stream()
                .map(ObjectError::getDefaultMessage)
                .reduce((a, b) -> a + ";" + b)
                .orElse("");
        return ApiResult.error("VALID FAIL", message);
    }

    /**
     * 请求参数欠缺异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ApiResult missingServletRequestParameterException(MissingServletRequestParameterException e) {
        //请求欠缺参数
        log.error("请求参数欠缺", e);
        return ApiResult.error("PARAM MISS", e.getMessage());

    }

    /**
     * 请求方法错误异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ApiResult httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        //请求方法错误
        log.error("请求方法错误", e);
        return ApiResult.error("METHOD TYPE WRONG", e.getMessage());
    }

    /**
     * 请求参数错误
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageConversionException.class)
    public ApiResult httpMessageConversionException(HttpMessageConversionException e) {
        //请求参数错误
        log.error("请求参数错误", e);
        return ApiResult.error("METHOD PARAM WRONG", e.getMessage());
    }
}
