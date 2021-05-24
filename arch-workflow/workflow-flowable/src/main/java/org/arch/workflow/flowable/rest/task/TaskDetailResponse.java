package org.arch.workflow.flowable.rest.task;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.flowable.task.api.DelegationState;

/**
 * 任务明细返回类
 *
 * @author lait.zhang@gmail.com
 * @date 2018/12/6
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TaskDetailResponse extends HistoricTaskResponse {
    private String delegationState;
    private boolean suspended;
    protected String ownerName;
    protected String assigneeName;
    private String formKey;

    public void setDelegationState(DelegationState delegationState) {
        this.delegationState = getDelegationStateString(delegationState);
    }

    private String getDelegationStateString(DelegationState state) {
        String result = null;
        if (state != null) {
            result = state.toString().toLowerCase();
        }
        return result;
    }


}
