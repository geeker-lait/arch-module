package org.arch.workflow.form.domain;

import org.arch.workflow.common.domain.BaseEntity;

import javax.persistence.*;


@Entity
@Table(name = "form_bytearray", catalog = "workflow_form")
@NamedQuery(name = "ByteArray.findAll", query = "SELECT b FROM ByteArray b")
public class ByteArray extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private byte[] contentByte;
    private String name;

    public ByteArray() {
    }


    @Lob
    @Column(name = "content_byte_")
    public byte[] getContentByte() {
        return this.contentByte;
    }

    public void setContentByte(byte[] contentByte) {
        this.contentByte = contentByte;
    }

    @Column(name = "name_")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}