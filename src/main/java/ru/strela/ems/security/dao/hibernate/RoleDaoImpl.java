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
import ru.strela.ems.security.dao.RoleDao;
import ru.strela.ems.security.model.Role;

import java.util.ArrayList;
import java.util.List;

//import ru.strela.ems.core.model.Role;
//import ru.strela.ems.core.model.RoleRole;


//public class RoleDaoImpl extends TypifiedObjectDaoImpl implements RoleDao {
public class RoleDaoImpl extends CommonObjectDaoImpl implements RoleDao {
    private final static Logger log = LoggerFactory.getLogger(RoleDaoImpl.class);

    public RoleDaoImpl() {
        super();
    }


    public Class getEntityClass() {
        return Role.class;
    }


    /*public Role getRole(Integer roleId) {
        Role role = (Role) getHibernateTemplate().get(Role.class, roleId);
        return role;
    }


     public int getIdByLogin(String login) {
        List objects = getHibernateTemplate().find("select eo.id from Role eo where eo.login = ?", login);
        if (objects.size() > 0) {
            return (Integer) objects.get(0);
        }
        else {
            return 0;
        }
    }*/


    public Role getRole(String name) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        tx = session.beginTransaction();
        List list = session.createQuery("from Role where roleName = '" + name + "'").list();

        tx.commit(); session.close(); log.warn("closeSession");
        if (list.size() > 0) {
            return (Role) list.get(0);
        } else {
            return null;
        }
    }


    public Role getRoleByName(String name) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        tx = session.beginTransaction();
        Role role = (Role) session.get(Role.class, name);
        tx.commit(); session.close(); log.warn("closeSession");
        return role;
    }

    public List<Role> getAllRoles() {
        //log.info("getAllRoles -1");
/*
        return getHibernateTemplate().executeFind(new HibernateCallback() {


            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(Role.class);
                return criteria.list();

            }

        });

*/

        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        Criteria criteria;
        List<Role> roles;
        try {
            tx = session.beginTransaction();
            //log.info("getAllRoles -2");
            criteria = session.createCriteria(Role.class);
            roles = criteria.list();
            tx.commit(); session.close(); log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }
        //log.info("getAllRoles -3");
        return roles;

    }


    public List getRoles() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        List list = new ArrayList();
        tx = session.beginTransaction();
        list = session.createCriteria(RoleVoter.class).list();
        tx.commit(); session.close(); log.warn("closeSession");
        return list;
//        return getHibernateTemplate().loadAll(RoleVoter.class);
    }


    public void deleteRole(Role role) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        tx = session.beginTransaction();
        session.delete(role);
        tx.commit(); session.close(); log.warn("closeSession");

    }


    public List findRoles(final String[] descriptions) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        List list  = new ArrayList();
        tx = session.beginTransaction();
        Criteria criteria = session.createCriteria(Role.class);
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
        tx.commit(); session.close(); log.warn("closeSession");
         /*else {
            return new ArrayList();
        }*/

       return list;

    }


}
