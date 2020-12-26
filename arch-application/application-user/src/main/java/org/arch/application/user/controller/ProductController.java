package org.arch.application.user.controller;

import com.uni.product.dto.ProductDto;
import com.uni.product.vo.ProductVo;
import org.arch.ums.account.biz.IProductBiz;
import org.arch.ums.account.vo.ApiBaseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Description: 用户相关功能
 *
 * @author kenzhao
 * @date 2020/3/30 19:04
 */
@Api(tags = "查询产品信息")
@RestController
public class ProductController {

    @Autowired
    private IProductBiz productBiz;

    @ApiOperation(value = "查询贷款app产品信息", authorizations = @Authorization(value = "token"))
    @PostMapping("/product/getAppProdcutList")
    public ApiBaseResult getAppProdcutList(@Validated @RequestBody ProductDto productDto) {
        productDto.setCategoryCode("DAIKUANAPP");
        List<ProductVo> productVos = productBiz.getProductList(productDto);
        return ApiBaseResult.success(productVos);
    }

    @ApiOperation(value = "查询贷超产品信息", authorizations = @Authorization(value = "token"))
    @PostMapping("/product/getProdcutList")
    public ApiBaseResult getProdcutList(@Validated @RequestBody ProductDto productDto) {
        productDto.setCategoryCode("DAICHAO");
        List<ProductVo> productVos = productBiz.getProductList(productDto);
        return ApiBaseResult.success(productVos);
    }

}