package com.unichain.pay.channel.mfe88.directive;

import org.arch.payment.sdk.Directive;
import org.arch.payment.sdk.PayAccount;
import org.arch.payment.sdk.directive.PreBindCardDirective;
import org.arch.payment.sdk.params.PreBindCardParams;

public class Mfe88PreBindCardDirective implements PreBindCardDirective {


    @Override
    public Directive setPayAccount(PayAccount payAccount) {
        return null;
    }

    @Override
    public void exec() {

    }

    @Override
    public void setPayParams(PreBindCardParams payParams) {

    }
}
