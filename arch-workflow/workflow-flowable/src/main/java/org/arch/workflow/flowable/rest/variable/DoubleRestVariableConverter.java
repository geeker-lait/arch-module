package org.arch.workflow.flowable.rest.variable;

import org.flowable.common.engine.api.FlowableIllegalArgumentException;

/**
 * Double类型变量
 *
 * @author lait.zhang@gmail.com
 * @date 2018/12/6
 */
public class DoubleRestVariableConverter implements RestVariableConverter {

    @Override
    public String getRestTypeName() {
        return "double";
    }

    @Override
    public Class<?> getVariableType() {
        return Double.class;
    }

    @Override
    public Object getVariableValue(RestVariable result) {
        if (result.getValue() != null) {
            try {
                return Double.valueOf(String.valueOf(result.getValue()));
            } catch (Exception e) {
                throw new FlowableIllegalArgumentException("Converter can only convert doubles");
            }
        }
        return null;
    }

    @Override
    public void convertVariableValue(Object variableValue, RestVariable result) {
        if (variableValue != null) {
            if (!(variableValue instanceof Double)) {
                throw new FlowableIllegalArgumentException("Converter can only convert doubles");
            }
            result.setValue(variableValue);
        } else {
            result.setValue(null);
        }
    }

}
