/*
 *  Eberom: a CRM web application 
 *  Copyright (C) 2006  Luk Morbee
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package ru.strela.ems.security.service.spring;


import org.springframework.orm.hibernate3.HibernateTemplate;
import ru.strela.ems.security.dao.RoleDao;
import ru.strela.ems.security.model.Role;
import ru.strela.ems.security.service.RoleService;

import java.util.List;

//import org.springframework.security.core.roledetails.Role;


public class RoleServiceImpl extends HibernateTemplate implements RoleService {

    private RoleDao roleDao;

    Role role = new Role();
    Class entityClass = role.getClass();

    public int getChildrenCount(int id) {
        return roleDao.getChildrenCount(entityClass, id);
    }

    public List<Role> getChildren(int parentId) {
        return getChildren(parentId, 0, 0, "", false);
    }

    public List<Role> getChildren(int parentId, int start, int quantity, String sortName, boolean desc) {
        return roleDao.getObjects(entityClass, start, quantity, sortName, desc);
    }

    public List<Role> getEntities() {
        return roleDao.getAllRoles();
    }

    public RoleDao getRoleDao() {
        return roleDao;
    }


    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
//        setTypifiedObjectDao(roleDao);
    }


    public Role getRole(String login) {
        return roleDao.getRole(login);
    }


    public Role getRoleByName(String login) {
        return roleDao.getRole(login);
    }


    public List<Role> getAllRoles() {
        return roleDao.getAllRoles();
    }


}
