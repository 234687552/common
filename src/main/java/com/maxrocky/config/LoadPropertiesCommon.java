package com.maxrocky.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by fly on 2017/10/16.
 */
@Configuration
@PropertySource(value = {"exception_common.properties"}, encoding = "UTF-8")
public class LoadPropertiesCommon {
}
