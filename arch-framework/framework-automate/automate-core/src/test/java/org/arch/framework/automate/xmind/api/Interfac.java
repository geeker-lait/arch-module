package org.arch.framework.automate.xmind.api;

import lombok.Data;
import org.checkerframework.checker.units.qual.C;

import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date :
 */
@Data
public class Interfac {
    // 接口类目
    private String name;
    // 接口描述
    private String descr;
    // 请求方法
    private List<Curl> curls;
}
