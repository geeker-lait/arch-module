package org.arch.workflow.form.domain;

import org.arch.workflow.common.domain.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "form_layout", catalog = "workflow_form")
@NamedQuery(name = "FormLayout.findAll", query = "SELECT f FROM FormLayout f")
public class FormLayout extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private int editorSourceId;
    private String key;
    private String name;
    private int tableId;
    private String remark;

    public FormLayout() {
    }

    @Column(name = "editor_source_id_")
    public int getEditorSourceId() {
        return this.editorSourceId;
    }

    public void setEditorSourceId(int editorSourceId) {
        this.editorSourceId = editorSourceId;
    }

    @Column(name = "key_")
    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Column(name = "name_")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "table_id_")
    public int getTableId() {
        return this.tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    @Column(name = "remark_")
    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}