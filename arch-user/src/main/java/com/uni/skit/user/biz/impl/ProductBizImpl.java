package com.uni.skit.user.biz.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.uni.common.utils.RedisUtils;
import com.uni.product.entity.mybatis.Category;
import com.uni.product.entity.mybatis.CategoryTags;
import com.uni.product.entity.mybatis.Product;
import com.uni.product.entity.mybatis.ProductTags;
import com.uni.product.service.*;
import com.uni.skit.user.biz.IProductBiz;
import com.uni.product.dto.ProductDto;
import com.uni.product.vo.ProductVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description: app产品展示相关业务
 *
 * @author kenzhao
 * @date 2020/4/13 16:30
 */
@Service
@Slf4j
public class ProductBizImpl implements IProductBiz {
    @Autowired
    private IProductService productService;
    @Autowired
    private IProductAttrsService productAttrsService;
    @Autowired
    private IProductLocationsService productLocationsService;
    @Autowired
    private IProductSpecsService productSpecsService;
    @Autowired
    private IProductTagsService productTagsService;
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private ICategoryTagsService categoryTagsService;
    @Autowired
    private RedisUtils redisUtils;
    /**
     * 获取产品列表
     *
     * @param productDto
     * @return
     */
    @Override
    public List<ProductVo> getProductList(ProductDto productDto) {
//        //查询类目信息
//        QueryWrapper<Category> categoryQueryWrapper = new QueryWrapper<>();
//        categoryQueryWrapper.lambda().eq(Category::getCategoryId,productDto.getCategoryId()).eq(Category::getAppId,productDto.getAppId());
//        Category category = categoryService.getOne(categoryQueryWrapper);
//        //查询类目标签信息
//        QueryWrapper<CategoryTags> categoryTagsQueryWrapper = new QueryWrapper<>();
//        categoryTagsQueryWrapper.lambda().eq(CategoryTags::getCategoryId,productDto.getCategoryId()).eq(CategoryTags::getAppId,productDto.getAppId());
//        CategoryTags categoryTags = categoryTagsService.getOne(categoryTagsQueryWrapper);
//        //查询产品信息
//        QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
//        productQueryWrapper.lambda().eq(Product::getCategoryId,productDto.getCategoryId()).eq(Product::getAppId,productDto.getAppId()).eq(Product::getProductId,productDto.getProductId());
//        Product product = productService.getOne(productQueryWrapper);
//        //查询产品标签信息
//        QueryWrapper<ProductTags> productTagsQueryWrapper = new QueryWrapper<>();
//        productTagsQueryWrapper.lambda().eq(ProductTags::getAppId,productDto.getAppId()).eq(ProductTags::getProductId,productDto.getProductId());
//        ProductTags productTags = productTagsService.getOne(productTagsQueryWrapper);

        List<ProductVo> productVos = productService.getProductList(productDto);
        return productVos;
    }


    public List<ProductVo> getProductInfos(ProductDto productDto) {
//        //查询类目信息
//        QueryWrapper<Category> categoryQueryWrapper = new QueryWrapper<>();
//        categoryQueryWrapper.lambda().eq(Category::getCategoryId,productDto.getCategoryId()).eq(Category::getAppId,productDto.getAppId());
//        Category category = categoryService.getOne(categoryQueryWrapper);
//        //查询类目标签信息
//        QueryWrapper<CategoryTags> categoryTagsQueryWrapper = new QueryWrapper<>();
//        categoryTagsQueryWrapper.lambda().eq(CategoryTags::getCategoryId,productDto.getCategoryId()).eq(CategoryTags::getAppId,productDto.getAppId());
//        CategoryTags categoryTags = categoryTagsService.getOne(categoryTagsQueryWrapper);
//        //查询产品信息
//        QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
//        productQueryWrapper.lambda().eq(Product::getCategoryId,productDto.getCategoryId()).eq(Product::getAppId,productDto.getAppId()).eq(Product::getProductId,productDto.getProductId());
//        Product product = productService.getOne(productQueryWrapper);
//        //查询产品标签信息
//        QueryWrapper<ProductTags> productTagsQueryWrapper = new QueryWrapper<>();
//        productTagsQueryWrapper.lambda().eq(ProductTags::getAppId,productDto.getAppId()).eq(ProductTags::getProductId,productDto.getProductId());
//        ProductTags productTags = productTagsService.getOne(productTagsQueryWrapper);

        List<ProductVo> productVos = productService.getProductList(productDto);
        return productVos;
    }
}