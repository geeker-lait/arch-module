package org.arch.framework.client.jdbc;

import java.util.List;

/**
 * 分页查询结果类
 */
@SuppressWarnings("rawtypes")
public class PageResponse {
    protected List data;
    protected long total;

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

}
