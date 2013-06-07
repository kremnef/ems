package ru.strela.ems.core.dao.hibernate;


import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.strela.ems.core.dao.SystemObjectDao;
import ru.strela.ems.core.dao.TypifiedObjectDao;
import ru.strela.ems.core.model.*;
import ru.tastika.tools.string.MD5Crypt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * User: hobal
 * Date: 03.08.2010
 * Time: 17:32:39
 */
public class SystemObjectDaoImpl extends TypifiedObjectDaoImpl implements SystemObjectDao {


    private final static Logger log = LoggerFactory.getLogger(SystemObjectDaoImpl.class);
    private static final int FREE_SYSTEM_NAME_LENGTH = 6;
//    private HashSet<SystemNodeNavigationKey> systemNodeNavigationKeys = new HashSet<SystemNodeNavigationKey>();
//    private HashSet<CommonObjectId> siteMapCommonObjects = new HashSet<CommonObjectId>();
//    private ObjectTypeDao objectTypeDao;
//    private HashSet<CommonObjectId> parentPathIds = new HashSet<CommonObjectId>();





    // todo: проверить не перекрывается ли метод
    public TypifiedObject getTypifiedObject(int id, boolean withFirstParent) {
        TypifiedObject typifiedObject = getTypifiedObject(id);
        if (withFirstParent && typifiedObject != null && typifiedObject instanceof SystemObject) {
            SystemObject systemObject = (SystemObject) typifiedObject;
            Integer parentId = systemObject.getEmsObject().getParentId();
            if (parentId != null && parentId > 0) {
                Class entityClass = getEntityClass();
                if (entityClass.equals(FileObject.class)) {
                    entityClass = Folder.class;
                }
                TypifiedObject parentObject = getTypifiedObject(entityClass, parentId);
                systemObject.getEmsObject().setParent(parentObject);
            }
        }
        return typifiedObject;
    }


    public int getChildrenCount(int id) {
        Session session = getCurrentSession();
        StringBuilder sql = new StringBuilder("select count(*) from EmsObject eo where eo.parentId ");
        if (id > 0) {
            sql.append("=").append(id);
        } else {
            sql.append("is null");
        }
        if (id == 0 && !getEntityClass().equals(TypifiedObject.class)) {
            sql.append(" and eo.entity = '");
            sql.append(getEntityClass().getSimpleName());
            sql.append("'");
        }
        int count = ((Long) session.createQuery(sql.toString()).iterate().next()).intValue();
        closeSession();
        return count;
    }


    public List<TypifiedObject> getChildren(final int parentId) {
        Session session = getCurrentSession();
        String sql = "from " + getEntityClass().getSimpleName() + " o where o.emsObject.parentId " + (parentId > 0 ? (" = " + parentId) : " is null order by o.position");
        Query query = session.createQuery(sql);
        List<TypifiedObject> list = query.list();
        closeSession();
        return list;


    }


    public List<TypifiedObject> getChildren(final int parentId, final int start, final int quantity, final String sortName, final boolean desc) {
        Session session = getCurrentSession();
        String sortBy = sortName.length() > 0 ? sortName : "position";
        int objectsCount = quantity == 0 ? Integer.MAX_VALUE : quantity;
        StringBuilder sb = new StringBuilder("from ");

        sb.append(getEntityClass().getSimpleName());
        sb.append(" o where o.emsObject.parentId ");
        if (parentId > 0) {
            sb.append(" = ");
            sb.append(parentId);
        } else {
            sb.append(" is null");
        }
        sb.append(" order by ");
        sb.append(sortBy);
        sb.append(desc ? " desc" : " asc");
//                sb.append(" limit ");
//                sb.append(start);
//                sb.append(",");
//                sb.append(objectsCount);
        Query query = session.createQuery(sb.toString());
        query.setFirstResult(start);
        query.setMaxResults(objectsCount);
        List list = query.list();
        closeSession();
        return list;

    }


    public List<TypifiedObject> getChildren(final int parentId, final int start, final int quantity, final String sortName, final boolean desc, final Filter filter) {
        Session session = getCurrentSession();
        String sortBy = sortName.length() > 0 ? sortName : "position";
        int objectsCount = quantity == 0 ? Integer.MAX_VALUE : quantity;
        StringBuilder sb = new StringBuilder("from ");

        sb.append(getEntityClass().getSimpleName());
        sb.append(" o where o.emsObject.parentId ");
        if (parentId > 0) {
            sb.append(" = ");
            sb.append(parentId);
        } else {
            sb.append(" is null");
        }
        sb.append(" order by ");
        sb.append(sortBy);
        sb.append(desc ? " desc" : " asc");

        /*if (filter.getEntity() == "FileObject") {
            sb.append(" and fo.").append(filter.getField()).append("=");
            sb.append(filterValue);
            log.warn("SQL" + sb);
        }*/
        Query query = session.createQuery(sb.toString());
        query.setFirstResult(start);
        query.setMaxResults(objectsCount);
        List list = query.list();
        closeSession();
        return list;

    }


    public List<TypifiedObject> getChildren(final SystemObject parent) {
        return getChildren(parent, 1);
    }


    public List<TypifiedObject> getChildren(final SystemObject parent, final int levels) {
        Session session = getCurrentSession();
        List list;
        if (levels > 0) {
            String sql = "from " + parent.getClass().getSimpleName() + " o where o.emsObject.parentId = " + parent.getId() + " order by o.position";
            Query query = session.createQuery(sql);
            list = query.list();
        } else {
            list = Collections.emptyList();
        }
        closeSession();
        return list;
    }


    public TypifiedObject getTypifiedObjectBySystemName(String systemName) {
        Session session = getCurrentSession();
        List objects;
        TypifiedObject t = null;
        objects = session.createQuery("from " + getEntityClass().getSimpleName() + " where emsObject.systemName = '" + systemName + "'").list();

        if (objects.size() > 0) {
            t = (TypifiedObject) objects.get(0);
        }
        closeSession();
        return t;
    }


    public int getIdBySystemName(String systemName) {
        Session session = getCurrentSession();
        List objects;
        Integer i = 0;
        objects = session.createQuery("select id from EmsObject where systemName = '" + systemName + "'").list();
        if (objects.size() > 0) {
            i = (Integer) objects.get(0);
        }
        closeSession();
        return i;

    }


    public int getIdBySystemName(String systemName, int parentId, String entity) {
        Session session = getCurrentSession();
        List objects;
        Integer i = 0;
        StringBuilder sql = new StringBuilder("select id from EmsObject where systemName = '").append(systemName).append("' and parentId ").append(parentId > 0 ? (" = " + parentId) : "is null");
        if (entity != null && entity.length() > 0) {
            sql.append(" and entity = '").append(entity).append("'");
        }
        objects = session.createQuery(sql.toString()).list();
        if (objects.size() > 0) {
            i = (Integer) objects.get(0);
        }
        closeSession();
        return i;
    }


    private static byte[] longToBytes(long longNumber) {
        byte[] bytes = new byte[8];
        for (int i = bytes.length - 1; 0 <= i; i--) {
            bytes[i] = (byte) longNumber;
            longNumber = longNumber >> 8;
        }
        return bytes;
    }


    public String getFreeSystemName() {
        String md5 = MD5Crypt.md5ToString16(longToBytes(System.currentTimeMillis())).substring(0, FREE_SYSTEM_NAME_LENGTH);
        TypifiedObject emsObjectBySystemName = getTypifiedObjectBySystemName(md5);
        while (emsObjectBySystemName != null) {
//            md5 = MD5Crypt.md5ToString16(longToBytes(System.currentTimeMillis())).substring(0, FREE_SYSTEM_NAME_LENGTH);
            emsObjectBySystemName = getTypifiedObjectBySystemName(md5);
        }
        return md5;
    }


