package code.dao;

import lombok.extern.slf4j.Slf4j;
import code.entity.MemberLife;
import code.mapper.MemberLifeMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 会员生命周期(member_life)数据DAO
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@Repository
public class MemberLifeDao extends ServiceImpl<MemberLifeMapper, MemberLife> {

}