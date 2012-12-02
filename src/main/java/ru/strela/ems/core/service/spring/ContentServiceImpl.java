package ru.strela.ems.core.service.spring;


import ru.strela.ems.core.dao.ContentDao;
import ru.strela.ems.core.model.Content;
import ru.strela.ems.core.service.ContentService;

import java.util.List;


public class ContentServiceImpl extends SystemObjectServiceImpl implements ContentService {


    public ContentDao getContentDao() {
        return (ContentDao) getTypifiedObjectDao();
    }


    public void setContentDao(ContentDao contentDao) {
        setTypifiedObjectDao(contentDao);
    }


    public Content getContent(int contentId) {
        return (Content) getTypifiedObject(contentId);
    }


    public List getContents(String owner) {
        return getContentDao().getContents(owner);
    }


    public List getContents() {
        return getContentDao().getContents();
    }


    public List getContentsByDocumentType(int documentTypeId) {
        return getContentDao().getContentsByDocumentType(documentTypeId);
    }


    public List findContents(String[] descriptions) {
        return getContentDao().findContents(descriptions);
    }

}
