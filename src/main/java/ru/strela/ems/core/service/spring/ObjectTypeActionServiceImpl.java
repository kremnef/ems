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
package ru.strela.ems.core.service.spring;


import ru.strela.ems.core.dao.ObjectTypeActionDao;
import ru.strela.ems.core.model.ObjectTypeAction;
import ru.strela.ems.core.service.ObjectTypeActionService;

import java.util.List;


public class ObjectTypeActionServiceImpl implements ObjectTypeActionService {


    private ObjectTypeActionDao objectTypeActionDao = null;
    ObjectTypeAction objectTypeAction = new ObjectTypeAction();
    Class entityClass = objectTypeAction.getClass();

    public int getChildrenCount(int id) {
        return objectTypeActionDao.getChildrenCount(entityClass, id);
    }

    public List<ObjectTypeAction> getChildren(int parentId) {
        return getChildren(parentId, 0, 0, "", false);
    }

    public List<ObjectTypeAction> getChildren(int parentId, int start, int quantity, String sortName, boolean desc) {
        return objectTypeActionDao.getObjects(entityClass, start, quantity, sortName, desc);
    }

    public List<ObjectTypeAction> getEntities() {
        return objectTypeActionDao.getObjectTypeActions();
    }

    public ObjectTypeActionDao getObjectTypeActionDao() {
        return objectTypeActionDao;
    }


    public void setObjectTypeActionDao(ObjectTypeActionDao objectTypeActionDao) {
        this.objectTypeActionDao = objectTypeActionDao;
    }


    public ObjectTypeAction getObjectTypeAction(Integer objectTypeActionId) {
        return objectTypeActionDao.getObjectTypeAction(objectTypeActionId);
    }


    public ObjectTypeAction saveObjectTypeAction(ObjectTypeAction objectTypeAction) {
        return objectTypeActionDao.saveObjectTypeAction(objectTypeAction);
    }


    public List getObjectTypeActions() {
        return objectTypeActionDao.getObjectTypeActions();
    }


    public void deleteObjectTypeAction(ObjectTypeAction objectTypeAction) {
        objectTypeActionDao.deleteObjectTypeAction(objectTypeAction);
    }


    public List findObjectTypeActions(String[] descriptions) {
        return objectTypeActionDao.findObjectTypeActions(descriptions);
    }

}
