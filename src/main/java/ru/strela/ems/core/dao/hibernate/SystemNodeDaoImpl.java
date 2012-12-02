package ru.strela.ems.core.dao.hibernate;


import org.hibernate.*;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.strela.ems.core.dao.SystemNodeDao;
import ru.strela.ems.core.dao.TypifiedObjectDao;
import ru.strela.ems.core.model.*;
import ru.tastika.tools.util.Utilities;

import java.util.*;


public class SystemNodeDaoImpl extends SystemObjectDaoImpl implements SystemNodeDao {


    private final static Logger log = LoggerFactory.getLogger(SystemNodeDaoImpl.class);


    public SystemNodeDaoImpl() {
        super();
    }


    @Override
    public Class getEntityClass() {
        return SystemNode.class;
    }


    public SystemNode getSystemNode(int systemNodeId) {
//    public SystemNode getSystemNode(int systemNodeId) {
        Session session = getCurrentSession();
        SystemNode systemNode = (SystemNode) getTypifiedObject(systemNodeId);
        return systemNode;
    }


    public String getSystemNodeName(int systemNodeId) {
        Session session = getCurrentSession();
        Query query = session.createQuery("from systemNode where id =" + systemNodeId);
        return query.toString();
    }

    public SystemNode getSystemNode(int id, boolean withFirstParent, boolean withTemplate) {
        return getSystemNode(id, withFirstParent, withTemplate, false);
    }


    public SystemNode getSystemNode(int id, boolean withFirstParent, boolean withTemplate, boolean withTypeActions) {
        Session session = getCurrentSession();
        SystemNode systemNode = (SystemNode) session.get(getEntityClass(), id);
        if (systemNode != null) {
            TypifiedObjectDao typifiedObjectDao = new TypifiedObjectDaoImpl();
            Integer parentSystemNodeId;
            parentSystemNodeId = systemNode.getEmsObject().getParentId();
//            System.out.println("parentSystemNodeId" + parentSystemNodeId);

            SystemNode parentSystemNode = null;
            if (parentSystemNodeId!= null && parentSystemNodeId > 0) {
                parentSystemNode = (SystemNode) typifiedObjectDao.getParent(parentSystemNodeId);
//                System.out.println("parentSystemNode" + parentSystemNode.getId());

                if (withFirstParent && parentSystemNodeId != null && systemNode.getEmsObject().getParentId() > 0 && parentSystemNode != null) {
//            if (withFirstParent && systemNode.getEmsObject().getParentId() != null && systemNode.getEmsObject().getParentId() > 0 && systemNode.getEmsObject().getParent() != null) {
//                    ((SystemObject) systemNode.getEmsObject().getParent()).getSystemName();
                    parentSystemNode.getSystemName();
                }
            }

            if (withTypeActions) {
                Set<ObjectTypeAction> objectTypeActions = systemNode.getObjectType().getTypeActions();
            }

            int templateId = 0;

            List<SystemNodeObjectsData> datas = systemNode.getObjectsDataList();
            ArrayList<SystemNodeObjectsData> parentDatas = new ArrayList<SystemNodeObjectsData>(datas.size());
            ////log.info("systemNode.getTemplateId() = " + systemNode.getTemplateId());

            if (withTemplate) {
                if (systemNode.getTemplateId() == 0) {
                    HashMap<Integer, SystemNodeObjectsData> systemNodeObjectsDataMap = new HashMap<Integer, SystemNodeObjectsData>();
                    HashMap<Integer, SystemNodeObjectsData> parentSystemNodeObjectsData = new HashMap<Integer, SystemNodeObjectsData>();
                    for (SystemNodeObjectsData objectsData : systemNode.getObjectsDataList()) {
                        systemNodeObjectsDataMap.put(objectsData.getPosition(), objectsData);
                    }
                    if (systemNode.getEmsObject().getParentId() != null) {
//                       SystemNode parentSystemNode = (SystemNode) session.get(getEntityClass(), systemNode.getEmsObject().getParentId());
                        ////log.info("parentSystemNode = " + parentSystemNode);
                        while (parentSystemNode != null) {
                            ////log.info("parentSystemNode.getObjectsDataList() = " + parentSystemNode.getObjectsDataList());
                            for (SystemNodeObjectsData parentObjectsData : parentSystemNode.getObjectsDataList()) {
                                int position = parentObjectsData.getPosition();
                                ////log.info("position = " + position);
                                SystemNodeObjectsData data = systemNodeObjectsDataMap.get(position);
                                if (data == null || data.getId() == 0) {
                                    systemNodeObjectsDataMap.put(position, (SystemNodeObjectsData) parentObjectsData.clone());
                                }
                                data = parentSystemNodeObjectsData.get(position);
                                if (data == null || data.getId() == 0) {
                                    parentSystemNodeObjectsData.put(position, parentObjectsData);
                                }
                            }
                            if (parentSystemNode.getTemplateId() == 0) {
                                parentSystemNode = (SystemNode) session.get(getEntityClass(), parentSystemNode.getEmsObject().getParentId());
                            } else {
                                templateId = parentSystemNode.getTemplateId();
                                parentSystemNode = null;
                            }
                            ////log.info("1. parentSystemNode = " + parentSystemNode);
                        }
                    }
                    ////log.info("systemNodeObjectsDataMap = " + systemNodeObjectsDataMap);
                    ////log.info("parentSystemNodeObjectsData = " + parentSystemNodeObjectsData);

                    ////log.info("templateId = " + templateId);
                    if (templateId > 0) {
                        Template template = (Template) session.get(Template.class, templateId);
                        systemNode.setRealTemplate(template);
                        ////log.info("template = " + template);
                        int amount = template.getPositionsAmount();
                        ////log.info("amount = " + amount);
                        for (int i = 0; i < amount; i++) {
                            ////log.info("i = " + i);
                            SystemNodeObjectsData data = systemNodeObjectsDataMap.get(i);
                            ////log.info("1. data = " + data);
                            if (data != null) {
                                systemNode.setObjectsData(i, data);
                            } else {
                                systemNode.setObjectsData(i);
                            }
                            data = parentSystemNodeObjectsData.get(i);
                            ////log.info("2. data = " + data);
                            if (data != null) {
                                systemNode.setParentObjectsData(i, data);
                            } else {
                                systemNode.setParentObjectsData(i);
                            }
                        }
                    }
                } else {
                    Template template = (Template) session.get(Template.class, systemNode.getTemplateId());
                    systemNode.setTemplate(template);
                }
            }
        }
        closeSession();
        return systemNode;
    }


