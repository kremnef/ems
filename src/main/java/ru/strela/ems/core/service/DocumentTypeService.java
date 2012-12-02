package ru.strela.ems.core.service;


import ru.strela.ems.core.dao.DocumentTypeDao;
import ru.strela.ems.core.model.DocumentType;

import java.util.List;


public interface DocumentTypeService {

    int getChildrenCount(int id);
    List<DocumentType> getChildren(int parentId);
    List<DocumentType> getChildren(int parentId, int start, int quantity, String sortName, boolean desc);
    List<DocumentType> getEntities();
    DocumentTypeDao getDocumentTypeDao();
	void setDocumentTypeDao(DocumentTypeDao documentTypeDao);
	DocumentType getDocumentType(int documentTypeId);
	DocumentType getDocumentTypeByName(String name);
	List getDocumentTypes();
//	List getDocumentTypes(String name);
//	List getDocumentTypes(Integer ownerId, boolean activeOnly);
	DocumentType saveDocumentType(DocumentType documentType);
	void deleteDocumentType(DocumentType documentType);
	List findDocumentTypes(String[] descriptions);
    
}