package org.arch.framework.ums.enums;

import org.arch.framework.api.IdKey;
import org.arch.framework.beans.exception.BusinessException;
import org.arch.framework.beans.exception.constant.ArgumentStatuesCode;
import org.springframework.lang.Nullable;

import java.util.concurrent.TimeUnit;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.substringAfterLast;

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

        @Override
        public int getIdLengthNonBizPrefix() {
            return getIdLengthNonBizPrefix(IdKey.UMS_ACCOUNT_ID);
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

        @Override
        public int getIdLengthNonBizPrefix() {
            return getIdLengthNonBizPrefix(IdKey.UMS_USER_ID);
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

        @Override
        public int getIdLengthNonBizPrefix() {
            return getIdLengthNonBizPrefix(IdKey.UMS_MEMBER_ID);
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

        @Override
        public int getIdLengthNonBizPrefix() {
            return getIdLengthNonBizPrefix(IdKey.UMS_MERCHANT_ID);
        }
    };

    /**
     * 返回对应的 {@link IdKey}
     * @return 返回对应的 {@link IdKey}
     */
    public abstract IdKey getIdKey();

    /**
     * {@link IdKey} 对应的去除 bizPrefix 长度的 id 长度.
     * @return 返回 {@link IdKey} 对应的去除 bizPrefix 长度的 id 长度.
     */
    public abstract int getIdLengthNonBizPrefix();
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

    static int getIdLengthNonBizPrefix(IdKey idKey) {
        String fmtSuffix = idKey.getFmtSuffix();
        TimeUnit timeUnit = idKey.getTimeUnit();
        String fmtSuffixLen = substringAfterLast(substringAfterLast(fmtSuffix, "$"), "d");
        switch(timeUnit) {
            case SECONDS: case MINUTES:
                return 10 + Integer.parseInt(fmtSuffixLen);
            case DAYS:
                return 5 + Integer.parseInt(fmtSuffixLen);
            default:
                throw new BusinessException(ArgumentStatuesCode.VALID_ERROR, null, "获取ID长度错误.");
        }
    }

}
