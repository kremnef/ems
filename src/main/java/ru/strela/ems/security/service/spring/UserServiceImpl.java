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
import org.springframework.security.core.userdetails.User;
import ru.strela.ems.security.dao.UserDao;
import ru.strela.ems.security.model.Group;
import ru.strela.ems.security.model.Role;
import ru.strela.ems.security.service.UserService;

import java.util.List;


public class UserServiceImpl extends HibernateTemplate implements UserService {

    private UserDao userDao = null;
    /*User user;
    Class entityClass  = user.getClass();

    public int getChildrenCount(int id) {
        return userDao.getChildrenCount(entityClass, id);
    }

    public List<User> getChildren(int parentId) {
        return getChildren(parentId, 0, 0, "", false);
    }

    public List<User> getChildren(int parentId, int start, int quantity, String sortName, boolean desc) {
        return userDao.getObjects(entityClass, start, quantity, sortName, desc);
    }*/

    public UserDao getUserDao() {
        return userDao;
    }


    public void setUserDao(UserDao userDao) {
//        setTypifiedObjectDao(userDao);
    }


    public User getUser(String login) {
        return getUserDao().getUser(login);
    }


    public User getUserByName(String login) {
        return getUserDao().getUser(login);
    }

    public List<User> getEntities() {
        return getUserDao().getUsers();
    }
    public List<User> getUsers() {
        return getUserDao().getUsers();
    }

    public List<Group> getGroups() {
        return getUserDao().getGroups();
    }


    public List<Role> getRoles() {
        return getUserDao().getRoles();
    }

}
