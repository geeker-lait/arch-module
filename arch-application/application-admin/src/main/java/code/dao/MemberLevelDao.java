package code.dao;

import lombok.extern.slf4j.Slf4j;
import code.entity.MemberLevel;
import code.mapper.MemberLevelMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 会员级别(member_level)数据DAO
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@Repository
public class MemberLevelDao extends ServiceImpl<MemberLevelMapper, MemberLevel> {

}