package ru.strela.ems.core.service.spring;


import org.hibernate.Session;
import ru.strela.ems.core.dao.ObjectLabelDao;
import ru.strela.ems.core.model.ObjectLabel;
import ru.strela.ems.core.service.ObjectLabelService;

import java.util.List;


public class ObjectLabelServiceImpl implements ObjectLabelService {


    private ObjectLabelDao objectLabelDao = null;


    public ObjectLabelDao getObjectLabelDao() {
        return objectLabelDao;
    }


    public void setObjectLabelDao(ObjectLabelDao objectLabelDao) {
        this.objectLabelDao = objectLabelDao;
    }


    public ObjectLabel getObjectLabel(int objectLabelId) {
        return objectLabelDao.getObjectLabel(objectLabelId);
    }

    public ObjectLabel getObjectLabelNaturalId(int objectId, String languageCode) {
        return objectLabelDao.getObjectLabelNaturalId(objectId, languageCode);
    }


    public ObjectLabel saveObjectLabel(ObjectLabel ObjectLabel) {
        return objectLabelDao.saveObjectLabel(ObjectLabel);
    }


    public void deleteObjectLabel(ObjectLabel objectLabel) {
        objectLabelDao.deleteObjectLabel(objectLabel);
    }

    public void saveObjectLabelList(List ObjectLabelList) {
        objectLabelDao.saveObjectLabelList(ObjectLabelList);
    }

    public void saveObjectLabelListSession(Session session, List objectLabelList) {
        objectLabelDao.saveObjectLabelListSession(session, objectLabelList);
    }


}
