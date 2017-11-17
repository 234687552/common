package com.maxrocky.common.http;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author fly
 * @Date 2017/3/31 下午5:16
 * @Describe Http参数类
 */
public class HttpParameter {

    public HttpParameter(){

        this.url = null;
        this.requestEncoded = "UTF-8";
        this.responseEncoded = "UTF-8";
        this.httpRequestType = null;
        this.headerParameter = new HashMap<String,String>();
        this.requestParameter = new HashMap<String,String>();
        this.connectTime = 5000;
        this.connectionRequestTime = 1000;
        this.socketTime = 10000;

    }

    public HttpParameter(String url, HttpRequestType httpRequestType){

        this.url = url;
        this.requestEncoded = "UTF-8";
        this.responseEncoded = "UTF-8";
        this.httpRequestType = httpRequestType;
        this.headerParameter = new HashMap<String,String>();
        this.requestParameter = new HashMap<String,String>();
        this.connectTime = 5000;
        this.connectionRequestTime = 1000;
        this.socketTime = 10000;

    }

    //请求地址
    private String url;

    //请求编码格式
    private String requestEncoded;

    //返回编码格式
    private String responseEncoded;

    //请求方式
    private HttpRequestType httpRequestType;

    //请求头参数
    private Map<String,String> headerParameter;

    //请求参数
    private Map<String,String> requestParameter;

    //链接时间
    private Integer connectTime;

    //请求时间
    private Integer connectionRequestTime;

    //传输时间
    private Integer socketTime;

    public String getUrl() {
        return url;
    }

    public HttpParameter setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getRequestEncoded() {
        return requestEncoded;
    }

    public HttpParameter setRequestEncoded(String requestEncoded) {
        this.requestEncoded = requestEncoded;
        return this;
    }

    public String getResponseEncoded() {
        return responseEncoded;
    }

    public HttpParameter setResponseEncoded(String responseEncoded) {
        this.responseEncoded = responseEncoded;
        return this;
    }

    public HttpRequestType getHttpRequestType() {
        return httpRequestType;
    }

    public HttpParameter setHttpRequestType(HttpRequestType httpRequestType) {
        this.httpRequestType = httpRequestType;
        return this;
    }

    public Map<String, String> getHeaderParameter() {
        return headerParameter;
    }

    public HttpParameter setHeaderParameter(Map<String, String> headerParameter) {
        this.headerParameter = headerParameter;
        return this;
    }

    public Map<String, String> getRequestParameter() {
        return requestParameter;
    }

    public HttpParameter setRequestParameter(Map<String, String> requestParameter) {
        this.requestParameter = requestParameter;
        return this;
    }

    public Integer getConnectTime() {
        return connectTime;
    }

    public HttpParameter setConnectTime(Integer connectTime) {
        this.connectTime = connectTime;
        return this;
    }

    public Integer getConnectionRequestTime() {
        return connectionRequestTime;
    }

    public HttpParameter setConnectionRequestTime(Integer connectionRequestTime) {
        this.connectionRequestTime = connectionRequestTime;
        return this;
    }

    public Integer getSocketTime() {
        return socketTime;
    }

    public HttpParameter setSocketTime(Integer socketTime) {
        this.socketTime = socketTime;
        return this;
    }
}
