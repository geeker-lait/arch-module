package org.arch.framework.automate.common.pojo;

public class Varchar2Field {
    private final String DATA_TYPE = "VARCHAR2";
    private final String COLUMN_NAME;
    private final String CHAR_LENGTH;
    private final String NULLABLE;
    private final String DATA_DEFAULT;
    private final String COMMENTS;

    public Varchar2Field(String COLUMN_NAME, String CHAR_LENGTH, String NULLABLE, String DATA_DEFAULT, String COMMENTS) {
        this.COLUMN_NAME = COLUMN_NAME;
        this.CHAR_LENGTH = CHAR_LENGTH;
        this.NULLABLE = NULLABLE;
        this.DATA_DEFAULT = DATA_DEFAULT == null ? null : "'" + DATA_DEFAULT + "'";
        this.COMMENTS = COMMENTS;
    }

    public String getDATA_TYPE() {
        return DATA_TYPE;
    }

    public String getCOLUMN_NAME() {
        return COLUMN_NAME;
    }

    public String getCHAR_LENGTH() {
        return CHAR_LENGTH;
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
