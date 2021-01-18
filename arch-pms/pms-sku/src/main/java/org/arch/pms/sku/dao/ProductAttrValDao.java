package org.arch.pms.sku.dao;

import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.arch.pms.sku.entity.ProductAttrVal;
import org.arch.pms.sku.mapper.ProductAttrValMapper;
import org.springframework.stereotype.Repository;

/**
 * 产品-属性值(product_attr_val)数据DAO
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@Repository
public class ProductAttrValDao extends ServiceImpl<ProductAttrValMapper, ProductAttrVal> {

}
