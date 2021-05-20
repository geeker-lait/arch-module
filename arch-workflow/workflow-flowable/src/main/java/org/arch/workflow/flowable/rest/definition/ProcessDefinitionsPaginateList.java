package org.arch.workflow.flowable.rest.definition;

import org.arch.workflow.flowable.rest.AbstractPaginateList;
import org.arch.workflow.flowable.rest.RestResponseFactory;

import java.util.List;


/**
 * 流程定义分页结果类
 *
 * @author lait.zhang@gmail.com
 * @date 2018/12/6
 */
public class ProcessDefinitionsPaginateList extends AbstractPaginateList {

    protected RestResponseFactory restResponseFactory;

    public ProcessDefinitionsPaginateList(RestResponseFactory restResponseFactory) {
        this.restResponseFactory = restResponseFactory;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    protected List processList(List list) {
        return restResponseFactory.createProcessDefinitionResponseList(list);
    }
}
