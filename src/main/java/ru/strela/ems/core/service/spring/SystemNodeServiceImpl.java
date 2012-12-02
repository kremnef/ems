package ru.strela.ems.core.service.spring;


import ru.strela.ems.core.dao.SystemNodeDao;
import ru.strela.ems.core.model.Navigation;
import ru.strela.ems.core.model.SystemNode;
import ru.strela.ems.core.service.SystemNodeService;

import java.util.List;


public class SystemNodeServiceImpl extends SystemObjectServiceImpl implements SystemNodeService {

    protected SystemNodeDao systemNodeDao = null;
    public SystemNodeDao getSystemNodeDao() {
        return (SystemNodeDao) super.getTypifiedObjectDao();
    }


    public void setSystemNodeDao(SystemNodeDao systemNodeDao) {
        super.setTypifiedObjectDao(systemNodeDao);
    }


    public SystemNode getSystemNode(int id) {
        return (SystemNode) typifiedObjectDao.getTypifiedObject(id);
    }

    public String getSystemNodeName(int id) {
        return  systemNodeDao.getSystemNodeName(id);
    }




    public SystemNode getSystemNode(int id, boolean withFirstParent) {
        return (SystemNode) getSystemNodeDao().getTypifiedObject(id, withFirstParent);
    }


    public SystemNode getSystemNode(int id, boolean withFirstParent, boolean withTemplate) {
        return getSystemNodeDao().getSystemNode(id, withFirstParent, withTemplate);
        
    }


    public List getSystemNodes() {
        return getSystemNodeDao().getSystemNodes();
    }


    public List getSystemNodes(int parentId) {
        return getSystemNodeDao().getSystemNodes(parentId);
    }


    public Navigation getNavigationBySystemNodeId(int systemNodeId) {
        return getSystemNodeDao().getNavigationBySystemNodeId(systemNodeId);
    }


}
