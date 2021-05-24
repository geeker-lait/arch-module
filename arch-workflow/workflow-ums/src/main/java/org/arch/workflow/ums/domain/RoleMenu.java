package org.arch.workflow.ums.domain;

import org.arch.workflow.common.domain.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name = "pw_id_role_menu", catalog = "plumdo_identity")
@NamedQuery(name = "RoleMenu.findAll", query = "SELECT r FROM RoleMenu r")
public class RoleMenu extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private int menuId;
    private int roleId;

    public RoleMenu() {
    }

    @Column(name = "menu_id_", nullable = false)
    public int getMenuId() {
        return this.menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }


    @Column(name = "role_id_", nullable = false)
    public int getRoleId() {
        return this.roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }


}