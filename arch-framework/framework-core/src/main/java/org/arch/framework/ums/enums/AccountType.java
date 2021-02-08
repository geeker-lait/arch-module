package org.arch.framework.ums.enums;

import org.arch.framework.id.IdKey;
import org.springframework.lang.Nullable;

import static java.util.Objects.isNull;

/**
 * 账号类型
 * @author YongWu zheng
 * @since 2021.1.3 15:03
 */
public enum AccountType {

    /**
     * 系统账号
     */
    ACCOUNT {
        @Override
        public IdKey getIdKey() {
            return IdKey.UMS_ACCOUNT_ID;
        }
    },
    /**
     * 用户
     */
    USER {
        @Override
        public IdKey getIdKey() {
            return IdKey.UMS_USER_ID;
        }
    },
    /**
     * 会员
     */
    MEMBER {
        @Override
        public IdKey getIdKey() {
            return IdKey.UMS_MEMBER_ID;
        }
    },
    /**
     * 商户
     */
    MERCHANT {
        @Override
        public IdKey getIdKey() {
            return IdKey.UMS_MERCHANT_ID;
        }
    };

    /**
     * 返回对应的 {@link IdKey}
     * @return 返回对应的 {@link IdKey}
     */
    public abstract IdKey getIdKey();

    /**
     * 根据 accountType 获取对应的 {@link AccountType}
     * @param accountType   account type 字符串
     * @return  返回 {@link AccountType}, 没有匹配的类型则返回 null.
     */
    @Nullable
    public static AccountType getAccountType(@Nullable String accountType) {
        if (isNull(accountType)) {
        	return null;
        }

        try {
            return valueOf(accountType.trim().toUpperCase());
        }
        catch (IllegalArgumentException e) {
            return null;
        }
    }

}
