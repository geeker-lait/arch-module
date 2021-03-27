package org.arch.framework.automate.generater.service.xmind;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 3/5/2021 4:59 PM
 */
@Data
@NoArgsConstructor
public class XMind {
    // title的内容
    private String title;
    // topic的id
    private String id;

    private XMindNode rootTopic;
}
