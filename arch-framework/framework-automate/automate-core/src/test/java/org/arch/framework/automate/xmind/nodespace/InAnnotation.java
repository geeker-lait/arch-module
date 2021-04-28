package org.arch.framework.automate.xmind.nodespace;

/**
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.4.28 22:46
 */
public enum InAnnotation {

    NOTNULL("Notnull"),
    EMAIL("Email"),
    NOT_BLANK("NotBlank"),
    NOT_EMPTY("NotEmpty"),
    DIGITS("Digits"),
    MAX("Max"),
    MIN("Min"),
    NULL("Null"),
    PATTERN("Pattern"),
    SIZE("Size"),
    DECIMAL_MAX("DecimalMax"),
    DECIMAL_MIN("DecimalMin"),
    POSITIVE("Positive"),
    POSITIVE_OR_ZERO("PositiveOrZero"),
    PAST("Past"),
    PAST_OR_PRESENT("PastOrPresent"),
    NEGATIVE("Negative"),
    NEGATIVE_OR_ZERO("NegativeOrZero"),
    FUTURE("Future"),
    FUTURE_OR_PRESENT("FutureOrPresent");

    private final String annotName;

    InAnnotation(String annotName) {
        this.annotName = annotName;
    }

    public String getAnnotName() {
        return annotName;
    }
}
