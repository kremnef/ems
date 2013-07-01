package ru.strela.ems.core.dao.hibernate;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.strela.ems.core.dao.DocumentDao;
import ru.strela.ems.core.model.Document;
import ru.strela.ems.core.model.FileObject;
import ru.strela.ems.core.model.FileSystemObject;
import ru.strela.ems.core.model.Folder;
import ru.tastika.tools.util.Utilities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

//
//import org.springframework.orm.hibernate4.support.HibernateDaoSupport;


public class DocumentDaoImpl implements DocumentDao {


    private final static Logger log = LoggerFactory.getLogger(DocumentDaoImpl.class);
//    private SessionFactoryStub sessionFactory;

    public DocumentDaoImpl() {
        super();
    }


    protected Session getCurrentSession() {
        Session session = HibernateUtil.currentSession();
        HibernateUtil.beginTransaction();
        return session;
    }


    protected void closeSession() {
        HibernateUtil.commitTransaction();
        HibernateUtil.closeSession();
    }


    public Document getDocument(int documentId) {
        Session session = getCurrentSession();
        Document document = (Document) session.get(Document.class, documentId);
        closeSession();
        return document;
    }


    public List getDocumentsByContentId(final int contentId) {
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(Document.class);
        criteria.add(Restrictions.eq("contentId", contentId));
        //            speed test
//        criteria.addOrder(Order.desc("version"));
        List list = criteria.list();
        closeSession();
        return list;

    }


    public List getDocuments(final String owner) {
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(Document.class);
        if (owner != null) {
            criteria.add(Restrictions.eq("owner", owner));
        }
        List list = criteria.list();
        closeSession();
        return list;
    }


    public Document getDocumentByNaturalId(int contentId, String languageCode) {
        Session session = getCurrentSession();
        Document document = (Document) session.byNaturalId(Document.class)
                .using("languageCode", languageCode)
                .using("contentId", contentId)
                .using("isLastVersion", true)
                .load();
        closeSession();
        return document;
    }


    public Document saveDocument(Document document) {
        log.warn("DAO saveDocument:");
        //log.info("document getContentId " + document.getContentId());
        //log.info("document getXmlSource " + document.getXmlSource());
        List fileSystemObjects = document.getFileSystemObjects();
        //log.info("document getFileSystemObjects " + document.getFileSystemObjects());
        HashSet<Integer> filesIds = new HashSet<Integer>();
        HashSet<Integer> foldersIds = new HashSet<Integer>();
        for (Iterator iterator = fileSystemObjects.iterator(); iterator.hasNext(); ) {

            FileSystemObject fileSystemObject = (FileSystemObject) iterator.next();
            if (fileSystemObject != null && fileSystemObject.getId() > 0) {
                if (fileSystemObject.isFolder()) {
                    log.warn("isFolder:"+fileSystemObject.getId());
                    foldersIds.add(fileSystemObject.getId());
                } else {
                    log.warn("filesIds:"+fileSystemObject.getId());
                    filesIds.add(fileSystemObject.getId());
                }
            }
        }

        Session session = getCurrentSession();
        List fileObjects = document.getFileObjects();
        fileObjects.clear();
        if (filesIds.size() > 0) {
            List list = session.createQuery("from FileObject fo where fo.id in (" + Utilities.implode(filesIds, ",") + ")").list();
            for (Object obj : list) {
                FileObject fileObject = (FileObject) obj;
                fileObjects.add(fileObject);
            }
        }

        List folders = document.getFolders();
        folders.clear();
        if (foldersIds.size() > 0) {
            List list = session.createQuery("from Folder f where f.id in (" + Utilities.implode(foldersIds, ",") + ")").list();
            for (Object obj : list) {
                Folder folder = (Folder) obj;
                folders.add(folder);
            }
        }


        /* if (document.getContentId() > 0 && document.getContent() == null) {
            Content content = (Content) session.get(Content.class, document.getContentId());
            document.setContent(content);
        }*/

        //log.info("document getVersionModifierId()) " + document.getVersionModifierId());
        //log.info("SAVE DOC = " + document.getState());

        session.saveOrUpdate(document);
        closeSession();
        return document;
    }


//    public EmsSelectionList getVersionSelectionList(int contentId, int languageId) {
//        EmsSelectionList emsSelectionList = new EmsSelectionList();
//        List<Map<String, Object>> versions = new ArrayList<Map<String, Object>>();
//        HashMap<Integer, TreeMap<Integer, String>> documentVersions = getDocumentVersions(contentId);
//        TreeMap<Integer, String> docVersions = documentVersions.get(languageId);
//        HashMap<String, Object> newVersionHashMap = new HashMap<String, Object>();
//        newVersionHashMap.put("label", "New");
//        newVersionHashMap.put("value", 0);
//        versions.add(newVersionHashMap);
//        if (docVersions != null) {
//            for (Integer version : docVersions.keySet()) {
//                HashMap<String, Object> versionHashMap = new HashMap<String, Object>();
//                versionHashMap.put("label", String.valueOf(version));
//                versionHashMap.put("value", version);
//                versions.add(versionHashMap);
//            }
//        }
//        emsSelectionList.setItems(versions);
//        return emsSelectionList;
//    }

