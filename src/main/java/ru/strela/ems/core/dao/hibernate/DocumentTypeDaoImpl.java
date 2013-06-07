package ru.strela.ems.core.dao.hibernate;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.slf4j.LoggerFactory;
import ru.strela.ems.core.dao.DocumentTypeDao;
import ru.strela.ems.core.model.DocumentType;
import ru.strela.ems.security.dao.hibernate.CommonObjectDaoImpl;

import java.util.ArrayList;
import java.util.List;


public class DocumentTypeDaoImpl extends CommonObjectDaoImpl implements DocumentTypeDao {

    private final static org.slf4j.Logger log = LoggerFactory.getLogger(DocumentTypeDaoImpl.class);
    public DocumentTypeDaoImpl() {
        super();
    }


    public DocumentType getDocumentType(int documentTypeId) {
        Session session = getCurrentSession();
        /*log.warn("openSession");

        Transaction tx = null;

        */
//         documentType = null;
        /*try {
                    tx = session.beginTransaction();*/
        DocumentType    documentType = (DocumentType) session.get(DocumentType.class, documentTypeId);

            /*tx.commit(); session.close(); log.warn("closeSession");
//            session.flush();
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }*/
        closeSession();
        return documentType;
    }


    public List getDocumentTypes() {
        Session session = getCurrentSession();

/*
        log.warn("openSession");
        Transaction tx = null;
*/
//        Criteria criteria = null;
//        List list = new ArrayList();

/*        try {
            tx = session.beginTransaction();*/
        Criteria  criteria = session.createCriteria(DocumentType.class);
//            speed test
//            criteria.addOrder(Order.asc("name"));
        List list =criteria.list();
            /*tx.commit(); session.close(); log.warn("closeSession");
//            session.flush();
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }*/
        closeSession();
        return list;

    }


    /*public List getDocumentTypes(final String name) {
        return getHibernateTemplate().executeFind(new HibernateCallback() {


            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(DocumentType.class);
                if (name != null) {
                    criteria.add(Restrictions.eq("name", name));
                }

                return criteria.list();
            }

        });
    }*/
    public DocumentType getDocumentTypeByName(String name) {
        Session session = getCurrentSession();
        /*log.warn("openSession");
        Transaction tx = null;*/
        DocumentType documentType;

//        try {
//            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(DocumentType.class);

            criteria.add(Restrictions.eq("name", name));

            documentType = (DocumentType) criteria.uniqueResult();
            /*tx.commit(); session.close(); log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }*/
        closeSession();
        return documentType;
//            }

//        });
    }


    public DocumentType saveDocumentType(DocumentType documentType) {
        /*Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;

        try {
            tx = session.beginTransaction();*/
        Session session = getCurrentSession();
            session.saveOrUpdate(documentType);

            /*tx.commit(); session.close(); log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }*/
        closeSession();
        return documentType;
    }


    public void deleteDocumentType(DocumentType documentType) {
        Session session = getCurrentSession();
        /*log.warn("openSession");
        Transaction tx = null;

        try {
            tx = session.beginTransaction();*/
            session.saveOrUpdate(documentType);

            session.delete(documentType);
            /*tx.commit(); session.close(); log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }*/
        closeSession();
    }


    public List findDocumentTypes(final String[] descriptions) {
        /*Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;*/
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(DocumentType.class);
        Criterion criterion = null;
        List list  = null;
        /*try {
            tx = session.beginTransaction();*/

        for (String description1 : descriptions) {
            String description = description1.trim();
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
            /*tx.commit(); session.close(); log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }*/
        closeSession();
        if (criterion != null) {
            criteria.add(criterion);

            return list;
        } else {
            return new ArrayList();
        }

    }

}
