package code.dao;

import lombok.extern.slf4j.Slf4j;
import code.entity.MemberRights;
import code.mapper.MemberRightsMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 会员权益(member_rights)数据DAO
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@Repository
public class MemberRightsDao extends ServiceImpl<MemberRightsMapper, MemberRights> {

}