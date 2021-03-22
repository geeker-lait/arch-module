package org.arch.framework.automate.generater.properties;

import org.arch.framework.automate.generater.core.ConfigProperties;

import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 3/15/2021 6:16 PM
 */

public class MethodProperties implements ConfigProperties {
    // 方法名
    private String name;
    // http请求方法（post/get/delete/update/....）
    private String httpMethod;
    // 方法描述
    private String descr;
    // 方法输入参数
    private List<ParamProperties> input;
    // 方法输出参数
    private ParamProperties output;

    private boolean client = true;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHttpMethod() {
        if (httpMethod.equalsIgnoreCase("post")) {
            return "PostMapping";
        } else if (httpMethod.equalsIgnoreCase("get")) {
            return "GetMapping";
        } else if (httpMethod.equalsIgnoreCase("put")) {
            return "PutMapping";
        } else if (httpMethod.equalsIgnoreCase("delete")) {
            return "DeleteMapping";
        }
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public List<ParamProperties> getInput() {
        return input;
    }

    public void setInput(List<ParamProperties> input) {
        this.input = input;
    }

    public ParamProperties getOutput() {
        return output;
    }

    public void setOutput(ParamProperties output) {
        this.output = output;
    }

    public boolean getClient() {
        return client;
    }

    public void setClient(boolean client) {
        this.client = client;
    }
}
