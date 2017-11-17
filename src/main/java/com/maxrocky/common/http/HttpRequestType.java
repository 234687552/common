package com.maxrocky.common.http;

/**
 * @Author fly
 * @Date 2017/3/31 下午6:09
 * @Describe Http请求方式
 */
public enum HttpRequestType {

    POST("post请求"),
    GET("get请求"),
    POSTFORJSON("JSON格式的post请求");

    private String value;

    HttpRequestType(String value){
        this.value = value;
    }

}
