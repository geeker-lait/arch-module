package com.unichain.pay.sharelink.utils;

import java.util.UUID;

public final class UUIDUtils {
    public static String getUUIDString() {
        return UUID.randomUUID().toString();
    }

    public static String get32BitUUIDString() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String getUUIDString(int length) {
        String code = get32BitUUIDString();

        if (length < 1 || length >= 32) {
            return code;
        } else {
            return code.substring(code.length() - length);
        }
    }

}
