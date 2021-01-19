package org.arch.pms.sku.dao;

import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.arch.pms.sku.entity.ProductAlbumPic;
import org.arch.pms.sku.mapper.ProductAlbumPicMapper;
import org.springframework.stereotype.Repository;

/**
 * 产品-相册图片(product_album_pic)数据DAO
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@Repository
public class ProductAlbumPicDao extends ServiceImpl<ProductAlbumPicMapper, ProductAlbumPic> {

}
