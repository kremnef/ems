package ru.strela.ems.core.dao;


import ru.strela.ems.core.model.Content;

import java.util.*;


public interface ContentDao extends SystemObjectDao {


    Content getContent(int contentId);
    List getContents(String owner);
    List getContents();
//    List getContents(int itemsOnPage, String sortField, String sortDirection, Integer tagId, int languageId, Session session);
//    List getContentsByParent(int parentId, int itemsOnPage, String sortField, String sortDirection, Integer tagId, String languageCode, Session session);
//    List getContentsByParent(int parentId, int itemsOnPage, String sortField, String sortDirection, Integer tagId, int languageId, Session session);
    List getContentsByDocumentType(Integer documentTypeId);
    List findContents(String[] descriptions);
//    void showProducts(Map model, Object obj, String src);
    HashMap<Integer, Integer> getDocumentMaxVersions(Set<Integer> contentIds, int languageId);
    HashMap<Integer, ArrayList<String[]>> getPathsForContents(HashSet<Integer> contentIds, int languageId);
}
