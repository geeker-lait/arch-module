package org.arch.automate.reader.xmind.meta;

import lombok.Data;

@Data
public class JsonRootBean {
    private String id;
    private String title;
    private RootTopic rootTopic;
}