    public List<TypifiedObject> getTypifiedObjectParents(int id, boolean includeTypifiedObject) {
        return getTypifiedObjectParents(getEntityClass(), id, includeTypifiedObject);
    }


    public List<TypifiedObject> getTypifiedObjectParents(Class entityClass, int id, boolean includeTypifiedObject) {
        Session session = getCurrentSession();
        ArrayList<TypifiedObject> parents = new ArrayList<TypifiedObject>();
        if (id > 0) {
            TypifiedObject typifiedObject = (TypifiedObject) session.get(entityClass, id);
            if (typifiedObject != null) {
                if (includeTypifiedObject) {
                    parents.add(typifiedObject);
                }
                if (typifiedObject instanceof SystemObject) {
                    SystemObject systemObject = (SystemObject) typifiedObject;
                    if (systemObject.getEmsObject().getParentId() != null && systemObject.getEmsObject().getParentId() > 0) {
                        int parentId = systemObject.getEmsObject().getParentId() != null ? systemObject.getEmsObject().getParentId() : 0;

                        TypifiedObjectDao typifiedObjectDao = new TypifiedObjectDaoImpl();
                        Object obj = typifiedObjectDao.getParent(parentId);
//                        Object obj = parentObject.getEmsObject().getParent();

//                        System.out.println("parentObject_CLASS:" + obj.getClass());
                        TypifiedObject parentObjectT = (TypifiedObject) obj;
//                        System.out.println("parentObject_Id:" + typ.getId());
//                        System.out.println("parentObject_Name:" + typ.getName());
//                        if (obj instanceof SystemObject) {
//                            while (parentId > 0 && (parentObject = (SystemObject) parentObject.getEmsObject().getParent()) != null && !parents.contains(parentObject)) {
                        while (parentId > 0 && (parentObjectT) != null && !parents.contains(parentObjectT)) {
//                                parents.add(0, (TypifiedObject) parentObject);
                            parents.add(0, parentObjectT);
                            parentId = systemObject.getEmsObject().getParentId() != null ? systemObject.getEmsObject().getParentId() : 0;
//                            }
                        }
                    }
                }
            }

        }
        closeSession();
        return parents;
    }


    @Override
    protected TypifiedObject saveObject(TypifiedObject typifiedObject) {
        Session session = getCurrentSession();
        EmsObject emsObject = ((SystemObject) typifiedObject).getEmsObject();
        emsObject.setEntity(typifiedObject.getClass().getSimpleName());
        String systemName = emsObject.getSystemName();
        boolean systemNameCorrect = systemName.length() > 0;
        if (systemNameCorrect) {
            int parentId = emsObject.getParentId() != null ? emsObject.getParentId() : 0;
            int idForSystemName = getIdBySystemName(systemName, parentId, emsObject.getEntity());
            systemNameCorrect = idForSystemName == 0 || idForSystemName == emsObject.getId();
        }
        if (!systemNameCorrect) {
            emsObject.setSystemName(getFreeSystemName());
        }

        super.saveObject(typifiedObject);
        emsObject.setObjectId(typifiedObject.getId());
        session.update(emsObject);
        session.flush();
        closeSession();
        return typifiedObject;
    }


//    private SiteMap getSiteMap(Navigation navigation) {
//
//        SiteMap navigationSiteMap = null;
//        if (!siteMapHomeIds.contains(navigation.getId())) {
//            siteMapHomeIds.add(navigation.getId());
//            log.warn("navigation = " + navigation.toExtendedString());
//            int systemNodeId = navigation.getSystemNodeId() != null ? navigation.getSystemNodeId() : 0;
//            log.warn("systemNodeId = " + systemNodeId);
//
//    //        SystemNode systemNode = navigation.getSystemNode();
//            //int redirectTo = navigation.getRedirectTo();
//            navigationSiteMap = getSiteMapByObjectId(navigation.getId());
//            if (navigationSiteMap == null) {
//                Session currentSession = getCurrentSession();
//                Object startPageSystemName = ServerTools.getGlobalParameter("startPage");
//                log.warn("startPageSystemName = " + startPageSystemName);
//                int homeId = navigation.getHomeId();
//                Integer parentId = navigation.getParentId();
//                SiteMap homeSiteMap = null;
//                String systemName = navigation.getSystemName();
//                if (systemName.equals(startPageSystemName)) {
//                    systemName = "";
//                }
//                else if (homeId > 0) {
//                    Navigation homeNavigation = (Navigation) getTypifiedObject(Navigation.class, homeId);
//                    homeSiteMap = getSiteMap(homeNavigation);
//                }
//                else if (parentId != null && parentId > 0) {
//                    Navigation homeNavigation = (Navigation) getTypifiedObject(Navigation.class, parentId);
//                    homeSiteMap = getSiteMap(homeNavigation);
//                }
//                String homeSiteMapUrl = homeSiteMap != null ? homeSiteMap.getUrl() : "";
//                log.warn("2. navigation = " + navigation.toExtendedString());
//                navigationSiteMap = new SiteMap();
//                navigationSiteMap.setUrl(homeSiteMapUrl.length() > 0 ? homeSiteMapUrl + "/" + systemName : systemName);
//                navigationSiteMap.setSystemNodeId(systemNodeId);
//                navigationSiteMap.setObjectId(navigation.getId());
//                navigationSiteMap.setRedirectTo(navigation.getRedirectTo());
//                navigationSiteMap.setNavigationId(navigation.getId());
//                if (homeSiteMap != null) {
//                    navigationSiteMap.setParentId(homeSiteMap.getId());
//                }
//                if (systemNodeId > 0) {
//                    SystemNode systemNode = (SystemNode) getTypifiedObject(SystemNode.class, systemNodeId);
//                    initSiteMapObjects(systemNode, navigationSiteMap);
//                }
//                log.warn("2. navigationSiteMap = " + navigationSiteMap);
//                currentSession.save(navigationSiteMap);
//                closeSession();
//            }
//        }
//        return navigationSiteMap;
//    }


//    private TreeMap<Integer, SystemNodeObject> initSiteMapObjects(SystemNode systemNode, SiteMap navigationSiteMap) {
//        TreeMap<Integer, SystemNodeObject> systemNodeObjects = new TreeMap<Integer, SystemNodeObject>();
//        if (systemNode != null) {
//            systemNodeObjects = getSystemNodeObjects(systemNode, false);
//            for (Integer position : systemNodeObjects.keySet()) {
//                SystemNodeObject systemNodeObject = systemNodeObjects.get(position);
////                String baseUrl = navigationSiteMap.getUrl();
////                String objectBaseUrl = baseUrl;
////                String objectUrl = "";
//                HomeObject homeObject = systemNodeObject.getHomeObject();
//                int homeId = homeObject.getHomeId();
//                if (homeId == 0) {
//                    homeObject.setHomeId(navigationSiteMap.getObjectId());
//                    getCurrentSession().update(homeObject);
//                    closeSession();
//                }
////                if (homeId > 0 &&  homeId != navigationSiteMap.getObjectId()) {
////                    objectBaseUrl = getParentPath(homeId, systemNodeObject instanceof SystemNodeObjectType);
////                    if (baseUrl.length() > 0) {
////                        if (baseUrl.startsWith(objectBaseUrl)) {
////                            objectUrl = baseUrl.replaceFirst("^" + objectBaseUrl + "/", "");
////                        }
////                    }
////                    else {
////                        objectUrl = baseUrl;
////                    }
////                }
////                SiteMapObject siteMapObject = new SiteMapObject(systemNodeObject.getObjectId(), systemNodeObject.getObjectId(), objectBaseUrl, objectUrl);
////                navigationSiteMap.setSiteMapObject(position, siteMapObject);
//            }
//        }
//        return systemNodeObjects;
//    }


//    private SiteMap getSiteMap(SystemObject systemObject) {
//        SiteMap siteMap = null;
//        if (!siteMapHomeIds.contains(systemObject.getId())) {
//            siteMapHomeIds.add(systemObject.getId());
//            siteMap = getSiteMapByObjectId(systemObject.getId());
//            if (siteMap == null) {
//                int homeId = systemObject.getHomeId();
//                log.warn("homeId = " + homeId);
//                if (homeId > 0) {
//                    Navigation homeNavigation = (Navigation) getTypifiedObject(Navigation.class, homeId);
//                    log.warn("homeNavigation = " + homeNavigation);
//                    siteMap = getSiteMap(homeNavigation);
//                    if (siteMap != null) {
//                        int levels = getMaxLevels(systemObject.getId(), false);
//                        siteMap.setLevels(levels);
//                    }
//    //                for (Integer position : siteMap.getSiteMapObjects().keySet()) {
//    //                    SiteMapObject siteMapObject = siteMap.getSiteMapObjects().get(position);
//    //                    if (siteMapObject.getObjectId() == systemObject.getId()) {
//    //                        siteMap.setActiveObjectPosition(position);
//    //                        break;
//    //                    }
//    //                }
//                }
//                else {
//                    SystemObject parent = (SystemObject) systemObject.getParent();
//                    if (parent != null) {
//                        SiteMap parentSiteMap = getSiteMap(parent);
//                        if (parentSiteMap != null) {
//                            log.warn("3. systemObject = " + systemObject.toExtendedString());
//                            siteMap = new SiteMap();
//                            String parentSiteMapUrl = parentSiteMap.getUrl();
//                            siteMap.setUrl(parentSiteMapUrl.length() > 0 ? parentSiteMapUrl + "/" + systemObject.getSystemName() : systemObject.getSystemName());
//                            siteMap.setSystemNodeId(parentSiteMap.getSystemNodeId());
//                            siteMap.setObjectId(systemObject.getId());
//                            siteMap.setParentId(parentSiteMap.getId());
//                            siteMap.setNavigationId(parentSiteMap.getNavigationId());
//                            int activeObjectPosition = parentSiteMap.getActiveObjectPosition();
//                            siteMap.setActiveObjectPosition(activeObjectPosition);
//                            siteMap.setLevels(parentSiteMap.getLevels() - 1);
//    //                        for (Integer position : parentSiteMap.getSiteMapObjects().keySet()) {
//    //                            SiteMapObject siteMapObject = parentSiteMap.getSiteMapObjects().get(position);
//    //                            SiteMapObject currentSiteMapObject = (SiteMapObject) siteMapObject.clone();
//    //                            if (position == activeObjectPosition) {
//    //                                String objectUrl = currentSiteMapObject.getObjectUrl();
//    //                                currentSiteMapObject.setObjectUrl(objectUrl.length() > 0 ? objectUrl + "/" + systemObject.getSystemName() : systemObject.getSystemName());
//    //                            }
//    //                            else {
//    //                                String baseUrl = currentSiteMapObject.getBaseUrl();
//    //                                currentSiteMapObject.setObjectUrl(baseUrl.length() > 0 ? baseUrl + "/" + systemObject.getSystemName() : systemObject.getSystemName());
//    //                            }
//    //                            siteMap.setSiteMapObject(position, currentSiteMapObject);
//    //                        }
//                            log.warn("3. siteMap = " + siteMap);
//                            getCurrentSession().save(siteMap);
//                            closeSession();
//                        }
//                    }
//                }
//            }
//        }
//        return siteMap;
//    }


//    private int getMaxLevels(int id, boolean objectType) {
//        SQLQuery sqlQuery = getCurrentSession().createSQLQuery("select max(levels) from system_node_object where object_type = " + (objectType ? "1" : "0") + " and object_id = " + id);
//        List list = sqlQuery.list();
//        closeSession();
//        return list.size() > 0 ? (Integer) list.get(0) : 0;
//    }


