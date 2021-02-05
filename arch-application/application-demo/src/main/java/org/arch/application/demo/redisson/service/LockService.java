package org.arch.application.demo.redisson.service;

import lombok.extern.slf4j.Slf4j;
import org.arch.application.demo.redisson.MyObjectDTO;
import org.arch.framework.beans.Response;
import org.redisson.api.RLock;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Slf4j
@Service
public class LockService {

    @Autowired
    private RedissonClient redisson;

    private final String KEY_PREFIX = "countersign:";


    /**
     * 客户端映射 key:processId value:包含了taskId跟延时请求对象
     */
    private final ConcurrentMap<String, List<MyObjectDTO>> clientResultMap = new ConcurrentHashMap<>();

    @PostConstruct
    private void init() {
        RTopic topic = redisson.getTopic(KEY_PREFIX + "lockTopic");
        topic.addListener(String.class, (channel, processId) -> {
            List<MyObjectDTO> clients = clientResultMap.remove(processId);
            if (null != clients) {
                clients.forEach(client -> {
                    DeferredResult<Response<MyObjectDTO>> deferredResult = client.getResult();
                    if (null != deferredResult && !deferredResult.isSetOrExpired()) {
                        //返回可刷新操作
                        deferredResult.setResult(Response.success(MyObjectDTO.builder().canFlush(true).build()));
                    }
                });
            }
        });
    }

    public void unLock(String corpId, String processId, String userId) {
        String lockUserKey = KEY_PREFIX + corpId + ":" + processId + ":lockUser";
        Object lockUser = redisson.getBucket(lockUserKey).get();
        if (null != lockUser) {
            if (lockUser.toString().contains(userId)) {
                try {
                    //删除锁用户信息
                    if (redisson.getBucket(lockUserKey).delete()) {
                        //发布实例id
                        publishProcessId(processId);
                    }
                } catch (Exception e) {
                    log.error("解锁失败:{}", e);
                }
            }
        }
    }

    private void publishProcessId(String processId) {
        RTopic topic = redisson.getTopic(KEY_PREFIX + "lockTopic");
        topic.publish(processId);
    }

    public DeferredResult<Response<MyObjectDTO>> tryLock(String corpId, String taskId) {
        DeferredResult<Response<MyObjectDTO>> deferredResult = createDeferredResult();
        TaskEntity task = TaskService.getById(corpId, taskId);

        String lockKey = KEY_PREFIX + corpId + ":" + task.getFdProcessId() + ":lock";
        String lockUserInfo = KEY_PREFIX + corpId + ":" + task.getFdProcessId() + ":lockUser";

        RLock lock = redisson.getFairLock(lockKey);
        try {
            //获取锁
            lock.lock();
            Object lockUser = redisson.getBucket(lockUserInfo).get();
            if (null == lockUser) {
                //...执行业务逻辑...
                deferredResult.setResult(Response.success(MyObjectDTO.builder().hasLock(true).processId(task.getFdProcessId()).build()));
            } else {
                //获取不到锁操作
                String[] str = lockUser.toString().split("#");
                String lockUserName = str[0];
                String lockUserId = str[1];
                //防止已经获取锁的用户再次操作
                if (lockUserId.equals(task.getFdUserId())) {
                    deferredResult.setResult(Response.success(MyObjectDTO.builder().hasLock(true).processId(task.getFdProcessId()).build()));
                    return deferredResult;
                }
                //缓存请求
                List<MyObjectDTO> clients = clientResultMap.get(task.getFdProcessId());
                if (null != clients) {
                    Optional<MyObjectDTO> countersignClient = clients.stream().filter(client -> client.getTaskId().equals(taskId)).findFirst();
                    if (countersignClient.isPresent()) {
                        countersignClient.get().setResult(deferredResult);
                    } else {
                        clients.add(MyObjectDTO.builder().taskId(taskId).result(deferredResult).build());
                    }
                } else {
                    clients = new ArrayList<>();
                    clients.add(MyObjectDTO.builder().taskId(taskId).result(deferredResult).build());
                    clientResultMap.putIfAbsent(task.getFdProcessId(), clients);
                }
            }
        } catch (Exception e) {
            log.info("获取锁lockKey:[{}]异常:{}", lockKey, e);
        } finally {
            if (lock.isLocked()) {
                lock.unlock();
            }
        }
        return deferredResult;
    }

    private DeferredResult<Response<MyObjectDTO>> createDeferredResult() {
        //默认过期时间为40秒
        DeferredResult<Response<MyObjectDTO>> deferredResult = new DeferredResult<>(40000L);
        deferredResult.onTimeout(() -> deferredResult.setResult(Response.success(MyObjectDTO.builder().hasLock(false).build())));
        return deferredResult;
    }

}
