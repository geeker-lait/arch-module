package org.arch.framework.automate.generater.xmind;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 3/5/2021 4:59 PM
 */
@Data
@NoArgsConstructor
public class XMindNode {

    private String name;

    private boolean folded;

    private String description;

    private List<XMindNode> childNodes;

    private Set<String> labels;

    public XMindNode(String name) {
        this.name = name;
    }

    public void addLabel(String label){
        if (Objects.isNull(labels)){
            labels = new HashSet<>();
        }
        labels.add(label);
    }
}