    private SiteMap getSiteMapByObjectId(int objectId) {
        Session session = getCurrentSession();
        List list = session.createQuery("from SiteMap where objectId = " + objectId).list();
        closeSession();
        return list.size() > 0 ? (SiteMap) list.get(0) : null;
    }


//    public void indexSiteMap(TypifiedObject typifiedObject) {
//        if (typifiedObject instanceof SystemObject) {
//            SystemObject systemObject = (SystemObject) typifiedObject;
//            siteMapCommonObjects.clear();
//            fillSiteMapUrls();
//            clearSiteMap(typifiedObject);
//            if (systemObject instanceof Navigation) {
//                indexSiteMap((Navigation) systemObject);
//            }
//            else if (systemObject instanceof SystemNode) {
//                indexSiteMap((SystemNode) systemObject);
//            }
//            else {
//                siteMapHomeIds.clear();
//                SiteMap siteMap = getSiteMap(systemObject);
//                if (siteMap != null) {
//                    indexSiteMap(systemObject, siteMap);
//                }
//            }
//        }
//    }


    private Navigation getNavigationBySystemNodeId(final int systemNodeId) {
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(Navigation.class);
        criteria.add(Restrictions.eq("systemNodeId", systemNodeId));
        List<Navigation> list = criteria.list();
        closeSession();
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }


//    private void indexSiteMap(SystemNode systemNode) {
//        Navigation navigation = getNavigationBySystemNodeId(systemNode.getId());
//        if (navigation != null) {
//            siteMapHomeIds.clear();
//            SiteMap navigationSiteMap = getSiteMap(navigation);
//            if (navigationSiteMap != null) {
//                indexSiteMap(systemNode, navigationSiteMap);
//            }
//        }
//    }


//    private void indexSiteMap(Navigation navigation) {
//        siteMapHomeIds.clear();
//        SiteMap navigationSiteMap = getSiteMap(navigation);
//        log.warn("navigationSiteMap = " + navigationSiteMap);
//        if (navigationSiteMap != null) {
//
//
////            indexSiteMap(navigation.getSystemNode(), navigationSiteMap);
//            int systemNodeId = navigation.getSystemNodeId() != null ? navigation.getSystemNodeId() : 0;
//            if (systemNodeId > 0) {
//                SystemNode systemNode = (SystemNode) getTypifiedObject(SystemNode.class, systemNodeId);
//                indexSiteMap(systemNode, navigationSiteMap);
//            }
//            indexSiteMap((SystemObject) navigation, navigationSiteMap);
//        }
//    }


//    private SiteMap indexSiteMap(Navigation navigation, SiteMap parentSiteMap) {
//        SiteMap navigationSiteMap = null;
//
//        int systemNodeId = navigation.getSystemNodeId() != null ? navigation.getSystemNodeId() : 0;
//
//        Object startPageSystemName = ServerTools.getGlobalParameter("startPage");
//        if (navigation.getSystemName().equals(startPageSystemName)) {
//            log.warn("4. navigation = " + navigation.toExtendedString());
//            navigationSiteMap = new SiteMap();
//            navigationSiteMap.setUrl("");
//            navigationSiteMap.setSystemNodeId(systemNodeId);
//            navigationSiteMap.setObjectId(navigation.getId());
//            navigationSiteMap.setRedirectTo(navigation.getRedirectTo());
//            navigationSiteMap.setNavigationId(navigation.getId());
//            navigationSiteMap.setLevels(parentSiteMap.getLevels() - 1);
//            navigationSiteMap.setActiveObjectPosition(parentSiteMap.getActiveObjectPosition());
//            log.warn("4. navigationSiteMap = " + navigationSiteMap);
//            getCurrentSession().save(navigationSiteMap);
//            closeSession();
//            if (systemNodeId > 0) {
//                SystemNode systemNode = (SystemNode) getTypifiedObject(SystemNode.class, systemNodeId);
//                indexSiteMap(systemNode, navigationSiteMap);
//            }
//        }
//        else {
//
//            int homeId = navigation.getHomeId();
//            if (homeId > 0) {
//                Navigation homeNavigation = (Navigation) getTypifiedObject(Navigation.class, homeId);
//                siteMapHomeIds.clear();
//                parentSiteMap = getSiteMap(homeNavigation);
//            }
//            if (parentSiteMap != null) {
//                String parentSiteMapUrl = parentSiteMap.getUrl();
//                String siteMapUrl = parentSiteMapUrl.length() > 0 ? parentSiteMapUrl + "/" + navigation.getSystemName() : navigation.getSystemName();
//                log.warn("5. navigation = " + navigation.toExtendedString());
//                navigationSiteMap = new SiteMap();
//                navigationSiteMap.setUrl(siteMapUrl);
//                navigationSiteMap.setSystemNodeId(systemNodeId);
//                navigationSiteMap.setObjectId(navigation.getId());
//                navigationSiteMap.setRedirectTo(navigation.getRedirectTo());
//                navigationSiteMap.setNavigationId(navigation.getId());
//                navigationSiteMap.setParentId(parentSiteMap.getId());
//                navigationSiteMap.setLevels(parentSiteMap.getLevels() - 1);
//                navigationSiteMap.setActiveObjectPosition(parentSiteMap.getActiveObjectPosition());
//                log.warn("5. siteMap = " + navigationSiteMap);
//                getCurrentSession().save(navigationSiteMap);
//                closeSession();
//            }
//            if (systemNodeId > 0) {
//                SystemNode systemNode = (SystemNode) getTypifiedObject(SystemNode.class, systemNodeId);
//                log.warn("systemNode = " + systemNode);
//                indexSiteMap(systemNode, navigationSiteMap);
//            }
//        }
//        return navigationSiteMap;
//    }


//    private void indexSiteMap(SystemObject systemObject, SiteMap parentSiteMap) {
//        log.warn("systemObject = " + systemObject.toExtendedString());
////        String siteMapUrl = parentSiteMapUrl.length() > 0 ? parentSiteMapUrl + "/" + systemObject.getSystemName() : systemObject.getSystemName();
//        CommonObjectId commonObjectId = new CommonObjectId(systemObject.getId(), false);
//        if (!siteMapCommonObjects.contains(commonObjectId)) {
//            siteMapCommonObjects.add(commonObjectId);
//            SiteMap siteMap = null;
//            if (systemObject instanceof Navigation) {
//                siteMap = indexSiteMap((Navigation) systemObject, parentSiteMap);
//                if (siteMap == null) {
//                    return;
//                }
//            }
//            else {
//
//                int homeId = systemObject.getHomeId();
//                if (homeId > 0) {
//                    Navigation homeNavigation = (Navigation) getTypifiedObject(Navigation.class, homeId);
//                    siteMapHomeIds.clear();
//                    parentSiteMap = getSiteMap(homeNavigation);
//                }
//                if (parentSiteMap != null) {
//                    String parentSiteMapUrl = parentSiteMap.getUrl();
//
//                    log.warn("6. systemObject = " + systemObject.toExtendedString());
//                    siteMap = new SiteMap();
//                    siteMap.setUrl(parentSiteMapUrl.length() > 0 ? parentSiteMapUrl + "/" + systemObject.getSystemName() : systemObject.getSystemName());
//                    siteMap.setSystemNodeId(parentSiteMap.getSystemNodeId());
//                    siteMap.setObjectId(systemObject.getId());
//                    siteMap.setParentId(parentSiteMap.getId());
//                    siteMap.setNavigationId(parentSiteMap.getNavigationId());
//                    int activeObjectPosition = parentSiteMap.getActiveObjectPosition();
//                    siteMap.setActiveObjectPosition(activeObjectPosition);
//                    siteMap.setLevels(parentSiteMap.getLevels() - 1);
////                    for (Integer position : parentSiteMap.getSiteMapObjects().keySet()) {
////                        SiteMapObject siteMapObject = parentSiteMap.getSiteMapObjects().get(position);
////                        SiteMapObject currentSiteMapObject = (SiteMapObject) siteMapObject.clone();
////                        if (position == activeObjectPosition) {
////                            String objectUrl = currentSiteMapObject.getObjectUrl();
////                            currentSiteMapObject.setObjectUrl(objectUrl.length() > 0 ? objectUrl + "/" + systemObject.getSystemName() : systemObject.getSystemName());
////                        }
////                        else {
////                            String baseUrl = currentSiteMapObject.getBaseUrl();
////                            currentSiteMapObject.setObjectUrl(baseUrl.length() > 0 ? baseUrl + "/" + systemObject.getSystemName() : systemObject.getSystemName());
////                        }
////                        siteMap.setSiteMapObject(position, currentSiteMapObject);
////                    }
//                    log.warn("6. siteMap = " + siteMap);
//                    getCurrentSession().save(siteMap);
//                    closeSession();
//                }
//            }
//            if (siteMap != null) {
//                log.warn("siteMap = " + siteMap);
//                for (TypifiedObject typifiedObject : getChildren(systemObject, siteMap.getLevels())) {
//                    log.warn("child typifiedObject = " + typifiedObject);
//                    indexSiteMap((SystemObject) typifiedObject, siteMap);
//                }
//            }
//        }
//    }
//
//
//    private void indexSiteMap(SystemNode systemNode, SiteMap siteMap) {
//        TreeMap<Integer, SystemNodeObject> systemNodeObjects = initSiteMapObjects(systemNode, siteMap);
//        for (Integer position : systemNodeObjects.keySet()) {
//            SystemNodeObject systemNodeObject = systemNodeObjects.get(position);
//            HomeObject homeObject = systemNodeObject.getHomeObject();
//            if (homeObject.getHomeId() == siteMap.getObjectId()) {
//                int levels = systemNodeObject.getLevels();
//                if (homeObject instanceof Navigation && levels == 0) {
//                    indexSiteMap((SystemObject) homeObject, siteMap);
//                }
//                else if (homeObject instanceof SystemObject) {
//                    log.warn("homeObject = " + homeObject);
//                    int maxLevels = getMaxLevels(homeObject.getId(), false);
//                    log.warn("maxLevels = " + maxLevels);
//                    siteMap.setLevels(maxLevels);
//                    for (TypifiedObject tObject : getChildren((SystemObject) homeObject, maxLevels)) {
//                        indexSiteMap((SystemObject) tObject, siteMap);
//                    }
//                }
//                else if (homeObject instanceof ObjectType) {
//                    ObjectType type = (ObjectType) homeObject;
//                    if (type.isHierarchical()) {
//                        int maxLevels = getMaxLevels(type.getId(), true);
//                        log.warn("maxLevels = " + maxLevels);
//                        siteMap.setLevels(maxLevels);
//                        ObjectTypeDao otDao = getObjectTypeDao();
//                        for (Object obj : otDao.getRootObjectsForType(type)) {
//                            TypifiedObject typifiedObject = (TypifiedObject) obj;
//                            indexSiteMap((SystemObject) typifiedObject, siteMap);
//                        }
//                    }
//                }
//            }
//        }
//        getCurrentSession().saveOrUpdate(siteMap);
//        closeSession();
//    }


//    private ObjectTypeDao getObjectTypeDao() {
//        if (objectTypeDao == null) {
//            objectTypeDao = (ObjectTypeDao) ServerTools.getWebApplicationContext().getBean("objectTypeDao");
//        }
//        return objectTypeDao;
//    }


