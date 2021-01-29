package org.arch.ums.user.dao;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.arch.framework.crud.CrudDao;
import org.arch.ums.user.entity.IdCard;
import org.arch.ums.user.mapper.IdCardMapper;
import org.springframework.stereotype.Repository;

/**
 * 用户身份证表(IdCard) 表数据库访问层
 *
 * @author YongWu zheng
 * @date 2021-01-29 22:05:56
 * @since 1.0.0
 */
@Slf4j
@Repository
public class IdCardDao extends ServiceImpl<IdCardMapper, IdCard> implements CrudDao<IdCard> {

}