package org.arch.framework.beans.enums;

/**
 * @author lait.zhang@gmail.com
 * @description: 状态码接口
 * @weixin PN15855012581
 * @date 12/11/2020 12:01 PM
 */
public interface StatusCode extends IntegerCodeDescr,Descr {

    /**
     * 返回的异常错误码
     *
     * @return
     */
    int getCode();

    /**
     * 异常错误信息
     *
     * @return
     */
    String getDescr();
}
