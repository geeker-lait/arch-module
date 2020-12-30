package org.arch.payment.core;

import org.arch.payment.sdk.DirectiveName;
import org.arch.payment.sdk.DirectiveRequest;
import org.arch.payment.sdk.PayDirective;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date :
 */
@Service
public class DefaultPayChannel implements PayChannel{




    @Override
    public List<PayDirective> getPayDirectives(DirectiveRequest directiveRequest) {

        DirectiveName directiveName = directiveRequest.getDirectiveName();

        directiveName.getDirectiveRequest()


        return null;
    }
}
