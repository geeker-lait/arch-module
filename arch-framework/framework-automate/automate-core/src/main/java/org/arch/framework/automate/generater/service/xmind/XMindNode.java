package org.arch.framework.automate.generater.service.xmind;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 3/5/2021 4:59 PM
 */
@Data
@NoArgsConstructor
public class XMindNode  implements Serializable {

    private String id;

    private String title;

    private Children children;

    @Data
    public static class Children implements Serializable {
        private List<XMindNode> attached;
    }
}
