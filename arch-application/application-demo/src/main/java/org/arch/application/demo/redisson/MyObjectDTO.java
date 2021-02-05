package org.arch.application.demo.redisson;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.web.context.request.async.DeferredResult;

import java.io.Serializable;

@Builder
@Data
@ToString
public class MyObjectDTO implements Serializable {
    private Long id;
    private String content;
    private boolean hasLock;
    private String processId;
    private boolean canFlush;
    private String taskId;

    private DeferredResult result;
}
