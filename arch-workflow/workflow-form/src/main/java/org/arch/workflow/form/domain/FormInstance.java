package org.arch.workflow.form.domain;

import org.arch.workflow.common.domain.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "form_instance", catalog = "workflow_form")
@NamedQuery(name = "FormInstance.findAll", query = "SELECT f FROM FormInstance f")
public class FormInstance extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private int formDefinitionId;
    private String formDefinitionName;
    private String relationTable;
    private int tableRelationId;

    public FormInstance() {
    }

    @Column(name = "form_definition_id_")
    public int getFormDefinitionId() {
        return this.formDefinitionId;
    }

    public void setFormDefinitionId(int formDefinitionId) {
        this.formDefinitionId = formDefinitionId;
    }

    @Column(name = "relation_table_")
    public String getRelationTable() {
        return this.relationTable;
    }

    public void setRelationTable(String relationTable) {
        this.relationTable = relationTable;
    }

    @Column(name = "table_relation_id_")
    public int getTableRelationId() {
        return this.tableRelationId;
    }

    public void setTableRelationId(int tableRelationId) {
        this.tableRelationId = tableRelationId;
    }

    @Column(name = "form_definition_name_")
    public String getFormDefinitionName() {
        return formDefinitionName;
    }

    public void setFormDefinitionName(String formDefinitionName) {
        this.formDefinitionName = formDefinitionName;
    }
}