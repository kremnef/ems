package ru.strela.ems.core.dao;


import ru.strela.ems.core.model.Filter;
import ru.strela.ems.core.model.SystemObject;
import ru.strela.ems.core.model.TypifiedObject;

import java.util.List;


/**
 * User: hobal
 * Date: 03.08.2010
 * Time: 17:18:33
 */
public interface SystemObjectDao extends TypifiedObjectDao {


    TypifiedObject getTypifiedObject(int id, boolean withFirstParent);
    List<TypifiedObject> getChildren(int parentId);
    List<TypifiedObject> getChildren(int parentId, int start, int quantity, String sortName, boolean desc);
    List<TypifiedObject> getChildren(int parentId, int start, int quantity, String sortName, boolean desc, Filter filter);
    List<TypifiedObject> getChildren(SystemObject parent);
    TypifiedObject getTypifiedObjectBySystemName(String systemName);
    int getIdBySystemName(String systemName);
    int getIdBySystemName(String systemName, int parentId, String entity);
    String getFreeSystemName();
    List<TypifiedObject> getTypifiedObjectParents(int id, boolean includeTypifiedObject);
    List<TypifiedObject> getTypifiedObjectParents(Class entityClass, int id, boolean includeTypifiedObject);
    String getParentPath(int objectId, boolean includeTypifiedObject);
//    void reindexSiteMap(SystemObject systemObject, boolean startOwnTransaction);
//    String getParentPath(int objectId, boolean objectType);

}
