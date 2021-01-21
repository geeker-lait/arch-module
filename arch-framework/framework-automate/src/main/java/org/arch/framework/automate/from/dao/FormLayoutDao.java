package org.arch.framework.automate.from.dao;

import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.arch.framework.automate.from.entity.FormLayout;
import org.arch.framework.automate.from.mapper.FormLayoutMapper;
import org.springframework.stereotype.Repository;

/**
 * 表单布局(form_layout)数据DAO
 *
 * @author lait
 * @since 2021-01-18 20:28:43
 * @description PN15855012581
 */
@Slf4j
@Repository
public class FormLayoutDao extends ServiceImpl<FormLayoutMapper, FormLayout> {

}
