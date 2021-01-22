package com.unichain.pay.channel.mfe88.demo.entity;

public enum ColorEnum {
    ERROR("#FF0000"), WARRING("#CD661D"), INFO("#000080"), DOING("#68228B"), SUCCESS("#006400") {
    };

    private String value;

    ColorEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
