package ru.strela.ems.core.dao;

import ru.strela.ems.core.model.DocumentType;
import ru.strela.ems.security.dao.CommonObjectDao;

import java.util.List;

public interface DocumentTypeDao extends CommonObjectDao{


    DocumentType getDocumentType(int documentTypeId);

    DocumentType getDocumentTypeByName(String name);
    List getDocumentTypes();
//    List getDocumentTypes(String name);
    DocumentType saveDocumentType(DocumentType documentType);
    void deleteDocumentType(DocumentType documentType);
    List findDocumentTypes(String[] descriptions);

}