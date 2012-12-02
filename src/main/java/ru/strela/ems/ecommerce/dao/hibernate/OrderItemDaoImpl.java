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
import ru.strela.ems.ecommerce.dao.OrderItemDao;
import ru.strela.ems.ecommerce.model.OrderItem;

import java.util.ArrayList;
import java.util.List;


public class OrderItemDaoImpl implements OrderItemDao {
    private final static Logger log = LoggerFactory.getLogger(OrderItemDaoImpl.class);

    public OrderItemDaoImpl() {
        super();
    }

    /*
     * (non-Javadoc)
     *
     * @see ru.strela.ems.core.dao.TemplateDao#getTemplate(java.lang.Integer)
     */

    public OrderItem getOrderItem(Integer orderItemId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        OrderItem orderItem = null;
        try {
            tx = session.beginTransaction();
            orderItem = (OrderItem) session.load(OrderItem.class, orderItemId);
            tx.commit(); session.close(); log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }
        return orderItem;
    }

    /*
     * (non-Javadoc)
     *
     * @see ru.strela.ems.ecommerce.dao.OrderItemDao#getOrderItem(java.lang.Integer, boolean)
     */

    public List getOrderItems() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        Criteria criteria = null;
        List list  = new ArrayList();
        try {
            tx = session.beginTransaction();
            criteria = session.createCriteria(OrderItem.class);
            list = criteria.list();
            tx.commit(); session.close(); log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }
        return list;

    }

    /*
     * (non-Javadoc)
     *
     * @see ru.strela.ems.ecommerce.dao.OrderItemDao#saveOrderItem(ru.strela.ems.core.model.OrderItem)
     */

    public OrderItem saveOrderItem(OrderItem orderItem) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(orderItem);

            tx.commit(); session.close(); log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }
        return orderItem;
    }

    /*
     * (non-Javadoc)
     *
     * @see ru.strela.ems.ecommerce.dao.OrderItemDao#deleteOrderItem(ru.strela.ems.core.model.OrderItem)
     */

    public void deleteOrderItem(OrderItem orderItem) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            session.delete(orderItem);
            tx.commit(); session.close(); log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }
    }

    /* (non-Javadoc)
     * @see ru.strela.ems.ecommerce.dao.OrderItemDao#findOrderItems(java.lang.String[])
     */

    public List findOrderItems(final String[] descriptions) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        Criterion criterion = null;
        Criteria criteria = null;
        List list  = new ArrayList();
        try {
            tx = session.beginTransaction();
            criteria = session.createCriteria(OrderItem.class);

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
            tx.commit(); session.close(); log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }
        /* else {
            return new ArrayList();
        }*/
        return list;

    }

}
