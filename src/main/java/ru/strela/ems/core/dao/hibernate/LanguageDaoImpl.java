package ru.strela.ems.core.dao.hibernate;


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
import ru.strela.ems.core.dao.LanguageDao;
import ru.strela.ems.core.model.Language;
import ru.strela.ems.core.model.TypifiedObject;
import ru.strela.ems.tools.ServerTools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


//public class LanguageDaoImpl extends EmsObjectDaoImpl implements LanguageDao {
public class LanguageDaoImpl extends TypifiedObjectDaoImpl implements LanguageDao {


    private final static Logger log = LoggerFactory.getLogger(LanguageDaoImpl.class);


    public LanguageDaoImpl() {
        super();
    }


    public Class getEntityClass() {
        return Language.class;
    }


    public Language getLanguage(int id) {
        Session session = getCurrentSession();
        Language language = (Language) session.load(Language.class, id);
        log.warn("getLanguage" + language);
        closeSession();
        return language;
    }


    public Language getLanguageByName(String languageName) {
        Session session = getCurrentSession();
        Language language = null;
        List list = session.createQuery("from Language where name = '" + languageName + "'").list();
        if (list.size() > 0) {
            language = (Language) list.get(0);
        }
        closeSession();
        return language;
    }


    public Language getLanguageByCode(String languageCode) {
        Session session = getCurrentSession();
        Language language = null;
        List list = session.createQuery("from Language where code = '" + languageCode + "'").list();
        if (list.size() > 0) {
            language = (Language) list.get(0);
        }
        closeSession();
        return language;
    }


    public List<Language> getVisibleLanguages() {
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(Language.class);
        criteria.add(Restrictions.eq("isPublished", Boolean.TRUE));
        List list = criteria.list();
        closeSession();
        return list;
    }


    public List getLanguages() {
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(Language.class);
        List list = criteria.list();
        closeSession();
        return list;
    }


    @Override
    protected TypifiedObject saveObject(TypifiedObject typifiedObject) {
        Session session = getCurrentSession();
        Language language = (Language) typifiedObject;
        if (language.getIsDefaultLang()) {
            session.createQuery("update Language l set l.isDefaultLang = 0").executeUpdate();
        }
        super.saveObject(language);
        closeSession();
        return language;
    }


    public void deleteLanguage(Language language) {
        super.delete(language);
    }


    public List findLanguages(final String[] descriptions) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        Criteria criteria = session.createCriteria(Language.class);
        Criterion criterion = null;
        List list = new ArrayList();
        try {
            tx = session.beginTransaction();

            for (int i = 0; i < descriptions.length; i++) {
                String description = descriptions[i].trim();
                if (description.length() > 0) {
                    if (criterion == null) {
                        criterion = Restrictions.like("description", description, MatchMode.ANYWHERE);
                    }
                    else {
                        criterion = Restrictions.or(criterion, Restrictions.like("description", description, MatchMode.ANYWHERE));
                    }
                    criterion = Restrictions.or(criterion, Restrictions.like("code", description, MatchMode.ANYWHERE));
                }
            }
            list = criteria.list();
            tx.commit();
            session.close(); log.warn("closeSession");

        }
        catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
            throw he;
        }
        if (criterion != null) {
            criteria.add(criterion);

            return list;
        }
        else {
            return new ArrayList();
        }

    }


    public void showLanguages(Map model, Object obj, String src) {
        Request request = ObjectModelHelper.getRequest(model);
        ServerTools.updateLocaleFromParameter(request);
    }

}
