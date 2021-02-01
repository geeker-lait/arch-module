package org.arch.ums.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.arch.ums.account.entity.Tag;

/**
 * 账号-标签(Tag) 表数据库 Mapper 层
 *
 * @author YongWu zheng
 * @date 2021-01-29 21:30:19
 * @since 1.0.0
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

}