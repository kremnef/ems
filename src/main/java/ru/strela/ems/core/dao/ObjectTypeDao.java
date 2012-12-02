package ru.strela.ems.core.dao;


import ru.strela.ems.core.model.ObjectType;

import java.util.Collection;
import java.util.List;


/**
 * User: hobal
 * Date: 04.05.2010
 * Time: 18:19:07
 */
public interface ObjectTypeDao {


    ObjectType getObjectType(String className);
    List getObjectTypes(boolean onlyEmbedded);
    int getRootObjectsForTypeCount(ObjectType objectType);
    List getRootObjectsForType(ObjectType objectType);
    List getObjectTypeActions(int objectTypeId);
    List getObjectTypeActions(Collection objectTypeActionIds);
    ObjectType getObjectType (int objectTypeId);
}
