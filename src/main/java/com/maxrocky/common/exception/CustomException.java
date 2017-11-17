package com.maxrocky.common.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kingboy--KingBoyWorld@163.com
 * @date 2017/8/1 下午5:13
 * @desc 自定义异常.
 */
@Data
@NoArgsConstructor
public class CustomException extends RuntimeException {

    public CustomException(String code, String msg) {
        super(code);
        this.code = code;
        this.msg = msg;
    }
    //错误码
    private String code;
    //错误提示信息
    private String msg;

}
