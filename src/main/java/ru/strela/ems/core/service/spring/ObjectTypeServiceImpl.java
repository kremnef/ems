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


import ru.strela.ems.core.dao.ObjectTypeDao;
import ru.strela.ems.core.dao.hibernate.ObjectTypeDaoImpl;
import ru.strela.ems.core.model.ObjectType;
import ru.strela.ems.core.service.ObjectTypeService;

import java.util.List;


public class ObjectTypeServiceImpl implements ObjectTypeService {


    private ObjectTypeDao objectTypeDao = null;
    ObjectType objectType = new ObjectType();
    Class entityClass = objectType.getClass();
//    private ObjectTypeDaoImpl objectTypeDao;

//    List<ObjectType> getChildren(int parentId);





   /* public int getChildrenCount(int id) {
        return objectTypeDao.getChildrenCount(entityClass, id);
    }*/

    /*public List<ObjectType> getChildren(int parentId) {
        return getChildren(parentId, 0, 0, "", false);
    }*/

    /*public List<ObjectType> getChildren(int parentId, int start, int quantity, String sortName, boolean desc) {
        return objectTypeDao.getObjects(entityClass, start, quantity, sortName, desc);
    }*/

    /*public List<ObjectType> getEntities() {
        return objectTypeDao.getObjectTypes();
    }*/

  /*  public ObjectTypeDao getObjectTypeDao() {
        return objectTypeDao;
    }
*/

    public void setObjectTypeDao(ObjectTypeDao objectTypeDao) {
        this.objectTypeDao = objectTypeDao;
    }


    public ObjectType getObjectType(Integer objectTypeId) {
        return objectTypeDao.getObjectType(objectTypeId);
    }


    /*public ObjectType saveObjectType(ObjectType objectType) {
        return objectTypeDao.saveObjectType(objectType);
    }
*/

    public List getObjectTypes() {
        return objectTypeDao.getObjectTypes(true);
    }


    public void setObjectTypeDao(ObjectTypeDaoImpl objectTypeDao) {
        this.objectTypeDao = objectTypeDao;
    }

    public ObjectTypeDao getObjectTypeDao() {
        return objectTypeDao;
    }
}
