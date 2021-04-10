package org.arch.framework.automate.generater.service.xmind.modle;
import lombok.Data;

import java.util.List;

@Data
public class Attached {
    private String id;
    private String title;
    private Notes notes;
    private List<Comments> comments;
    private Children children;
}
