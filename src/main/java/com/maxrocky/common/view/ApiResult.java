package com.maxrocky.common.view;

/**
 * @Author fly
 * @Date 2017/3/28 下午5:19
 * @Describe Json返回结果抽象类
 */
public abstract class ApiResult {

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static ApiResult success(Object data){
        return new SuccessResult(data);
    }

    public static ApiResult error(String code, String errorMessage){
        return new ErrorResult(code,errorMessage);
    }
}
