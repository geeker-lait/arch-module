package org.arch.framework.beans;

import org.apache.commons.beanutils.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 5/3/2021 10:28 PM
 */
public interface Convertable {
    default <T> T convert(Object obj, Class<T> clzz) {
        if (null == obj) {
            return null;
        }
        try {
            T t = clzz.newInstance();
            BeanUtils.copyProperties(obj, t);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    default <T> List<T> converts(List<?> objs, Class<T> clzz) {
        List<T> list = new ArrayList<>();
        try {
            for (Object obj : objs) {
                T t = clzz.newInstance();
                BeanUtils.copyProperties(obj, t);
                list.add(t);
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