    @Override
    protected void deleteObject(TypifiedObject typifiedObject) {
        List<TypifiedObject> typifiedObjectList = getChildren(typifiedObject.getId());
        for (TypifiedObject child : typifiedObjectList) {
            deleteObject(child);
        }
        super.deleteObject(typifiedObject);
    }


    public String getParentPath(int objectId, boolean includeTypifiedObject) {
        List<TypifiedObject> parents = getTypifiedObjectParents(objectId, includeTypifiedObject);
        StringBuilder fullUrl = new StringBuilder();
        for (TypifiedObject to : parents) {
            fullUrl.append(to.getSystemName()).append("/");
        }
        return fullUrl.toString();
    }


    private List<Navigation> getNavigationsBySystemNodeId(final int systemNodeId) {
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(Navigation.class);
        criteria.add(Restrictions.eq("systemNodeId", systemNodeId));
        List<Navigation> list = criteria.list();
        closeSession();
        return list;

    }


//    public void reindexSiteMap(SystemObject systemObject) {
//        reindexSiteMap(systemObject, false);
//    }


//    public void reindexSiteMap(SystemObject systemObject, boolean startOwnTransaction) {
//        Session session = getCurrentSession(startOwnTransaction);
//        systemNodeNavigationKeys.clear();
//        siteMapUrls.clear();
//        clearSiteMap(systemObject);
//        fillSiteMapUrls();
//        Object startPageSystemName = ServerTools.getGlobalParameter("startPage");
//        if (systemObject instanceof Navigation && systemObject.getSystemName().equals(startPageSystemName)) {
//            reindexStartPage((Navigation) systemObject);
//        }
//        else if (systemObject instanceof SystemNode) {
//            for (Navigation navigation : getNavigationsBySystemNodeId(systemObject.getId())) {
//                reindexSiteMap(navigation);
//            }
//        }
//        else {
//            ArrayList<SiteMapPosition> siteMaps = getBaseSiteMaps(systemObject);
//            for (SiteMapPosition siteMapPosition : siteMaps) {
//                reindexSiteMap(systemObject, siteMapPosition.siteMap, siteMapPosition.position, siteMapPosition.siteMap.isMainObject());
//            }
//        }
//        if (startOwnTransaction) {
//            closeSession();
//        }
//    }


//    private void fillSiteMapUrls() {
//        List objectIds = getCurrentSession().createQuery("select objectId from SiteMap").list();
//        for (Object objectId : objectIds) {
//            siteMapCommonObjects.add(new CommonObjectId((Integer) objectId, false));
//        }
//        closeSession();
//    }


