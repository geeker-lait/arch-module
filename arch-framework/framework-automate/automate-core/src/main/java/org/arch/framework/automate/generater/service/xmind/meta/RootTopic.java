package org.arch.framework.automate.generater.service.xmind.meta;
import lombok.Data;

import java.util.List;

@Data
public class RootTopic {
    private String id;
    private String title;
    private Notes notes;
    private List<Comments> comments;
    private Children children;
}
