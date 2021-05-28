package org.arch.framework.automate.common.api;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * api 包信息
 *
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.5.22 18:43
 */
@Data
@Accessors(chain = true)
public class Api {
    /**
     * api 包名称
     */
    private String name;
    /**
     * 接口描述
     */
    private String descr;
    /**
     * 接口列表
     */
    private final List<Interfac> interfaces = new ArrayList<>();

    public boolean addInterface(Interfac interfac) {
        return this.interfaces.add(interfac);
    }

}
