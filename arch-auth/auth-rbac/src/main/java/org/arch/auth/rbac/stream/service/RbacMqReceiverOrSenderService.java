package org.arch.auth.rbac.stream.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageConst;
import org.arch.auth.rbac.stream.channel.RbacSource;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.MimeTypeUtils;
import top.dcenter.ums.security.common.utils.UuidUtils;
import top.dcenter.ums.security.core.permission.dto.UpdateRoleResourcesDto;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Collections.singletonMap;
import static java.util.Objects.nonNull;
import static org.apache.rocketmq.common.message.MessageConst.PROPERTY_KEYS;
import static org.apache.rocketmq.common.message.MessageConst.PROPERTY_TAGS;
import static org.arch.auth.rbac.stream.channel.RbacSink.RBAC_PERMISSION_UPDATE_CONSUME;
import static org.arch.auth.rbac.utils.RbacUtils.publishEvent;
import static org.springframework.util.StringUtils.hasText;

/**
 * 发送权限更新消息到 MQ(output) 的服务.<br>
 * 从 MQ(input) 订阅权限更新消息的服务.
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.3.20 13:25
 */
@Slf4j
public class RbacMqReceiverOrSenderService implements ApplicationEventPublisherAware {

    /**
     * RBAC permission update cache tag
     */
    public static final String RPUC_TAG = "RPUC";
    /**
     * retry cron
     */
    public static final String RETRY_CRON = "0 */5 * * * ?";

    /**
     * 用于校验是否本应用发送的消息
     */
    private final Map<String, Integer> messageKeysCache = new ConcurrentHashMap<>(64);

    /**
     * 用于放置发送失败的消息
     */
    private final Map<String, Message<Object>> retryMessages = new ConcurrentHashMap<>(64);

    private final RbacSource source;
    private ApplicationEventPublisher applicationEventPublisher;

    public RbacMqReceiverOrSenderService(@Nullable RbacSource source) {
        this.source = source;
    }

    /**
     * 订阅权限更新消息处理 //
     * @param message   {@link Message}
     */
    @StreamListener(value = RBAC_PERMISSION_UPDATE_CONSUME,
            condition = ("headers['rocketmq_" + PROPERTY_TAGS + "']=='" + RPUC_TAG + "'"))
    public void rbacPermissionUpdateConsume(Message<UpdateRoleResourcesDto<Object>> message) {
        String keys = (String) message.getHeaders().get("rocketmq_" + PROPERTY_KEYS);
        // 检查是否本应用发送的消息
        if (hasText(keys) && nonNull(messageKeysCache.remove(keys))) {
            // 本应用本地权限缓存已更新, 直接返回
            return;
        }
        // 从 MQ 接收权限更新事件, 并发布本地角色更新事件
        publishEvent(message.getPayload(), this, applicationEventPublisher, Boolean.FALSE);
    }

    /**
     * 发送消息, 带默认 {@link MessageConst#PROPERTY_TAGS} 的 {@code RPUC}.
     * @param msg   消息
     * @param <T>   消息类型
     * @return 消息的全局唯一 keys, 当发送失败时返回 null.
     */
    @Nullable
    public <T> String send(@NonNull T msg) {
        return sent(msg, PROPERTY_TAGS, RPUC_TAG);
    }

    /**
     * 发送消息, 带标签 {@code tag = tagValue}.
     * @param msg       消息
     * @param tag       标签
     * @param tagValue  标签值
     * @param <T>       消息类型
     * @return 消息的全局唯一 keys, 当发送失败时返回 null.
     */
    @Nullable
    public <T> String sendWithTags(@NonNull T msg, @NonNull String tag, @NonNull String tagValue) {
        return sent(msg, tag, tagValue);
    }

    /**
     * 发送异常消息, 带标签 {@code tag = tagValue}.
     * @param msg       异常
     * @param tag       标签
     * @param tagValue  标签值
     */
    public void sendErrorWithTags(@NonNull Throwable msg, @NonNull String tag, @NonNull String tagValue) throws Exception {
        if (null != this.source) {
            Message<Throwable> message =
                    MessageBuilder.createMessage(msg, new MessageHeaders(singletonMap(tag, tagValue)));
            source.rbacPermissionUpdateProducer().send(message);
        }
        log.warn("org.arch.auth.rbac.stream.channel.RbacSource is null");
    }

    /**
     * 发送消息, 带标签 {@code tag = tagValue}.
     * @param msg       消息
     * @param tag       标签
     * @param tagValue  标签值
     * @param <T>       消息类型
     * @return 消息的全局唯一 keys, 当发送失败时返回 null.
     */
    @Nullable
    public <T> String sendObject(@NonNull T msg, @NonNull String tag, @NonNull String tagValue){
        return sent(msg, tag, tagValue);
    }

    @Override
    public void setApplicationEventPublisher(@NonNull ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Scheduled(cron = RETRY_CRON)
    public void retry() {
        /*
            应用的权限更新频率极少, 所以这里用了定时任务的方式.
            待优化: 极端情况下, 不停的更新权限且发送 MQ 的消息一直失败, 这样 retryMessages 会无限扩容, 直到 OOM.
         */
        if (nonNull(this.source)) {
            Iterator<Map.Entry<String, Message<Object>>> iterator = retryMessages.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Message<Object>> entry = iterator.next();
                String keys = entry.getKey();
                try {
                    boolean send = this.source.rbacPermissionUpdateProducer().send(entry.getValue());
                    if (send) {
                        iterator.remove();
                        log.info("重试发送权限更新事件成功: keys={}", keys);
                    }
                }
                catch (Exception e) {
                    log.error(String.format("重试发送权限更新事件失败: keys=%s", keys), e);
                }
            }
        }
    }

    /**
     * 发送消息, 带标签 {@code tag = tagValue}.
     * @param msg       消息
     * @param tag       标签
     * @param tagValue  标签值
     * @param <T>       消息类型
     * @return 消息的全局唯一 keys, 当发送失败时返回 null.
     */
    @Nullable
    private <T> String sent(@NonNull T msg, @NonNull String tag, @NonNull String tagValue) {
        if (null != this.source) {
            MessageBuilder<T> builder = MessageBuilder.withPayload(msg)
                                                      .setHeader(tag, tagValue)
                                                      .setHeader(MessageHeaders.CONTENT_TYPE,
                                                                 MimeTypeUtils.APPLICATION_JSON);
            if (!PROPERTY_TAGS.equals(tag)) {
                builder.setHeader(PROPERTY_TAGS, RPUC_TAG);
            }
            String keys;
            if (PROPERTY_KEYS.equals(tag)) {
                keys = tagValue;
            }
            else {
                keys = UuidUtils.getUUID();
                builder.setHeader(PROPERTY_KEYS, keys);
            }
            Message<T> message = builder.build();
            try {
                boolean send = this.source.rbacPermissionUpdateProducer().send(message);
                if (!send) {
                    //noinspection unchecked
                    this.retryMessages.put(keys, (Message<Object>) message);
                    log.warn("权限更新事件发送失败: {}", message.toString());
                    return null;
                }
                // 缓存本应用发送的消息 tags
                this.messageKeysCache.put(keys, 1);
                return keys;
            }
            catch (Exception e) {
                String errMsg = String.format("权限更新事件发送失败: %s", message.toString());
                log.error(errMsg, e);
                //noinspection unchecked
                this.retryMessages.put(keys, (Message<Object>) message);
                return null;
            }
        }
        log.warn("org.arch.auth.rbac.stream.channel.RbacSource is null");
        return null;
    }
}
