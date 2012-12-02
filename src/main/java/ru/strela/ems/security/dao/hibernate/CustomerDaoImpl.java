package ru.strela.ems.security.dao.hibernate;


import org.hibernate.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.strela.ems.core.dao.hibernate.HibernateUtil;
import ru.strela.ems.core.model.Tag;
import ru.strela.ems.security.dao.CustomerDao;
import ru.strela.ems.security.model.Customer;

import java.util.ArrayList;
import java.util.List;


//public class CustomerDaoImpl extends EmsObjectDaoImpl implements CustomerDao {
public class CustomerDaoImpl extends CommonObjectDaoImpl implements CustomerDao {


    private final static Logger log = LoggerFactory.getLogger(CustomerDao.class);
    /*private SessionFactory _sessions;

    public void configure() throws HibernateException {
        _sessions = new Configuration()
                .addClass(Customer.class)
                .buildSessionFactory();
    }*/


    public CustomerDaoImpl() {
        super();
    }

    /*
     * (non-Javadoc)
     *
     * @see ru.strela.ems.security.dao.CustomerDao#getName(java.lang.Integer)
     */


    public Customer getCustomer(int сustomerId) {

         Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        Customer customer = null;
        try {
            tx = session.beginTransaction();
            customer = (Customer) session.load(Customer.class, сustomerId);

            tx.commit(); session.close(); log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }

       /* System.out.println("getCustomer:"+сustomerId);
        Session session = getCurrentSession();
        Customer customer = (Customer) session.load(Customer.class, сustomerId);
        closeSession();
        System.out.println("Customer:" + customer.getLogin());*/
        return customer;
    }

    /* public Customer getCustomerByLogin(String login) {
        List list = getHibernateTemplate().find("from Customer where login = ?", login);
        Customer Customer = null;
        if (list.size() > 0) {
            Customer = (Customer) list.get(0);
        }
        return Customer;
    }*/

    /*public Customer getCustomerByLogin(final String login) {

        Session session = HibernateUtil.getSessionFactory().session();
        //log.info("CUSTOMER ID OPEN= " + login);
        Query query = session.createQuery("from Customer where login = :login");
            query.setParameter("login", login);
            Customer customer = (Customer) query.uniqueResult();

            //log.info("CUSTOMER ID = " + customer.getId());
            return customer;



    }*/


    public Customer getCustomerByLogin(final String login)
            throws HibernateException {

        Session session = getCurrentSession();
        Query q = session.createQuery(
                "from Customer as customer where login = :login"
        );
        q.setParameter("login", login);
        Customer customer = (Customer) q.uniqueResult();
        closeSession();

//        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//        Transaction tx = null;
//        Customer customer = null;
//        int needCloseSession = 0;
//        if (!session.isOpen()) {
//            needCloseSession = 1;
//            session = HibernateUtil.getSessionFactory().openSession();
//            log.warn("openSession");
//        }
//        try {
//            tx = session.beginTransaction();
//            Query q = session.createQuery(
//                    "from Customer as customer where login = :login"
//            );
//            q.setParameter("login", login);
//            customer = (Customer) q.uniqueResult();
//            tx.commit();
//            if (needCloseSession == 1) {
//                session.close();
//                log.warn("closeSession");
//            }
//        }
//        catch (HibernateException he) {
//            if (tx != null) {
//                tx.rollback();
//            }
//            throw he;
//        }
//        /*finally {
//
//        }*/
        return customer;

    }
/*    public Customer getCustomerByLogin(final String login) {

        Session session = HibernateUtil.getSessionFactory().session();
        //log.info("CUSTOMER ID OPEN= " + login);
        Criteria criteria = session.createCriteria(Customer.class).add(
                Restrictions.eq("login", login));
//        ).setCacheable(true)
        if (criteria != null) {
            Customer customer = (Customer) criteria.uniqueResult();

            //log.info("CUSTOMER ID = " + customer.getId());
            return customer;

        } else {
            //log.info("CUSTOMER ID NOT FOUND= ");
            return null;
        }

    }*/
    /*

     * (non-Javadoc)
     *
     * @see ru.strela.ems.security.dao.CustomerDao#getCustomers(java.lang.Integer, boolean)
     */

    /*public List getCustomers() {
        return getHibernateTemplate().executeFind(new HibernateCallback() {


            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(Customer.class);

                return criteria.list();
            }

        });
    }*/
/*
    public List getCustomerNames() {
        return getHibernateTemplate().executeFind(new HibernateCallback() {


            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(Customer.class);

                return criteria.list();
            }

        });
    }
*/


    /*
    * (non-Javadoc)
    *
    * @see ru.strela.ems.security.dao.CustomerDao#saveCustomer(ru.strela.ems.core.model.Customer)
    */


    public Customer saveCustomer(Customer customer) {
//        if (Customer.getIsDefaultCustomer()) {
//            Session session = getCurrentSession();
//            session.createQuery("update Customer l set l.isDefaultLang = 0").executeUpdate();
//        }
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(customer);

            tx.commit();
            session.close();
            log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }
        return customer;
    }


    public List findCustomers(String[] descriptions) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /*
     * (non-Javadoc)
     *
     * @see ru.strela.ems.security.dao.CustomerDao#deleteCustomer(ru.strela.ems.core.model.Customer)
     */


    public void deleteCustomer(Customer customer) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(customer);
            tx.commit();
            session.close();
            log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }


    }

    /* (non-Javadoc)
     * @see ru.strela.ems.security.dao.CustomerDao#findCustomers(java.lang.String[])
     */

    /*public List findCustomers(final String[] descriptions) {
        return getHibernateTemplate().executeFind(new HibernateCallback() {


            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(Customer.class);
                Criterion criterion = null;
                for (int i = 0; i < descriptions.length; i++) {
                    String description = descriptions[i].trim();
                    if (description.length() > 0) {
                        if (criterion == null) {
                            criterion = Restrictions.like("description", description, MatchMode.ANYWHERE);
                        }
                        else {
                            criterion = Restrictions.or(criterion, Restrictions.like("description", description, MatchMode.ANYWHERE));
                        }
                        criterion = Restrictions.or(criterion, Restrictions.like("code", description, MatchMode.ANYWHERE));
                    }
                }
                if (criterion != null) {
                    criteria.add(criterion);
                    return criteria.list();
                }
                else {
                    return new ArrayList();
                }
            }

        });
    }*/


    public List<Customer> getCustomers() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        Criteria criteria = null;
        List list = new ArrayList();
        try {
            tx = session.beginTransaction();
            criteria = session.createCriteria(Customer.class);
            list = criteria.list();
            tx.commit();
            session.close();
            log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }
        return list;

//                criteria.add(Restrictions.eq("isPublished", Boolean.TRUE));
    }

}
