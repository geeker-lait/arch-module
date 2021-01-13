package org.arch.framework.crud.utils;

/**
 * 分页参数类型
 */
public class PageInfo {

    /**
     * 当前分页页码
     */
    private int number;

    /**
     * 每页的数据大小
     */
    private int size;

    /**
     * 排序类型,默认按照主键id排序
     */
    private String sortType = "sortTypes";

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public PageInfo(int number, int size, String sortType) {
        super();
        this.number = number;
        this.size = size;
        this.sortType = sortType;
    }

}
