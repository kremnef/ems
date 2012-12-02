package ru.strela.ems.core.service.spring;


import ru.strela.ems.core.dao.SystemObjectDao;
import ru.strela.ems.core.model.Filter;
import ru.strela.ems.core.model.TypifiedObject;
import ru.strela.ems.core.service.SystemObjectService;

import java.util.List;


/**
 * User: hobal
 * Date: 04.08.2010
 * Time: 14:11:12
 */
public class SystemObjectServiceImpl extends TypifiedObjectServiceImpl implements SystemObjectService {



    private SystemObjectDao getSystemObjectDao() {
        return (SystemObjectDao) typifiedObjectDao;
    }


    public TypifiedObject getTypifiedObject(int id, boolean withFirstParent) {
        return getSystemObjectDao().getTypifiedObject(id, withFirstParent);
    }


    public TypifiedObject getTypifiedObjectBySystemName(String systemName) {
        return getSystemObjectDao().getTypifiedObjectBySystemName(systemName);
    }


    public int getIdBySystemName(String systemName) {
        return getSystemObjectDao().getIdBySystemName(systemName);
    }


    public List<TypifiedObject> getTypifiedObjectParents(int id, boolean includeTypifiedObject) {
        return getSystemObjectDao().getTypifiedObjectParents(id, includeTypifiedObject);
    }


    public List<TypifiedObject> getTypifiedObjectParents(Class entityClass, int id, boolean includeTypifiedObject) {
        return getSystemObjectDao().getTypifiedObjectParents(entityClass, id, includeTypifiedObject);
    }


    public List<TypifiedObject> getChildren(int parentId) {
        return getChildren(parentId, 0, 0, "", false);
    }


    public List<TypifiedObject> getChildren(int parentId, int start, int quantity, String sortName, boolean desc, Filter filter) {
        return getSystemObjectDao().getChildren(parentId, start, quantity, sortName, desc, filter);
    }


    public List<TypifiedObject> getChildren(int parentId, int start, int quantity, String sortName, boolean desc) {
        return getSystemObjectDao().getChildren(parentId, start, quantity, sortName, desc);
    }


    public int getTypifiedObjectChildrenCount(int id) {
        return getSystemObjectDao().getChildrenCount(id);
    }


    public String getFreeSystemName() {
        return getSystemObjectDao().getFreeSystemName();
    }


    public String getParentPath(int objectId, boolean includeTypifiedObject) {
        return getSystemObjectDao().getParentPath(objectId, includeTypifiedObject);
    }

}
