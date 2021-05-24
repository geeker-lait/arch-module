package org.arch.alarm.core.engine.rule;

import lombok.Data;

@Data
public class RuleResult {

    private boolean success;

    private Object result;

    public RuleResult() {
    }

    public RuleResult(boolean success) {
        this(success, null);
    }

    public RuleResult(boolean success, Object result) {
        this.success = success;
        this.result = result;
    }
}
