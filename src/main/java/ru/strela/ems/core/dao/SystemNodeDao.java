package ru.strela.ems.core.dao;


import ru.strela.ems.core.model.Navigation;
import ru.strela.ems.core.model.SystemNode;

import java.util.List;


public interface SystemNodeDao extends SystemObjectDao {


    SystemNode getSystemNode(int systemNodeId);
    String getSystemNodeName(int systemNodeId);
    SystemNode getSystemNode(int id, boolean withFirstParent, boolean withTemplate);
    SystemNode getSystemNodeByURI(String nodeURI);
    List getSystemNodes();
    List getSystemNodes(int parentId);
    List findSystemNodes(String[] descriptions);
    Navigation getNavigationBySystemNodeId(int systemNodeId);

}
