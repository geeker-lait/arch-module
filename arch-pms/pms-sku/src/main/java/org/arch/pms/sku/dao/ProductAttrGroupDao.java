package org.arch.pms.sku.dao;

import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.arch.pms.sku.entity.ProductAttrGroup;
import org.arch.pms.sku.mapper.ProductAttrGroupMapper;
import org.springframework.stereotype.Repository;

/**
 * 产品-规格组，规格参数的分组表，
每个商品分类下有多个规格参数组(product_attr_group)数据DAO
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@Repository
public class ProductAttrGroupDao extends ServiceImpl<ProductAttrGroupMapper, ProductAttrGroup> {

}
