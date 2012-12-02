package ru.strela.ems.actions;


import org.apache.avalon.framework.parameters.Parameters;
import org.apache.cocoon.acting.AbstractAction;
import org.apache.cocoon.environment.Redirector;
import org.apache.cocoon.environment.SourceResolver;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.strela.ems.core.dao.hibernate.HibernateUtil;

import java.util.HashMap;
import java.util.Map;


/**
 * User: hobal
 * Date: 16.09.2010
 * Time: 2:32:40
 */
public class ReindexAction extends AbstractAction {


    private final static Logger log = LoggerFactory.getLogger(ReindexAction.class);


    public Map act(Redirector redirector,
                   SourceResolver resolver,
                   Map objectModel,
                   String source,
                   Parameters params) {


//        Object startPageSystemName = ServerTools.getGlobalParameter("startPage");
//
//        WebApplicationContext context = ServerTools.getWebApplicationContext();
//        NavigationDao navigationDao = (NavigationDao) context.getBean("navigationDao");
//
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
//
//        try {
//            tx = session.beginTransaction();
//            TypifiedObject indexNavigation = navigationDao.getTypifiedObjectBySystemName(startPageSystemName.toString());
//            if (indexNavigation != null) {
//                navigationDao.indexSiteMap(indexNavigation);
//            }
//            tx.commit();
//        }
//        catch (HibernateException he) {
//            if (tx != null) {
//                tx.rollback();
//            }
//            throw he;
//        }
//        indexer
        try {
            tx = session.beginTransaction();
            FullTextSession fullTextSession = Search.getFullTextSession(session);
            fullTextSession.createIndexer().startAndWait();

            tx.commit();
            session.close(); log.warn("closeSession");
        }
        catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
            throw he;
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new HashMap();
    }

}