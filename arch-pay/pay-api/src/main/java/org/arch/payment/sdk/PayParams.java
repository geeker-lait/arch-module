package org.arch.payment.sdk;

import com.alibaba.fastjson.JSONObject;
import org.arch.framework.beans.utils.ReflectUtils;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 12/31/2020 10:46 AM
 */
public interface PayParams {
    default JSONObject getParams() {
        return ReflectUtils.convert(this, JSONObject.class);
    }
}
