package test.auth.ums.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import test.auth.ums.entity.Identifier;

/**
 * 用户-标识(Identifier) 表数据库 Mapper 层
 *
 * @author YongWu zheng
 * @date 2021-01-29 20:52:46
 * @since 1.0.0
 */
@Mapper
public interface IdentifierMapper extends BaseMapper<Identifier> {

}