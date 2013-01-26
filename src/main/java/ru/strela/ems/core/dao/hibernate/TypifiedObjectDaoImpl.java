package ru.strela.ems.core.dao.hibernate;
//chenged

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.strela.ems.core.dao.MetaInfoDao;
import ru.strela.ems.core.dao.ObjectLabelDao;
import ru.strela.ems.core.dao.TypifiedObjectDao;
import ru.strela.ems.core.model.*;
import ru.tastika.tools.util.Utilities;

import java.util.Collection;
import java.util.List;


/**
 * User: hobal
 * Date: 03.08.2010
 * Time: 16:17:43
 */
public class TypifiedObjectDaoImpl implements TypifiedObjectDao {


    private final static Logger log = LoggerFactory.getLogger(TypifiedObjectDaoImpl.class);


    public TypifiedObjectDaoImpl() {
        super();
    }


    public ObjectType getObjectType(String className) {
        Session session = getCurrentSession();
        ObjectType o = null;
        List objects = session.createQuery("from ObjectType ot where ot.className ='" + className + "'").list();
//            List objects = getHibernateTemplate().find("from ObjectType ot where ot.className = ?", className);
        if (objects.size() > 0) {
            o = (ObjectType) objects.get(0);
        }
        /*if (objects.size() > 0) {
            return (ObjectType) objects.get(0);
        } else {
            return null;
        }*/
        closeSession();
        return o;
    }


    public ObjectType getObjectTypeById(int objectTypeId) {
        Session session = getCurrentSession();
        ObjectType o = null;
        List objects = session.createQuery("from ObjectType ot where ot.id =" + objectTypeId).list();
//            List objects = getHibernateTemplate().find("from ObjectType ot where ot.className = ?", className);
        if (objects.size() > 0) {
            o = (ObjectType) objects.get(0);
        }
        /*if (objects.size() > 0) {
            return (ObjectType) objects.get(0);
        } else {
            return null;
        }*/
        closeSession();
        return o;
    }


    public List getObjectTypes(boolean onlyEmbedded) {
        Session session = getCurrentSession();

        StringBuilder sql = new StringBuilder("from ObjectType ot");
        if (onlyEmbedded) {
            sql.append(" where ot.embedded = 1");
        }
        List list = session.createQuery(sql.toString()).list();
        closeSession();
        return list;
    }


    public List<TypifiedObject> getTypifiedObjects(Collection<Integer> ids) {
        return getTypifiedObjects(getEntityClass(), ids);
    }


    public List<TypifiedObject> getTypifiedObjects(Class entityClass, Collection<Integer> ids) {
        Session session = getCurrentSession();
        StringBuilder sql = new StringBuilder("from ");
        sql.append(entityClass.getSimpleName());
        sql.append(" where id in (");
        sql.append(Utilities.implode(ids, ","));
        sql.append(")");
        List list = session.createQuery(sql.toString()).list();
        closeSession();
        return list;
    }


//    public int getTypifiedObjectUrlRelativity(int id) {
//        int urlRelativity = TypifiedObject.URL_RELATIVITY_RELATIVE;
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        Transaction tx = null;
//        try {
//            tx = session.beginTransaction();
//            List<Integer> list = session.createQuery("select urlRelativity from TypifiedObject where id = " + id).list();
//
//            if (list.size() > 0) {
//                urlRelativity = list.get(0);
//            }
//            tx.commit();
//            session.close(); log.warn("closeSession");
//        } catch (HibernateException he) {
//            if (tx != null) tx.rollback();
//            throw he;
//        }
//        return urlRelativity;
//    }


    public TypifiedObject getTypifiedObject(int id) {
//        TypifiedObject  typifiedObject = getTypifiedObject(getEntityClass(), id);

        return getTypifiedObject(getEntityClass(), id);
    }

    public TypifiedObject getParent(int id) {

        return getTypifiedObject(getEntityClass(), id);
    }


    public TypifiedObject getTypifiedObject(Class entityClass, int id) {
        Session session = getCurrentSession();
        TypifiedObject typifiedObject = (TypifiedObject) session.get(entityClass, id);
        closeSession();
        return typifiedObject;
    }


    public List<TypifiedObject> getObjects() {
        log.warn("getObjects()-1");
        return getObjects(null);
    }


    public List<TypifiedObject> getObjects(int start, int quantity, final String sortName, final boolean desc) {
        log.warn("getObjects()-2");
        String sortBy = sortName.length() > 0 ? sortName : "position";
        Order order = desc ? Order.desc(sortBy) : Order.asc(sortBy);
        return getObjects(order, start, quantity);
    }


    public List<TypifiedObject> getObjects(int parentId, int start, int quantity, final String sortName, final boolean desc, Filter filter) {
        return getObjects(start, quantity, sortName, desc);
    }


    public List<TypifiedObject> getObjects(int start, int quantity, final String sortName, final boolean desc, String filter) {
        log.warn("getObjects()-3");
        String sortBy = sortName.length() > 0 ? sortName : "position";
        Order order = desc ? Order.desc(sortBy) : Order.asc(sortBy);
        return getObjects(order, start, quantity);
    }


    public List<TypifiedObject> getObjects(final Order order) {
        log.warn("getObjects()-4");
        return getObjects(order, 0, 0);
    }


