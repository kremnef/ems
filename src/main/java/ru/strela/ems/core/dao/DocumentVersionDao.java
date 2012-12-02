package ru.strela.ems.core.dao;


import ru.strela.ems.core.model.DocumentVersion;

import java.util.List;


public interface DocumentVersionDao {

    DocumentVersion getDocumentVersion(int documentVersionId);
    List getDocumentVersions(String owner);
    List getDocumentVersionsByContentId(int contentId);
    List getAllVersions(int contentId);
    DocumentVersion saveDocumentVersion(DocumentVersion documentVersion);
    int getLastVersion(int contentId, String languageCode);
    void deleteDocumentVersion(DocumentVersion documentVersion);
}