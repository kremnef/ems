package ru.strela.ems.security.dao.hibernate;


import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.strela.ems.core.dao.hibernate.HibernateUtil;
import ru.strela.ems.security.dao.CommonObjectDao;

import java.util.ArrayList;
import java.util.List;


//public class TransactionHistoryDaoImpl extends EmsObjectDaoImpl implements TransactionHistoryDao {
public class CommonObjectDaoImpl implements CommonObjectDao {


    private final static Logger log = LoggerFactory.getLogger(CommonObjectDao.class);

    /*public void configure() throws HibernateException {
        _sessions = new Configuration()
                .addClass(TransactionHistory.class)
                .buildSessionFactory();
    }*/


    public CommonObjectDaoImpl() {
        super();
    }


    public int getChildrenCount(Class entityClass, int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        int count;
        try {
            tx = session.beginTransaction();
//          //log.info("--commonObject."+entityClass);
            StringBuilder sql = new StringBuilder("select count(*) from " + entityClass.getSimpleName() + " eo");
            count = ((Long) session.createQuery(sql.toString()).iterate().next()).intValue();
//        //log.info("--getChildrenCount"+count);
            tx.commit();
            session.close();
            log.warn("closeSession");
        }
        catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
            throw he;
        }
        return count;
    }


/*
public int getChildrenCount(int id) {
        Session session = getHibernateTemplate().getSessionFactory().session();
        StringBuilder sql = new StringBuilder("select count(*) from " + getEntityClass().getSimpleName() + " eo");
        int count = ((Long) session.createQuery(sql.toString()).iterate().next()).intValue();

        return count;
    }

*/


    public List getObjects(Object o) {
        return getObjects(null);
    }


    public List getObjects(Class entityClass, int start, int quantity, final String sortName, final boolean desc) {
        String sortBy = sortName.length() > 0 ? sortName : "id";
        Order order = desc ? Order.desc(sortBy) : Order.asc(sortBy);
        return getObjects(entityClass, order, start, quantity);
    }


    public List getObjects(Class entityClass, final Order order) {
        return getObjects(entityClass, order, 0, 0);
    }


    public List getObjects(final Class entityClass, final Order order, final int start, final int quantity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        Criteria criteria = null;
        List list = new ArrayList();
        try {
            tx = session.beginTransaction();


            int objectsCount = quantity == 0 ? Integer.MAX_VALUE : quantity;
            criteria = session.createCriteria(entityClass);
            //            speed test
            /*if (order != null) {
                criteria.addOrder(order);
            }
            else {
                criteria.addOrder(Order.asc("date"));
            }*/
            criteria.setFirstResult(start);
            criteria.setFetchSize(objectsCount);
            criteria.setMaxResults(objectsCount);
            list = criteria.list();
            tx.commit();
            session.close();
            log.warn("closeSession");

        }
        catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
            throw he;
        }
        return list;
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

}
