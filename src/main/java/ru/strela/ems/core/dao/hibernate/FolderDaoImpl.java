package ru.strela.ems.core.dao.hibernate;


import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.strela.ems.core.dao.FolderDao;
import ru.strela.ems.core.model.*;
import ru.strela.ems.tools.ServerTools;
import ru.tastika.tools.file.FileAddition;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class FolderDaoImpl extends SystemObjectDaoImpl implements FolderDao {

    private final static Logger log = LoggerFactory.getLogger(FolderDaoImpl.class);

    public FolderDaoImpl() {
        super();
    }


    @Override
    public Class getEntityClass() {
        return Folder.class;
    }


    public Folder getFolder(int folderId) {
        return (Folder) getTypifiedObject(folderId);
    }


    public List getFolders() {
        return getObjects();
    }


    public List getFolders(int parentId) {
        return getChildren(parentId);
    }


    @Override
    protected TypifiedObject saveObject(TypifiedObject typifiedObject) {
        Folder folder = (Folder) typifiedObject;
        String realRootPath = ServerTools.getContextRealPath();

        File file = new File(realRootPath, folder.getPath());
        boolean dirExist = true;
        if (!file.isDirectory()) {
            dirExist = file.mkdirs();
        }

        if (dirExist) {
            typifiedObject = super.saveObject(typifiedObject);
        }

        return typifiedObject;
    }


    @Override
    protected void deleteObject(TypifiedObject typifiedObject) {
        PathObject folder = (PathObject) typifiedObject;
        Session session = getCurrentSession();
//         Transaction tx = session.beginTransaction();
        super.deleteObject(typifiedObject);
       /* StringBuilder sb = new StringBuilder("delete from ImageGalleryFileObject imfo where imfo.fileObjectId = ");
        sb.append(typifiedObject.getId());
        session.createQuery(sb.toString()).executeUpdate();*/
        String realRootPath = ServerTools.getContextRealPath();
        File file = new File(realRootPath, folder.getPath());
        FileAddition.delete(file);
        closeSession();
//        tx.commit();
    }

    //    current_timestamp()
    public List<TypifiedObject> getChildren(final int parentId) {
        Session session = getCurrentSession();
        log.warn("getChildren-01");
        Query query = session.createQuery("from Folder f where f.emsObject.parentId " + (parentId > 0 ? (" = " + parentId) : " is null"));
        List list = query.list();
        query = session.createQuery("from FileObject fo where fo.emsObject.parentId " + (parentId > 0 ? (" = " + parentId) : " is null"));
        list.addAll(query.list());
        closeSession();
        return list;
    }

    public List<TypifiedObject> getFoldersByFileTypeGroup(final int parentId, final int fileTypeGroupId) {
            Session session = getCurrentSession();
            log.warn("getChildren-01");
            Query query = session.createQuery("from Folder f where f.emsObject.parentId " + (parentId > 0 ? (" = " + parentId) : " is null"));
            List list = query.list();
            query = session.createQuery("from FileObject fo where fo.emsObject.parentId " + (parentId > 0 ? (" = " + parentId) : " is null and fo.fileTypeGroupId="+fileTypeGroupId));
            list.addAll(query.list());
            closeSession();
            return list;
        }


    public List<TypifiedObject> getChildren(final int parentId, final int start, final int itemsOnPage, final String sortField, final String SortDirection) {

        Session session = getCurrentSession();
        List list = new ArrayList();
        log.warn("getChildren-02");
        StringBuilder sql = new StringBuilder("select count(*) from Folder f where f.emsObject.parentId " + (parentId > 0 ? (" = " + parentId) : " is null"));
        int foldersAmount = ((Long) session.createQuery(sql.toString()).iterate().next()).intValue();

        int objectsCount = itemsOnPage == 0 ? Integer.MAX_VALUE - start : itemsOnPage;
        if (start < foldersAmount) {
            StringBuilder sb = new StringBuilder("from Folder f where f.emsObject.parentId");
            if (parentId > 0) {
                sb.append(" = ");
                sb.append(parentId);
            } else {
                sb.append(" is null");
            }
            if (sortField.length() > 0) {
                sb.append(" order by ");
                sb.append(sortField);
                sb.append(SortDirection);
//                sb.append(SortDirection ? " desc" : " asc");
            }

            //sb.append(" limit ");
            //sb.append(start);
            //sb.append(",");
            //sb.append(objectsCount);

            Query query = session.createQuery(sb.toString());
            query.setFirstResult(start);
            query.setFetchSize(objectsCount);
            list.addAll(query.list());
        }
        if (start + objectsCount > foldersAmount) {
            StringBuilder sb = new StringBuilder("from FileObject fo where fo.emsObject.parentId");
            if (parentId > 0) {
                sb.append(" = ");
                sb.append(parentId);
            } else {
                sb.append(" is null");
            }
            if (sortField.length() > 0) {
                sb.append(" order by ");
                sb.append(sortField);
                sb.append(SortDirection);
//                sb.append(desc ? " desc" : " asc");
            }
            log.warn("SQL" + sb);
            /*    sb.append(" limit ");
          sb.append(start - foldersAmount);
          sb.append(",");
          sb.append(objectsCount - list.size());*/
            Query query = session.createQuery(sb.toString());
            query.setFirstResult(start > foldersAmount ? start : 0);
            query.setFetchSize(objectsCount - list.size());
            list.addAll(query.list());
        }
        closeSession();
        return list;
    }

    public List<TypifiedObject> getChildren(final int parentId, final int start, final int itemsOnPage, final String sortField, final boolean desc, final Filter filter) {
        Session session = getCurrentSession();
        List list = new ArrayList();
        log.warn("getChildren-02");
        StringBuilder sql = new StringBuilder("select count(*) from Folder f where f.emsObject.parentId " + (parentId > 0 ? (" = " + parentId) : " is null"));
        int foldersAmount = ((Long) session.createQuery(sql.toString()).iterate().next()).intValue();

        int objectsCount = itemsOnPage == 0 ? Integer.MAX_VALUE - start : itemsOnPage;
        if (start < foldersAmount) {
            StringBuilder sb = new StringBuilder("from Folder f where f.emsObject.parentId");
            if (parentId > 0) {
                sb.append(" = ");
                sb.append(parentId);
            } else {
                sb.append(" is null");
            }
            if (sortField.length() > 0) {
                sb.append(" order by ");
                sb.append(sortField);
                sb.append(desc ? " desc" : " asc");
            }

            Query query = session.createQuery(sb.toString());
            query.setFirstResult(start);
            query.setFetchSize(objectsCount);
            list.addAll(query.list());
        }
        if (start + objectsCount > foldersAmount) {
            StringBuilder sb = new StringBuilder("from FileObject fo where fo.emsObject.parentId");
            if (parentId > 0) {
                sb.append(" = ");
                sb.append(parentId);
            } else {
                sb.append(" is null");
            }
            if (sortField.length() > 0) {
                sb.append(" order by ");
                sb.append(sortField);
                sb.append(desc ? " desc" : " asc");
            }
            if (filter != null && filter.getEntity().equals("FileObject")) {
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
            query.setFirstResult(start > foldersAmount ? start : 0);
            query.setFetchSize(objectsCount - list.size());
            list.addAll(query.list());
        }
        closeSession();
         return list;
    }


    public int getChildrenCount(int id) {
        Session session = getCurrentSession();
        StringBuilder sql = new StringBuilder("select count(*) from EmsObject eo where eo.parentId ");
        if (id > 0) {
            sql.append("=").append(id);
        }
        else {
            sql.append("is null");
        }
        if (id == 0 && !getEntityClass().equals(EmsObject.class)) {
            sql.append(" and (eo.entity = '");
            sql.append(Folder.class.getSimpleName());
            sql.append("' or eo.entity = '");
            sql.append(FileObject.class.getSimpleName());
            sql.append("')");
        }
        int count = ((Long) session.createQuery(sql.toString()).iterate().next()).intValue();
        closeSession();
        return count;
    }
}
