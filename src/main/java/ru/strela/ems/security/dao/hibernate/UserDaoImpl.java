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
package ru.strela.ems.security.dao.hibernate;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.core.userdetails.User;
import ru.strela.ems.core.dao.hibernate.HibernateUtil;
import ru.strela.ems.security.dao.UserDao;

import java.util.ArrayList;
import java.util.List;

//import ru.strela.ems.core.model.User;
//import ru.strela.ems.core.model.Role;


//public class UserDaoImpl extends TypifiedObjectDaoImpl implements UserDao {
public class UserDaoImpl extends CommonObjectDaoImpl implements UserDao {
    private final static Logger log = LoggerFactory.getLogger(UserDaoImpl.class);

    public UserDaoImpl() {
        super();
    }


    public Class getEntityClass() {
        return User.class;
    }


    /*public User getUser(Integer userId) {
        User user = (User) getHibernateTemplate().get(User.class, userId);
        return user;
    }


     public int getIdByLogin(String login) {
        List objects = getHibernateTemplate().find("select eo.id from User eo where eo.login = ?", login);
        if (objects.size() > 0) {
            return (Integer) objects.get(0);
        }
        else {
            return 0;
        }
    }*/


    public User getUser(String name) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        tx = session.beginTransaction();
        List list = session.createQuery("from User where username = '" + name + "'").list();
        tx.commit();
        session.close(); log.warn("closeSession");

        if (list.size() > 0) {
            return (User) list.get(0);
        } else {
            return null;
        }
    }


    public User getUserByName(String name) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        tx = session.beginTransaction();
        User user = (User) session.get(User.class, name);
        tx.commit();
        session.close(); log.warn("closeSession");


        return user;
    }

    public List getGroups() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        List list = new ArrayList();
        tx = session.beginTransaction();


        Criteria criteria = session.createCriteria(User.class);
        criteria.addOrder(Order.asc("name"));
        list = criteria.list();
        tx.commit();
        session.close(); log.warn("closeSession");

        return list;

    }


    public List getRoles() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        List list = new ArrayList();
        tx = session.beginTransaction();


        Criteria criteria = session.createCriteria(RoleVoter.class);
        list = criteria.list();
        tx.commit();
        session.close(); log.warn("closeSession");
        return list;

    }


    public List getUsers() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        List list = new ArrayList();
        tx = session.beginTransaction();


        Criteria criteria = session.createCriteria(User.class);
        criteria.addOrder(Order.asc("name"));
        list = criteria.list();
        tx.commit();
        session.close(); log.warn("closeSession");
        return list;
    }


    public void deleteUser(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        tx = session.beginTransaction();


        session.delete(user);
        tx.commit();
        session.close(); log.warn("closeSession");
    }


    public List findUsers(final String[] descriptions) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        List list = new ArrayList();
        tx = session.beginTransaction();
        Criteria criteria = session.createCriteria(User.class);
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
            list = criteria.list();
        }
        tx.commit();
        session.close(); log.warn("closeSession");
        /* else {
            return new ArrayList();
        }*/
        return list;
    }


}
