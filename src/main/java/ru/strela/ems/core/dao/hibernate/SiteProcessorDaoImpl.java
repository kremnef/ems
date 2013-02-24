package ru.strela.ems.core.dao.hibernate;


import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.strela.ems.core.dao.*;
import ru.strela.ems.core.model.*;
import ru.strela.ems.generator.ChildrenMap;
import ru.strela.ems.generator.SitePageGenerator;
import ru.tastika.tools.util.Utilities;

import java.util.*;


/**
 * User: hobal
 * Date: 12.05.2010
 * Time: 23:55:19
 */
public class
        SiteProcessorDaoImpl implements SiteProcessorDao {

    private String currentLocale;
    private final static Logger log = LoggerFactory.getLogger(SiteProcessorDaoImpl.class);


    public SiteProcessorDaoImpl() {
        super();
    }


    //    public TreeMap<String, Object> getSystemObjects(String systemNamesPath, String indexPage, int languageId) {
    public TreeMap<String, Object> getSystemObjects(String systemNamesPath, String indexPage, String languageCode) {
        currentLocale = languageCode;
        TreeMap<String, Object> systemObjects = new TreeMap<String, Object>();

      Session session = HibernateUtil.getSessionFactory().openSession();
//    kremnef
      session.setDefaultReadOnly(true);

      Transaction tx = null;
        try {
            tx = session.beginTransaction();

            if (systemNamesPath.length() == 0) {
                systemNamesPath = indexPage;
            }

//            //System.out.println("systemNamesPath = " + systemNamesPath);
//            todo: оптимизировать загрузку страницы. достаточно медленно
            System.out.println("1. System.currentTimeMillis() = " + System.currentTimeMillis());

            HashSet<Integer> redirectCycle = new HashSet<Integer>();
            // s.id, s.redirect_to s.full_path, e.system_name, t.id template_id, t.positions_amount
            ArrayList<Object[]> pathData = getPathData(session, systemNamesPath);
            if (pathData.size() > 0) {
                String systemNodesUrl = (String) pathData.get(pathData.size() - 1)[2];
                String objectURL = "";
                if (systemNamesPath.length() > systemNodesUrl.length()) {
                    objectURL = systemNamesPath.substring(systemNodesUrl.length() + 1);
                }
                boolean checkRedirect = true;
                while (pathData.size() > 0 && checkRedirect) {
                    Object[] lastRow = pathData.get(pathData.size() - 1);
                    Integer redirectTo = (Integer) lastRow[1];
                    if (redirectTo > 0 && !redirectCycle.contains(redirectTo)) {
                        redirectCycle.add(redirectTo);
                        String fullUrl = (String) lastRow[2];
                        pathData = getPathData(session, fullUrl);
                    } else {
                        checkRedirect = false;
                    }
                }
                if (pathData.size() > 0) {
                    int templatePositionsAmount = 0;
                    int templateId = 0;
                    for (int i = pathData.size() - 1; i >= 0 && templateId == 0; i--) {
                        Object[] row = pathData.get(i);
                        if (row[4] != null) {
                            templateId = (Integer) row[4];
                            templatePositionsAmount = (Byte) row[5];
                        }
                    }
                    if (templateId > 0) {
                        int systemNodeId = (Integer) pathData.get(pathData.size() - 1)[0];

                        SystemNode systemNode = getSystemNode(session, systemNodeId);
                        if (systemNode != null) {
                            systemObjects.put(SystemNode.class.getSimpleName(), systemNode);
                            HashMap<Integer, SystemNodeObject> systemNodeObjectMap = getSystemNodeObjects(systemNode, templatePositionsAmount);

                            if (systemNodeObjectMap.size() > 0) {
                                systemObjects.put(SitePageGenerator.OBJECTS_KEY, systemNodeObjectMap.values());
                            }

                            Template template = getTemplate(session, templateId);
                            if (template != null) {
                                systemObjects.put(template.getClass().getSimpleName(), template);
                            }

                            ChildrenMap children = new ChildrenMap();
                            HashSet<Integer> systemNodeIdsForUrls = new HashSet<Integer>();
                            for (Integer position : systemNodeObjectMap.keySet()) {
                                SystemNodeObject systemNodeObject = systemNodeObjectMap.get(position);
                                String sortField = systemNodeObject.getSortField();
                                if (sortField == null) {
                                    sortField = "position";
                                }
                                String sortDirection = systemNodeObject.getSortDirection();
                                if (sortDirection == null) {
                                    sortDirection = "asc";
                                }
                                if (systemNodeObject instanceof SystemNodeObjectType) {
                                    SystemNodeObjectType systemNodeObjectType = (SystemNodeObjectType) systemNodeObject;
                                    int levels = systemNodeObject.getLevels();
                                    if (levels > 0) {
                                        children.putAll(getChildren(systemNodeObjectType.getObjectType(), systemNodeObjectType.getItemsOnPage(), levels, 0, sortField, sortDirection, systemNodeObjectType.getTagId(), session, systemNodeIdsForUrls, languageCode, position));
                                    }
                                } else {
                                    SystemNodeTypifiedObject systemNodeTypifiedObject = (SystemNodeTypifiedObject) systemNodeObject;
                                    TypifiedObject typifiedObject = systemNodeTypifiedObject.getTypifiedObject();
                                    if (typifiedObject instanceof SystemObject) {
                                        int levels = systemNodeObject.getLevels();
                                        if (levels > 0) {
//                                        todo: add sort Direction^ Filter + Custom SQL
                                            children.putAll(getChildren(typifiedObject, levels, 0, systemNodeTypifiedObject.getItemsOnPage(), sortField, sortDirection, systemNodeTypifiedObject.getTagId(), session, systemNodeIdsForUrls, languageCode, position));
                                        }
                                        if (typifiedObject instanceof Content) {
                                            Content content = (Content) typifiedObject;

                                            if (content.getHomeId() > 0) {
                                                systemNodeIdsForUrls.add(content.getHomeId());
                                            }
                                            int contentChildrenCount = content.getEmsObject().getChildrenCount();
                                            if (contentChildrenCount > 0) {
                                                int totalPages = Math.round(contentChildrenCount / systemNodeObject.getItemsOnPage()) + 1;
                                                systemNodeObject.setTotalPages(totalPages);
                                            }


//                                            todo: need move to ContentDao
                                            fillDocumentFolders(content, children, session, languageCode, position);
                                        } else if (typifiedObject instanceof Navigation) {
                                            Navigation navigation = (Navigation) typifiedObject;
                                            if (navigation.getSystemNodeId() != null && navigation.getSystemNodeId() > 0) {
                                                systemNodeIdsForUrls.add(navigation.getSystemNodeId());
                                            }
                                        }
                                    }
                                }
                                System.out.println("4.1 System.currentTimeMillis() = " + System.currentTimeMillis());
                            }
                            if (children.size() > 0) {
                                systemObjects.put(SitePageGenerator.CHILDREN_KEY, children);
                            }

                            HashMap<Integer, String> systemNodeUrls = getSystemNodeUrls(session, systemNodeIdsForUrls);
                            fillObjectsUrls(systemNodeObjectMap.values(), systemNodesUrl, objectURL, systemNodeUrls);
                            fillObjectsUrls(children, systemNodesUrl, objectURL, systemNodeUrls);
                        }
                    }
                }
            }
            System.out.println("5. System.currentTimeMillis() = " + System.currentTimeMillis());
            tx.commit();
            session.close();
        } catch (HibernateException he) {
            if (session.isOpen()) {
                if (tx != null && tx.isActive()) {
                    tx.rollback();
                }
            }
            throw he;
        }
        return systemObjects;
    }


    private void fillTypifiedObjectUrls(TypifiedObject typifiedObject, String fullURL, String baseURL, String objectURL, HashMap<Integer, String> systemNodeUrls) {
        boolean customPath = false;
        if (typifiedObject instanceof Content) {
            Content content = (Content) typifiedObject;
            if (content.getHomeId() > 0) {
                String url = systemNodeUrls.get(content.getHomeId());
                if (url != null) {
                    customPath = true;
                    content.setBaseURL(url);
                    if (fullURL.startsWith(url)) {
                        content.setObjectURL(fullURL.replaceFirst("^" + url, ""));
                    }
                }
            }
        } else if (typifiedObject instanceof Navigation) {
            Navigation navigation = (Navigation) typifiedObject;
            if (navigation.getSystemNodeId() != null && navigation.getSystemNodeId() > 0) {
                String url = systemNodeUrls.get(navigation.getSystemNodeId());
                if (url != null) {
                    navigation.setPathURL(url);
                }
            }
        }
        if (!customPath) {
            typifiedObject.setBaseURL(baseURL);
            typifiedObject.setObjectURL(objectURL);
        }
    }


    private void fillObjectsUrls(Collection<SystemNodeObject> values, String baseURL, String objectURL, HashMap<Integer, String> systemNodeUrls) {
        String fullURL = baseURL.length() > 0 && objectURL.length() > 0 ? baseURL + "/" + objectURL : baseURL + objectURL;
        for (SystemNodeObject systemNodeObject : values) {
            if (systemNodeObject instanceof SystemNodeTypifiedObject) {
                SystemNodeTypifiedObject systemNodeTypifiedObject = (SystemNodeTypifiedObject) systemNodeObject;
                TypifiedObject typifiedObject = systemNodeTypifiedObject.getTypifiedObject();
                fillTypifiedObjectUrls(typifiedObject, fullURL, baseURL, objectURL, systemNodeUrls);
            }
        }
    }


    private void fillObjectsUrls(ChildrenMap children, String baseURL, String objectURL, HashMap<Integer, String> systemNodeUrls) {
        String fullURL = baseURL.length() > 0 && objectURL.length() > 0 ? baseURL + "/" + objectURL : baseURL + objectURL;
        for (TypifiedObject[] tObjects : children.getChildren().values()) {
            for (TypifiedObject to : tObjects) {
                fillTypifiedObjectUrls(to, fullURL, baseURL, objectURL, systemNodeUrls);
            }
        }
    }


    private HashMap<Integer, String> getSystemNodeUrls(Session session, HashSet<Integer> systemNodeIdsForUrls) {
        HashMap<Integer, String> systemNodeUrls = new HashMap<Integer, String>();
        if (systemNodeIdsForUrls.size() > 0) {
            List list = session.createQuery("select id, fullURL from SystemNode where id in (" + Utilities.implode(systemNodeIdsForUrls, ",") + ")").list();
            for (Object rowObj : list) {
                Object[] row = (Object[]) rowObj;
                systemNodeUrls.put((Integer) row[0], (String) row[1]);
            }
        }
        return systemNodeUrls;
    }


    private ArrayList<Object[]> getPathData(Session session, String systemNamesPath) {
        ArrayList<Object[]> pathData = new ArrayList<Object[]>();
        String[] parts = systemNamesPath.split("/");
        int parentId = 0;
        for (String pathPart : parts) {
            StringBuilder sql = new StringBuilder("select s.id, s.redirect_to, s.full_url, e.system_name, t.id template_id, t.positions_amount from system_node s inner join object e on s.ems_object_id = e.id left join template t on s.template_id = t.id where e.parent_id ");
            if (parentId > 0) {
                sql.append(" = ").append(parentId);
            } else {
                sql.append(" is null");
            }
            sql.append(" and e.system_name = '").append(pathPart).append("'");
            List list = session.createSQLQuery(sql.toString()).list();
            if (list.size() > 0) {
                Object[] row = (Object[]) list.get(0);
                pathData.add(row);
                parentId = (Integer) row[0];
            } else {
                break;
            }
        }
        return pathData;

    }


    private Template getTemplate(Session session, int templateId) {
        return (Template) getObject(session, Template.class, templateId);
    }


    private SystemNode getSystemNode(Session session, int systemNodeId) {
        SystemNode systemNode = (SystemNode) getObject(session, SystemNode.class, systemNodeId);
//        int emsObjectId = systemNode.getEmsObject().getId();
//        currentLocale = "ru";
        /*MetaInfoDao metaInfoDao = new MetaInfoDaoImpl();
        MetaInfo metaInfo = metaInfoDao.getMetaInfoNaturalId(emsObjectId, currentLocale);
        if (metaInfo != null) {
            systemNode.setMetaInfo(metaInfo);
        }
        ObjectLabelDao objectLabelDao = new ObjectLabelDaoImpl();
        ObjectLabel objectLabel = objectLabelDao.getObjectLabelNaturalId(emsObjectId, currentLocale);
        //System.out.println("SystemNode - objectLabel: " + objectLabel + " and currentLocale: " + currentLocale + " and emsObjectId: " + emsObjectId);
        if (objectLabel != null && objectLabel.getId() > 0) {
            systemNode.setObjectLabel(objectLabel);
        }*/

        MetaInfo metaInfo = getMetaInfo(systemNode);
        if (metaInfo != null) {
            systemNode.setMetaInfo(metaInfo);
        }
        ObjectLabel objectLabel = getObjectLabel(systemNode);
        if (objectLabel != null) {
            systemNode.setObjectLabel(objectLabel);
        }


        return systemNode;
    }


    private Object getObject(Session session, Class entityClass, int id) {

        //System.out.println("Class: " + entityClass + " and id: " + id);
        return session.get(entityClass, id);
    }

    /*private void fillDocumentSimpleFolders(Content content, ChildrenMap children, Session session, int languageId) {
        DocumentDao documentDao = new DocumentDaoImpl();
        DocumentSimple document = (DocumentSimple) documentDao.getLastVersionDocument(content.getId(), languageId);
        if (document != null) {
            //System.out.println("document id: " + document.getId() + " title: " + document.getTitle());
            content.setDocument(DocumentSimple);

    }*/


    /* private boolean filteredByTag(int contentId, int languageId, Integer tagId) {
        DocumentDao documentDao = new DocumentDaoImpl();
        TagDao tagDao = new TagDaoImpl();
        Document document = documentDao.getLastVersionDocument(contentId, languageId);
        if (document.getTags().contains(tagDao.getTag(tagId))) {
            return true;
        } else {
            return false;
        }

    }*/

    //    private void fillDocumentFolders(Content content, ChildrenMap children, Session session, int languageId, int position) {
    private void fillDocumentFolders(Content content, ChildrenMap children, Session session, String languageCode, int position) {
        //System.out.println("fillDocumentFolders:>> ENtER");
        DocumentDao documentDao = new DocumentDaoImpl();
        Document document = documentDao.getDocumentByNaturalId(content.getId(), languageCode);

        if (document != null) {
            //System.out.println("fillDocumentFolders:>> document.getId(): " + document.getId());
            content.setDocument(document);
            for (Object obj : document.getFolders()) {
                Folder folder = (Folder) obj;
//                //System.out.println(folder.toExtendedString());
                children.putAll(getChildren(folder, 1, 0, 0, "position", "asc", 0, session, new HashSet<Integer>(), languageCode, position));
            }

        } else {
            //System.out.println("fillDocumentFolders:>>  Need Remove Content with Id: " + content.getId());
        }
    }


    private HashMap<Integer, SystemNodeObject> getSystemNodeObjects(SystemNode systemNode, int templatePositionsAmount) {
        return getSystemNodeObjects(systemNode, templatePositionsAmount, new HashMap<Integer, SystemNodeObject>());
    }


    private HashMap<Integer, SystemNodeObject> getSystemNodeObjects(SystemNode systemNode, int templatePositionsAmount, HashMap<Integer, SystemNodeObject> systemNodeObjectMap) {
        for (SystemNodeTypifiedObject systemNodeTypifiedObject : systemNode.getTypifiedObjects()) {
            if (!systemNodeObjectMap.containsKey(systemNodeTypifiedObject.getPosition())) {
                systemNodeObjectMap.put(systemNodeTypifiedObject.getPosition(), systemNodeTypifiedObject);
            }
        }
        for (SystemNodeObjectType systemNodeObjectType : systemNode.getObjectTypes()) {
            if (!systemNodeObjectMap.containsKey(systemNodeObjectType.getPosition())) {
                systemNodeObjectMap.put(systemNodeObjectType.getPosition(), systemNodeObjectType);
            }
        }
        if (systemNodeObjectMap.size() < templatePositionsAmount && systemNode.getTemplateId() == 0 && systemNode.getEmsObject().getParentId() != null && systemNode.getEmsObject().getParentId() > 0) {
//            SystemNode parentSystemNode = (SystemNode) systemNode.getEmsObject().getParent();
            int parentId = systemNode.getEmsObject().getParentId();
            TypifiedObjectDao typifiedObjectDao = new TypifiedObjectDaoImpl();
            SystemNode parentSystemNode = (SystemNode) typifiedObjectDao.getParent(parentId);
            systemNodeObjectMap = getSystemNodeObjects(parentSystemNode, templatePositionsAmount, systemNodeObjectMap);
        }
        return systemNodeObjectMap;
    }


    private ChildrenMap getChildren(TypifiedObject typifiedObject, int levels, int currentLevel, int itemsOnPage, String sortField, String sortDirection, Integer tagId, Session session, HashSet<Integer> systemNodeIdsForUrls, String languageCode, int position) {
        currentLocale = languageCode;
//    private ChildrenMap getChildren(TypifiedObject typifiedObject, int levels, int currentLevel, int itemsOnPage, String sortField, String sortDirection, Integer tagId, Session session, HashSet<Integer> systemNodeIdsForUrls, int languageId, int position) {
//        //System.out.println("typifiedObject.toExtendedString() = " + typifiedObject.toExtendedString());
        ChildrenMap children = new ChildrenMap();
        int typifiedObjectId = typifiedObject.getId();
        int typifiedObjectParentId = typifiedObject.getParentId();

        System.out.println("CLASS ID =  " + typifiedObjectId);
        System.out.println("CLASS PARENT ID =  " + typifiedObjectParentId);
        System.out.println("itemsOnPage =  " + itemsOnPage);
        if (typifiedObject instanceof SystemObject) {
            String typifiedObjectClass = typifiedObject.getClass().getSimpleName();
            System.out.println("CLASS NAME =  " + typifiedObjectClass);
            List list;
            Query query;
            StringBuilder sql = new StringBuilder("");

            Integer page = 1;
//             else {

            /*if (filter != null){
                var children = typifiedObjectService.getChildren(parentId, (page - 1) * itemsOnPage, itemsOnPage, sortName, sortDesc, filter);
            }else{*/
            TypifiedObjectDao typifiedObjectDao = null;
//            TypifiedObjectService typifiedObjectService = null;
            if (typifiedObjectClass == "Content") {
                list = typifiedObjectDao.getObjects(typifiedObjectParentId, (page - 1) * itemsOnPage, itemsOnPage, sortField, true, null);
            } else {
                            //System.out.println("!!! Ищем объекты у который emsObject.parentId =  " + typifiedObjectId);
            sql.append("from ");
            sql.append(typifiedObjectClass);
            sql.append(" where emsObject.parentId = ");
            sql.append(typifiedObject.getId());


            sql.append(" and entity != 'Content'");
            sql.append(" order by ").append(sortField).append(" ").append(sortDirection);
            ////System.out.println("sql = " + sql);

            query = session.createQuery(sql.toString());

/*
            if (itemsOnPage > 0) {
                query.setMaxResults(itemsOnPage);
            }
*/
            list = query.list();

//                list = typifiedObjectDao.getObjects(typifiedObjectParentId, 0, 0, "", false, null);
            }



            DocumentDao documentDao = new DocumentDaoImpl();
            if (typifiedObject instanceof Content && documentDao.getDocumentByNaturalId(typifiedObjectId, languageCode) != null) {


                sql = new StringBuilder("from ");
                sql.append(typifiedObjectClass);
                sql.append(" where emsObject.parentId = ");
                sql.append(typifiedObject.getId());
//                List list = contentDao.
                sql.append(" and publishDateTime <= current_timestamp() ");
                if (tagId != null) {
                    //System.out.println("!!! tagId " + tagId);
                    /*    StringBuilder contentSql = new StringBuilder("select d.content_id from document d");
//                    contentSql.append(" inner join document_tag dt on d.id = dt.document_id");
//                    contentSql.append(" where dt.tag_id = ").append(tagId);
//                    contentSql.append(" and d.language_id = ").append(languageId);
                    contentSql.append(" and d.language_code = ").append(languageCode);
//                    contentSql.append(" and d.version = (select max(d1.version) from document d1 where d1.content_id = d.content_id and d1.language_id = ").append(languageId).append(")");
//                    contentSql.append(" and d.version = (select d1.version from document d1 where d1.is_last_version = 1 and d1.content_id = d.content_id and d1.language_code = ").append(languageCode).append(")");
                    List contentIds = session.createSQLQuery(contentSql.toString()).list();
                    sql.append(" and id in (");
                    if (contentIds.size() > 0) {
                        sql.append(Utilities.implode(contentIds, ","));
                    } else {
                        sql.append("''");
                    }
                    sql.append(")");*/
                }
                sql.append(" order by ").append(sortField).append(" ").append(sortDirection);
                query = session.createQuery(sql.toString());

                if (itemsOnPage > 0) {
                    query.setMaxResults(itemsOnPage);
                }
                list.addAll(query.list());
            }
            if (typifiedObject instanceof Folder) {
                sql = new StringBuilder("from ");
                sql.append(FileObject.class.getSimpleName());
                sql.append(" where emsObject.parentId = ");
                sql.append(typifiedObject.getId());
                sql.append(" order by position");
                query = session.createQuery(sql.toString());
                list.addAll(query.list());
            }

            TypifiedObject[] systemObjects = (TypifiedObject[]) list.toArray(new TypifiedObject[list.size()]);
            children.put(typifiedObject.getId(), typifiedObject.getSystemName(), position, systemObjects);
            for (TypifiedObject tObject : systemObjects) {

                if (tObject instanceof Content) {
                    Content content = (Content) tObject;

                    MetaInfo metaInfo = getMetaInfo(content);
                    if (metaInfo != null) {
                        content.setMetaInfo(metaInfo);
                    }
                    ObjectLabel objectLabel = getObjectLabel(content);
                    if (objectLabel != null) {
                        content.setObjectLabel(objectLabel);
                    }

                    if (content.getHomeId() > 0) {
                        systemNodeIdsForUrls.add(content.getHomeId());
                    }
                    /*  if (tagId != null) {
                    //System.out.println("tagId != null"+tagId);
                    session.enableFilter("hasTag").setParameter("tagId", tagId);
                } else
                //System.out.println("tagId = null"+tagId);
                    session.disableFilter("hasTag");*/


                    fillDocumentFolders(content, children, session, languageCode, position);

                } else if (tObject instanceof Navigation) {
                    Navigation navigation = (Navigation) tObject;

                    ObjectLabel objectLabel = getObjectLabel(navigation);
                    if (objectLabel != null) {
                        navigation.setObjectLabel(objectLabel);
                    }

                    if (navigation.getSystemNodeId() != null && navigation.getSystemNodeId() > 0) {
                        systemNodeIdsForUrls.add(navigation.getSystemNodeId());
                    }
                }
            }

            currentLevel++;
            if (currentLevel < levels) {
                for (Object obj : list) {
                    children.putAll(getChildren((TypifiedObject) obj, levels, currentLevel, itemsOnPage, sortField, sortDirection, tagId, session, systemNodeIdsForUrls, languageCode, position));
//                    children.putAll(getChildren((TypifiedObject) obj, levels, currentLevel, itemsOnPage, sortField, sortDirection, tagId, session, systemNodeIdsForUrls, languageId, position));
                }
            }
        }
        return children;
    }


    //    private ChildrenMap getChildren(ObjectType objectType, int levels, int currentLevel, int itemsOnPage, String sortField, String sortDirection, Integer tagId, Session session, HashSet<Integer> systemNodeIdsForUrls, String languageCode) {
//    private ChildrenMap getChildren(ObjectType objectType, int levels, int currentLevel, int itemsOnPage, String sortField, String sortDirection, Integer tagId, Session session, HashSet<Integer> systemNodeIdsForUrls, int languageId, int position) {
    private ChildrenMap getChildren(ObjectType objectType, int levels, int currentLevel, int itemsOnPage, String sortField, String sortDirection, Integer tagId, Session session, HashSet<Integer> systemNodeIdsForUrls, String languageCode, int position) {


        ChildrenMap children = new ChildrenMap();

        StringBuilder sb = new StringBuilder("from TypifiedObject to where to.objectType = ?");
        if (objectType.isHierarchical()) {
            sb.append(" and to.emsObject.parentId is null");
        }
        sb.append("order by id desc");
        Query query = session.createQuery(sb.toString());

        if (itemsOnPage > 0) {
            query.setMaxResults(itemsOnPage);
        }

        query.setParameter(0, objectType);
        List list = query.list();

        if (list != null) {
            TypifiedObject[] systemObjects = (TypifiedObject[]) list.toArray(new TypifiedObject[list.size()]);
            children.put(objectType.getId(), objectType.getName(), position, systemObjects);
            currentLevel++;
            for (Object obj : list) {

                TypifiedObject to = (TypifiedObject) obj;
//                //System.out.println("systemObjects " + to.getName());
                if (currentLevel < levels) {
                    children.putAll(getChildren(to, levels, currentLevel, itemsOnPage, sortField, sortDirection, tagId, session, systemNodeIdsForUrls, languageCode, position));
//                    children.putAll(getChildren(to, levels, currentLevel, itemsOnPage, sortField, sortDirection, tagId, session, systemNodeIdsForUrls, languageId, position));
                }
            }
        }
        return children;


    }

    private MetaInfo getMetaInfo(SystemObject systemObject) {
        MetaInfoDao metaInfoDao = new MetaInfoDaoImpl();
        int emsObjectId = systemObject.getEmsObject().getId();
        MetaInfo metaInfo = metaInfoDao.getMetaInfoNaturalId(emsObjectId, currentLocale);


        return metaInfo;
    }

    private ObjectLabel getObjectLabel(SystemObject systemObject) {
        int emsObjectId = systemObject.getEmsObject().getId();
        ObjectLabelDao objectLabelDao = new ObjectLabelDaoImpl();
        ObjectLabel objectLabel = objectLabelDao.getObjectLabelNaturalId(emsObjectId, currentLocale);
//        //System.out.println("TypifiedObject - objectLabel: " + objectLabel + " and currentLocale: " + currentLocale + " and emsObjectId: " + emsObjectId);

        return objectLabel;
    }

}
