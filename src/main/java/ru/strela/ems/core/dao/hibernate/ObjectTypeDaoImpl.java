package ru.strela.ems.core.dao.hibernate;


import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.strela.ems.core.dao.ObjectTypeDao;
import ru.strela.ems.core.model.ObjectType;
import ru.tastika.tools.util.Utilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * User: hobal
 * Date: 04.05.2010
 * Time: 18:17:33
 */
public class ObjectTypeDaoImpl implements ObjectTypeDao {

    private final static Logger log = LoggerFactory.getLogger(ObjectTypeDaoImpl.class);

    public ObjectTypeDaoImpl() {
        super();
    }


    public ObjectType getObjectType(String className) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        List objects;
        ObjectType o = null;
        try {
            tx = session.beginTransaction();
            objects = session.createQuery("from ObjectType ot where ot.className = '" + className + "'").list();
            if (objects.size() > 0) {
                o = (ObjectType) objects.get(0);
            }
            tx.commit();
            session.close();
            log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }
        return o;

    }


    public List getObjectTypes(boolean onlyEmbedded) {
        log.warn("onlyEmbedded" + onlyEmbedded);
        /*Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");*/
        int newSession = 0;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().openSession();
            log.warn("openSession");
            newSession = 1;
        }

        Transaction tx = null;
        List list;
        try {
            tx = session.beginTransaction();
//            List objects = session.createQuery("from ObjectType ot = '" + className + "'").list();
            StringBuilder sql = new StringBuilder("from ObjectType ot");
            if (onlyEmbedded) {
                sql.append(" where ot.embedded = 1");
            }
            list = session.createQuery(sql.toString()).list();

            tx.commit();
            //            session.close(); log.warn("closeSession");
            if (newSession == 1) {
                session.close();
                log.warn("closeSession");
            }
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }
        return list;
    }


    public int getRootObjectsForTypeCount(ObjectType objectType) {
        int count;
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx;

        tx = session.beginTransaction();
        try {
            if (objectType.isHierarchical()) {
                Query query = session.createQuery("select count(*) from TypifiedObject to where to.emsObject.parentId is null and to.objectType = ?");
                query.setParameter(0, objectType);
                count = ((Long) query.iterate().next()).intValue();

            } else {
                Query query = session.createQuery("select count(*) from TypifiedObject to where to.objectType = ?");
                query.setParameter(0, objectType);
                count = ((Long) query.iterate().next()).intValue();
//            open
            }
            tx.commit();
            session.close();
            log.warn("closeSession");

        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }
        return count;

    }


    public List getRootObjectsForType(ObjectType objectType) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        List list = new ArrayList();
        tx = session.beginTransaction();
        try {
            StringBuffer sb = new StringBuffer("from TypifiedObject to where to.objectType = ?");
            if (objectType.isHierarchical()) {
                sb.append(" and to.emsObject.parentId is null");
            }
            Query query = session.createQuery(sb.toString());
            query.setParameter(0, objectType);
            list = query.list();

            tx.commit();
            session.close();
            log.warn("closeSession");

        } catch (
                HibernateException he
                )

        {
            if (tx != null) tx.rollback();
            throw he;
        }
        return list;
    }


    public List getObjectTypeActions(int objectTypeId) {
        int newSession = 0;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().openSession();
            log.warn("openSession");
            newSession = 1;
        }

        Transaction tx = null;
        List list = new ArrayList();
        tx = session.beginTransaction();
        try {
            Query query = session.createQuery("from ObjectTypeAction ota where ota.objectTypeId = ?");
            query.setParameter(0, objectTypeId);
            list = query.list();
            tx.commit();
            if (newSession == 1) {
                session.close();
                log.warn("closeSession");
            }

        } catch (
                HibernateException he
                )

        {
            if (tx != null) tx.rollback();
            throw he;
        }
        return list;
    }


    public List getObjectTypeActions(Collection objectTypeActionIds) {
        int newSession = 0;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().openSession();
            log.warn("openSession");
            newSession = 1;
        }

        Transaction tx = null;
        tx = session.beginTransaction();
        try {
            if (objectTypeActionIds.size() > 0) {
                String inCondition = Utilities.implode(objectTypeActionIds, ",");
                Query query = session.createQuery("from ObjectTypeAction ota where ota.id in (" + inCondition + ")");
                List list = query.list();
                tx.commit();
                if (newSession == 1) {
                    session.close();
                    log.warn("closeSession");
                }
                return list;
            }
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }


        return new ArrayList();

    }

    public ObjectType getObjectType(int objectTypeId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        ObjectType objectType = null;
        tx = session.beginTransaction();
        try {
            objectType = (ObjectType) session.load(ObjectType.class, objectTypeId);
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }

        tx.commit();
        session.close();
        log.warn("closeSession");
        return objectType;

    }

}
