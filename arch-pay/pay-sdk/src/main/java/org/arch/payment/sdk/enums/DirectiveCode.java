package org.arch.payment.sdk.enums;

import lombok.Getter;
import org.arch.framework.api.IdKey;
import org.arch.payment.sdk.Directive;
import org.arch.payment.sdk.PayParams;
import org.arch.payment.sdk.directive.BindCardDirective;
import org.arch.payment.sdk.directive.PayingDirective;
import org.arch.payment.sdk.directive.PreBindCardDirective;
import org.arch.payment.sdk.directive.PrePayingDirective;
import org.arch.payment.sdk.params.*;

public enum DirectiveCode {
    // 预绑卡
    PRE_BINDCARD_DIRECTIVE(PreBindCardParams.class, PreBindCardDirective.class, IdKey.PAY_BINDCARD_ID),
    // 确认绑卡
    BINDCARD_DIRECTIVE(BindCardParams.class, BindCardDirective.class, IdKey.PAY_BINDCARD_ID),

    //  预支付
    PRE_PAY_DIRECTIVE(PrePayingParams.class, PrePayingDirective.class, IdKey.PAY_ORDER),
    //  确认支付
    PAY_DIRECTIVE(PayingParams.class, PayingDirective.class, IdKey.PAY_ORDER),

    // 预转账
    PRE_TRANSATION_DIRECTIVE(PreTransationParams.class, BindCardDirective.class, IdKey.BANGKCARD_ID),
    // 确认转账
    TRANSATION_DIRECTIVE(TransationParams.class, BindCardDirective.class, IdKey.BANGKCARD_ID),

    // 预代扣
    PRE_DEDUCT_DIRECTIVE(PreDeductParams.class, BindCardDirective.class, IdKey.BANGKCARD_ID),
    // 确认代扣
    DEDUCT_DIRECTIVE(DeductParams.class, BindCardDirective.class, IdKey.BANGKCARD_ID),
    ;


    @Getter
    private Class<? extends Directive> directiveTyp;
    @Getter
    private Class<? extends PayParams> payParams;
    @Getter
    private IdKey idKey;

    DirectiveCode(Class<? extends PayParams> payParams, Class<? extends Directive> directiveTyp, IdKey idKey) {
        this.payParams = payParams;
        this.directiveTyp = directiveTyp;
        this.idKey = idKey;
    }

}
