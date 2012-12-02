package ru.strela.ems.core.service.spring;


import ru.strela.ems.core.dao.NavigationDao;
import ru.strela.ems.core.model.Navigation;
import ru.strela.ems.core.service.NavigationService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


public class NavigationServiceImpl extends SystemObjectServiceImpl implements NavigationService {


    public NavigationDao getNavigationDao() {
        return (NavigationDao) getTypifiedObjectDao();
    }


    public void setNavigationDao(NavigationDao navigationDao) {
        setTypifiedObjectDao(navigationDao);
    }


    public Navigation getNavigation(int navigationId) {
        return (Navigation) getTypifiedObject(navigationId);
    }

    public List getUsedPages() {
          return getNavigationDao().getUsedPages();
    }

    public List getNavigations() {
        return getNavigationDao().getNavigations();
    }


    /* public List getNavigations(int parentId, int start, int quantity, String sortName, boolean desc) {
        return getNavigationDao().getNavigations(parentId, start,  quantity, sortName, desc);
    }*/

    public List getNavigations(int parentId) {
        return getNavigationDao().getNavigations(parentId, 0, 0, "", false);
//        return getNavigationDao().getNavigations(parentId);
    }

    public String getParentPath(int navigationId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


    public List findNavigations(String[] descriptions) {
        return getNavigationDao().findNavigations(descriptions);
    }


    public HashMap<Integer, String> getSystemNodeUrls(HashSet<Integer> systemNodeIdsForUrls) {
        return getNavigationDao().getSystemNodeUrls(systemNodeIdsForUrls);
    }


}
