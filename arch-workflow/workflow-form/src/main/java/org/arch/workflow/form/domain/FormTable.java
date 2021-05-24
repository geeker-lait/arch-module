package org.arch.workflow.form.domain;

import org.arch.workflow.common.domain.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "form_table", catalog = "workflow_form")
@NamedQuery(name = "FormTable.findAll", query = "SELECT f FROM FormTable f")
public class FormTable extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private String name;
    private String key;
    private String category;
    private String remark;

    public FormTable() {
    }

    @Column(name = "name_")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "key_")
    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Column(name = "category_")
    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Column(name = "remark_")
    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}