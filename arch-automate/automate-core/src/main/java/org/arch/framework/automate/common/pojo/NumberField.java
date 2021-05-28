package org.arch.framework.automate.common.pojo;

public class NumberField {
    private final String DATA_TYPE = "NUMBER";
    private final String COLUMN_NAME;
    private final String DATA_PRECISION;
    private final String DATA_SCALE;
    private final String NULLABLE;
    private final String DATA_DEFAULT;
    private final String COMMENTS;

    public NumberField(String COLUMN_NAME, String DATA_PRECISION, String DATA_SCALE, String NULLABLE, String DATA_DEFAULT, String COMMENTS) {
        this.COLUMN_NAME = COLUMN_NAME;
        this.DATA_PRECISION = DATA_PRECISION;
        this.DATA_SCALE = DATA_SCALE;
        this.NULLABLE = NULLABLE;
        this.DATA_DEFAULT = DATA_DEFAULT;
        this.COMMENTS = COMMENTS;
    }

    public String getDATA_TYPE() {
        return DATA_TYPE;
    }

    public String getCOLUMN_NAME() {
        return COLUMN_NAME;
    }

    public String getDATA_PRECISION() {
        return DATA_PRECISION;
    }

    public String getDATA_SCALE() {
        return DATA_SCALE;
    }

    public String getNULLABLE() {
        return NULLABLE;
    }

    public String getDATA_DEFAULT() {
        return DATA_DEFAULT;
    }

    public String getCOMMENTS() {
        return COMMENTS;
    }
}
