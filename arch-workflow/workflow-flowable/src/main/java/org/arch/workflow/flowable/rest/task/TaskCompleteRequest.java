package org.arch.workflow.flowable.rest.task;

import org.arch.workflow.flowable.rest.variable.RestVariable;

import java.util.List;

/**
 * 任务完成请求类
 *
 * @author lait.zhang@gmail.com
 * @date 2018/12/6
 */
public class TaskCompleteRequest {

    private List<RestVariable> variables;

    public List<RestVariable> getVariables() {
        return variables;
    }

    public void setVariables(List<RestVariable> variables) {
        this.variables = variables;
    }

}
