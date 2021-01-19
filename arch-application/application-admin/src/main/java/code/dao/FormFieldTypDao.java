package code.dao;

import lombok.extern.slf4j.Slf4j;
import code.entity.FormFieldTyp;
import code.mapper.FormFieldTypMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 表单字段类型(form_field_typ)数据DAO
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@Repository
public class FormFieldTypDao extends ServiceImpl<FormFieldTypMapper, FormFieldTyp> {

}