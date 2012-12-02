package ru.strela.ems.core.dao;


import ru.strela.ems.core.model.Navigation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


public interface NavigationDao extends SystemObjectDao {


    Navigation getNavigation(int navigationId);
    Navigation getNavigation(int navigationId, boolean withFirstParent);
    List getNavigations();
    List getUsedPages();
    List getNavigations(int parentId, int start, int quantity, String sortName, boolean desc);
    List findNavigations(String[] descriptions);
    String getConcatURL(Integer id);
//    List getNavigations(int parentId);
    HashMap<Integer,String> getSystemNodeUrls(HashSet<Integer> systemNodeIdsForUrls);
}