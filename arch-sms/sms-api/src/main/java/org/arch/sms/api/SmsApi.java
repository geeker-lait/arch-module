package org.arch.sms.api;

import org.arch.sms.api.enums.CodeTyp;

public interface SmsApi {
    void sendCode(CodeTyp codeTyp, String phoneNo);
}
