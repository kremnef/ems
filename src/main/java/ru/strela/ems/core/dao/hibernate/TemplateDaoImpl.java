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
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.strela.ems.core.dao.TemplateDao;
import ru.strela.ems.core.model.Template;
import ru.strela.ems.security.dao.hibernate.CommonObjectDaoImpl;

import java.util.ArrayList;
import java.util.List;


public class TemplateDaoImpl extends CommonObjectDaoImpl implements TemplateDao {

    private final static Logger log = LoggerFactory.getLogger(TemplateDaoImpl.class);
    public TemplateDaoImpl() {
        super();
    }

    /*
     * (non-Javadoc)
     *
     * @see ru.strela.ems.core.dao.TemplateDao#getTemplate(java.lang.Integer)
     */

    public Template getTemplate(Integer templateId) {
        Session session = getCurrentSession();
        /*log.warn("openSession");
        Transaction tx = null;*/
//        Criteria criteria = null;
        Template template;
//        try {
//            tx = session.beginTransaction();
            template = (Template) session.load(Template.class, templateId);
          /*  tx.commit();
            session.close(); log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }*/
            closeSession();
        return template;
    }

    /*
     * (non-Javadoc)
     *
     * @see ru.strela.ems.core.dao.TemplateDao#getTemplates(java.lang.Integer, boolean)
     */

    public List getTemplates() {
//         int newSession = 0;
        Session session = getCurrentSession();
        /*if (!session.isOpen()) {
            session = HibernateUtil.getSessionFactory().openSession();
            log.warn("openSession");
            newSession = 1;
        }
        Transaction tx = null;*/
        Criteria criteria;
        List list;
        /*try {
            tx = session.beginTransaction();*/
            criteria = session.createCriteria(Template.class);
            list = criteria.list();
            /*tx.commit();
            if(newSession==1){
                session.close(); log.warn("closeSession");
            }
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }*/
        closeSession();
        return list;
    }

    /*
     * (non-Javadoc)
     *
     * @see ru.strela.ems.core.dao.TemplateDao#saveTemplate(ru.strela.ems.core.model.Template)
     */

    public Template saveTemplate(Template template) {
        Session session =getCurrentSession();
        /*log.warn("openSession");
        Transaction tx = null;

        try {
            tx = session.beginTransaction();*/
            session.saveOrUpdate(template);

            /*tx.commit();
            session.close(); log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }*/
        closeSession();
        return template;
    }

    /*
     * (non-Javadoc)
     *
     * @see ru.strela.ems.core.dao.TemplateDao#deleteTemplate(ru.strela.ems.core.model.Template)
     */

    public void deleteTemplate(Template template) {
        Session session = getCurrentSession();
        /*log.warn("openSession");
        Transaction tx = null;
        try {
            tx = session.beginTransaction();*/
            session.delete(template);
            /*tx.commit();
            session.close(); log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }*/
        closeSession();
    }

    /* (non-Javadoc)
    * @see ru.strela.ems.core.dao.TemplateDao#findTemplates(java.lang.String[])
    */

    public List findTemplates(final String[] descriptions) {
        Session session = getCurrentSession();
        /*log.warn("openSession");
        Transaction tx = null;*/
        Criteria criteria;
        Criterion criterion = null;
        List list = new ArrayList();
        /*try {
            tx = session.beginTransaction();*/
            criteria = session.createCriteria(Template.class);

        for (String description1 : descriptions) {
            String description = description1.trim();
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

            /*tx.commit();
            session.close(); log.warn("closeSession");


        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }
*/
        closeSession();
        return list;
    }
}
