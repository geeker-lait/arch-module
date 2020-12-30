package org.arch.payment.sdk;

import lombok.Getter;
import org.arch.framework.id.IdKey;
import org.arch.payment.sdk.directive.BindCardDirective;
import org.arch.payment.sdk.directive.PayingDirective;
import org.arch.payment.sdk.directive.PreBindCardDirective;
import org.arch.payment.sdk.directive.PrePayingDirective;

public enum DirectiveCode {
    // 预绑卡
    PRE_BINDCARD_DIRECTIVE(PreBindCardDirective.class, IdKey.BANGKCARD_ID),
    // 确认绑卡
    BINDCARD_DIRECTIVE(BindCardDirective.class, IdKey.BANGKCARD_ID),

    //  预支付
    PRE_PAY_DIRECTIVE(PrePayingDirective.class, IdKey.BANGKCARD_ID),
    //  确认支付
    PAY_DIRECTIVE(PayingDirective.class, IdKey.BANGKCARD_ID),

    // 预转账
    PRE_TRANSATION_DIRECTIVE(BindCardDirective.class, IdKey.BANGKCARD_ID),
    // 确认转账
    TRANSATION_DIRECTIVE(BindCardDirective.class, IdKey.BANGKCARD_ID),

    // 预代扣
    PRE_DEDUCT_DIRECTIVE(BindCardDirective.class, IdKey.BANGKCARD_ID),
    // 确认代扣
    DEDUCT_DIRECTIVE(BindCardDirective.class, IdKey.BANGKCARD_ID),
    ;


    @Getter
    private Class<? extends Directive> directiveTyp;
    @Getter
    private IdKey idKey;

    DirectiveCode(Class<? extends Directive> directiveTyp, IdKey idKey) {
        this.directiveTyp = directiveTyp;
        this.idKey = idKey;
    }

}
