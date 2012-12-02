package ru.strela.ems.core.dao;


import ru.strela.ems.core.model.EmsObject;

import java.util.List;


/**
 *
 * @author hobal
 */
public interface EmsObjectDao {


    EmsObject getEmsObject(int id, boolean withFirstParent);
    int getEmsObjectChildrenCount(int id);
    List<EmsObject> getChildren(int parentId);
    EmsObject getEmsObjectBySystemName(String systemName);
    int getIdBySystemName(String systemName);
    String getFreeSystemName();

}
