package org.arch.framework.automate.api;

import org.arch.framework.automate.api.request.FormDefinitionRequest;
import org.arch.framework.automate.api.response.FormDefinitionResponse;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 2/8/2021 1:48 PM
 */
public interface FormDefinitionClient {

    /**
     * 创建form表单
     *
     * @param formDefinitionRequest
     * @return
     */
    FormDefinitionResponse createFormTable(FormDefinitionRequest formDefinitionRequest);


    /**
     * 修改form表单
     * @param
     * @return
     */


}
