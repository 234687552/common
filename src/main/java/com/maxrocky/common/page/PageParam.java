package com.maxrocky.common.page;

import lombok.Data;

/**
 * @author kingboy--KingBoyWorld@163.com
 * @date 2017/7/23 下午7:54
 * @desc  分页请求参数封装.
 */
@Data
public class PageParam {

    public static final int PAGE_SIZE = 10;

    public PageParam() {
        this.c = PAGE_SIZE;
    }

    public PageParam(Integer p, Integer c) {
        this.setP(p);
        this.setC(c);
    }

    //当前页
    private Integer p = 0;

    //每页容量
    private Integer c;

    public int firstResult() {
        return (p - 1) * c;
    }

    public Integer getP() {
        return p;
    }

    public void setP(Integer p) {
        if (p != null) {
            this.p = p;
        }
    }

    public Integer getC() {
        return c;
    }

    public void setC(Integer c) {
        if (c != null) {
            this.c = c;
        }
    }

    /**
     * @desc 分页时获取页码
     */
    public Integer getPForPaging() {
       return this.getP() - 1;
    }

}
