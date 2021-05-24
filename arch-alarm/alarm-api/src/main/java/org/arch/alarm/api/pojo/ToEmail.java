package org.arch.alarm.api.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 5/11/2021 8:58 AM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToEmail implements Serializable {

    /**
     * 发件人邮箱
     */
    private String mailFrom;
    /**
     * 发件人昵称
     */
    private String mailFromNick;
    /**
     * 邮件接收方，可多人
     */
    private List<String> mailTos = new ArrayList<>();
    /**
     * 抄送人邮箱(可为空，方法内部处理)
     */
    private List<String> ccs = new ArrayList<>();
    /**
     * 邮件主题
     */
    private String subject;
    /**
     * 邮件内容
     */
    private String content;
}
