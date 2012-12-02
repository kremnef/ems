package ru.strela.ems.ecommerce.dao.hibernate;


import org.apache.cocoon.environment.ObjectModelHelper;
import org.apache.cocoon.environment.Request;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.strela.ems.core.dao.hibernate.HibernateUtil;
import ru.strela.ems.core.dao.hibernate.TypifiedObjectDaoImpl;
import ru.strela.ems.ecommerce.dao.PhotoDao;
import ru.strela.ems.ecommerce.model.Photo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


//public class CustomerDaoImpl extends EmsObjectDaoImpl implements CustomerDao {
public class PhotoDaoImpl extends TypifiedObjectDaoImpl implements PhotoDao {

    private final static Logger log = LoggerFactory.getLogger(PhotoDaoImpl.class);

    public PhotoDaoImpl() {
        super();
    }


    public Class getEntityClass() {
        return Photo.class;
    }


    public Photo getPhoto(int id) {
        return (Photo) getTypifiedObject(id);
        /*Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Photo photo = (Photo) session.load(Photo.class, id);
        return photo;*/
    }


    public Photo getPhotoByName(String photoName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        Criteria criteria = null;
        List list = new ArrayList();
        Photo photo = null;
        try {
            tx = session.beginTransaction();
            list = session.createQuery("from Photo where name = '" + photoName + "'").list();
            tx.commit();
            session.close();
            log.warn("closeSession");

            if (list.size() > 0) {
                photo = (Photo) list.get(0);
            }

        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }


        return photo;
    }


    public List getPhotos() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Criteria criteria = session.createCriteria(Photo.class);

        return criteria.list();
    }


    public void deletePhoto(Photo photo) {
        super.delete(photo);
    }


    public List findPhotos(final String[] descriptions) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Criteria criteria = session.createCriteria(Photo.class);
        Criterion criterion = null;
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
            return criteria.list();
        } else {
            return new ArrayList();
        }

    }


    public void showPhotos(Map model, Object obj) {
        Request request = ObjectModelHelper.getRequest(model);
//        checkLocale(request);
    }


}