    public List<TypifiedObject> getObjects(final Order order, final int start, final int quantity) {
        Session session = getCurrentSession();
        log.warn("getObjects()-5");
        Criteria criteria = session.createCriteria(getEntityClass());
        //            speed test
        /* if (order != null) {
            criteria.addOrder(order);
        } else {
            criteria.addOrder(Order.asc("position"));
        }*/
        criteria.setFirstResult(start);
        if (quantity > 0) {
            criteria.setMaxResults(quantity);
        }
        System.out.println("8. System.currentTimeMillis() = " + System.currentTimeMillis());

        List<TypifiedObject> list = criteria.list();
        System.out.println("9. System.currentTimeMillis() = " + System.currentTimeMillis());
        closeSession();
        return list;
    }


    public int getChildrenCount(int id) {
        Session session = getCurrentSession();
        StringBuilder sql = new StringBuilder("select count(*) from ").append(getEntityClass().getSimpleName()).append(" eo");
        int count = ((Long) session.createQuery(sql.toString()).iterate().next()).intValue();
        closeSession();
        return count;
    }


    public Class getEntityClass() {
        return TypifiedObject.class;
    }


    public final TypifiedObject save(TypifiedObject typifiedObject) {
//kremnef убрал здесь ссесию, потому как ниже у теяб еще убудт открыти я и закрытия , и хистори закомментил, его видимон надо будет потом преенсти в метод
//                    typifiedObject = saveObject(typifiedObject);
        Session session = beforeAction();
        try {

            typifiedObject = saveObject(typifiedObject);
            //indexSiteMap(typifiedObject);

//            Date dateTime = Calendar.getInstance().getTime();
            //        transaction history
//            log.warn("typifiedObject.getId() = " + typifiedObject.getId());
//            TransactionHistory transactionHistory = new TransactionHistory(typifiedObject, typifiedObject.getOwner(), "record", dateTime);
//            session.saveOrUpdate(transactionHistory);
            afterAction();
        } catch (HibernateException he) {
            he.printStackTrace();
            if (session.isOpen()) {
                Transaction tx = session.getTransaction();
                if (tx != null && tx.isActive()) {
                    tx.rollback();
                }
            }
            throw he;
        }
        return typifiedObject;
    }


    public void indexSiteMap(TypifiedObject typifiedObject) {
    }


    protected void clearSiteMap(TypifiedObject typifiedObject) {
    }


    protected TypifiedObject saveObject(TypifiedObject typifiedObject) {
        typifiedObject.setObjectType(getObjectType(getEntityClass().getName()));
//        typifiedObject.synchronizeLabels();

//        Session session = HibernateUtil.getSessionFactory().openSession();
//        log.warn("openSession");
//        Transaction tx = null;
//        tx = session.beginTransaction();

        Session currentSession = getCurrentSession();


        /*ObjectLabelDao objectLabelDao = new ObjectLabelDaoImpl();
        objectLabelDao.saveObjectLabelList(currentSession, typifiedObject.getObjectLabelList());
*/
        /*MetaInfoDao metaInfoDao = new MetaInfoDaoImpl();
        metaInfoDao.saveMetaInfoList(currentSession, typifiedObject.getMetaInfoList());
*/

        currentSession.saveOrUpdate(typifiedObject);
//        session.saveOrUpdate(typifiedObject);
//        tx.commit();
//        session.close();
        closeSession();
        return typifiedObject;
    }


    public final void delete(TypifiedObject typifiedObject) {
        Session session = beforeAction();

        try {
//            clearSiteMap(typifiedObject);
            deleteObject(typifiedObject);
            afterAction();
        } catch (HibernateException he) {
            if (session.isOpen()) {
                Transaction tx = session.getTransaction();
                if (tx != null) {
                    tx.rollback();
                }
            }
            throw he;
        }

        /*    afterAction();
        } catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
                throw he;
            }
        }*/
    }


    protected void deleteObject(TypifiedObject typifiedObject) {
        StringBuilder sb = new StringBuilder("delete from SystemNodeObject sno where sno.objectId = ");
        sb.append(typifiedObject.getId());
//        delete objectLabelList
        ObjectLabelDao objectLabelDao = new ObjectLabelDaoImpl();
        List<ObjectLabel> objectLabelList = typifiedObject.getObjectLabelList();

        for (Object obj : objectLabelList) {
            ObjectLabel objectLabel = (ObjectLabel) obj;
            objectLabelDao.deleteObjectLabel(objectLabel);
        }
//        delete metaInfoList

        MetaInfoDao metaInfoDao = new MetaInfoDaoImpl();
        List<MetaInfo> metaInfoList = typifiedObject.getMetaInfoList();

        for (Object obj : metaInfoList) {
            MetaInfo metaInfo = (MetaInfo) obj;
            metaInfoDao.deleteMetaInfo(metaInfo);
        }

        Session session = getCurrentSession();
        session.createQuery(sb.toString()).

                executeUpdate();

        session.delete(typifiedObject);

        closeSession();

    }


    private Session beforeAction() {
        return getCurrentSession();
    }


    private void afterAction() {
        closeSession();
    }


    protected Session getCurrentSession() {
        log.warn("getCurrentSession()");
        Session session = HibernateUtil.currentSession();
        log.warn("beginTransaction()");
        HibernateUtil.beginTransaction();
        return session;
    }


    protected void closeSession() {
        log.warn("commitTransaction()");
        HibernateUtil.commitTransaction();
        log.warn("closeSession()");
        HibernateUtil.closeSession();
    }

//    protected Session session() {
//        return getHibernateTemplate().getSessionFactory().session();
//    }

}