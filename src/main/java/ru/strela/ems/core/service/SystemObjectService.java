package ru.strela.ems.core.service;


import ru.strela.ems.core.model.TypifiedObject;


/**
 * User: hobal
 * Date: 04.08.2010
 * Time: 13:44:40
 */
public interface SystemObjectService extends TypifiedObjectService {


    TypifiedObject getTypifiedObject(int id, boolean withFirstParent);
    int getTypifiedObjectChildrenCount(int id);
    TypifiedObject getTypifiedObjectBySystemName(String systemName);
    int getIdBySystemName(String systemName);
    String getParentPath(int objectId, boolean includeTypifiedObject);

}
