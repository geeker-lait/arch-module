package org.arch.payment.sdk.directive;

import org.arch.payment.sdk.Directive;
import org.arch.payment.sdk.PayParams;
import org.arch.payment.sdk.params.PreBindCardParams;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 12/30/2020 2:20 PM
 */
public interface PreBindCardDirective extends Directive {

    void setPayParams(PreBindCardParams payParams);
}
