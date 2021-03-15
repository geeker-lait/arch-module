package org.arch.framework.automate.generater.ex;

import org.arch.framework.beans.enums.StatusCode;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 3/15/2021 2:54 PM
 */
public enum GeneratorCode implements StatusCode {
    GENERATOR_CONFIG_ERROR(400, "生成配置参数错误");

    private int code;
    private String descr;

    GeneratorCode(int code, String descr) {
        this.code = code;
        this.descr = descr;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getDescr() {
        return this.descr;
    }
}
