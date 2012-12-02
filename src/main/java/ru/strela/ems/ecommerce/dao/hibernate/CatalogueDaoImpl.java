package ru.strela.ems.ecommerce.dao.hibernate;


import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.strela.ems.core.dao.hibernate.HibernateUtil;
import ru.strela.ems.core.dao.hibernate.SystemObjectDaoImpl;
import ru.strela.ems.ecommerce.dao.CatalogueDao;
import ru.strela.ems.ecommerce.model.Catalogue;

import java.util.ArrayList;
import java.util.List;


public class CatalogueDaoImpl extends SystemObjectDaoImpl implements CatalogueDao {
     private final static Logger log = LoggerFactory.getLogger(CatalogueDaoImpl.class);

    public CatalogueDaoImpl() {
        super();
    }


    @Override
    public Class getEntityClass() {
        return Catalogue.class;
    }


    public Catalogue getCatalogue(int сatalogueId) {
        return (Catalogue) getTypifiedObject(сatalogueId);
    }


    public List getCatalogues(final String owner) {
//        Session session = sessionFactory.openSession();
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
//        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Criteria criteria = null;
        List list = new ArrayList();
        try {
            tx = session.beginTransaction();
            criteria = session.createCriteria(Catalogue.class);
            if (owner != null) {
                criteria.add(Restrictions.eq("owner", owner));
            }
            //            speed test
//            criteria.addOrder(Order.asc("position"));
            list = criteria.list();
            tx.commit(); session.close(); log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }
        return list;
    }


    public List getCatalogues() {
        return getObjects(Order.asc("position"));
    }


    public List findCatalogues(final String[] descriptions) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        Criteria criteria = null;
        Criterion criterion = null;
        List list = new ArrayList();
        try {
            tx = session.beginTransaction();
            criteria = session.createCriteria(Catalogue.class);
            for (int i = 0; i < descriptions.length; i++) {
                String description = descriptions[i].trim();
                if (description.length() > 0) {
                    if (criterion == null) {
                        criterion = Restrictions.like("description", description, MatchMode.ANYWHERE);
                    } else {
                        criterion = Restrictions.or(criterion, Restrictions.like("description", description, MatchMode.ANYWHERE));
                    }
                    criterion = Restrictions.or(criterion, Restrictions.like("code", description, MatchMode.ANYWHERE));
                }
            }
            if (criterion != null) {
            criteria.add(criterion);
            list=  criteria.list();
        }
            tx.commit(); session.close(); log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }
        /* else {
            return new ArrayList();
        }*/
        return list;


    }

}