    /*public HashMap<Integer, TreeMap<Integer, String>> getDocumentVersions(int contentId) {
        HashMap<String, TreeMap<Integer, String>> documentVersions = new HashMap<String, TreeMap<Integer, String>>();
//            log.debug("getAllVersions " + this.getAllVersions(contentId));
        for (Object docObject : this.getAllVersions(contentId)) {
            Document doc = (Document) docObject;

            TreeMap<Integer, String> versions = documentVersions.get(doc.getLanguageCode());
            if (versions == null) {
                versions = new TreeMap<Integer, String>();
//                log.debug("doc.getLanguageId() " + doc.getLanguageId());
//                log.debug("versions " + versions);
                documentVersions.put(doc.getLanguageCode(), versions);
            }
//            versions.put(doc.getVersion(), doc.getVersionComment());
        }
        return documentVersions;
    }


    public List getAllVersions(int contentId) {
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(Document.class);
        criteria.add(Restrictions.eq("contentId", contentId));
        //            speed test
//        criteria.addOrder(Order.desc("version"));
        List documentVersions = criteria.list();
        closeSession();
        return documentVersions;
    }

    public Document getLastVersionDocument(int contentId, int languageId) {
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(Document.class);

        criteria.add(Restrictions.eq("contentId", contentId));
        criteria.add(Restrictions.eq("languageId", languageId));
        criteria.setMaxResults(1);


        Document lastVersion = (Document) criteria.uniqueResult();
        closeSession();
        return lastVersion;
    }*/

    /*public DocumentSimple getLastVersionDocumentSimple(int contentId, int languageId) {
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(DocumentSimple.class);

        criteria.add(Restrictions.eq("contentId", contentId));
        criteria.add(Restrictions.eq("languageId", languageId));
        //            speed test
//        criteria.addOrder(Order.desc("version"));
        criteria.setMaxResults(1);


        DocumentSimple lastVersion = (DocumentSimple) criteria.uniqueResult();
        closeSession();
        return lastVersion;
    }
*/
/*    public int getLastVersion(int contentId, int languageId) {
        Session session = getCurrentSession();
        Integer lastVersion = null;
        Criteria criteria = session.createCriteria(Document.class);
        criteria.add(Restrictions.eq("contentId", contentId));
        criteria.add(Restrictions.eq("languageId", languageId));
        criteria.setMaxResults(1);


        lastVersion = 1;
        Document doc = (Document) criteria.uniqueResult();
        if (doc != null) {

            lastVersion = lastVersion + doc.getVersion();
        }

        closeSession();
        return lastVersion;
    }*/


    /*public int getLastVersion(int contentId, String languageCode) {
        Session session = getCurrentSession();
//        Integer lastVersion = null;
        Criteria criteria = session.createCriteria(Document.class);
        criteria.add(Restrictions.eq("contentId", contentId));
        criteria.add(Restrictions.eq("languageCode", languageCode));
        criteria.add(Restrictions.eq("isLastVersion", true));
        criteria.setMaxResults(1);


//        lastVersion = 1;
        Document doc = (Document) criteria.uniqueResult();
        if (doc != null) {

            lastVersion = lastVersion + doc.getVersion();
        }

        closeSession();
        return lastVersion;
    }*/


    public void deleteDocument(Document document) {
        Session session = getCurrentSession();
        session.delete(document);
        closeSession();
    }


   /* public void addTags(Document document, String newTags) {
        Session session = getCurrentSession();
        HashSet<String> hashSet = new HashSet(Arrays.asList(newTags.split(",")));
        ArrayList<Tag> tempArray = new ArrayList<Tag>();

        if (document.getTags() != null) {


            for (Object tagObj : document.getTags()) {
                Tag documentTag = (Tag) tagObj;
                String tagName = documentTag.getTag();
                if (hashSet.contains(tagName)) {
                    tempArray.add(documentTag);
                    hashSet.remove(tagName);

                }
            }

            if (hashSet.size() > 0) {
                List list = new ArrayList();
                try {
                    StringBuffer sb = new StringBuffer();
                    for (String tagName : hashSet) {
                        sb.append(",'").append(tagName).append("'");
                    }
                    String inCondition = sb.substring(1);
                    list = session.createQuery("from Tag t where t.tag in (" + inCondition + ")").list();
                } catch (Throwable e) {
                    e.printStackTrace();
                }


                for (Object tagObj : list) {
                    Tag documentTag = (Tag) tagObj;
                    String tagName = documentTag.getTag();
                    tempArray.add(documentTag);
                    hashSet.remove(tagName);


                }
            }

            for (String tagStr : hashSet) {
                Tag tag = new Tag();
                tag.setTag(tagStr);
                tempArray.add(tag);

            }

            try {
                if (tempArray.isEmpty()) {
                } else {
                }
                document.setTags(tempArray);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        } else {
        }
        closeSession();
    }
*/

    public List findDocuments(final String[] descriptions) {
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(Document.class);
        Criterion criterion = null;
        List list;
        for (String description : descriptions) {
            description = description.trim();
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
        if (criterion != null) {
            criteria.add(criterion);
        } else {
            list = new ArrayList();
        }
        closeSession();
        return list;
    }
}
