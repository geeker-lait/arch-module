package org.arch.payment.sdk.message.builder;

import org.arch.payment.sdk.message.PayOutMessage;

public abstract class BaseBuilder<BuilderType, ValueType> {


    public abstract ValueType build();

    public void setCommon(PayOutMessage m) {

    }

}