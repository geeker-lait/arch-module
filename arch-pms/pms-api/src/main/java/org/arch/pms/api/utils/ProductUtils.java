package org.arch.pms.api.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

@Slf4j
public class ProductUtils {
    public static final String EXP_SKU_POSTFIX = "-L";
    public static final String GLOBAL_SKU_POSTFIX = "-G";
    public static final String WAREHOUSE_SKU_POSTFIX = "-A";
    public static final String YIJIAN_DAIFA_SKU_POSTFIX = "-T";

    public ProductUtils() {
    }

    public static String getSellerSkuCode(String skuCode) {
        String originalSkuCode = skuCode;
        if (skuCode.indexOf("-L") > 0) {
            originalSkuCode = skuCode.substring(0, skuCode.indexOf("-L"));
        }

        if (originalSkuCode.indexOf("-G") > 0) {
            originalSkuCode = originalSkuCode.substring(0, originalSkuCode.indexOf("-G"));
        }

        if (originalSkuCode.indexOf("-A") > 0) {
            originalSkuCode = originalSkuCode.substring(0, originalSkuCode.indexOf("-A"));
        }

        if (originalSkuCode.indexOf("-T") > 0) {
            originalSkuCode = originalSkuCode.substring(0, originalSkuCode.indexOf("-T"));
        }

        if (originalSkuCode.indexOf("-") == 1) {
            originalSkuCode = originalSkuCode.substring(2);
        }

        return originalSkuCode;
    }

    public static boolean isFreshSku(String barCode, String barCodeType, String unit) {
        if (StringUtils.isNotBlank(unit) && unit.toLowerCase().equals("kg")) {
            if (StringUtils.isNotBlank(barCodeType)) {
                return "3".equals(barCodeType) || "03".equals(barCodeType) || "9".equals(barCodeType) || "09".equals(barCodeType);
            }

            if (StringUtils.isNotBlank(barCode)) {
                return barCode.startsWith("23") || barCode.startsWith("29");
            }
        }

        return false;
    }

}
