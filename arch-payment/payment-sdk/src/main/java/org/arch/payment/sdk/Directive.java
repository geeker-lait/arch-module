package org.arch.payment.sdk;

import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 12/29/2020 9:05 PM
 */
public interface Directive {

    Directive setPayAccount(PayAccount payAccount);


    List<? extends Directive> getPayDirectives();

    <T extends DirectiveRequest> T creatDirectiveRequest();
}
