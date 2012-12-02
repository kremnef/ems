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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.provisioning.GroupManager;
import ru.strela.ems.security.dao.GroupDao;
import ru.strela.ems.security.model.Group;
import ru.strela.ems.security.service.GroupService;

import java.util.List;

//import org.springframework.security.core.groupdetails.Group;


public class GroupServiceImpl extends HibernateTemplate implements GroupService, GroupManager {

   private GroupDao groupDao;
   Group group = new Group();
    Class entityClass  = group.getClass();

    public int getChildrenCount(int id) {
        return groupDao.getChildrenCount(entityClass, id);
    }

    public List<Group> getChildren(int parentId) {
        return getChildren(parentId, 0, 0, "", false);
    }

    public List<Group> getChildren(int parentId, int start, int quantity, String sortName, boolean desc) {
        return groupDao.getObjects(entityClass, start, quantity, sortName, desc);
    } 
    

    public GroupDao getGroupDao() {
        return groupDao;
    }


    public void setGroupDao(GroupDao groupDao) {
       this.groupDao = groupDao;
//        setTypifiedObjectDao(groupDao);
    }




    public Group getGroup(String login) {
        return groupDao.getGroup(login);
    }


     public Group getGroupByName(String login) {
        return groupDao.getGroup(login);
    }


    public List<Group> getEntities() {
        return groupDao.getAllGroups();

    }public List<Group> getAllGroups() {
        return groupDao.getAllGroups();
    }


    public List getRoles() {
        return groupDao.getRoles();
    }

    /**
     * Returns the names of all groups that this group manager controls.
     */
    public List<String> findAllGroups() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Locates the users who are members of a group
     *
     * @param groupName the group whose members are required
     * @return the usernames of the group members
     */
    public List<String> findUsersInGroup(String groupName) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Creates a new group with the specified list of authorities.
     *
     * @param groupName   the name for the new group
     * @param authorities the authorities which are to be allocated to this group.
     */
    public void createGroup(String groupName, List<GrantedAuthority> authorities) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Removes a group, including all members and authorities.
     *
     * @param groupName the group to remove.
     */
    public void deleteGroup(String groupName) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Changes the name of a group without altering the assigned authorities or members.
     */
    public void renameGroup(String oldName, String newName) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Makes a user a member of a particular group.
     *
     * @param username the user to be given membership.
     * @param group    the name of the group to which the user will be added.
     */
    public void addUserToGroup(String username, String group) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Deletes a user's membership of a group.
     *
     * @param username  the user
     * @param groupName the group to remove them from
     */
    public void removeUserFromGroup(String username, String groupName) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Obtains the list of authorities which are assigned to a group.
     */
    public List<GrantedAuthority> findGroupAuthorities(String groupName) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Assigns a new authority to a group.
     */
    public void addGroupAuthority(String groupName, GrantedAuthority authority) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Deletes an authority from those assigned to a group
     */
    public void removeGroupAuthority(String groupName, GrantedAuthority authority) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
