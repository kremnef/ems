package ru.strela.ems.core.dao;


import ru.strela.ems.core.model.Document;

import java.util.List;


public interface DocumentDao {


    Document getDocument(int documentId);
    List getDocuments(String owner);
    List getDocumentsByContentId(int contentId);

    Document getDocumentByNaturalId(int contentId, String languageCode);



//    HashMap<Integer, TreeMap<Integer, String>> getDocumentVersions(int contentId);
//    List getAllVersions(int contentId);

//    Document getLastVersionDocument( int contentId, int languageId);

//    EmsSelectionList getVersionSelectionList(int contentId, int languageId);
    Document saveDocument(Document document);

//    int getLastVersion(int contentId, int languageId);

    void deleteDocument(Document document);
    List findDocuments(String[] descriptions);
}