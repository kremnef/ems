package ru.strela.ems.core.service.spring;


import ru.strela.ems.core.dao.DocumentDao;
import ru.strela.ems.core.model.Document;
import ru.strela.ems.core.service.DocumentService;

import java.util.List;


public class DocumentServiceImpl implements DocumentService {


    private DocumentDao documentDao = null;


    public DocumentDao getDocumentDao() {
        return documentDao;
    }


    public void setDocumentDao(DocumentDao documentDao) {
        this.documentDao = documentDao;
    }


    public Document getDocument(int documentId) {
        return documentDao.getDocument(documentId);
    }

    /*public HashMap<Integer, TreeMap<Integer, String>> getDocumentVersions(int contentId){
        return documentDao.getDocumentVersions(contentId);
    }*/
    /*public List getAllVersions(int contentId){
        return documentDao.getAllVersions(contentId);
    }*/

    /*public EmsSelectionList getVersionSelectionList(int contentId, int languageId) {
         return documentDao.getVersionSelectionList(contentId, languageId);
    }*/

    /*public Document getLastVersionDocument(int contentId, int languageId) {
         return documentDao.getLastVersionDocument(contentId, languageId);
    }*/

    public Document getDocumentByNaturalId(int contentId, String languageCode) {
        return documentDao.getDocumentByNaturalId(contentId, languageCode);
    }

    public Document saveDocument(Document Document) {
        return documentDao.saveDocument(Document);
    }


    public List getDocuments(String owner) {
        return documentDao.getDocuments(owner);
    }


    public List getDocumentsByContentId(int contentId) {
        return documentDao.getDocumentsByContentId(contentId);
    }


    public void deleteDocument(Document document) {
        documentDao.deleteDocument(document);
    }

   /* public void addTags(Document document, String newTags) {
        documentDao.addTags(document, newTags);
    }
*/

    public List findDocuments(String[] descriptions) {
        return documentDao.findDocuments(descriptions);
    }

/*

    public int getLastVersion(int contentId, int languageId) {
        return documentDao.getLastVersion(contentId, languageId);
    }
*/

}
