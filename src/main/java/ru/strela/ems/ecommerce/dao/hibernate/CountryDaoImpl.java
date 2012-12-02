package ru.strela.ems.ecommerce.dao.hibernate;


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
import ru.strela.ems.ecommerce.dao.CountryDao;
import ru.strela.ems.ecommerce.model.Country;

import java.util.ArrayList;
import java.util.List;


//public class LanguageDaoImpl extends EmsObjectDaoImpl implements LanguageDao {
public class CountryDaoImpl implements CountryDao {
     private final static Logger log = LoggerFactory.getLogger(CountryDaoImpl.class);

    public CountryDaoImpl() {
        super();
    }

    /*
     * (non-Javadoc)
     *
     * @see ru.strela.ems.ecommerce.dao.CountryDao#getName(java.lang.Integer)
     */

    public Country getCountry(int countryId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        Country country = null;
        try {
            tx = session.beginTransaction();
            country = (Country) session.load(Country.class, countryId);
            tx.commit();
            session.close(); log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }
        return country;
    }

    /*
     * (non-Javadoc)
     *
     * @see ru.strela.ems.ecommerce.dao.CountryDao#getCountrys(java.lang.Integer, boolean)
     */

    public List getCountries() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        Criteria criteria = null;
        List list = new ArrayList();
        try {
            tx = session.beginTransaction();
            criteria = session.createCriteria(Country.class);
            list = criteria.list();
            tx.commit();
            session.close(); log.warn("closeSession");

        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }
        return list;

    }

    public List getCountryNames() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        Criteria criteria = null;
        List list = new ArrayList();
        try {
            tx = session.beginTransaction();
            criteria = session.createCriteria(Country.class);
            list = criteria.list();
            tx.commit();
            session.close(); log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }
        return list;

    }


    public Country getCountryByName(String countryName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        Country country = null;
        try {
            tx = session.beginTransaction();
            List list = session.createQuery("from Country where name = '" + countryName + "'").list();

            if (list.size() > 0) {
                country = (Country) list.get(0);
            }


            tx.commit();
            session.close(); log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }
        return country;
    }

    /*
    * (non-Javadoc)
    *
    * @see ru.strela.ems.ecommerce.dao.CountryDao#saveCountry(ru.strela.ems.core.model.Country)
    */

    public Country saveCountry(Country country) {
//        if (country.getIsDefaultCountry()) {
//            Session session = getCurrentSession();
//            session.createQuery("update Country l set l.isDefaultLang = 0").executeUpdate();
//        }


        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(country);

            tx.commit();
            session.close(); log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }
        return country;
    }

    /*
     * (non-Javadoc)
     *
     * @see ru.strela.ems.ecommerce.dao.CountryDao#deleteCountry(ru.strela.ems.core.model.Country)
     */

    public void deleteCountry(Country country) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            session.delete(country);
            tx.commit();
            session.close(); log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }
    }

    /* (non-Javadoc)
     * @see ru.strela.ems.ecommerce.dao.CountryDao#findCountrys(java.lang.String[])
     */

    public List findCountries(final String[] descriptions) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        Criterion criterion = null;
        Criteria criteria = session.createCriteria(Country.class);
        List list = new ArrayList();
        try {
            tx = session.beginTransaction();


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
                list = criteria.list();
            }
            tx.commit();
            session.close(); log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }
        return list;


    }


    public List<Country> getVisibleCountries() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        Criterion criterion = null;
        Criteria criteria = session.createCriteria(Country.class);
        List list = new ArrayList();
        try {
            tx = session.beginTransaction();
//                criteria.add(Restrictions.eq("isPublished", Boolean.TRUE));

            list = criteria.list();
            tx.commit();
            session.close(); log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }
        return list;
    }


}
