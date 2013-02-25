package ru.strela.ems.core.service;


import ru.strela.ems.core.dao.TypifiedObjectDao;
import ru.strela.ems.core.model.Filter;
import ru.strela.ems.core.model.ObjectType;
import ru.strela.ems.core.model.TypifiedObject;

import java.util.List;


/**
 * User: hobal
 * Date: 04.08.2010
 * Time: 13:42:30
 */
public interface TypifiedObjectService {


    TypifiedObjectDao getTypifiedObjectDao();
    void setTypifiedObjectDao(TypifiedObjectDao typifiedObjectDao);
    TypifiedObject getTypifiedObject(int id);
    TypifiedObject getTypifiedObject(Class entityClass, int id);
    Class getEntityClass();
    TypifiedObject getParent(int id);
    int getChildrenCount(int id);


//    List<TypifiedObject> getChildren(int start, int quantity, String sortName, boolean desc);
//    List<TypifiedObject> getChildren(Integer parentId, int start, int quantity, String sortName, boolean desc, Filter filter);

    List<TypifiedObject> getObjects(int start, int itemsOnPage, String sortField, String sortDirection);
    List<TypifiedObject> getChildren(Integer parentId, int start, int itemsOnPage, String sortField, boolean sortDirection, Filter filter);

    List<TypifiedObject> getTypifiedObjectParents(int id, boolean includeTypifiedObject);
    List<TypifiedObject> getTypifiedObjectParents(Class entityClass, int id, boolean includeTypifiedObject);

    String getEntityClassName();
    ObjectType getObjectType(String className);
    TypifiedObject save(TypifiedObject typifiedObject);
    void deleteObject(TypifiedObject emsObject);
    String getContextRealPath();
    String getFreeSystemName();

}
