package com.maxrocky.common.http;

import com.maxrocky.common.exception.ExceptionManager;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author fly
 * @Date 2017/3/31 下午5:12
 * @Describe HttpClient工具类
 */
@Component
public class HttpClientUtils {

    @Autowired
    private ExceptionManager exceptionManager;

    /**
     * @Author fly
     * @Date 2017/4/1 下午1:41
     * @Parameter
     * @ReturnValues
     * @Describe 返回结果http请求结果字符串
     */
    public String httpResponseResult(HttpParameter httpParameter) {

        this.checkParameter(httpParameter);

        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();

//        return HttpRequestType.POST.equals(httpParameter.getHttpRequestType())
//                ?
//                this.httpPost(closeableHttpClient,httpParameter)
//                :
//                this.httpGet(closeableHttpClient,httpParameter);

        return HttpRequestType.POST.equals(httpParameter.getHttpRequestType())
                ?
                this.httpPost(closeableHttpClient, httpParameter)
                :
                HttpRequestType.GET.equals(httpParameter.getHttpRequestType()) ?
                        this.httpGet(closeableHttpClient, httpParameter)
                        :
                        this.httpPostForJson(closeableHttpClient, httpParameter);

    }

    /**
     * @Author fly
     * @Date 2017/3/31 下午6:06
     * @Parameter
     * @ReturnValues
     * @Describe 检测参数是否正确
     */
    private void checkParameter(HttpParameter httpParameter) {

        if (Objects.isNull(httpParameter)) {
            throw exceptionManager.create("HC_CP10001");
        } else if (Objects.isNull(httpParameter.getUrl())) {
            throw exceptionManager.create("HC_CP10002");
        } else if (Objects.isNull(httpParameter.getHttpRequestType())) {
            throw exceptionManager.create("HC_CP10003");
        } else if (Objects.isNull(httpParameter.getRequestEncoded())) {
            throw exceptionManager.create("HC_CP10004");
        } else if (Objects.isNull(httpParameter.getResponseEncoded())) {
            throw exceptionManager.create("HC_CP10005");
        }

    }

    /**
     * @Author fly
     * @Date 2017/4/1 上午9:54
     * @Parameter
     * @ReturnValues
     * @Describe post请求
     */
    private String httpPost(CloseableHttpClient closeableHttpClient, HttpParameter httpParameter) {

        HttpPost httpPost = new HttpPost(httpParameter.getUrl());

        List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();

        if (Objects.nonNull(httpParameter.getHeaderParameter()) && httpParameter.getHeaderParameter().size() > 0) {
            for (Map.Entry<String, String> headerParameter : httpParameter.getHeaderParameter().entrySet()) {
                httpPost.setHeader(headerParameter.getKey(), headerParameter.getValue());
            }
        }

        if (Objects.nonNull(httpParameter.getRequestParameter()) && httpParameter.getRequestParameter().size() > 0) {
            for (Map.Entry<String, String> requestParameter : httpParameter.getRequestParameter().entrySet()) {
                nameValuePairList.add(new BasicNameValuePair(requestParameter.getKey(), requestParameter.getValue()));
            }
        }

        String result = null;

        try {

            HttpEntity httpRequestEntity =
                    new UrlEncodedFormEntity(
                            nameValuePairList, httpParameter.getRequestEncoded());

            httpPost.setConfig(
                    RequestConfig.custom()
                            .setConnectTimeout(httpParameter.getConnectTime())
                            .setConnectionRequestTimeout(httpParameter.getConnectionRequestTime())
                            .setSocketTimeout(httpParameter.getSocketTime())
                            .build());

            httpPost.setEntity(httpRequestEntity);
            HttpResponse httpResponse = closeableHttpClient.execute(httpPost);
            HttpEntity httpResponseEntity = httpResponse.getEntity();

            result = EntityUtils.toString(httpResponseEntity, httpParameter.getResponseEncoded());

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;

    }

    /**
     * @Author fly
     * @Date 2017/4/1 上午11:32
     * @Parameter
     * @ReturnValues
     * @Describe get请求
     */
    private String httpGet(CloseableHttpClient closeableHttpClient, HttpParameter httpParameter) {

        HttpGet httpGet = new HttpGet();

        List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();

        if (Objects.nonNull(httpParameter.getHeaderParameter()) && httpParameter.getHeaderParameter().size() > 0) {
            for (Map.Entry<String, String> headerParameter : httpParameter.getHeaderParameter().entrySet()) {
                httpGet.setHeader(headerParameter.getKey(), headerParameter.getValue());
            }
        }

        if (Objects.nonNull(httpParameter.getRequestParameter()) && httpParameter.getRequestParameter().size() > 0) {
            for (Map.Entry<String, String> requestParameter : httpParameter.getRequestParameter().entrySet()) {
                nameValuePairList.add(new BasicNameValuePair(requestParameter.getKey(), requestParameter.getValue()));
            }
        }

        String result = null;

        try {

            httpGet.setConfig(
                    RequestConfig.custom()
                            .setConnectTimeout(httpParameter.getConnectTime())
                            .setConnectionRequestTimeout(httpParameter.getConnectionRequestTime())
                            .setSocketTimeout(httpParameter.getSocketTime())
                            .build());

            httpGet.setURI(
                    URI.create(
                            httpParameter.getUrl() + "?" +
                                    EntityUtils.toString(new UrlEncodedFormEntity(nameValuePairList, httpParameter.getRequestEncoded()))));

            HttpResponse httpResponse = closeableHttpClient.execute(httpGet);
            HttpEntity httpResponseEntity = httpResponse.getEntity();

            result = EntityUtils.toString(httpResponseEntity, httpParameter.getResponseEncoded());

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;

    }

    /**
     * @Author jepson
     * @Date 2017/6/12 上午9:09
     * @Parameter
     * @ReturnValues
     * @Describe 请求格式是json的post请求
     */
    private String httpPostForJson(CloseableHttpClient closeableHttpClient, HttpParameter httpParameter) {

        HttpPost httpPost = new HttpPost(httpParameter.getUrl());

        List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();

        if (Objects.nonNull(httpParameter.getHeaderParameter()) && httpParameter.getHeaderParameter().size() > 0) {
            for (Map.Entry<String, String> headerParameter : httpParameter.getHeaderParameter().entrySet()) {
                httpPost.setHeader(headerParameter.getKey(), headerParameter.getValue());
            }
        }

        if (Objects.nonNull(httpParameter.getRequestParameter()) && httpParameter.getRequestParameter().size() > 0) {
            for (Map.Entry<String, String> requestParameter : httpParameter.getRequestParameter().entrySet()) {
                nameValuePairList.add(new BasicNameValuePair(requestParameter.getKey(), requestParameter.getValue()));
            }
        }

        String result = null;

        try {

            JSONObject jsonParam = new JSONObject();
            if (Objects.nonNull(httpParameter.getRequestParameter()) && httpParameter.getRequestParameter().size() > 0) {
                for (Map.Entry<String, String> requestParameter : httpParameter.getRequestParameter().entrySet()) {
                    jsonParam.put(requestParameter.getKey(), requestParameter.getValue());
                }
            }
            StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");//解决中文乱码问题
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");

            httpPost.setEntity(entity);
            HttpResponse httpResponse = closeableHttpClient.execute(httpPost);
            HttpEntity httpResponseEntity = httpResponse.getEntity();

            result = EntityUtils.toString(httpResponseEntity, httpParameter.getResponseEncoded());

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;

    }

}
