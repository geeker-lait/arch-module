package org.arch.pms.sku.dao;

import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.arch.pms.sku.entity.ProductAttrName;
import org.arch.pms.sku.mapper.ProductAttrNameMapper;
import org.springframework.stereotype.Repository;

/**
 * 产品-规格参数组下的
参数名(product_attr_name)数据DAO
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@Repository
public class ProductAttrNameDao extends ServiceImpl<ProductAttrNameMapper, ProductAttrName> {

}
