package org.arch.project.rule;

/**
 * @author lait.zhang@gmail.com
 * @description: TODO
 * @weixin PN15855012581
 * @date 6/18/2021 3:30 PM
 */
public class UserInfo {
    long id;
    long tag;
    String name;

    public UserInfo(long aId, String aName, long aUserTag) {
        this.id = aId;
        this.tag = aUserTag;
        this.name = aName;
    }

    public String getName() {
        return name;
    }

    public long getUserId() {
        return id;
    }

    public long getUserTag() {
        return tag;
    }
}
