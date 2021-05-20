package org.arch.workflow.flowable.rest.task;

import org.arch.workflow.flowable.rest.AbstractPaginateList;
import org.arch.workflow.flowable.rest.RestResponseFactory;

import java.util.List;

/**
 * 任务分页结果类
 *
 * @author lait.zhang@gmail.com
 * @date 2018/12/6
 */
public class TaskTodoPaginateList extends AbstractPaginateList {

    protected RestResponseFactory restResponseFactory;

    public TaskTodoPaginateList(RestResponseFactory restResponseFactory) {
        this.restResponseFactory = restResponseFactory;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    protected List processList(List list) {
        return restResponseFactory.createTaskResponseList(list);
    }
}
