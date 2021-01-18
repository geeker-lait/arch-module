package code.dao;

import lombok.extern.slf4j.Slf4j;
import code.entity.FormInstance;
import code.mapper.FormInstanceMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 表单实例(form_instance)数据DAO
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@Repository
public class FormInstanceDao extends ServiceImpl<FormInstanceMapper, FormInstance> {

}