package org.arch.framework.event;

import lombok.Getter;
import lombok.NonNull;
import org.arch.framework.ums.userdetails.ArchUser;
import org.springframework.context.ApplicationEvent;
import org.springframework.lang.Nullable;

/**
 * 注册事件
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.6 12:40
 */
public class RegisterEvent extends ApplicationEvent {
    private static final long serialVersionUID = 5949415516860129089L;

    @Getter
    private final ArchUser archUser;
    /**
     * 推广或用户推荐
     */
    @Getter
    private final String source;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param archUser  {@link ArchUser}
     * @param source    用户来源(/百度/抖音/头条/微信/微信公众号/微信小程序/用户推荐(user_userId)), 可以为 null.
     */
    public RegisterEvent(@NonNull ArchUser archUser, @Nullable String source) {
        super(archUser.getUsername());
        this.archUser = archUser;
        this.source = source;
    }
}
