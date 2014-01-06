package ru.strela.ems.core.dao.hibernate;


import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.strela.ems.core.dao.*;
import ru.strela.ems.core.model.*;
import ru.strela.ems.generator.ChildrenMap;
import ru.strela.ems.generator.SitePageGenerator;
import ru.strela.ems.tools.ServerTools;
import ru.tastika.tools.util.Utilities;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;


/**
 * User: hobal
 * Date: 12.05.2010
 * Time: 23:55:19
 */
public class SiteProcessorDaoImpl implements SiteProcessorDao {

    private String objectURL;
    private int temporaryCountItemsValue;
    private String currentLocale;
    private String requestQueryString;
    private Map requestParameters;
    private final static Logger log = LoggerFactory.getLogger(SiteProcessorDaoImpl.class);
//    private ArrayList<String> documentTypes = new ArrayList<String>();


    public SiteProcessorDaoImpl() {
        super();
    }

    protected Session getCurrentSession() {
        log.warn("getCurrentSession()");
        Session session = HibernateUtil.currentSession();
        log.warn("beginTransaction()");
        HibernateUtil.beginTransaction();
        return session;
    }


    protected void closeSession() {
        log.warn("commitTransaction()");
        HibernateUtil.commitTransaction();
        log.warn("closeSession()");
        HibernateUtil.closeSession();
    }


