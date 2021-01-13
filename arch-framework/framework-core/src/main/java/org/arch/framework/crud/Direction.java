package org.arch.framework.crud;

import java.util.Locale;

public enum Direction {
    ASC,
    DESC;

    private Direction() {
    }

    public boolean isAscending() {
        return this.equals(ASC);
    }

    public boolean isDescending() {
        return this.equals(DESC);
    }

    public static Direction fromString(String value) {
        try {
            return valueOf(value.toUpperCase(Locale.US));
        } catch (Exception var2) {
            throw new IllegalArgumentException(String.format("Invalid value '%s' for orders given! Has to be either 'desc' or 'asc' (case insensitive).", value), var2);
        }
    }

}