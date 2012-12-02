package ru.strela.ems.core.dao.hibernate;


import org.hibernate.*;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.strela.ems.core.dao.ContentDao;
import ru.strela.ems.core.dao.DocumentVersionDao;
import ru.strela.ems.core.model.*;
import ru.tastika.tools.util.Utilities;

import java.util.*;

//import org.springframework.transaction.support.TransactionTemplate;

//


public class ContentDaoImpl extends SystemObjectDaoImpl implements ContentDao {
    private final static Logger log = LoggerFactory.getLogger(ContentDaoImpl.class);
    private SessionFactory sessionFactory;
//    private TransactionTemplate transactionTemplate;
    /*public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }*/

    /*public Collection loadProductsByCategory(String category) {
        return this.sessionFactory.getCurrentSession()
                .createQuery("from test.Product product where product.category=?")
                .setParameter(0, category)
                .list();
    }*/


    public ContentDaoImpl() {
        super();
    }


    @Override
    public Class getEntityClass() {
        return Content.class;
    }


    public Content getContent(int contentId) {
        return (Content) getTypifiedObject(contentId);
    }


    public List getContents(final String owner) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        Criteria criteria = null;
        List list = new ArrayList();
        try {
            tx = session.beginTransaction();
//            session.saveOrUpdate(tag);
//            criteria = session.createCriteria(Content.class);
            criteria = session.createCriteria(Content.class);
            if (owner != null) {
                criteria.add(Restrictions.eq("owner", owner));
            }
//            criteria.addOrder(Order.desc("publishDateTime"));
            list = criteria.list();
            tx.commit();
            session.close();
            log.warn("closeSession");


        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }
        return list;

    }


    /* this.transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            public Object doInTransactionObject(TransactionStatus status) {
               Criteria criteria = session.createCriteria(Content.class);
            if (owner != null) {
                criteria.add(Restrictions.eq("owner", owner));
            }
            criteria.addOrder(Order.desc("publishDateTime"));
            return criteria.list();
            }
        }
    );*/


    /*
        public List getContents(final String owner) {
            return getHibernateTemplate().executeFind(new HibernateCallback() {


                public Object doInHibernate(Session session) throws HibernateException {
                    Criteria criteria = session.createCriteria(Content.class);
                    if (owner != null) {
                        criteria.add(Restrictions.eq("owner", owner));
                    }
                    criteria.addOrder(Order.desc("publishDateTime"));
                    return criteria.list();
                }

            });
        }
    */

    public List getContentsByParent(int parentId, int itemsOnPage, String sortField, String sortDirection, Integer tagId, int languageId, Session session) {
        Criteria criteria = session.createCriteria(Content.class);
        criteria.add(Restrictions.eq("parentId", parentId));
        if (itemsOnPage > 0) {
            criteria.setMaxResults(itemsOnPage);
        }
        //            speed test
        /*if (sortField != null) {
            if (sortDirection.equalsIgnoreCase("desc")) {
                criteria.addOrder(Order.desc(sortField));
            }
            else if (sortDirection.equalsIgnoreCase("asc")) {
                criteria.addOrder(Order.asc(sortField));
            }
        }*/
        //            speed test


        //            session.setDefaultReadOnly(true);

//            session.enableFilter("hasTag").setParameter("tagId", tagId);
        //            criteria.add(Restrictions.eq("languageId", 1));
//            criteria.add(Restrictions.lt("publishDateTime", dateTime));
//            criteria.add(Restrictions.ge("expireDateTime", dateTime));
        List list = criteria.list();
        return list;
    }


    /*public List<TypifiedObject> getChildren(final int parentId, final int start, final int quantity, final String sortName, final boolean desc, final ru.strela.ems.core.model.Filter filter) {
           Session session = getCurrentSession();
           List list = new ArrayList();
           log.warn("getChildren-02");
           StringBuilder sql = new StringBuilder("select count(*) from Content f where f.emsObject.parentId " + (parentId > 0 ? (" = " + parentId) : " is null"));
           int contentsAmount = ((Long) session.createQuery(sql.toString()).iterate().next()).intValue();

           int objectsCount = quantity == 0 ? Integer.MAX_VALUE - start : quantity;
           if (start < contentsAmount) {
               StringBuilder sb = new StringBuilder("from Content f where f.emsObject.parentId");
               if (parentId > 0) {
                   sb.append(" = ");
                   sb.append(parentId);
               } else {
                   sb.append(" is null");
               }
               if (sortName.length() > 0) {
                   sb.append(" order by ");
                   sb.append(sortName);
                   sb.append(desc ? " desc" : "asc");
               }

               Query query = session.createQuery(sb.toString());
               query.setFirstResult(start);
               query.setFetchSize(objectsCount);
               list.addAll(query.list());
           }
           if (start + objectsCount > contentsAmount) {
               StringBuilder sb = new StringBuilder("from Content fo where fo.emsObject.parentId");
               if (parentId > 0) {
                   sb.append(" = ");
                   sb.append(parentId);
               } else {
                   sb.append(" is null");
               }
               if (sortName.length() > 0) {
                   sb.append(" order by ");
                   sb.append(sortName);
                   sb.append(desc ? " desc" : "asc");
               }
               if (filter != null && filter.getEntity().equals("Content")) {
                   int filterValue = 0;
                   try {
                       filterValue = Integer.parseInt(filter.getFieldValue());
                   } catch (NumberFormatException e) {
                       log.error("filterValue-!!!!!" + e);
                   }
                   sb.append(" and fo.").append(filter.getField()).append("=");
                   sb.append(filterValue);
                   log.warn("SQL" + sb);
               }

               Query query = session.createQuery(sb.toString());
               query.setFirstResult(start > contentsAmount ? start : 0);
               query.setFetchSize(objectsCount - list.size());
               list.addAll(query.list());
           }
           closeSession();
            return list;
       }*/
    public List getContents(int itemsOnPage, String sortField, String sortDirection, Integer tagId, int languageId, Session session) {
        Criteria criteria = session.createCriteria(Content.class);
        if (itemsOnPage > 0) {
            criteria.setMaxResults(itemsOnPage);
        }

        //            speed test
       /* if (sortField != null) {
            if (sortDirection.equalsIgnoreCase("desc")) {
                criteria.addOrder(Order.desc(sortField));
            }
            else if (sortDirection.equalsIgnoreCase("asc")) {
                criteria.addOrder(Order.asc(sortField));
            }
        }*/
        //            session.setDefaultReadOnly(true);

//            session.enableFilter("hasTag").setParameter("tagId", tagId);
        //            criteria.add(Restrictions.eq("languageId", 1));
//            criteria.add(Restrictions.lt("publishDateTime", dateTime));
//            criteria.add(Restrictions.ge("expireDateTime", dateTime));
        List list = criteria.list();
        return list;
    }


    public List getContents() {
        return getObjects(Order.desc("publishDateTime"));
    }


    public List getContentsByDocumentType(final Integer documentTypeId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        Criteria criteria = null;
        List list = null;
        try {
            tx = session.beginTransaction();
            criteria = session.createCriteria(Content.class);
            criteria.add(Restrictions.eq("documentTypeId", documentTypeId));
//            criteria.addOrder(Order.desc("publishDateTime"));
            list = criteria.list();


            tx.commit();
            session.close();
            log.warn("closeSession");


        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }
        return list;
    }


    public List findContents(final String[] descriptions) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        Criteria criteria = session.createCriteria(Content.class);
        Criterion criterion = null;
        List list = new ArrayList();
        try {
            tx = session.beginTransaction();

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
            list = criteria.list();
            tx.commit();
            session.close();
            log.warn("closeSession");


        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }
        if (criterion != null) {
            criteria.add(criterion);

            return list;

        } else {
            return new ArrayList();
        }
    }


    public HashMap<Integer, Integer> getDocumentMaxVersions(Set<Integer> contentIds, int languageId) {
        HashMap<Integer, Integer> versions = new HashMap<Integer, Integer>();
        if (contentIds.size() > 0) {
            Session currentSession = getCurrentSession();
            List list = currentSession.createQuery("select contentId, max(version) from Document where contentId in (" + Utilities.implode(contentIds, ",") + ") and languageId = " + languageId + " group by contentId").list();
            for (Object row : list) {
                Object[] rowData = (Object[]) row;
                versions.put((Integer) rowData[0], (Integer) rowData[1]);
            }
            closeSession();
        }
        return versions;
    }


    public HashMap<Integer, ArrayList<String[]>> getPathsForContents(HashSet<Integer> contentIds, int languageId) {
        HashMap<Integer, ArrayList<String[]>> paths = new HashMap<Integer, ArrayList<String[]>>();
        if (contentIds.size() > 0) {
            Session currentSession = getCurrentSession();
            HashMap<Integer, Integer> parents = new HashMap<Integer, Integer>();
            for (Integer contentId : contentIds) {
                fillParents(contentId, parents, currentSession);
            }
            closeSession();
        }
        return paths;
    }


    private void fillParents(Integer contentId, HashMap<Integer, Integer> parents, Session session) {
        if (!parents.containsKey(contentId)) {
            session.createQuery("select parent_id from object where object_id");
        }
    }


 /*   public void showProducts(Map model, Object obj, String src) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            Request request = ObjectModelHelper.getRequest(model);
            String requestURI = request.getRequestURI();
            String servletPath = request.getServletPath();
            String sitemapPath = request.getSitemapPath();
            String pathPrefix = servletPath.length() > 0 ? (servletPath + '/' + sitemapPath) : "/";
            String clearRequestURI = src;
            if (clearRequestURI.startsWith("/")) {
                clearRequestURI = clearRequestURI.substring(1);
            }
            StringBuilder sql = new StringBuilder("from SiteMap where url = '");
            sql.append(clearRequestURI).append("'");
            List list = session.createQuery(sql.toString()).list();
            if (list.size() > 0) {
                SiteMap siteMap = (SiteMap) list.iterator().next();
                sql = new StringBuilder("from TypifiedObject where id = ").append(siteMap.getObjectId());
                list = session.createQuery(sql.toString()).list();
                if (list.size() > 0) {
                    TypifiedObject typifiedObject = (TypifiedObject) list.iterator().next();
                    if (typifiedObject instanceof Content) {
                        Content content = (Content) typifiedObject;
                        int childrenCount = content.getEmsObject().getChildrenCount();
                        if (childrenCount > 0) {
                            String redirectURI = pathPrefix + clearRequestURI;
                            while (childrenCount > 0) {
                                List<TypifiedObject> children = getChildren(content);
                                content = (Content) children.get(0);
                                redirectURI += "/" + content.getSystemName();
                                childrenCount = content.getEmsObject().getChildrenCount();
                            }
                            request.setAttribute("makeRedirect", "true");
                            request.setAttribute("redirect", redirectURI);
                        }
                    }
                }
            }
            tx.commit();
            session.close();
            log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }
    }*/



    protected TypifiedObject saveObject(TypifiedObject typifiedObject) {

//    protected TypifiedObject saveObject(Content typifiedObject) {


        /*    List objects = typifiedObject.getLinked();
                HashSet<Integer> linkedIds = new HashSet<Integer>();
                for (Iterator iterator = objects.iterator(); iterator.hasNext(); ) {
                    Content linked = (Content) iterator.next();
                    if (linked != null && linked.getId() > 0) {

                            linkedIds.add(linked.getId());

                    }
                }

                List linkeds = typifiedObject.getLinked();
                linkeds.clear();
                if (linkedIds.size() > 0) {
                    List list = getHibernateTemplate().find("from Content f where f.id in (" + Utilities.implode(linkedIds, ",") + ")");
                    for (Object obj : list) {
                        Content content = (Content) obj;
                        linkeds.add(content);
                    }
                }

        */
        /*Content c = (Content) typifiedObject;
        Integer emsId = c.getEmsObject().getId();*/
//        System.out.println("Content saveOrUpdate: " +emsId);
//        if (emsId != null){
            super.saveObject(typifiedObject);
//        }

//        reindexSiteMap((SystemObject) typifiedObject);
        return typifiedObject;
    }
    @Override
    protected void deleteObject(TypifiedObject typifiedObject) {
        DocumentVersionDao documentVersionDao = new DocumentVersionDaoImpl();
        List<DocumentVersion> documentVersions = documentVersionDao.getAllVersions(typifiedObject.getId());


        for (DocumentVersion documentVersion : documentVersions) {
            documentVersionDao.deleteDocumentVersion(documentVersion);
        }

        super.deleteObject(typifiedObject);
    }
}

