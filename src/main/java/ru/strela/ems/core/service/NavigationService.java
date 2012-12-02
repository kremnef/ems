package ru.strela.ems.core.service;


import ru.strela.ems.core.dao.NavigationDao;
import ru.strela.ems.core.model.Navigation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


public interface NavigationService extends SystemObjectService {

    
    NavigationDao getNavigationDao();
    void setNavigationDao(NavigationDao navigationDao);
    Navigation getNavigation(int navigationId);
    List getNavigations();
    List getUsedPages();
    List getNavigations(int parentId);
//    List getNavigations(int parentId, int start, int quantity, String sortName, boolean desc);
    List findNavigations(String[] descriptions);
    HashMap<Integer, String> getSystemNodeUrls(HashSet<Integer> systemNodeIdsForUrls);
}