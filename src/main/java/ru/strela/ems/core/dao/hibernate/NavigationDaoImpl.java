package ru.strela.ems.core.dao.hibernate;
//chenged

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.strela.ems.core.dao.NavigationDao;
import ru.strela.ems.core.model.Navigation;
import ru.strela.ems.core.model.SystemNode;
import ru.strela.ems.core.model.TypifiedObject;
import ru.tastika.tools.util.Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


public class NavigationDaoImpl extends SystemObjectDaoImpl implements NavigationDao {


    private final static Logger log = LoggerFactory.getLogger(NavigationDaoImpl.class);


    public NavigationDaoImpl() {
        super();
    }


    @Override
    public Class getEntityClass() {
        return Navigation.class;
    }


    private SystemNode getAttachedSystemNode(int systemNodeId) {
        return (SystemNode) getTypifiedObject(SystemNode.class, systemNodeId);
    }


    public Navigation getNavigation(int navigationId) {

          /*if (navigation.getSystemNodeId() > 0) {
            navigation.getSystemNode();
        }*/
        return (Navigation) getTypifiedObject(navigationId);
    }


    public Navigation getNavigation(int navigationId, boolean withFirstParent) {
        /*if (navigation.getSystemNodeId() != null && navigation.getSystemNodeId() > 0) {
            navigation.setSystemNode((SystemNode) getTypifiedObject(SystemNode.class, navigation.getSystemNodeId()));
        }*/
        /*if (navigation.getSystemNodeId() != null && navigation.getSystemNodeId() > 0) {
            navigation.setSystemNode((SystemNode) getTypifiedObject(SystemNode.class, navigation.getSystemNodeId()));
        }*/
        return (Navigation) super.getTypifiedObject(navigationId, withFirstParent);
    }


    @Override
    public TypifiedObject getTypifiedObject(int id, boolean withFirstParent) {
        return getNavigation(id, withFirstParent);
    }


    public List getNavigations() {
        return getObjects();
    }


    @Override
    public List<TypifiedObject> getChildren(int parentId) {
        return this.getChildren(parentId, 0, 0, "", false);
    }


    public List<TypifiedObject> getChildren(int parentId, int start, int quantity, String sortName, boolean desc) {
        return getNavigations(parentId, start, quantity, sortName, desc);
    }


    @Override
    protected TypifiedObject saveObject(TypifiedObject typifiedObject) {
        Navigation navigation = (Navigation) typifiedObject;
        Session currentSession = getCurrentSession();
        Integer systemNodeId = navigation.getSystemNodeId();
        super.saveObject(typifiedObject);
        if (systemNodeId != null && !systemNodeId.equals(navigation.getSystemNodeId())) {
            navigation.setSystemNodeId(systemNodeId);
            currentSession.saveOrUpdate(navigation);
        }
        closeSession();
        return navigation;
    }


    @Override
    protected void deleteObject(TypifiedObject typifiedObject) {
        /*if (typifiedObject instanceof Navigation) {
            Navigation navigation = (Navigation) typifiedObject;
            int navigationId = navigation.getId();
            StringBuilder sb = new StringBuilder("update TypifiedObject");
            sb.append(navigationId);
            Session currentSession = getCurrentSession();
            currentSession.createQuery(sb.toString()).executeUpdate();
            closeSession();
        }*/
        super.deleteObject(typifiedObject);
    }


    public List getNavigations(int parentId, int start, int quantity, String sortName, boolean desc) {
        List<TypifiedObject> children = super.getChildren(parentId, start, quantity, sortName, desc);
        HashMap<Integer, Navigation> navigationSystemNodeIds = new HashMap<Integer, Navigation>();
        for (TypifiedObject typifiedObject : children) {
            Navigation navigation = (Navigation) typifiedObject;
            if (navigation.getSystemNodeId() != null && navigation.getSystemNodeId() > 0) {
                navigationSystemNodeIds.put(navigation.getSystemNodeId(), navigation);
            } else {
                navigation.setSystemNodeId(0);
            }
        }
        /*if (navigationSystemNodeIds.size() > 0) {
            List<TypifiedObject> typifiedObjects = getTypifiedObjects(SystemNode.class, navigationSystemNodeIds.keySet());
            for (TypifiedObject typifiedObject : typifiedObjects) {
                SystemNode systemNode = (SystemNode) typifiedObject;
                Navigation navigation = navigationSystemNodeIds.get(systemNode.getId());
                navigation.setSystemNode(systemNode);
                SystemNode node = navigation.getSystemNode();
            }
        }*/
        return children;
    }


    public List findNavigations(final String[] descriptions) {
        Session session = getCurrentSession();
        /*log.warn("openSession");
        Transaction tx = null;*/
        Criteria criteria = session.createCriteria(Navigation.class);
        Criterion criterion = null;
//        List list = new ArrayList();
        /*try {
            tx = session.beginTransaction();
*/
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
        List list = criteria.list();
            /*tx.commit();
            session.close();
            log.warn("closeSession");


        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }*/
        closeSession();
        if (criterion != null) {
            criteria.add(criterion);

            return list;
        } else {
            return new ArrayList();
        }
    }


    public List getUsedPages() {
        Session session = getCurrentSession();
        log.warn("getUsedPages");
        String sql = "select systemNodeId from Navigation f where f.systemNodeId is not null";

//        List list = session.createQuery(sql.toString()).list();
        List list = session.createQuery(sql).list();
        closeSession();
        return list;
    }

    public String getPathURL(Integer parentId) {
        String navigationURL = null;
        if (parentId != null) {
            navigationURL = getNavigation(parentId).getSystemName();
        }
        log.warn("navigationURL" + navigationURL);
        return navigationURL;
    }

    public String getConcatURL(Integer id) {
        Integer parentId = id;
        String concatUrl = "/";
        List UrlList = null;
        while (parentId > 0) {
            Navigation navigation = getNavigation(id);
            String navigationURL = navigation.getSystemName();
            UrlList.add(navigationURL);
            log.warn("URL =" + navigationURL);
            parentId = navigation.getEmsObject().getParentId();
//           concatUrl.append("navigationURL");
/*
        for (int i = 0; !(parentId <= 0 || parentId == null); i++) {
            Navigation navigation = getNavigation(id);
            String navigationURL = navigation.getSystemName();
            UrlList.add(navigationURL);
            parentId = navigation.getParentId();

                }
            }
*/
        }

        return concatUrl.toString();
    }


    public HashMap<Integer, String> getSystemNodeUrls(HashSet<Integer> systemNodeIdsForUrls) {
        HashMap<Integer, String> systemNodeUrls = new HashMap<Integer, String>();
        if (systemNodeIdsForUrls.size() > 0) {
            Session session = getCurrentSession();
            String sql = "select id, fullURL from SystemNode where id in (" + Utilities.implode(systemNodeIdsForUrls, ",") + ")";
            List list = session.createQuery(sql).list();
            for (Object rowObj : list) {
                Object[] row = (Object[]) rowObj;
                systemNodeUrls.put((Integer) row[0], (String) row[1]);
            }
            closeSession();
        }
        return systemNodeUrls;

    }

}
