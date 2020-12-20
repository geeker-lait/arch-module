package org.arch.framework.crud;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 12/11/2020 4:19 PM
 */
public class CrudEntity<T extends Model<?>> extends Model<T> implements Serializable {
}
