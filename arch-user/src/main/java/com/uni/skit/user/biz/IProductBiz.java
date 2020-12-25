package com.uni.skit.user.biz;

import com.uni.product.dto.ProductDto;
import com.uni.product.vo.ProductVo;

import java.util.List;

/**
 * Description: app产品相关业务
 *
 * @author kenzhao
 * @date 2020/4/13 15:49
 */
public interface IProductBiz {

    /**
     * 获取产品列表
     * @param productDto
     * @return
     */
    List<ProductVo> getProductList(ProductDto productDto);
}