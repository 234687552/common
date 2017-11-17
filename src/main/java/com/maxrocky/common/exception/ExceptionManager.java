package com.maxrocky.common.exception;


import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author kingboy--KingBoyWorld@163.com
 * @date 2017/8/1 下午5:22
 * @desc 异常工厂.
 * <pre>
 *      @Resource
 *      ExceptionManager exceptionManager;
 *
 *      throw exceptionManager.create("ERR001");
 * </pre>
 * 结合@PropertySource(value = {"exception.properties"}, encoding = "UTF-8")
 *
 * 然后在exception.properties文件上定义：
 *
 *      ERR001="不能为空"
 *
 * 在exception处理上：
 * <pre>
 *     if (e instanceOf CustomException.Class){
 *         ((CustomException)e).getMsg() ; //该值为"不能为空"
 *     }
 * </pre>
 */
@Component
public class ExceptionManager {

    @Resource
    Environment environment;

    public CustomException create(String code) {
        return new CustomException(code, environment.getProperty(code));
    }

}
