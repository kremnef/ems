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
package ru.strela.ems.security.service;


import ru.strela.ems.security.dao.GroupDao;
import ru.strela.ems.security.model.Group;

import java.util.List;


public interface GroupService {


    GroupDao getGroupDao();
    void setGroupDao(GroupDao userDao);
//    Customer getGroup(Integer userId);
    Group getGroupByName(String login);
    Group getGroup(String login);
    List<Group> getEntities();
    List<Group> getAllGroups();
    List getRoles();
    int getChildrenCount(int id);
    List<Group> getChildren(int parentId);
    List<Group> getChildren(int parentId, int start, int quantity, String sortName, boolean desc);
    
}
