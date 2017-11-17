package com.maxrocky.common.view;

/**
 * @Author fly
 * @Date 2017/3/28 下午6:19
 * @Describe Json错误返回结果
 */
public class ErrorResult extends ApiResult {

    public ErrorResult(){}

    public ErrorResult(String code,String errorMessage) {
        super.setCode(code);
        this.errorMessage = errorMessage;
    }

    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

}
