package ru.strela.ems.security.dao.hibernate;


import org.hibernate.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.strela.ems.core.dao.hibernate.HibernateUtil;
import ru.strela.ems.security.dao.TransactionHistoryDao;
import ru.strela.ems.security.model.TransactionHistory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


//public class TransactionHistoryDaoImpl extends EmsObjectDaoImpl implements TransactionHistoryDao {
public class TransactionHistoryDaoImpl extends CommonObjectDaoImpl implements TransactionHistoryDao {
     private final static Logger log = LoggerFactory.getLogger(TransactionHistoryDaoImpl.class);
//    private SessionFactory _sessions;

    /*public void configure() throws HibernateException {
        _sessions = new Configuration()
                .addClass(TransactionHistory.class)
                .buildSessionFactory();
    }
*/
    public TransactionHistoryDaoImpl() {
        super();
    }

    /*   public int getChildrenCount(int id) {
            Session session = getHibernateTemplate().getSessionFactory().session();
            StringBuilder sql = new StringBuilder("select count(*) from TransactionHistory eo");
            int count = ((Long) session.createQuery(sql.toString()).iterate().next()).intValue();
            open
            //log.info("--getChildrenCount"+count);
            return count;
        }
    */
    /*
     * (non-Javadoc)
     *
     * @see ru.strela.ems.security.dao.TransactionHistoryDao#getName(java.lang.Integer)
     */

    public TransactionHistory getTransaction(Integer transactionId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        TransactionHistory customer = null;

        tx = session.beginTransaction();
        TransactionHistory TransactionHistory = (TransactionHistory) session.load(TransactionHistory.class, transactionId);
        tx.commit();
        session.close(); log.warn("closeSession");
        return TransactionHistory;
    }


    public List getTransactionByUserId(Integer id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List getTransactionByDatePeriod(Date dateStart, Date dateEnd) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public TransactionHistory getTransactionHistoryByLogin(final String login)
            throws HibernateException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        TransactionHistory customer = null;
        try {
            tx = session.beginTransaction();
            Query q = session.createQuery(
                    "from TransactionHistory as customer where login = :login"
            );
            q.setParameter("login", login);
            customer = (TransactionHistory) q.uniqueResult();
//            //log.info("CUSTOMER = " + customer);
            tx.commit();
            session.close(); log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }
        /*finally {

     /   }*/
        return customer;

    }


    /*public List<TransactionHistory> getObjects() {
          return getObjects(null);
      }


      public List<TransactionHistory> getObjects(int start, int quantity, final String sortName, final boolean desc) {
          String sortBy = sortName.length() > 0 ? sortName : "date";
          Order order = desc ? Order.desc(sortBy) : Order.asc(sortBy);
          return getObjects(order, start, quantity);
      }


      public List<TransactionHistory> getObjects(final Order order) {
          return getObjects(order, 0, 0);
      }


      public List<TransactionHistory> getObjects(final Order order, final int start, final int quantity) {
          return getHibernateTemplate().executeFind(new HibernateCallback() {


              public Object doInHibernate(Session session) throws HibernateException {
                  int objectsCount = quantity == 0 ? Integer.MAX_VALUE : quantity;
                  Criteria criteria = session.createCriteria(TransactionHistory.class);

                  if (order != null) {
                      criteria.addOrder(order);
                  }
                  else {
                      criteria.addOrder(Order.asc("date"));
                  }
                  criteria.setFirstResult(start);
                  criteria.setFetchSize(objectsCount);
                  return criteria.list();
              }

          });
      }
    
    
    */
    /*
    * (non-Javadoc)
    *
    * @see ru.strela.ems.security.dao.TransactionHistoryDao#saveTransactionHistory(ru.strela.ems.core.model.TransactionHistory)
    */

    public TransactionHistory saveTransaction(TransactionHistory transaction) {
//        if (TransactionHistory.getIsDefaultTransactionHistory()) {
//            Session session = getCurrentSession();
//            session.createQuery("update TransactionHistory l set l.isDefaultLang = 0").executeUpdate();
//        }
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
/*
          String remoteAddress = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest().getRemoteAddr();

        transaction.setIpAddress(remoteAddress);*/

        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(transaction);

            tx.commit();
            session.close(); log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }
        return transaction;
    }

    public List findTransactions(String[] descriptions) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /*
     * (non-Javadoc)
     *
     * @see ru.strela.ems.security.dao.TransactionHistoryDao#deleteTransactionHistory(ru.strela.ems.core.model.TransactionHistory)
     */

    public void deleteTransaction(TransactionHistory customer) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        tx = session.beginTransaction();
        session.delete(customer);
        tx.commit();
        session.close(); log.warn("closeSession");
    }


    public List<TransactionHistory> getTransactions() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        List list = new ArrayList();
        tx = session.beginTransaction();
        Criteria criteria = session.createCriteria(TransactionHistory.class);

        list = criteria.list();
//            List<ru.strela.ems.security.model.Transaction> transactions = null;

//                ru.strela.ems.security.model.Transaction transaction = new ru.strela.ems.security.model.Transaction()
//        list = criteria.list();
        tx.commit();
        session.close(); log.warn("closeSession");
        return list;
    }

}


