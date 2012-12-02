package ru.strela.ems.core.service;


import ru.strela.ems.core.dao.SystemNodeDao;
import ru.strela.ems.core.model.Navigation;
import ru.strela.ems.core.model.SystemNode;

import java.util.List;


public interface SystemNodeService extends SystemObjectService {


    SystemNodeDao getSystemNodeDao();
    void setSystemNodeDao(SystemNodeDao systemNodeDao);
    String getSystemNodeName(int id);
    SystemNode getSystemNode(int id);
    SystemNode getSystemNode(int id, boolean withFirstParent);
    SystemNode getSystemNode(int id, boolean withFirstParent, boolean withTemplate);
    List getSystemNodes();
    List getSystemNodes(int parentId);
    Navigation getNavigationBySystemNodeId(int systemNodeId);
}
