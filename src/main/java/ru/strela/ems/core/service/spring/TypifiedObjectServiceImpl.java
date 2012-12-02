package ru.strela.ems.core.service.spring;


import ru.strela.ems.core.dao.TypifiedObjectDao;
import ru.strela.ems.core.model.Filter;
import ru.strela.ems.core.model.ObjectType;
import ru.strela.ems.core.model.TypifiedObject;
import ru.strela.ems.core.service.TypifiedObjectService;
import ru.strela.ems.tools.ServerTools;

import java.util.ArrayList;
import java.util.List;


/**
 * User: hobal
 * Date: 04.08.2010
 * Time: 14:08:43
 */
public class TypifiedObjectServiceImpl implements TypifiedObjectService {


    protected TypifiedObjectDao typifiedObjectDao = null;



    public TypifiedObjectDao getTypifiedObjectDao() {
        return typifiedObjectDao;
    }


    public void setTypifiedObjectDao(TypifiedObjectDao typifiedObjectDao) {
        this.typifiedObjectDao = typifiedObjectDao;
    }

    public TypifiedObject getParent(int id) {
        return typifiedObjectDao.getParent(id);
    }

    public TypifiedObject getTypifiedObject(int id) {
        return typifiedObjectDao.getTypifiedObject(id);
    }


    public TypifiedObject getTypifiedObject(Class entityClass, int id) {
        return typifiedObjectDao.getTypifiedObject(entityClass, id);
    }


    public Class getEntityClass() {
        return typifiedObjectDao.getEntityClass();
    }


    public int getChildrenCount(int id) {
        return typifiedObjectDao.getChildrenCount(id);
    }


    public List<TypifiedObject> getChildren(int parentId) {
        return getChildren(parentId, 0, 0, "", false);
    }


    public List<TypifiedObject> getChildren(int parentId, int start, int quantity, String sortName, boolean desc) {
        return typifiedObjectDao.getObjects(start, quantity, sortName, desc);
//        return typifiedObjectDao.getObjects(parentId, start, quantity, sortName, desc);
    }


    public List<TypifiedObject> getChildren(int parentId, int start, int quantity, String sortName, boolean desc, Filter filter) {
        return typifiedObjectDao.getObjects(parentId, start, quantity, sortName, desc, filter);
    }


    public List<TypifiedObject> getTypifiedObjectParents(int id, boolean includeTypifiedObject) {
        ArrayList<TypifiedObject> parents = new ArrayList<TypifiedObject>();
        if (includeTypifiedObject) {
            parents.add(getTypifiedObject(id));
        }
        return parents;
    }


    public List<TypifiedObject> getTypifiedObjectParents(Class entityClass, int id, boolean includeTypifiedObject) {
        ArrayList<TypifiedObject> parents = new ArrayList<TypifiedObject>();
        if (includeTypifiedObject) {
            parents.add(getTypifiedObject(entityClass, id));
        }
        return parents;
    }


    public String getEntityClassName() {
        return getEntityClass().getName();
    }


    public ObjectType getObjectType(String className) {
        return typifiedObjectDao.getObjectType(className);
    }



    public TypifiedObject save(TypifiedObject typifiedObject) {
        return typifiedObjectDao.save(typifiedObject);
    }


    public void deleteObject(TypifiedObject typifiedObject) {
        typifiedObjectDao.delete(typifiedObject);
    }


    public String getContextRealPath() {
        return ServerTools.getContextRealPath();
    }



    public String getFreeSystemName() {
        return "";
    }

}
