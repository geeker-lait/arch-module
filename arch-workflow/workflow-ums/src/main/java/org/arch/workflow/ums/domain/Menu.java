package org.arch.workflow.ums.domain;

import org.arch.workflow.common.domain.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name = "pw_id_menu", catalog = "plumdo_identity")
@NamedQuery(name = "Menu.findAll", query = "SELECT m FROM Menu m")
public class Menu extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private String icon;
    private String route;
    private String name;
    private int order;
    private int parentId;
    private String remark;
    private byte type;
    private byte status;

    public Menu() {
    }


    @Column(name = "icon_", nullable = false, length = 255)
    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Column(name = "route_", length = 255)
    public String getRoute() {
        return this.route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    @Column(name = "name_", nullable = false, length = 64)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "order_", nullable = false)
    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Column(name = "parent_id_", nullable = false)
    public int getParentId() {
        return this.parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    @Column(name = "remark_", length = 500)
    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "type_", nullable = false)
    public byte getType() {
        return this.type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    @Column(name = "status_", nullable = false)
    public byte getStatus() {
        return this.status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }
}