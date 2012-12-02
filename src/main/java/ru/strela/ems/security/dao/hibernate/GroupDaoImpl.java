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
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.vote.RoleVoter;
import ru.strela.ems.core.dao.hibernate.HibernateUtil;
import ru.strela.ems.security.dao.GroupDao;
import ru.strela.ems.security.model.Group;

import java.util.ArrayList;
import java.util.List;

//import ru.strela.ems.core.model.Group;
//import ru.strela.ems.core.model.GroupRole;


//public class GroupDaoImpl extends TypifiedObjectDaoImpl implements GroupDao {
public class GroupDaoImpl extends CommonObjectDaoImpl implements GroupDao {

    private final static Logger log = LoggerFactory.getLogger(GroupDaoImpl.class);

    public GroupDaoImpl() {
        super();
    }


    public Class getEntityClass() {
        return Group.class;
    }


    /*public Group getGroup(Integer groupId) {
        Group group = (Group) getHibernateTemplate().get(Group.class, groupId);
        return group;
    }


     public int getIdByLogin(String login) {
        List objects = getHibernateTemplate().find("select eo.id from Group eo where eo.login = ?", login);
        if (objects.size() > 0) {
            return (Integer) objects.get(0);
        }
        else {
            return 0;
        }
    }*/


    public Group getGroup(String name) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        List<Group> groups;
        try {
            tx = session.beginTransaction();
            groups = session.createQuery("from Group where groupname ='" + name + "'").list();
            tx.commit(); session.close(); log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }


        if (groups.size() > 0) {
            return groups.get(0);
        } else {
            return null;
        }

    }


    public Group getGroupByName(String name) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        Group group = null;
        try {
            tx = session.beginTransaction();
            group = (Group) session.get(Group.class, name);
            tx.commit(); session.close(); log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }


        return group;
    }

    public List<Group> getAllGroups() {
        //log.info("getAllGroups -1");
/*
        return getHibernateTemplate().executeFind(new HibernateCallback() {


            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(Group.class);
                return criteria.list();

            }

        });

*/

        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        Criteria criteria;
        List<Group> groups;
        try {
            tx = session.beginTransaction();
            //log.info("getAllGroups -2");
            criteria = session.createCriteria(Group.class);
            groups = criteria.list();
            tx.commit(); session.close(); log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }
        //log.info("getAllGroups -3");
        return groups;

    }


    public List getRoles() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        Criteria criteria;
        List<Group> groups;
        try {
            tx = session.beginTransaction();
            //log.info("getAllGroups -2");
            criteria = session.createCriteria(RoleVoter.class);
            groups = criteria.list();
            tx.commit(); session.close(); log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }
        //log.info("getAllGroups -3");
        return groups;
//        return getHibernateTemplate().loadAll(RoleVoter.class);
    }


    public void deleteGroup(Group group) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.delete(group);
            tx.commit(); session.close(); log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }
    }


    public List findGroups(final String[] descriptions) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        Criteria criteria;
        Criterion criterion = null;
        List list  = new ArrayList();
        try {
            tx = session.beginTransaction();
            criteria = session.createCriteria(Group.class);

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

         return list;
    }


}
