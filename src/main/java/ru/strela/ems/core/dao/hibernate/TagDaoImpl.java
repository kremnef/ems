package ru.strela.ems.core.dao.hibernate;


import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.strela.ems.core.dao.TagDao;
import ru.strela.ems.core.model.Tag;
import ru.strela.ems.security.dao.hibernate.CommonObjectDaoImpl;

import java.util.ArrayList;
import java.util.List;

//import org.hibernate.criterion.Criterion;
//import org.hibernate.criterion.Restrictions;
//import org.hibernate.criterion.MatchMode;


//public class LanguageDaoImpl extends EmsObjectDaoImpl implements LanguageDao {
public class TagDaoImpl extends CommonObjectDaoImpl implements TagDao {
    private final static Logger log = LoggerFactory.getLogger(TagDaoImpl.class);

    public TagDaoImpl() {
        super();
    }

    /*
     * (non-Javadoc)
     *
     * @see ru.strela.ems.core.dao.TagDao#getName(java.lang.Integer)
     */

    public Tag getTag(int TagId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        Tag tag = null;
        try {
            tx = session.beginTransaction();
            tag = (Tag) session.load(Tag.class, TagId);
            tx.commit(); session.close(); log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }

        return tag;
    }

    /*
     * (non-Javadoc)
     *
     * @see ru.strela.ems.core.dao.TagDao#getTags(java.lang.Integer, boolean)
     */

    /*public List getTags() {
        return getHibernateTemplate().executeFind(new HibernateCallback() {


            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(Tag.class);

                return criteria.list();
            }

        });
    }*/
/*
    public List getTagNames() {
        return getHibernateTemplate().executeFind(new HibernateCallback() {


            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(Tag.class);

                return criteria.list();
            }

        });
    }
*/


    /* public Tag getTagByName(String TagName) {
        List list = getHibernateTemplate().find("from Tag where tag = ?", TagName);
        Tag Tag = null;
        if (list.size() > 0) {
            Tag = (Tag) list.get(0);
        }
        return Tag;
    }*/

    /*
     * (non-Javadoc)
     *
     * @see ru.strela.ems.core.dao.TagDao#saveTag(ru.strela.ems.core.model.Tag)
     */

    public Tag saveTag(Tag tag) {
//        if (Tag.getIsDefaultTag()) {
//            Session session = getCurrentSession();
//            session.createQuery("update Tag l set l.isDefaultLang = 0").executeUpdate();
//        }
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(tag);

            tx.commit(); session.close(); log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }
        return tag;
    }

    /*
     * (non-Javadoc)
     *
     * @see ru.strela.ems.core.dao.TagDao#deleteTag(ru.strela.ems.core.model.Tag)
     */

    public void deleteTag(Tag Tag) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.delete(Tag);
            tx.commit(); session.close(); log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }
    }


    public List<Tag> getTags() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        Criteria criteria = null;
        List list = new ArrayList();
        try {
            tx = session.beginTransaction();
            criteria = session.createCriteria(Tag.class);
//                criteria.add(Restrictions.eq("isPublished", Boolean.TRUE));

            list= criteria.list();
            tx.commit(); session.close(); log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }
        return  list;
    }


}
