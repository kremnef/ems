package ru.strela.ems.core.service;


import ru.strela.ems.core.dao.DocumentVersionDao;
import ru.strela.ems.core.model.DocumentVersion;

import java.util.List;


public interface DocumentVersionService {


    DocumentVersionDao getDocumentVersionDao();
    void setDocumentVersionDao(DocumentVersionDao documentDao);
    int getLastVersion(int contentId, String languageCode);
    DocumentVersion getDocumentVersion(int documentId);
    List getDocumentVersions(String owner);
    List getDocumentVersionsByContentId(int contentId);
    List getAllVersions(int contentId);
//    DocumentVersion getLastDocumentVersion(int contentId, int languageId);
    DocumentVersion saveDocumentVersion(DocumentVersion document);
    void deleteDocumentVersion(DocumentVersion document);
//    void addTags(DocumentVersion document, String newTags);
    List findDocumentVersions(String[] descriptions);

}