    public List getSystemNodes() {
        return getObjects();
    }


    public List getSystemNodes(final int parentId) {
        return getChildren(parentId);
    }


    public SystemNode getSystemNodeByURI(String nodeURI) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        SystemNode systemNode = (SystemNode) session.load(SystemNode.class, nodeURI);
        try {
            tx = session.beginTransaction();

            tx.commit();
            session.close();
            log.warn("closeSession");

        } catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
            throw he;
        }
        return systemNode;
    }


    /*public List getSystemNodeByURI( final String nodeURI) {
    return getHibernateTemplate().executeFind(new HibernateCallback() {
    public Object doInHibernate(Session session) throws HibernateException {
    Criteria criteria = session.createCriteria(SystemNode.class);
    criteria.add(Restrictions.eq("nodeURI", nodeURI));
    return criteria.list();
    }
    });
    }*/


    public List findSystemNodes(final String[] descriptions) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        Criteria criteria = session.createCriteria(SystemNode.class);
        Criterion criterion = null;
        List list = new ArrayList();
        try {
            tx = session.beginTransaction();

            for (int i = 0; i < descriptions.length; i++) {
                String description = descriptions[i].trim();
                if (description.length() > 0) {
                    if (criterion == null) {
                        criterion = Restrictions.like("nodeURI", description, MatchMode.ANYWHERE);
                    } else {
                        criterion = Restrictions.or(criterion, Restrictions.like("description", description, MatchMode.ANYWHERE));
                    }
                    criterion = Restrictions.or(criterion, Restrictions.like("code", description, MatchMode.ANYWHERE));
                }
            }
            list = criteria.list();
            tx.commit();
            session.close();
            log.warn("closeSession");


        } catch (HibernateException he) {
            if (tx != null) {
                tx.rollback();
            }
            throw he;
        }
        if (criterion != null) {
            criteria.add(criterion);

            return list;
        } else {

            return new ArrayList();
        }
    }


    @Override
    protected TypifiedObject saveObject(TypifiedObject typifiedObject) {
        boolean fullURLChanges = false;
        Session session = getCurrentSession();
        if (typifiedObject instanceof SystemNode) {
            SystemNode systemNode = (SystemNode) typifiedObject;
            ArrayList updatedObjectsData = systemNode.synchronizeObjectsWithDataList();
            HashSet<Integer> updatedTypifiedObjects = new HashSet<Integer>();
            HashSet<Integer> updatedObjectTypes = new HashSet<Integer>();
            for (Object updatedObject : updatedObjectsData) {
                if (updatedObject instanceof TypifiedObject || updatedObject instanceof ObjectType) {
                    session.update(updatedObject);
                } else {
                    int[] intData = (int[]) updatedObject;
                    if (intData[1] == 1) {
                        updatedObjectTypes.add(intData[0]);
                    } else {
                        updatedTypifiedObjects.add(intData[0]);
                    }
                }
            }
            if (updatedObjectTypes.size() > 0) {
                List list = session.createQuery("from ObjectType where id in (" + Utilities.implode(updatedObjectTypes, ",") + ")").list();
                for (Object objectTypeObj : list) {
                    ObjectType objectType = (ObjectType) objectTypeObj;

                    for (SystemNodeObjectType systemNodeObjectType : systemNode.getObjectTypes()) {
                        if (systemNodeObjectType.getObjectId() == objectType.getId()) {
                            systemNodeObjectType.setObjectType(objectType);
                        }
                    }
                }
            }
            if (updatedTypifiedObjects.size() > 0) {
                List list = session.createQuery("from TypifiedObject where id in (" + Utilities.implode(updatedTypifiedObjects, ",") + ")").list();
                for (Object typifiedObjectObj : list) {
                    TypifiedObject to = (TypifiedObject) typifiedObjectObj;

                    for (SystemNodeTypifiedObject systemNodeTypifiedObject : systemNode.getTypifiedObjects()) {
                        if (systemNodeTypifiedObject.getObjectId() == to.getId()) {
                            systemNodeTypifiedObject.setTypifiedObject(to);
                        }
                    }
                }
            }
            if (systemNode.getId() > 0) {
                String initialFullURL = getSystemNodeFullURL(session, systemNode.getId());
                fullURLChanges = !initialFullURL.equals(systemNode.getFullURL());
            }
        }

        super.saveObject(typifiedObject);
        if (typifiedObject instanceof SystemNode && fullURLChanges) {
            updatePath(session, (SystemNode) typifiedObject);
        }
        closeSession();
        return typifiedObject;
    }


    private String getSystemNodeFullURL(Session session, int systemNodeId) {
        List list = session.createQuery("select fullURL from SystemNode where id = " + systemNodeId).list();
        return list.size() > 0 ? list.get(0).toString() : "";
    }


    private void updatePath(Session session, SystemNode systemNode) {
        String fullUrl = getParentPath(systemNode.getId(), false);
        systemNode.setFullURL(fullUrl + systemNode.getSystemName());
        session.update(systemNode);
        updateChildrenFullURL(session, systemNode);
    }


    private void updateChildrenFullURL(Session session, SystemNode systemNode) {
        for (TypifiedObject to : getChildren(systemNode)) {
            SystemNode childNode = (SystemNode) to;
            String parentFullPath = systemNode.getFullURL();
            childNode.setFullURL(parentFullPath.length() > 0 ? parentFullPath + "/" + childNode.getSystemName() : childNode.getSystemName());
            session.update(childNode);
            updateChildrenFullURL(session, childNode);
        }
    }


    private List<Navigation> getNavigationsBySystemNodeId(final int systemNodeId) {
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(Navigation.class);
        criteria.add(Restrictions.eq("systemNodeId", systemNodeId));
        List list = criteria.list();
        closeSession();
        return list;
    }


    @Override
    protected void deleteObject(TypifiedObject typifiedObject) {
        if (typifiedObject instanceof SystemNode) {
            Session session = getCurrentSession();
            List<Navigation> navigations = getNavigationsBySystemNodeId(typifiedObject.getId());
            for (Navigation navigation : navigations) {
                navigation.setSystemNodeId(0);
                session.update(navigation);
            }
            closeSession();
        }
        super.deleteObject(typifiedObject);
    }


    public Navigation getNavigationBySystemNodeId(final int systemNodeId) {
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

}