    public TreeMap<String, Object> getSystemObjects(String systemNamesPath, String indexPage, String languageCode) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    //    public TreeMap<String, Object> getSystemObjects(String systemNamesPath, String indexPage, int languageId) {
//    public TreeMap<String, Object> getSystemObjects(String systemNamesPath, String indexPage, String languageCode, String requestQueryString) {
    public TreeMap<String, Object> getSystemObjects(String systemNamesPath, String indexPage, String languageCode, Map requestParameters) {
        currentLocale = languageCode;
        TreeMap<String, Object> systemObjects = new TreeMap<String, Object>();

        Session session = getCurrentSession();
        this.requestParameters = requestParameters;
//        this.requestQueryString = requestQueryString;
//        log.warn("requestQueryString!! " + requestQueryString);
//    kremnef
        session.setDefaultReadOnly(true);

        /*Transaction tx = null;
        try {
            tx = session.beginTransaction();
*/
        if (systemNamesPath.length() == 0) {
            systemNamesPath = indexPage;
        }


        log.warn("1. System.currentTimeMillis() = " + System.currentTimeMillis());

        SitePageGenerator.documentTypes.clear();
//            log.warn("systemNamesPath = " + systemNamesPath);

        HashSet<Integer> redirectCycle = new HashSet<Integer>();
        // s.id, s.redirect_to s.full_path, e.system_name, t.id template_id, t.positions_amount
        ArrayList<Object[]> pathData = getPathData(session, systemNamesPath);
        if (pathData.size() > 0) {
            String systemNodesUrl = (String) pathData.get(pathData.size() - 1)[2];
//                log.warn("systemNodesUrl = " + systemNodesUrl);
            String objectURL = "";
            if (systemNamesPath.length() > systemNodesUrl.length()) {
                objectURL = systemNamesPath.substring(systemNodesUrl.length() + 1);
//                    если к странице добавлен документ с вложенностью
                if (objectURL.contains("/")) {

                    objectURL = objectURL.substring(0, objectURL.lastIndexOf("/"));
                }
                this.objectURL = objectURL;
                log.warn("objectURL = " + objectURL);
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
//                                String rendreLike = systemNodeObject.getRenderLike();
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
//                                    Основное выполение
                                SystemNodeTypifiedObject systemNodeTypifiedObject = (SystemNodeTypifiedObject) systemNodeObject;
                                TypifiedObject typifiedObject = systemNodeTypifiedObject.getTypifiedObject();
                                if (typifiedObject instanceof SystemObject) {
                                    int levels = systemNodeObject.getLevels();
//                                        Определяем глубину отображаемых элементов
                                    log.warn("typifiedObject = " + typifiedObject.getName());
                                    log.warn("levels = " + levels);


                                    if (levels > 0) {
                                        SystemObject systemObject = (SystemObject) typifiedObject;
                                        int contentChildrenCount = systemObject.getEmsObject().getChildrenCount();
                                        if (contentChildrenCount > 1) {
                                            temporaryCountItemsValue = contentChildrenCount;
//                                            System.out.println("temporaryCountItemsValue 1:" + temporaryCountItemsValue);
                                            children.putAll(getChildren(typifiedObject, levels, 0, systemNodeTypifiedObject.getItemsOnPage(), sortField, sortDirection, systemNodeTypifiedObject.getTagId(), session, systemNodeIdsForUrls, languageCode, position));
//                                            System.out.println("temporaryCountItemsValue 3:" + temporaryCountItemsValue);
//                                            System.out.println("systemNodeObject.getItemsOnPage() " + systemNodeObject.getItemsOnPage());

                                        }
                                    }
                                    if (typifiedObject instanceof Content) {
                                        Content content = (Content) typifiedObject;
                                        String documentTypeName = content.getDocumentType().getName();


                                        if (!SitePageGenerator.documentTypes.contains(documentTypeName)) {
                                            SitePageGenerator.documentTypes.add(documentTypeName);
                                        }
//                                            log.warn("content = " + content.getName());
//                                            request.setAttribute(DOCUMENT_TYPES, documentTypes);


                                        if (content.getHomeId() > 0) {
                                            systemNodeIdsForUrls.add(content.getHomeId());
                                        }

//                                        System.out.println("systemNodeObject.getID:" + systemNodeObject.getId());
//                                        System.out.println("systemNodeObject.getItemsOnPage():" + systemNodeObject.getItemsOnPage());
                                        int itemsOnPage = systemNodeObject.getItemsOnPage();
                                        int totalPages = 1;
                                        if (itemsOnPage > 0) {
                                            totalPages = Math.round(temporaryCountItemsValue / itemsOnPage) + 1;
                                        }
                                        ;
                                        systemNodeObject.setTotalPages(totalPages);
//                                            todo: need move to ContentDao
                                        setDocumentToCurrentContent(content, children, session, languageCode, position);
                                    } else if (typifiedObject instanceof Navigation) {
                                        Navigation navigation = (Navigation) typifiedObject;
                                        if (navigation.getSystemNodeId() != null && navigation.getSystemNodeId() > 0) {
                                            systemNodeIdsForUrls.add(navigation.getSystemNodeId());
                                        }
                                    }
                                }
                            }
                            log.warn("4.1 System.currentTimeMillis() = " + System.currentTimeMillis());
                        }
                        if (children.size() > 0) {

                            systemObjects.put(SitePageGenerator.CHILDREN_KEY, children);
                        }

                        HashMap<Integer, String> systemNodeUrls = getSystemNodeUrls(session, systemNodeIdsForUrls);
                        setFullUrltoObject(systemNodeObjectMap.values(), systemNodesUrl, objectURL, systemNodeUrls);
                        setFullUrltoObjectChildren(children, systemNodesUrl, objectURL, systemNodeUrls);
                    }
                }
            }
        }
        log.warn("5. System.currentTimeMillis() = " + System.currentTimeMillis());
            /*tx.commit();
            session.close();
        } catch (HibernateException he) {
            if (session.isOpen()) {
                if (tx != null && tx.isActive()) {
                    tx.rollback();
                }
            }
            throw he;
        }*/
        closeSession();
        return systemObjects;
    }


    private void fillTypifiedObjectUrls(TypifiedObject typifiedObject, String fullURL, String baseURL, String objectURL, HashMap<Integer, String> systemNodeUrls) {
        boolean customPath = false;
//        log.warn("fillTypifiedObjectUrls 1" +typifiedObject.getName());
        if (typifiedObject instanceof Content) {
            Content content = (Content) typifiedObject;
            MetaInfo metaInfo = getMetaInfo(content);
            if (metaInfo != null) {
                content.setMetaInfo(metaInfo);
            }
            ObjectLabel objectLabel = getObjectLabel(content);
            log.warn("ObjectLabel name " + content.getName() + " -- label -" + objectLabel);
            if (objectLabel != null) {
                content.setObjectLabel(objectLabel);
            }
            if (content.getHomeId() > 0) {
                String url = systemNodeUrls.get(content.getHomeId());
//                log.warn("url "+ url);
                if (url != null) {
                    customPath = true;
                    content.setBaseURL(this.currentLocale+"/"+url);
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
            typifiedObject.setBaseURL(this.currentLocale+"/"+baseURL);
            typifiedObject.setObjectURL(objectURL);
        }
    }


    private void setFullUrltoObject(Collection<SystemNodeObject> values, String baseURL, String objectURL, HashMap<Integer, String> systemNodeUrls) {
        String fullURL = baseURL.length() > 0 && objectURL.length() > 0 ? baseURL + "/" + objectURL : baseURL + objectURL;
//        fullURL = this.currentLocale+fullURL;
        System.out.println("setFullUrltoObject 1"+fullURL);
        for (SystemNodeObject systemNodeObject : values) {
            if (systemNodeObject instanceof SystemNodeTypifiedObject) {
                SystemNodeTypifiedObject systemNodeTypifiedObject = (SystemNodeTypifiedObject) systemNodeObject;
                TypifiedObject typifiedObject = systemNodeTypifiedObject.getTypifiedObject();
                fillTypifiedObjectUrls(typifiedObject, fullURL, baseURL, objectURL, systemNodeUrls);
            }
        }
    }


    private void setFullUrltoObjectChildren(ChildrenMap children, String baseURL, String objectURL, HashMap<Integer, String> systemNodeUrls) {

        String fullURL = baseURL.length() > 0 && objectURL.length() > 0 ? baseURL + "/" + objectURL : baseURL + objectURL;
//        fullURL = this.currentLocale+fullURL;
        System.out.println("setFullUrltoObject 2"+fullURL);
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

        MetaInfo metaInfo = getMetaInfo(systemNode);
        if (metaInfo != null) {
            systemNode.setMetaInfo(metaInfo);
        }


        ObjectLabel objectLabel = getObjectLabel(systemNode);
        log.warn("systemNode ObjectLabel name " + systemNode.getName() + " -- label -" + objectLabel);
        if (objectLabel != null) {
            systemNode.setObjectLabel(objectLabel);
        }


        return systemNode;
    }


    private Object getObject(Session session, Class entityClass, int id) {

        log.warn("Class: " + entityClass + " and id: " + id);
        return session.get(entityClass, id);
    }

    /*private void fillDocumentSimpleFolders(Content content, ChildrenMap children, Session session, int languageId) {
        DocumentDao documentDao = new DocumentDaoImpl();
        DocumentSimple document = (DocumentSimple) documentDao.getLastVersionDocument(content.getId(), languageId);
        if (document != null) {
            //log.warn("document id: " + document.getId() + " title: " + document.getTitle());
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

    //    private void setDocumentToCurrentContent(Content content, ChildrenMap children, Session session, int languageId, int position) {
    private void setDocumentToCurrentContent(Content content, ChildrenMap children, Session session, String languageCode, int position) {
        //log.warn("setDocumentToCurrentContent:>> ENtER");
        DocumentDao documentDao = new DocumentDaoImpl();
        Document document = documentDao.getDocumentByNaturalId(content.getId(), languageCode);

        if (document != null) {
            //log.warn("setDocumentToCurrentContent:>> document.getId(): " + document.getId());
            content.setDocument(document);
            for (Object obj : document.getFolders()) {
                Folder folder = (Folder) obj;
//                //log.warn(folder.toExtendedString());
                children.putAll(getChildren(folder, 1, 0, 0, "position", "asc", 0, session, new HashSet<Integer>(), languageCode, position));
            }

        } else {
            //log.warn("setDocumentToCurrentContent:>>  Need Remove Content with Id: " + content.getId());
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

    public static Map<String, String> getUrlParameters(String query)
            throws UnsupportedEncodingException {
        Map<String, String> params = new HashMap<String, String>();
        for (String param : query.split("&")) {
            String pair[] = param.split("=");
            String key = URLDecoder.decode(pair[0], "UTF-8");
            String value = "";
            if (pair.length > 1) {
                value = URLDecoder.decode(pair[1], "UTF-8");
            }
            params.put(key, value);
                /*List<String> values = params.get(key);
                if (values == null) {
                    values = new ArrayList<String>();
                    params.put(key, values);
                }
                values.add(value);*/
        }
//        }
        return params;
    }

    public static String getUrlParameter(Map<String, List<String>> params, String paramName)
            throws UnsupportedEncodingException {
        String value;
        value = params.get(paramName).get(1);
        /*for(int i=0; params.size()>i; i++){

            params.containsKey(paramName);
        }*/

        return value;
    }

    public static String getUrlParameterValue(String pairUrl)
            throws UnsupportedEncodingException {

        String pair[] = pairUrl.split("=");
//            String key = URLDecoder.decode(pair[0], "UTF-8");
        String value = "";
        if (pair.length > 1) {
            value = URLDecoder.decode(pair[1], "UTF-8");
        }
        return value;
    }


    //    Получить все объекты для которых задан уровень больше 0
    private ChildrenMap getChildren(TypifiedObject typifiedObject, int levels, int currentLevel, int itemsOnPage, String sortField, String sortDirection, Integer tagId, Session session, HashSet<Integer> systemNodeIdsForUrls, String languageCode, int position) {
        currentLocale = languageCode;
//    private ChildrenMap getChildren(TypifiedObject typifiedObject, int levels, int currentLevel, int itemsOnPage, String sortField, String sortDirection, Integer tagId, Session session, HashSet<Integer> systemNodeIdsForUrls, int languageId, int position) {
//        //log.warn("typifiedObject.toExtendedString() = " + typifiedObject.toExtendedString());


        ChildrenMap children = new ChildrenMap();
        Integer typifiedObjectId = typifiedObject.getId();
//        Integer typifiedObjectParentId = typifiedObject.getParentId();

//        log.warn("CLASS ID =  " + typifiedObjectId);
//        log.warn("CLASS PARENT ID =  " + typifiedObjectParentId);
//        log.warn("itemsOnPage =  " + itemsOnPage);


        if (typifiedObject instanceof SystemObject) {
            String typifiedObjectClass = typifiedObject.getClass().getSimpleName();
//            log.warn("CLASS NAME =  " + typifiedObjectClass);
            List list = null;
            Query query;
            StringBuilder sql = new StringBuilder("");


            if (typifiedObject instanceof Content) {
                DocumentDao documentDao = new DocumentDaoImpl();
                if (documentDao.getDocumentByNaturalId(typifiedObjectId, languageCode) != null) {


                    Integer pageNumber = 1;
//                    if (requestQueryString != null && requestQueryString.contains("page")) {
                    if (requestParameters.containsKey("page")) {
//                        try {
//                            Map<String, String> paramsMap = getUrlParameters(requestQueryString);
                        Map<String, String> paramsMap = requestParameters;
//                            String pageValue = null;
                        Set<String> keys = paramsMap.keySet();
                        for (String key : keys) {
                            if (key.equalsIgnoreCase("page")) {
                                pageNumber = Integer.parseInt(paramsMap.get(key));
//                                    pageValue = paramsMap.get(key);
                            }
                            if (key.equalsIgnoreCase("itemsOnPage")) {
                                itemsOnPage = Integer.parseInt(paramsMap.get(key));
                            }
                            if (key.equalsIgnoreCase("sortField")) {
                                sortField = paramsMap.get(key);
                            }
                            if (key.equalsIgnoreCase("sortDirection")) {
                                sortDirection = paramsMap.get(key);
                            }

                        }
//                            pageNumber = Integer.parseInt(pageValue);
                        /*} catch (UnsupportedEncodingException e) {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }*/

                    }
//                    int contentChildrenCount = typifiedObject.getEmsObject().getChildrenCount();
//                    ContentDaoImpl contentDaoImpl = new ContentDaoImpl();
//                    list = contentDaoImpl.getChildren(typifiedObjectId, (pageNumber - 1) * itemsOnPage, itemsOnPage, sortField, true, null);
                    sql = new StringBuilder("from ");
                    sql.append(typifiedObjectClass);
                    sql.append(" where");
                    if (tagId != null) {
                        MetaInfoDao metaInfoDao = new MetaInfoDaoImpl();
                        List<MetaInfo> objectsIds = metaInfoDao.getObjectIdsByTagId(tagId);
                        temporaryCountItemsValue = objectsIds.size();
//                        System.out.println("temporaryCountItemsValue:" + temporaryCountItemsValue);
                        int lastObjectId = objectsIds.get(objectsIds.size() - 1).getObjectId();
//                        System.out.println("lastObjectId " + lastObjectId);
                        if (objectsIds != null) {
                            sql.append(" emsObject.id IN ( ");
                            for (MetaInfo mObject : objectsIds) {
                                int objectId = mObject.getObjectId();
                                sql.append(objectId);
                                if (objectId != lastObjectId) {
                                    sql.append(",");
                                }
                            }
                            sql.append(" ) and ");

                        }
                    }
                    sql.append(" emsObject.parentId = ");
                    sql.append(typifiedObjectId);


                    sql.append(" and publishDateTime <= current_timestamp()");
                    sql.append(" order by ").append(sortField).append(" ").append(sortDirection);


                    query = session.createQuery(sql.toString());
//                    System.out.println("SQL:" + sql.toString());
                    query.setFirstResult((pageNumber - 1) * itemsOnPage);
                    if (itemsOnPage > 0) {
                        query.setMaxResults(itemsOnPage);
//                        query.setFetchSize(itemsOnPage);
                    }
                    list = query.list();
                }

            } else {
                sql.append("from ");
                sql.append(typifiedObjectClass);
                sql.append(" where emsObject.parentId = ");
                sql.append(typifiedObjectId);
                sql.append(" order by ").append(sortField).append(" ").append(sortDirection);
                ////log.warn("sql = " + sql);

                query = session.createQuery(sql.toString());
                if (itemsOnPage > 0) {
                    query.setMaxResults(itemsOnPage);
                }
                list = query.list();

            }
            if (typifiedObject instanceof Folder) {
                sql = new StringBuilder("from ");
                sql.append(FileObject.class.getSimpleName());
                sql.append(" where emsObject.parentId = ");
                sql.append(typifiedObjectId);
                sql.append(" order by position");
                query = session.createQuery(sql.toString());
                list.addAll(query.list());
            }

            if (list != null) {
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
                        log.warn("ObjectLabel name " + content.getName() + " -- label -" + objectLabel);
                        if (objectLabel != null) {
                            content.setObjectLabel(objectLabel);
                        }

                        if (content.getHomeId() > 0) {
                            systemNodeIdsForUrls.add(content.getHomeId());
                        }


                    /*
                    //log.warn("tagId != null"+tagId);
                    session.enableFilter("hasTag").setParameter("tagId", tagId);
                } else
                //log.warn("tagId = null"+tagId);
                    session.disableFilter("hasTag");*/

//                    добавить условие только для ObjectURL
                        if (this.objectURL != null && content.getEmsObject().getSystemName().contains(this.objectURL)) {
                            setDocumentToCurrentContent(content, children, session, languageCode, position);
                        }

                    } else if (tObject instanceof Navigation) {
                        Navigation navigation = (Navigation) tObject;

                        ObjectLabel objectLabel = getObjectLabel(navigation);
                        if (objectLabel != null) {
                            navigation.setObjectLabel(objectLabel);
                        }

                        if (navigation.getSystemNodeId() != null && navigation.getSystemNodeId() > 0) {
                            systemNodeIdsForUrls.add(navigation.getSystemNodeId());
                        }

  /*              } else if (tObject instanceof Folder) {
                    Navigation navigation = (Navigation) tObject;

                    ObjectLabel objectLabel = getObjectLabel(navigation);
                    if (objectLabel != null) {
                        navigation.setObjectLabel(objectLabel);
                    }

                    if (navigation.getSystemNodeId() != null && navigation.getSystemNodeId() > 0) {
                        systemNodeIdsForUrls.add(navigation.getSystemNodeId());
                    }
  */
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
//                //log.warn("systemObjects " + to.getName());
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

        return metaInfoDao.getMetaInfoNaturalId(emsObjectId, currentLocale);
    }

    private ObjectLabel getObjectLabel(SystemObject systemObject) {
        int emsObjectId = systemObject.getEmsObject().getId();
        ObjectLabelDao objectLabelDao = new ObjectLabelDaoImpl();
        //        //log.warn("TypifiedObject - objectLabel: " + objectLabel + " and currentLocale: " + currentLocale + " and emsObjectId: " + emsObjectId);

        return objectLabelDao.getObjectLabelNaturalId(emsObjectId, currentLocale);
    }

}
