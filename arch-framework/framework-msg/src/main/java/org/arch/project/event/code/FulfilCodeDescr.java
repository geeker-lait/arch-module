package org.arch.project.event.code;

/**
 * @author lait.zhang@gmail.com
 * @Tel 15801818092
 * @date 11/30/2020 10:08 AM
 * @description: TODO
 */
public enum FulfilCodeDescr implements CodeDescr {
    FULFIL_HAS_EXISTED(20000, "履约单已经存在"),
    FULFIL_NOT_EXIST(20001, "履约单不存在");


    private Integer code;
    private String descr;

    FulfilCodeDescr(Integer code, String descr) {
        this.code = code;
        this.descr = descr;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getDescr() {
        return descr;
    }
}
