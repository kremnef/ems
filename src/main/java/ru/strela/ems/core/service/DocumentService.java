package ru.strela.ems.core.service;


import ru.strela.ems.core.dao.DocumentDao;
import ru.strela.ems.core.model.Document;

import java.util.List;


public interface DocumentService {


    DocumentDao getDocumentDao();
    void setDocumentDao(DocumentDao documentDao);
//    int getLastVersion(int contentId, int languageId);
    Document getDocument(int documentId);
    List getDocuments(String owner);
    List getDocumentsByContentId(int contentId);

    Document getDocumentByNaturalId(int contentId, String languageCode);


//    HashMap<Integer, TreeMap<Integer, String>> getDocumentVersions(int contentId);

//    List getAllVersions(int contentId);
//    EmsSelectionList getVersionSelectionList(int contentId, int languageId);


//    Document getLastVersionDocument( int contentId, int languageId);
//    Document getLastVersion( int contentId, int languageId);
    Document saveDocument(Document document);
    void deleteDocument(Document document);
    List findDocuments(String[] descriptions);

}
