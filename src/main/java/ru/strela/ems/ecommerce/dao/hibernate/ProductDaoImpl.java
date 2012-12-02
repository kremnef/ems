/*
 * Eberom: a CRM web application Copyright (C) 2006 Luk Morbee
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */
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
import ru.strela.ems.ecommerce.dao.ProductDao;
import ru.strela.ems.ecommerce.model.Product;
import ru.strela.ems.security.dao.hibernate.CommonObjectDaoImpl;

import java.util.ArrayList;
import java.util.List;


public class ProductDaoImpl extends CommonObjectDaoImpl implements ProductDao {

   private final static Logger log = LoggerFactory.getLogger(ProductDaoImpl.class);
    public ProductDaoImpl() {
        super();
    }

    /*
     * (non-Javadoc)
     *
     * @see ru.strela.ems.core.dao.TemplateDao#getTemplate(java.lang.Integer)
     */


    public Product getProduct(Integer productId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        tx = session.beginTransaction();
        Product product = (Product) session.load(Product.class, productId);
        tx.commit(); session.close(); log.warn("closeSession");
        return product;
    }

    /*
     * (non-Javadoc)
     *
     * @see ru.strela.ems.ecommerce.dao.ProductDao#getProduct(java.lang.Integer, boolean)
     */

    public List getProducts() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        Criteria criteria = null;

        try {
            tx = session.beginTransaction();
            criteria = session.createCriteria(Product.class);

            tx.commit(); session.close(); log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }


        return criteria.list();
    }

    /*
     * (non-Javadoc)
     *
     * @see ru.strela.ems.ecommerce.dao.ProductDao#saveProduct(ru.strela.ems.core.model.Product)
     */

    public Product saveProduct(Product product) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(product);

            tx.commit(); session.close(); log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }
        return product;
    }

    /*
     * (non-Javadoc)
     *
     * @see ru.strela.ems.ecommerce.dao.ProductDao#deleteProduct(ru.strela.ems.core.model.Product)
     */

    public void deleteProduct(Product product) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            session.delete(product);
            tx.commit(); session.close(); log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }
    }

    /* (non-Javadoc)
     * @see ru.strela.ems.ecommerce.dao.ProductDao#findProducts(java.lang.String[])
     */

    public List findProducts(final String[] descriptions) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        Criterion criterion = null;
        Criteria criteria = null;

        try {
            tx = session.beginTransaction();

            criteria = session.createCriteria(Product.class);

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
            tx.commit(); session.close(); log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }
        if (criterion != null) {
            criteria.add(criterion);
            return criteria.list();
        } else {
            return new ArrayList();
        }

    }


}
