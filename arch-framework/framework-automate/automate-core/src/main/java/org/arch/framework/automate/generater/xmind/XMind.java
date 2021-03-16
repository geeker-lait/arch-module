package org.arch.framework.automate.generater.xmind;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.xmind.core.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

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
