package org.arch.framework.automate.from.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.arch.framework.crud.CrudEntity;

@TableName
@Data
public class ByteArray extends CrudEntity<ByteArray> {
    private byte[] contentByte;
    private String name;


}
