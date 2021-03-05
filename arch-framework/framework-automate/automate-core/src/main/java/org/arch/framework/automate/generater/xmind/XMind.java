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

    private String title;

    private File file;

    private List<XMindNode> topics;

    public XMind(String title) {
        this.title = title;
    }

    public void save() throws Exception {
        save(this);
    }

    public static void save(XMind xMind) throws Exception {
            // 创建思维导图的工作空间
            IWorkbookBuilder workbookBuilder = Core.getWorkbookBuilder();
            IWorkbook workbook = workbookBuilder.createWorkbook();

            // 获得默认sheet
            ISheet primarySheet = workbook.getPrimarySheet();

            // 获得根主题
            ITopic rootTopic = primarySheet.getRootTopic();
            // 设置根主题的标题
            rootTopic.setTitleText(xMind.getTitle());

            // 填充内容
            addTopics(workbook, rootTopic, xMind.getTopics());
            // workbook.getManifest().createFileEntry("").openOutputStream();
            // 保存
            workbook.save(xMind.getFile().getAbsolutePath());

    }

    private static void addTopics(IWorkbook workbook, ITopic rootTopic, List<XMindNode> topics) {
        for (XMindNode xMindNode : topics) {
            ITopic topic = workbook.createTopic();
            topic.setTitleText(xMindNode.getName());
            topic.setFolded(xMindNode.isFolded());
            if (Objects.nonNull(xMindNode.getLabels())
                    && !xMindNode.getLabels().isEmpty()){
                topic.setLabels(xMindNode.getLabels());
            }
            List<XMindNode> childNodes = xMindNode.getChildNodes();
            if (Objects.nonNull(childNodes)){
                addTopics(workbook, topic, childNodes);
            }
            rootTopic.add(topic, ITopic.ATTACHED);
        }
    }
}
