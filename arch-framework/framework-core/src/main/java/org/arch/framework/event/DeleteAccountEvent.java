package org.arch.framework.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 删除账号事件
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.6 13:12
 */
public class DeleteAccountEvent extends ApplicationEvent {
    private static final long serialVersionUID = 850830967092472322L;

    @Getter
    private final Long accountId;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param accountId 账号 id
     */
    public DeleteAccountEvent(Long accountId) {
        super(accountId);
        this.accountId = accountId;
    }
}
