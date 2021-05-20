package org.arch.workflow.common.model;

import lombok.Data;

/**
 * 错误返回类
 *
 * @author lait.zhang@gmail.com
 * @date 2018年4月8日
 */
@Data
public class ErrorInfo {
    private String ret;
    private String msg;

    public ErrorInfo(String ret, String msg) {
        this.ret = ret;
        this.msg = msg;
    }

}
