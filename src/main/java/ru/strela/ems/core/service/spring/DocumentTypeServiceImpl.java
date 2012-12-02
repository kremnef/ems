package ru.strela.ems.core.service.spring;


import ru.strela.ems.core.dao.DocumentTypeDao;
import ru.strela.ems.core.model.DocumentType;
import ru.strela.ems.core.service.DocumentTypeService;

import java.util.List;


public class DocumentTypeServiceImpl implements DocumentTypeService {


    private DocumentTypeDao documentTypeDao = null;
    DocumentType documentType = new DocumentType();
    Class entityClass = documentType.getClass();

    public int getChildrenCount(int id) {
        return documentTypeDao.getChildrenCount(entityClass, id);
    }

    public List<DocumentType> getChildren(int parentId) {
        return getChildren(parentId, 0, 0, "", false);
    }

    public List<DocumentType> getChildren(int parentId, int start, int quantity, String sortName, boolean desc) {
        return documentTypeDao.getObjects(entityClass, start, quantity, sortName, desc);
    }

    public List<DocumentType> getEntities() {
        return documentTypeDao.getDocumentTypes();
    }

    public DocumentTypeDao getDocumentTypeDao() {
        return documentTypeDao;
    }


    public void setDocumentTypeDao(DocumentTypeDao documentTypeDao) {
        this.documentTypeDao = documentTypeDao;
    }


    public DocumentType getDocumentType(int documentTypeId) {
        return documentTypeDao.getDocumentType(documentTypeId);
    }



    public DocumentType saveDocumentType(DocumentType documentType) {
        return documentTypeDao.saveDocumentType(documentType);
    }


    public DocumentType getDocumentTypeByName(String name) {
        return documentTypeDao.getDocumentTypeByName(name);
    }

    public List getDocumentTypes() {
        return documentTypeDao.getDocumentTypes();
    }


    /*public List getDocumentTypes(String name) {
        return documentTypeDao.getDocumentTypes(name);
    }
*/

    public void deleteDocumentType(DocumentType documentType) {
        documentTypeDao.deleteDocumentType(documentType);
    }


    public List findDocumentTypes(String[] descriptions) {
        return documentTypeDao.findDocumentTypes(descriptions);
    }

}
