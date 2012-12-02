package ru.strela.ems.core.dao.hibernate;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.strela.ems.core.dao.DocumentVersionDao;
import ru.strela.ems.core.model.DocumentVersion;

import java.util.ArrayList;
import java.util.List;

//
//import org.springframework.orm.hibernate4.support.HibernateDaoSupport;


public class DocumentVersionDaoImpl implements DocumentVersionDao {


    private final static Logger log = LoggerFactory.getLogger(DocumentVersionDaoImpl.class);
//    private SessionFactoryStub sessionFactory;

    public DocumentVersionDaoImpl() {
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


    public DocumentVersion getDocumentVersion(int documentVersionId) {
        Session session = getCurrentSession();
        DocumentVersion documentVersion = (DocumentVersion) session.get(DocumentVersion.class, documentVersionId);
        closeSession();
        return documentVersion;
    }


    public List getDocumentVersionsByContentId(final int contentId) {
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(DocumentVersion.class);
        criteria.add(Restrictions.eq("contentId", contentId));
        //            speed test
//        criteria.addOrder(Order.desc("version"));
        List list = criteria.list();
        closeSession();
        return list;

    }


    public List getDocumentVersions(final String owner) {
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(DocumentVersion.class);
        if (owner != null) {
            criteria.add(Restrictions.eq("owner", owner));
        }
        List list = criteria.list();
        closeSession();
        return list;
    }


    public DocumentVersion saveDocumentVersion(DocumentVersion documentVersion) {

        Session session = getCurrentSession();
       


        session.saveOrUpdate(documentVersion);
        closeSession();
        return documentVersion;
    }



    public List getAllVersions(int contentId) {
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(DocumentVersion.class);
        criteria.add(Restrictions.eq("contentId", contentId));
        List documentVersionVersions = criteria.list();
        closeSession();
        return documentVersionVersions;
    }

    /*public DocumentVersion getLastDocumentVersion(int contentId, int languageId) {

        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(DocumentVersion.class);

        criteria.add(Restrictions.eq("contentId", contentId));
        criteria.add(Restrictions.eq("languageId", languageId));
        criteria.setMaxResults(1);


        DocumentVersion lastVersion = (DocumentVersion) criteria.uniqueResult();
        closeSession();
        return lastVersion;
    }*/


    public int getLastVersion(int contentId, String languageCode) {
        Session session = getCurrentSession();
//        Integer lastVersion = null;
        Criteria criteria = session.createCriteria(DocumentVersion.class);

        criteria.add(Restrictions.eq("contentId", contentId));
        criteria.add(Restrictions.eq("languageCode", languageCode));

        criteria.setMaxResults(1);

        Integer lastVersion = 1;
        DocumentVersion doc = (DocumentVersion) criteria.uniqueResult();
        if (doc != null) {

            lastVersion = lastVersion + doc.getVersion();
        }

        closeSession();
        return lastVersion;
    }


    public void deleteDocumentVersion(DocumentVersion documentVersion) {
        Session session = getCurrentSession();
        session.delete(documentVersion);
        closeSession();
    }


    public List findDocumentVersions(final String[] descriptions) {
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(DocumentVersion.class);
        Criterion criterion = null;
        List list;
        for (String description : descriptions) {
            description = description.trim();
            if (description.length() > 0) {
                if (criterion == null) {
                    criterion = Restrictions.like("description", description, MatchMode.ANYWHERE);
                } else {
                    criterion = Restrictions.or(criterion, Restrictions.like("description", description, MatchMode.ANYWHERE));
                }
                criterion = Restrictions.or(criterion, Restrictions.like("code", description, MatchMode.ANYWHERE));
            }
        }
        list = criteria.list();
        if (criterion != null) {
            criteria.add(criterion);
        } else {
            list = new ArrayList();
        }
        closeSession();
        return list;
    }
}
