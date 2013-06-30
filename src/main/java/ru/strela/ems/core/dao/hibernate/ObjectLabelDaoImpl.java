package ru.strela.ems.core.dao.hibernate;


import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.strela.ems.core.dao.ObjectLabelDao;
import ru.strela.ems.core.model.ObjectLabel;

import java.util.List;

//
//import org.springframework.orm.hibernate4.support.HibernateDaoSupport;


public class ObjectLabelDaoImpl implements ObjectLabelDao {


    private final static Logger log = LoggerFactory.getLogger(ObjectLabelDaoImpl.class);
//    private SessionFactoryStub sessionFactory;

    public ObjectLabelDaoImpl() {
        super();
    }


    protected Session getCurrentSession() {
        Session session = HibernateUtil.currentSession();
        HibernateUtil.beginTransaction();
        return session;
    }


    protected void closeSession() {
        HibernateUtil.commitTransaction();
        HibernateUtil.closeSession();
    }


    public ObjectLabel getObjectLabel(int objectLabelId) {
        Session session = getCurrentSession();
        ObjectLabel objectLabel = (ObjectLabel) session.get(ObjectLabel.class, objectLabelId);
        closeSession();
        return objectLabel;
    }

    /*  public ObjectLabel getObjectLabel(int ObjectLabelId, String languageCode) {
        Session session = getCurrentSession();
        ObjectLabel ObjectLabel = (ObjectLabel) session.get(ObjectLabel.class, ObjectLabelId);
        closeSession();
        return ObjectLabel;
    }*/

    public ObjectLabel getObjectLabelNaturalId(int objectId, String languageCode) {
        Session session = getCurrentSession();
//        log.warn("ObjectLabel - session: " +session);
        ObjectLabel ObjectLabel = (ObjectLabel) session.byNaturalId(ObjectLabel.class)
                .using("languageCode", languageCode)
                .using("objectId", objectId)
                .load();
//        log.warn("ObjectLabel : " +ObjectLabel);
        closeSession();
        return ObjectLabel;
    }


    public void saveObjectLabelListSession(Session session, List objectLabelList) {
        for (Object obj : objectLabelList) {
            ObjectLabel objectLabel = (ObjectLabel) obj;
//            log.warn("label: " + objectLabel.getLabel());
            if (objectLabel.getLabel() == null) {
//                log.warn("objectLabel NOT Saved");
            } else {
                session.saveOrUpdate(objectLabel);
            }
            session.flush();
        }
    }

    public void saveObjectLabelList(List objectLabelList) {
        Session session = getCurrentSession();
        for (Object obj : objectLabelList) {
            ObjectLabel objectLabel = (ObjectLabel) obj;
//            log.warn(" saveObjectLabelList ---------------------");
//            log.warn("id:"+objectLabel.getId());
//            log.warn("label: "+objectLabel.getLabel());
//            log.warn("languageCode: "+objectLabel.getLanguageCode());
//            log.warn("objectId: "+objectLabel.getObjectId());


//            log.warn("label: " + objectLabel.getLabel());

//            log.warn("languageCode: "+objectLabel.getLanguageCode());
//            log.warn("objectId: "+objectLabel.getObjectId());
//            if (!objectLabel.getLabel().equals("")) {
            if (objectLabel.getLabel() == null) {
                log.warn("objectLabel NOT Saved");
            } else {
                session.saveOrUpdate(objectLabel);
            }
        }
        closeSession();
    }


    public ObjectLabel saveObjectLabel(ObjectLabel ObjectLabel) {

        Session session = getCurrentSession();

        session.saveOrUpdate(ObjectLabel);
        closeSession();
        return ObjectLabel;
    }


    public void deleteObjectLabel(ObjectLabel metaInfo) {
        Session session = getCurrentSession();
        session.delete(metaInfo);
        closeSession();
    }


}
