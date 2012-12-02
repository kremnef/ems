package ru.strela.ems.core.dao.hibernate;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * User: hobal
 * Date: 24.10.2010
 * Time: 2:03:39
 */
public class HibernateUtil {


    private static SessionFactory sessionFactory = configureSessionFactory();
    private static ServiceRegistry serviceRegistry;

    private static Logger logger = LoggerFactory.getLogger(HibernateUtil.class);
    public static final ThreadLocal session = new ThreadLocal();
    public static final ThreadLocal transaction = new ThreadLocal();
    private static final ThreadLocal used = new ThreadLocal();


    private static SessionFactory configureSessionFactory() throws HibernateException {
        Configuration configuration = new Configuration();
//        configuration.configure();
        configuration.configure("/META-INF/hibernate/hibernate.cfg.xml");
        serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        return sessionFactory;
    }


    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }


    public static Session currentSession() {
        return currentSession(false);
    }


    /**
     * Get current Hibernate session or initialize it if it's null
     *
     * @return Hibernate session instance
     */
    private static Session currentSession(boolean forTransaction) {
        Session s = (Session) session.get();
        // Open a new Session, if this Thread has none yet
        try {
            if (!forTransaction) {
                Integer i = (Integer) used.get();
                if (i == null) {
                    i = 0;
                }
                used.set(i + 1);
            }
            if (s == null) {
                s = sessionFactory.openSession();
                session.set(s);
            }
        }
        catch (HibernateException e) {
            logger.error("Get current session error: " + e.getMessage());
        }
        return s;
    }


    /**
     * Close current Hibernate session
     */
    public static void closeSession() {
        Session s = (Session) session.get();
        Integer i = (Integer) used.get();
        if (i != null) {
            i--;
            if (i == 0) {
                session.set(null);
                try {
                    if (s != null) {
                        s.flush();
                        s.close();
                    }
                }
                catch (HibernateException e) {
                    logger.error("Close current session error: " + e.getMessage());
                }
                used.set(null);
            }
            else {
                used.set(i);
            }
        }
    }


    /**
     * Begin Hibernate transaction
     */
    public static void beginTransaction() {
        Transaction tx = (Transaction) transaction.get();
        try {
            if (tx == null) {
                tx = currentSession(true).beginTransaction();
                transaction.set(tx);
            }
        }
        catch (HibernateException e) {
            logger.error("Begin transaction error: " + e.getMessage());
        }
    }


    /**
     * Commit Hibernate transaction
     */
    public static void commitTransaction() {
        Transaction tx = (Transaction) transaction.get();
        try {
            if (tx != null && !tx.wasCommitted() && !tx.wasRolledBack()) {
                tx.commit();
            }
            transaction.set(null);
        }
        catch (HibernateException e) {
            rollbackTransaction();
            logger.error("Commit transaction error: " + e.getMessage());
        }
    }


    /**
     * Rollback Hibernate transaction
     */
    public static void rollbackTransaction() {
        Transaction tx = (Transaction) transaction.get();
        try {
            transaction.set(null);
            if (tx != null && !tx.wasCommitted() && !tx.wasRolledBack()) {
                tx.rollback();
            }
        }
        catch (HibernateException e) {
            logger.error("Rollback transaction error: " + e.getMessage());
        }
        finally {
            closeSession();
        }
    }

}
