package org.arch.workflow.flowable.rest.model;

import org.arch.workflow.flowable.rest.AbstractPaginateList;
import org.arch.workflow.flowable.rest.RestResponseFactory;

import java.util.List;


/**
 * 模型分页结果类
 *
 * @author lait.zhang@gmail.com
 * @date 2018/12/6
 */
public class ModelsPaginateList extends AbstractPaginateList {

    protected RestResponseFactory restResponseFactory;

    public ModelsPaginateList(RestResponseFactory restResponseFactory) {
        this.restResponseFactory = restResponseFactory;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    protected List processList(List list) {
        return restResponseFactory.createModelResponseList(list);
    }
}
