package org.arch.framework.automate.xmind;

import lombok.Data;

import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 4/26/2021 10:34 AM
 */
@Data
public class DataNode {
    private NodeSpace namespace;
    private String name;
    private String descr;
    private List<DataNode> childs;
}
