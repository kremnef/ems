package ru.strela.ems.core.service.spring;


import ru.strela.ems.core.dao.DocumentVersionDao;
import ru.strela.ems.core.model.DocumentVersion;
import ru.strela.ems.core.service.DocumentVersionService;

import java.util.List;


public class DocumentVersionServiceImpl implements DocumentVersionService {


    private DocumentVersionDao documentDao = null;


    public DocumentVersionDao getDocumentVersionDao() {
        return documentDao;
    }


    public void setDocumentVersionDao(DocumentVersionDao documentDao) {
        this.documentDao = documentDao;
    }


    public DocumentVersion getDocumentVersion(int documentId) {
        return documentDao.getDocumentVersion(documentId);
    }


    public List getAllVersions(int contentId){
        return documentDao.getAllVersions(contentId);
    }



    /*public DocumentVersion getLastDocumentVersion(int contentId, int languageId) {
         return documentDao.getLastDocumentVersion(contentId, languageId);
    }*/

    public DocumentVersion saveDocumentVersion(DocumentVersion DocumentVersion) {
        return documentDao.saveDocumentVersion(DocumentVersion);
    }


    public List getDocumentVersions(String owner) {
        return documentDao.getDocumentVersions(owner);
    }


    public List getDocumentVersionsByContentId(int contentId) {
        return documentDao.getDocumentVersionsByContentId(contentId);
    }


    public void deleteDocumentVersion(DocumentVersion document) {
        documentDao.deleteDocumentVersion(document);
    }

    public List findDocumentVersions(String[] descriptions) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }




    public int getLastVersion(int contentId, String languageCode) {
        return documentDao.getLastVersion(contentId, languageCode);
    }

}
