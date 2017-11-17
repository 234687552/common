package com.maxrocky.common.file;

import com.aliyun.oss.OSSClient;
import com.maxrocky.common.exception.ExceptionManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

/**
 * Created by fly on 2017/10/26.
 * 上传文件
 */
@Component
public class Upload {

    @Resource
    private ExceptionManager exceptionManager;

    //OSS连接地址
    @Value("${oss.endpoint}")
    private String endpoint;
    //KeyId
    @Value("${oss.accessKeyId}")
    private String accessKeyId;
    //KeySecret
    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;
    //bucketName
    @Value("${oss.bucketName}")
    private String bucketName;
    //文件名
    @Value("${oss.key}")
    private String key;

    /**
     *@Author:Fly
     *@Date:下午3:55 2017/10/26
     *@Description: 上传文件至阿里云OSS
     */
    public String uploadOSS(MultipartFile file){

        if (Objects.isNull(file)|| StringUtils.isEmpty(file.getOriginalFilename())){

            throw exceptionManager.create("UPLOAD_OSS001");
        }

        URL resultImgUrl = null;

        String fileName =
                new Random().nextInt(10000)
                        +System.currentTimeMillis()
                        +file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")).toLowerCase();
        try {

            OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            ossClient.putObject(
                    bucketName,
                    key+fileName,
                    file.getInputStream());
            resultImgUrl = ossClient.generatePresignedUrl(bucketName,key+fileName,new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 100));
            ossClient.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultImgUrl.toString();
    }
}
