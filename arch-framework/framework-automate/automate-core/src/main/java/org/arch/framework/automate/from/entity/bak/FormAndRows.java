package org.arch.framework.automate.from.entity.bak;

import lombok.Data;
import lombok.ToString;

/**
 * 前端为了显示行数，所以添加返回前端的数据
 */
@Data
@ToString
public class FormAndRows extends Form {
    private Integer rowNumber;
    private Integer fieldNumber;
    private Integer ifCreateTable;
}
