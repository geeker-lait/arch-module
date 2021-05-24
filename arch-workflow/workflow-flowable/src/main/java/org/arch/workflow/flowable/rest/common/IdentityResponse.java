package org.arch.workflow.flowable.rest.common;

/**
 * 用户信息返回类
 *
 * @author lait.zhang@gmail.com
 * @date 2018/12/6
 */
public class IdentityResponse {
    private String identityId;
    private String type;
    private String identityName;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    public String getIdentityName() {
        return identityName;
    }

    public void setIdentityName(String identityName) {
        this.identityName = identityName;
    }

}
