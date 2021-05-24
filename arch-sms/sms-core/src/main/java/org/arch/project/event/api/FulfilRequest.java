package org.arch.project.event.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * 履约请求
 */
@ToString
@Data
public class FulfilRequest {
    // 订单通道(OMS/ALEPH/OTHERS)
    private String orderChannel;
    // 履约id号,回传给业务方，分布式id生成器生成
    private Long fulfilId;
    // 履约阶段
    private FulfilPhase fulfilPhase;
    /**
     * 履约批次，如果有填写批次（比如拆成几单，分几次完成），如果没有为0,
     * 这个可以提前确定，因为买货方一定知道货存情况
     */
    private Integer fulfilBatch;
    // 具体订单参数
    private JSONObject orderParams;

    private FulfilRequest() {

    }

    public static FulfilRequest of(FulfilPhase fulfilPhase) {
        FulfilRequest fulfilRequest = new FulfilRequest();
        //
        fulfilRequest.fulfilPhase = fulfilPhase;

        // 生成id
        fulfilRequest.fulfilId = Long.MAX_VALUE;
        return fulfilRequest;
    }

    /**
     * 创建map请求参数
     *
     * @param httpServletRequest
     * @return
     */
    public FulfilRequest init(HttpServletRequest httpServletRequest) {
        orderParams = getRequestParam(httpServletRequest);

        Long fid = orderParams.getLong("fulfilId");
        // 关联用
        if (fid != 0) {
            fulfilId = fid;
        }
        return this;
    }


    /**
     * @param request
     * @return
     * @throws Exception
     * @description 从POST请求中获取参数
     */
    private JSONObject getRequestParam(HttpServletRequest request) {
        // 返回参数
        JSONObject params = new JSONObject();
        // 获取内容格式327977
        String contentType = request.getContentType();
        if (contentType != null && !contentType.equals("")) {
            contentType = contentType.split(";")[0];
        }
        // form表单格式  表单形式可以从 ParameterMap中获取
        if ("appliction/x-www-form-urlencoded".equalsIgnoreCase(contentType)) {
            // 获取参数
            Map<String, String[]> parameterMap = request.getParameterMap();
            if (parameterMap != null) {
                for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                    params.put(entry.getKey(), entry.getValue()[0]);
                }
            }
        }
        // json格式 json格式需要从request的输入流中解析获取
        if ("application/json".equalsIgnoreCase(contentType)) {
            // 使用 commons-io中 IOUtils 类快速获取输入流内容
            String paramJson = null;
            try {
                paramJson = IOUtils.toString(request.getInputStream(), "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            params.putAll(JSON.parseObject(paramJson));
        }
        return params;
    }

}
