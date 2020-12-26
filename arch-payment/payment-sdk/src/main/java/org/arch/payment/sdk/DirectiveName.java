package org.arch.payment.sdk;

import lombok.Getter;
import org.arch.payment.sdk.request.*;

public enum DirectiveName {
    // 预绑卡
    PRE_BINDCARD_DIRECTIVE(PreBindCardDirectiveRequest.class),
    // 确认绑卡
    BINDCARD_DIRECTIVE(BindCardDirectiveRequest.class),

    //  预支付
    PRE_PAY_DIRECTIVE(PrePayDiectiveRequest.class),
    //  确认支付
    PAY_DIRECTIVE(PayDiectiveRequest.class),

    // 预转账
    PRE_TRANSATION_DIRECTIVE(PreTransationDiectiveRequest.class),
    // 确认转账
    TRANSATION_DIRECTIVE(PreBindCardDirectiveRequest.class),

    // 预代扣
    PRE_DEDUCT_DIRECTIVE(PreDeductDiectiveRequest.class),
    // 确认代扣
    DEDUCT_DIRECTIVE(DeductDiectiveRequest.class),
    ;


    @Getter
    private Class<? extends DirectiveRequest> directiveRequest;

    DirectiveName(Class<? extends DirectiveRequest> directiveRequest) {
        this.directiveRequest = directiveRequest;
    }

}
