package ru.strela.ems.security.model;

import org.springframework.security.access.vote.RoleVoter;

/**
 * Created by IntelliJ IDEA.
 * User: andrejkremnev
 * Date: 28.11.11
 * Time: 14:34
 * To change this template use File | Settings | File Templates.
 */
public class Role   {
//RoleVoter

    int roleId;
    String roleName;
    String authority;
    RoleVoter roleVoter;


    public Role(String roleName) {
        this.roleName = roleName;

    }

    public Role() {
    }


    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
