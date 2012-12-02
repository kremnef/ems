package ru.strela.ems.core.service;


import ru.strela.ems.core.dao.ContentDao;
import ru.strela.ems.core.model.Content;

import java.util.List;


public interface ContentService extends SystemObjectService {


    ContentDao getContentDao();
    void setContentDao(ContentDao contentDao);
    Content getContent(int contentId);
    List getContents();
    List getContents(String owner);
    List getContentsByDocumentType(int documentTypeId);
    List findContents(String[] descriptions);

}
