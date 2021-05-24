package org.arch.alarm.api.enums;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 4/28/2021 9:42 AM
 */
public enum MessageTopic {
    OFS_ALARM_CANAL_MONITOR("ofs-alarm-canal-monitor","履约监控预警Binlog数据监控", "monitor","lait"),
    ;
    private String topic;
    private String name;
    private String method;
    private String owner;


    MessageTopic(String topic, String name,String method,String owner) {
        this.topic = topic;
        this.name = name;
        this.method = method;
        this.owner = owner;
    }

    public String getTopic() {
        return topic;
    }

    public String getName() {
        return name;
    }

    public String getMethod() {
        return method;
    }

    public String getOwner() {
        return owner;
    }
}
