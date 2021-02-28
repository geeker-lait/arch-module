package org.arch.project.event.code;

/**
 * @author lait.zhang@gmail.com
 * @Tel 15801818092
 * @date 11/30/2020 10:01 AM
 * @description: TODO
 */
public enum UserCodeDescr implements CodeDescr {
    USER_HAS_EXISTED(20000,"用户已经存在"),
    USER_NOT_EXIST(20000, "用户不存在");


    private Integer code;
    private String descr;

    UserCodeDescr(Integer code, String descr) {
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
