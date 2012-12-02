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
package ru.strela.ems.core.dao.hibernate;


import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.strela.ems.core.dao.ObjectTypeActionDao;
import ru.strela.ems.core.model.ObjectTypeAction;
import ru.strela.ems.security.dao.hibernate.CommonObjectDaoImpl;

import java.util.ArrayList;
import java.util.List;


public class ObjectTypeActionDaoImpl extends CommonObjectDaoImpl implements ObjectTypeActionDao {

    private final static Logger log = LoggerFactory.getLogger(ObjectTypeActionDaoImpl.class);
    public ObjectTypeActionDaoImpl() {
        super();
    }

    /*
     * (non-Javadoc)
     *
     * @see ru.strela.ems.core.dao.ObjectTypeActionDao#getObjectTypeAction(java.lang.Integer)
     */

    public ObjectTypeAction getObjectTypeAction(Integer objectTypeActionId) {
            Session currentSession = getCurrentSession();
        Criteria criteria = null;
        ObjectTypeAction objectTypeAction = null;
     /*   try {
            tx = session.beginTransaction();*/
            objectTypeAction = (ObjectTypeAction) currentSession.load(ObjectTypeAction.class, objectTypeActionId);
            /*tx.commit();
            session.close(); log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }*/
           closeSession();
        return objectTypeAction;
    }

    /*
     * (non-Javadoc)
     *
     * @see ru.strela.ems.core.dao.ObjectTypeActionDao#getObjectTypeActions(java.lang.Integer, boolean)
     */

    public List getObjectTypeActions() {
         int newSession = 0;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().openSession();
            log.warn("openSession");
            newSession = 1;
        }
        Transaction tx = null;
        Criteria criteria = null;
        List list = new ArrayList();
        try {
            tx = session.beginTransaction();
            criteria = session.createCriteria(ObjectTypeAction.class);
            list = criteria.list();
            tx.commit();
            if(newSession==1){
                session.close(); log.warn("closeSession");
            }
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }
        return list;
    }

    /*
     * (non-Javadoc)
     *
     * @see ru.strela.ems.core.dao.ObjectTypeActionDao#saveObjectTypeAction(ru.strela.ems.core.model.ObjectTypeAction)
     */

    public ObjectTypeAction saveObjectTypeAction(ObjectTypeAction objectTypeAction) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(objectTypeAction);

            tx.commit();
            session.close(); log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }
        return objectTypeAction;
    }

    /*
     * (non-Javadoc)
     *
     * @see ru.strela.ems.core.dao.ObjectTypeActionDao#deleteObjectTypeAction(ru.strela.ems.core.model.ObjectTypeAction)
     */

    public void deleteObjectTypeAction(ObjectTypeAction objectTypeAction) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(objectTypeAction);
            tx.commit();
            session.close(); log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }
    }

    /* (non-Javadoc)
    * @see ru.strela.ems.core.dao.ObjectTypeActionDao#findObjectTypeActions(java.lang.String[])
    */

    public List findObjectTypeActions(final String[] descriptions) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        Criteria criteria = null;
        Criterion criterion = null;
        List list = new ArrayList();
        try {
            tx = session.beginTransaction();
            criteria = session.createCriteria(ObjectTypeAction.class);

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
}
