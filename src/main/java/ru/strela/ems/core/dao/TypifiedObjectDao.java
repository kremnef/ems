package ru.strela.ems.core.dao;


import org.hibernate.criterion.Order;
import ru.strela.ems.core.model.Filter;
import ru.strela.ems.core.model.ObjectType;
import ru.strela.ems.core.model.TypifiedObject;

import java.util.Collection;
import java.util.List;


/**
 * User: hobal
 * Date: 03.08.2010
 * Time: 16:05:47
 */
public interface TypifiedObjectDao {


    TypifiedObject getTypifiedObject(int id);
    List<TypifiedObject> getTypifiedObjects(Collection<Integer> ids);
    int getChildrenCount(int id);
    int getObjectsCount() ;

    TypifiedObject getTypifiedObject(Class entityClass, int id);
    TypifiedObject getParent(int id);

    List<TypifiedObject> getObjects();
    List<TypifiedObject> getObjects(Order order);
    List<TypifiedObject> getObjects(int start, int itemsOnPage, String sortField, String sortDirection);
    List<TypifiedObject> getChildren(Integer parentId, int start, int itemsOnPage, String sortField, boolean sortDirection, Filter filter);

    Class getEntityClass();
    ObjectType getObjectType(String className);
    ObjectType getObjectTypeById(int objectTypeId);
//    List getObjectTypes(boolean onlyEmbedded);
    TypifiedObject save(TypifiedObject typifiedObject);
    void delete(TypifiedObject typifiedObject);
    void indexSiteMap(TypifiedObject typifiedObject);

}