    //    private void reindexStartPage(Navigation startNavigation) {
    //        Session session = getCurrentSession();
    //        SiteMap rootSiteMap;
    //        clearAllSiteMaps();
    //        List<SiteMap> startPageNavigationSiteMaps = getSiteMapsByObjectId(startNavigation.getId());
    //
    //        if (startPageNavigationSiteMaps.size() > 0) {
    //            rootSiteMap = startPageNavigationSiteMaps.get(0);
    //        }
    //        else {
    //            rootSiteMap = new SiteMap();
    //            rootSiteMap.setObjectId(startNavigation.getId());
    //            rootSiteMap.setUrl("");
    //            rootSiteMap.setMainObject(true);
    //            siteMapUrls.add(rootSiteMap.getUrl());
    //            session.save(rootSiteMap);
    //        }
    //        reindexSiteMap(startNavigation, rootSiteMap);
    //    }


//    private void reindexStartPage(Navigation startNavigation) {
//        Session session = getCurrentSession();
//        SiteMap rootSiteMap;
//        clearAllSiteMaps();
//        List<SiteMap> startPageNavigationSiteMaps = getSiteMapsByObjectId(startNavigation.getId());
//
//        if (startPageNavigationSiteMaps.size() > 0) {
//            rootSiteMap = startPageNavigationSiteMaps.get(0);
//        }
//        else {
//            rootSiteMap = new SiteMap();
//            rootSiteMap.setObjectId(startNavigation.getId());
//            rootSiteMap.setUrl("");
//            rootSiteMap.setMainObject(true);
//            siteMapUrls.add(rootSiteMap.getUrl());
//            session.save(rootSiteMap);
//        }
//        reindexSiteMap(startNavigation, rootSiteMap);
//    }


//    private void reindexSiteMap(SystemObject systemObject, SiteMap baseSiteMap, int activeObjectPosition, boolean mainObject) {
//        reindexSiteMap(systemObject, baseSiteMap, activeObjectPosition, mainObject, 0);
//    }


//    private void reindexSiteMap(SystemObject systemObject, SiteMap baseSiteMap, int activeObjectPosition, boolean mainObject, int levels) {
//        Session session = getCurrentSession();
//        if (systemObject instanceof SystemNode) {
//            for (Navigation navigation : getNavigationsBySystemNodeId(systemObject.getId())) {
//                reindexSiteMap(navigation);
//            }
//        }
//        else {
//            if (mainObject) {
//                TypifiedObject to = ((TypifiedObject) systemObject);
//                //log.warn("to = " + to);
//                //log.warn("to = " + to.getId());
//                //log.warn("to.getClass() = " + to.getClass());
//                boolean urlAbsolute = to.getUrlRelativity() == TypifiedObject.URL_RELATIVITY_ABSOLUTE;
//                //log.warn("urlAbsolute = " + urlAbsolute);
//                //log.warn("baseSiteMap.getUrl() = " + baseSiteMap.getUrl());
//                //log.warn("to.getRelativeURL() = " + to.getRelativeURL());
//                if ((urlAbsolute && baseSiteMap.getUrl().equals(to.getRelativeURL())) || !urlAbsolute) {
//                    List<TypifiedObject> children = getChildren(systemObject, levels);
//                    String initialUrl = baseSiteMap.getUrl();
//                    if (urlAbsolute) {
//                        baseSiteMap.setUrl(to.getRelativeURL());
//                    }
//                    for (TypifiedObject typifiedObject : children) {
//                        reindexSiteMap((SystemObject) typifiedObject, baseSiteMap, activeObjectPosition, false, levels - 1);
//                    }
//                    if (urlAbsolute) {
//                        baseSiteMap.setUrl(initialUrl);
//                    }
//                    if (to instanceof Navigation && children.size() == 0) {
//                        mainObject = false;
//                    }
//                }
//            }
//            if (!mainObject) {
//                String siteMapUrl = (baseSiteMap.getUrl().length() > 0 ? (baseSiteMap.getUrl() + "/") : "") + systemObject.getSystemName();
//                //log.warn("siteMapUrl = " + siteMapUrl);
//                if (!siteMapUrls.contains(siteMapUrl)) {
//                    SiteMap systemObjectSiteMap = new SiteMap();
//                    systemObjectSiteMap.setSystemNodeId(baseSiteMap.getSystemNodeId());
//                    //                systemObjectSiteMap.setSystemNodeSiteMap(baseSiteMap.getSystemNodeSiteMap());
//                    systemObjectSiteMap.setUrl(siteMapUrl);
//                    systemObjectSiteMap.setObjectId(systemObject.getId());
//                    systemObjectSiteMap.setActiveObjectPosition(activeObjectPosition);
//                    Map<Integer, SiteMapObject> siteMapObjectMap = baseSiteMap.getSiteMapObjects();
//                    int activeMainObjectId = 0;
//                    int activeObjectId = 0;
//                    SiteMapObject activeSiteMapObject = siteMapObjectMap.get(activeObjectPosition);
//                    if (activeSiteMapObject != null) {
//                        activeObjectId = activeSiteMapObject.getObjectId();
//                        activeMainObjectId = activeSiteMapObject.getMainObjectId();
//                    }
//                    for (Integer position : siteMapObjectMap.keySet()) {
//                        SiteMapObject siteMapObject = siteMapObjectMap.get(position);
//                        if (siteMapObject != null) {
//                            SiteMapObject clonedSiteMapObject = (SiteMapObject) siteMapObject.clone();
//                            if (activeObjectId != 0 && activeMainObjectId == clonedSiteMapObject.getMainObjectId() && activeObjectId == clonedSiteMapObject.getObjectId()) {
//                                clonedSiteMapObject.setObjectId(systemObject.getId());
//                                clonedSiteMapObject.setObjectUrl((clonedSiteMapObject.getObjectUrl().length() > 0 ? (clonedSiteMapObject.getObjectUrl() + "/") : "") + systemObject.getSystemName());
//                            }
//                            setSiteMapObject(systemObjectSiteMap, position, clonedSiteMapObject);
//                        }
//                    }
//                    baseSiteMap.addChild(systemObjectSiteMap);
//                    siteMapUrls.add(siteMapUrl);
//
//
//                    session.save(systemObjectSiteMap);
//                    if (systemObject instanceof Navigation) {
//                        systemObjectSiteMap.setMainObject(true);
//                        reindexSiteMap((Navigation) systemObject, systemObjectSiteMap);
//                    }
//                    List<TypifiedObject> children = getChildren(systemObject, levels);
//                    //log.warn("children = " + children);
//                    for (TypifiedObject typifiedObject : children) {
//                        reindexSiteMap((SystemObject) typifiedObject, systemObjectSiteMap, activeObjectPosition, false, levels - 1);
//                    }
//
//
//                }
//            }
//        }
//    }


//    private void reindexSiteMap(SystemNode systemNode, SiteMap baseSiteMap) {
//        boolean hasCycle = false;
//        HashMap<Integer, SiteMapObject> siteMapObjects = new HashMap<Integer, SiteMapObject>();
//
//        TreeMap<Integer, SystemNodeObject> systemNodeObjects = getSystemNodeObjects(systemNode, true);
//        for (SystemNodeTypifiedObject systemNodeTypifiedObject : systemNode.getTypifiedObjects()) {
//            systemNodeObjects.put(systemNodeTypifiedObject.getPosition(), systemNodeTypifiedObject);
//        }
//        for (SystemNodeObject systemNodeObject : systemNodeObjects.values()) {
//            SystemNodeTypifiedObject systemNodeTypifiedObject = (SystemNodeTypifiedObject) systemNodeObject;
//            TypifiedObject typifiedObject = systemNodeTypifiedObject.getTypifiedObject();
//            if (typifiedObject instanceof Navigation) {
//                SystemNodeNavigationKey systemNodeNavigationKey = new SystemNodeNavigationKey(systemNode.getId(), typifiedObject.getId());
//                if (systemNodeNavigationKeys.contains(systemNodeNavigationKey)) {
//                    hasCycle = true;
//                }
//                else {
//                    systemNodeNavigationKeys.add(systemNodeNavigationKey);
//                }
//            }
//            int urlRelativity = typifiedObject.getUrlRelativity();
//            boolean urlRelativityAbsolute = urlRelativity == TypifiedObject.URL_RELATIVITY_ABSOLUTE;
//            String baseUrl = urlRelativityAbsolute ? typifiedObject.getRelativeURL() : baseSiteMap.getUrl();
//            String objectUrl = urlRelativityAbsolute ? (baseSiteMap.getUrl().length() > 0 ? baseSiteMap.getUrl().replaceFirst("^" + typifiedObject.getRelativeURL(), "") : "") : "";
//            siteMapObjects.put(systemNodeTypifiedObject.getPosition(), new SiteMapObject(typifiedObject.getId(), typifiedObject.getId(), baseUrl, objectUrl));
//        }
//        Set<SystemNodeObjectType> systemNodeObjectTypes = systemNode.getObjectTypes();
//        if (systemNodeObjectTypes.size() > 0) {
//            ObjectTypeDao objectTypeDao = (ObjectTypeDao) ServerTools.getWebApplicationContext().getBean("objectTypeDao");
//            for (SystemNodeObjectType systemNodeObjectType : systemNodeObjectTypes) {
//                ObjectType type = systemNodeObjectType.getObjectType();
//                siteMapObjects.put(systemNodeObjectType.getPosition(), new SiteMapObject(type.getId(), type.getId(), baseSiteMap.getUrl(), ""));
//                if (type.isHierarchical()) {
//                    for (Object obj : objectTypeDao.getRootObjectsForType(systemNodeObjectType.getObjectType())) {
//                        TypifiedObject typifiedObject = (TypifiedObject) obj;
//                        if (typifiedObject instanceof Navigation) {
//                            SystemNodeNavigationKey systemNodeNavigationKey = new SystemNodeNavigationKey(systemNode.getId(), typifiedObject.getId());
//                            if (systemNodeNavigationKeys.contains(systemNodeNavigationKey)) {
//                                hasCycle = true;
//                            }
//                            else {
//                                systemNodeNavigationKeys.add(systemNodeNavigationKey);
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        if (!hasCycle) {
//            for (Integer position : siteMapObjects.keySet()) {
//                SiteMapObject mapObject = siteMapObjects.get(position);
//                setSiteMapObject(baseSiteMap, position, mapObject);
//            }
//            Session session = getCurrentSession();
//            session.update(baseSiteMap);
//
//            for (SystemNodeObject systemNodeObject : systemNodeObjects.values()) {
//                SystemNodeTypifiedObject systemNodeTypifiedObject = (SystemNodeTypifiedObject) systemNodeObject;
//                TypifiedObject typifiedObject = systemNodeTypifiedObject.getTypifiedObject();
//                //log.warn("typifiedObject = " + typifiedObject);
//                if (typifiedObject instanceof SystemObject) {
//                    reindexSiteMap((SystemObject) typifiedObject, baseSiteMap, systemNodeTypifiedObject.getPosition(), true, systemNodeObject.getLevels());
//                }
//            }
//            systemNodeObjectTypes = systemNode.getObjectTypes();
//            if (systemNodeObjectTypes.size() > 0) {
//                ObjectTypeDao objectTypeDao = (ObjectTypeDao) ServerTools.getWebApplicationContext().getBean("objectTypeDao");
//                for (SystemNodeObjectType systemNodeObjectType : systemNodeObjectTypes) {
//                    ObjectType type = systemNodeObjectType.getObjectType();
//                    if (type.isHierarchical()) {
//                        for (Object obj : objectTypeDao.getRootObjectsForType(systemNodeObjectType.getObjectType())) {
//                            TypifiedObject typifiedObject = (TypifiedObject) obj;
//                            reindexSiteMap((SystemObject) typifiedObject, baseSiteMap, systemNodeObjectType.getPosition(), true, systemNodeObjectType.getLevels());
//                        }
//                    }
//                }
//            }
//        }
//    }


//    private void reindexSiteMap(Navigation navigation, SiteMap baseSiteMap) {
//        Session session = getCurrentSession();
//        SystemNode systemNode = navigation.getSystemNode();
//        //log.warn("systemNode = " + systemNode);
//        if (systemNode != null) {
//            baseSiteMap.setSystemNodeId(systemNode.getId());
//            Map<Integer, SiteMapObject> siteMapObjectMap = baseSiteMap.clearSiteMapObjects();
////            Session session = getCurrentSession();
//            for (SiteMapObject siteMapObject : siteMapObjectMap.values()) {
//                session.delete(siteMapObject);
//            }
//            //getCurrentSession().update(baseSiteMap);
//            TreeMap<Integer, SystemNodeObject> systemNodeObjects = getSystemNodeObjects(systemNode, false);
//            for (Integer position : systemNodeObjects.keySet()) {
//                SystemNodeObject systemNodeObject = systemNodeObjects.get(position);
//                String baseUrl = baseSiteMap.getUrl();
//                String objectUrl = "";
//                SiteMapObject mapObject = siteMapObjectMap.get(position);
//                if (mapObject != null && mapObject.getObjectId() == systemNodeObject.getObjectId()) {
//                    baseUrl = mapObject.getBaseUrl();
//                    objectUrl = mapObject.getObjectUrl();
//                }
//                else if (systemNodeObject instanceof SystemNodeTypifiedObject) {
//                    TypifiedObject typifiedObject = ((SystemNodeTypifiedObject) systemNodeObject).getTypifiedObject();
//                    int urlRelativity = typifiedObject.getUrlRelativity();
//                    boolean urlRelativityAbsolute = urlRelativity == TypifiedObject.URL_RELATIVITY_ABSOLUTE;
//                    baseUrl = urlRelativityAbsolute ? typifiedObject.getRelativeURL() : baseSiteMap.getUrl();
//                    log.warn("baseUrl = " + baseUrl);
//                    log.warn("baseSiteMap.getUrl() = " + baseSiteMap.getUrl());
//                    log.warn("\"^\" + typifiedObject.getRelativeURL() + \"/\" = " + "^" + typifiedObject.getRelativeURL() + "/");
//                    objectUrl = urlRelativityAbsolute ? (baseSiteMap.getUrl().length() > 0 ? baseSiteMap.getUrl().replaceFirst("^" + typifiedObject.getRelativeURL() + "/", "") : "") : "";
//                    log.warn("objectUrl = " + objectUrl);
//                }
//                setSiteMapObject(baseSiteMap, position, new SiteMapObject(systemNodeObject.getObjectId(), systemNodeObject.getObjectId(), baseUrl, objectUrl));
//            }
//            reindexSiteMap(systemNode, baseSiteMap);
//        }
//    }


//    private void setSiteMapObject(SiteMap baseSiteMap, Integer position, SiteMapObject siteMapObject) {
//        Session session = getCurrentSession();
//        SiteMapObject mapObject = baseSiteMap.setSiteMapObject(position, siteMapObject);
//        if (mapObject != null) {
//            session.delete(mapObject);
//        }
//        session.saveOrUpdate(baseSiteMap);
//    }


//    private TreeMap<Integer, SystemNodeObject> getSystemNodeObjects(SystemNode systemNode, boolean onlyAbsoluteURL) {
//        TreeMap<Integer, SystemNodeObject> systemNodeObjects = new TreeMap<Integer, SystemNodeObject>();
//        if (systemNode.getTemplateId() == Template.INHERIT_ID && systemNode.getParent() != null) {
//            systemNodeObjects = getSystemNodeObjects((SystemNode) systemNode.getParent(), onlyAbsoluteURL);
//        }
//        if (onlyAbsoluteURL) {
//            for (SystemNodeTypifiedObject typifiedObject : systemNode.getTypifiedObjects()) {
//                if (typifiedObject.getTypifiedObject().getUrlRelativity() == TypifiedObject.URL_RELATIVITY_ABSOLUTE) {
//                    systemNodeObjects.put(typifiedObject.getPosition(), typifiedObject);
//                }
//            }
//        }
//        else {
//            for (SystemNodeTypifiedObject typifiedObject : systemNode.getTypifiedObjects()) {
//                systemNodeObjects.put(typifiedObject.getPosition(), typifiedObject);
//            }
//            for (SystemNodeObjectType objectType : systemNode.getObjectTypes()) {
//                systemNodeObjects.put(objectType.getPosition(), objectType);
//            }
//        }
//        return systemNodeObjects;
//    }


//    private ArrayList<SiteMapPosition> getBaseSiteMaps(SystemNode systemNode) {
//        Session session = getCurrentSession();
//        ArrayList<SiteMapPosition> siteMaps = new ArrayList<SiteMapPosition>();
//        List list = session.createQuery("select id from Navigation where systemNodeId = " + systemNode.getId()).list();
//        if (list != null && list.size() > 0) {
//            String inCondition = Utilities.implode(list, ",");
//            List siteMapList = session.createQuery("from SiteMap where objectId in (" + inCondition + ")").list();
//            for (Object obj : siteMapList) {
//                SiteMap siteMap = (SiteMap) obj;
//                siteMaps.add(new SiteMapPosition(siteMap, 0));
//            }
//        }
//        closeSession();
//        return siteMaps;
//    }


//    private ArrayList<SiteMapPosition> getBaseSiteMaps(SystemObject systemObject) {
//        if (systemObject instanceof SystemNode) {
//            return getBaseSiteMaps((SystemNode) systemObject);
//        }
//        else {
//            ArrayList<SiteMapPosition> siteMaps = getBaseSiteMapsFromSystemNode(systemObject);
//            TypifiedObject parent = systemObject.getParent();
//            if (parent != null) {
//                List<SiteMap> parentSiteMaps = getSiteMapsByObjectId(parent.getId());
//                for (SiteMap parentSiteMap : parentSiteMaps) {
//                    parentSiteMap.setMainObject(false);
//                    siteMaps.add(new SiteMapPosition(parentSiteMap, parentSiteMap.getActiveObjectPosition()));
//                }
//                ArrayList<SiteMapPosition> siteMapsFromSystemNode = getBaseSiteMapsFromSystemNode((SystemObject) parent);
//                for (SiteMapPosition siteMapPosition : siteMapsFromSystemNode) {
//                    siteMapPosition.siteMap.setMainObject(false);
//                }
//                siteMaps.addAll(siteMapsFromSystemNode);
//            }
//            return siteMaps;
//        }
//    }


//    private ArrayList<SiteMapPosition> getBaseSiteMapsFromSystemNode(SystemObject systemObject) {
//        Session session = getCurrentSession();
//        ArrayList<SiteMapPosition> siteMaps = new ArrayList<SiteMapPosition>();
//        List data = session.createQuery("select systemNodeId, position from SystemNodeObject where objectId = " + systemObject.getId()).list();
//        HashMap<Integer, Integer> systemNodeIds = new HashMap<Integer, Integer>();
//        for (Object obj : data) {
//            Object[] row = (Object[]) obj;
//            systemNodeIds.put((Integer) row[0], (Integer) row[1]);
//        }
//        if (systemNodeIds.size() > 0) {
//            String inCondition = Utilities.implode(systemNodeIds.keySet(), ",");
//            List systemNodeList = session.createQuery("from SystemNode where id in (" + inCondition + ")").list();
//            for (Object obj : systemNodeList) {
//                SystemNode systemNode = (SystemNode) obj;
//                ArrayList<SiteMapPosition> systemNodeSiteMaps = getBaseSiteMaps(systemNode);
//                Integer position = systemNodeIds.get(systemNode.getId());
//                if (position != null) {
//                    for (SiteMapPosition siteMapPosition : systemNodeSiteMaps) {
//                        siteMapPosition.siteMap.setMainObject(true);
//                        siteMaps.add(new SiteMapPosition(siteMapPosition.siteMap, position));
//                    }
//                }
//            }
//        }
//        closeSession();
//        return siteMaps;
//    }


//    private List<SiteMap> getSiteMapsByObjectId(int objectId) {
//        Session session = getCurrentSession();
//        List list = session.createQuery("from SiteMap where objectId = " + objectId).list();
//        closeSession();
//        return list;
//    }


//    protected void clearSiteMap(TypifiedObject typifiedObject) {
//        if (typifiedObject instanceof SystemNode) {
//            typifiedObject = getNavigationBySystemNodeId(typifiedObject.getId());
//        }
//        if (typifiedObject != null && typifiedObject instanceof SystemObject) {
//            SiteMap siteMap = getSiteMapByObjectId(typifiedObject.getId());
//            deleteSiteMap(siteMap);
//        }
//    }


//    private void deleteSiteMap(SiteMap siteMap) {
//        if (siteMap != null) {
////        kremnef remove
//            /*
//            for (SiteMap child : siteMap.getChildren()) {
//                deleteSiteMap(child);
//            }
//            getCurrentSession().delete(siteMap);
//            closeSession();*/
//        }
//    }


//    private void clearSiteMap(SystemObject systemObject) {
//        Session session = getCurrentSession();
//        if (systemObject instanceof SystemNode) {
//            clearSiteMap((SystemNode) systemObject);
//        }
//        else {
//            List<SiteMap> list = session.createQuery("from SiteMap where objectId = " + systemObject.getId()).list();
//            for (SiteMap siteMap : list) {
//                session.delete(siteMap);
//            }
//        }
//    }


//    private void clearAllSiteMaps() {
//        Session session = getCurrentSession();
//        List<SiteMap> list = session.createQuery("from SiteMap").list();
//        for (SiteMap siteMap : list) {
//            session.delete(siteMap);
//        }
//    }
//
//
//    private void clearSiteMap(SystemNode systemNode) {
//        Session session = getCurrentSession();
//        List<SiteMap> list = session.createQuery("from SiteMap where systemNodeId = " + systemNode.getId()).list();
//        for (SiteMap siteMap : list) {
//            session.delete(siteMap);
//        }
////        session.createQuery("delete from SiteMap where systemNodeId = " + systemNode.getId()).executeUpdate();
//    }


//    public String getParentPath(int objectId, boolean objectType) {
//        return getParentPath(objectId, objectType, true);
//    }
//
//
//    //todo ����� ��������� �����, ������ ������ ������ ������ (systemName, parentId, homeId, objectTypeId), � �� ����������� ������ �������
//    private String getParentPath(int objectId, boolean objectType, boolean first) {
//        if (first) {
//            parentPathIds.clear();
//            getCurrentSession();
//        }
//        StringBuilder path = new StringBuilder("");
//        CommonObjectId parentPathId = new CommonObjectId(objectId, objectType);
//        if (!parentPathIds.contains(parentPathId)) {
//            parentPathIds.add(parentPathId);
//            if (objectType) {
//                ObjectType objectTypeById = getObjectTypeById(objectId);
//                if (objectTypeById != null && objectTypeById.getHomeId() > 0) {
//                    path.append(getParentPath(objectTypeById.getHomeId(), false, false));
//                }
//            }
//            else {
//                log.warn("getParentPath objectId = " + objectId);
//                SystemObject systemObject = (SystemObject) getTypifiedObject(TypifiedObject.class, objectId);
//                log.warn("getParentPath systemObject = " + systemObject);
//                if (systemObject != null) {
//                    log.warn("getParentPath systemObject = " + systemObject.toExtendedString());
//                    int parentId = 0;
//                    SystemObject parent = (SystemObject) systemObject.getParent();
//                    if (parent != null) {
//                        parentId = parent.getId();
//                    }
//                    String systemName = systemObject.getSystemName();
//                    Object startPageSystemName = ServerTools.getGlobalParameter("startPage");
//                    if (systemName.equals(startPageSystemName)) {
//                        systemName = "";
//                    }
//                    log.warn("getParentPath systemObject.getHomeId() = " + systemObject.getHomeId());
//                    if (systemObject.getHomeId() > 0) {
//                        if (systemObject.getId() == systemObject.getHomeId()) {
//                            path.append(getParentPath(parentId, false, false));
//                            if (path.length() > 0) {
//                                path.append("/");
//                            }
//                            path.append(systemName);
//                        }
//                        else {
//                            path.append(getParentPath(systemObject.getHomeId(), false, false));
//                        }
//                    }
//                    else {
//                        log.warn("getParentPath parentId = " + parentId);
//                        if (parentId > 0) {
//                            path.append(getParentPath(parentId, false, false));
//                            if (path.length() > 0) {
//                                path.append("/");
//                            }
//                            path.append(systemName);
//                        }
//                        else {
//                            int objectTypeId = ((TypifiedObject) systemObject).getObjectTypeId();
//                            log.warn("getParentPath objectTypeId = " + objectTypeId);
//                            path.append(getParentPath(objectTypeId, true, false));
//                            if (path.length() > 0) {
//                                path.append("/");
//                            }
//                            path.append(systemName);
//
//                        }
//                    }
//                }
//            }
//        }
//        if (first) {
//            closeSession();
//        }
//        return path.toString();
//    }


//    private class SiteMapPosition {
//
//
//        public final SiteMap siteMap;
//        public final int position;
//
//
//        public SiteMapPosition(SiteMap siteMap, int position) {
//            this.siteMap = siteMap;
//            this.position = position;
//        }
//
//
//        @Override
//        public String toString() {
//            return siteMap.toString() + ", position=" + position;
//        }
//    }


    private class SystemNodeNavigationKey implements Comparable<SystemNodeNavigationKey> {


        private int systemNodeId;
        private int navigationId;


        public SystemNodeNavigationKey(int systemNodeId, int navigationId) {
            this.systemNodeId = systemNodeId;
            this.navigationId = navigationId;
        }


        @Override
        public boolean equals(Object obj) {
            if (obj instanceof SystemNodeNavigationKey) {
                SystemNodeNavigationKey systemNodeNavigationKey = (SystemNodeNavigationKey) obj;
                return systemNodeId == systemNodeNavigationKey.systemNodeId && navigationId == systemNodeNavigationKey.navigationId;
            }
            return false;
        }


        @Override
        public int hashCode() {
            final int multiplier = 23;
            int code = 133;
            code = multiplier * code + systemNodeId;
            code = multiplier * code + navigationId;
            return code;
        }


        public int compareTo(SystemNodeNavigationKey o) {
            return systemNodeId == o.systemNodeId ? navigationId - o.navigationId : systemNodeId - o.systemNodeId;
        }

    }


//    private class CommonObjectId {
//
//
//        private int objectId;
//        private boolean objectType;
//
//
//        private CommonObjectId(int objectId, boolean objectType) {
//            this.objectId = objectId;
//            this.objectType = objectType;
//        }
//
//
//        @Override
//        public int hashCode() {
//            final int multiplier = 23;
//            int code = 133;
//            code = multiplier * code * (isObjectType() ? 1 : 0);
//            code = multiplier * code + getObjectId();
//            return code;
//        }
//
//
//        @Override
//        public boolean equals(Object obj) {
//            if (obj instanceof CommonObjectId) {
//                CommonObjectId parentPathId = (CommonObjectId) obj;
//                return objectType == parentPathId.objectType && objectId == parentPathId.objectId;
//            }
//            return false;
//        }
//
//
//        public int getObjectId() {
//            return objectId;
//        }
//
//
//        public void setObjectId(int objectId) {
//            this.objectId = objectId;
//        }
//
//
//        public boolean isObjectType() {
//            return objectType;
//        }
//
//
//        public void setObjectType(boolean objectType) {
//            this.objectType = objectType;
//        }
//
//
//        @Override
//        public String toString() {
//            return "[CommonObjectId (objectId = " + objectId + ", objectType = " + objectType + ")]";
//        }
//    }

}