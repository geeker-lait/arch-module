package org.arch.pms.sku.dao;

import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.arch.pms.sku.entity.ProductBrand;
import org.arch.pms.sku.mapper.ProductBrandMapper;
import org.springframework.stereotype.Repository;

/**
 * 产品-品牌(product_brand)数据DAO
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@Repository
public class ProductBrandDao extends ServiceImpl<ProductBrandMapper, ProductBrand> {

}
