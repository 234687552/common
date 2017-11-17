package com.maxrocky.common.view;

/**
 * @Author fly
 * @Date 2017/3/28 下午6:18
 * @Describe Json成功返回结果
 */
public class SuccessResult extends ApiResult {

    public SuccessResult(){}

    public SuccessResult(Object data) {
        super.setCode("success");
        this.data = data;
    }

    private Object data;

    public Object getData() {
        return data;
    }

}
