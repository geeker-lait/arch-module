package org.arch.framework.generater.core;

import java.io.Serializable;

public interface NameToField extends Serializable {
    //存储该数据在第几行，可以提示使用者哪行出现错误数据
    void setRow(int row);

    boolean isEmpty();
}