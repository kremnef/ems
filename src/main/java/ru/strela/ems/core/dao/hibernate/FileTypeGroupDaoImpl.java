package ru.strela.ems.core.dao.hibernate;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.strela.ems.core.dao.FileTypeGroupDao;
import ru.strela.ems.core.model.FileTypeGroup;

import java.util.List;

//
//import org.springframework.orm.hibernate4.support.HibernateDaoSupport;


public class FileTypeGroupDaoImpl implements FileTypeGroupDao {


    private final static Logger log = LoggerFactory.getLogger(FileTypeGroupDaoImpl.class);
//    private SessionFactoryStub sessionFactory;

    public FileTypeGroupDaoImpl() {
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


    public List getFileTypeGroups() {
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(FileTypeGroup.class);

        List list = criteria.list();
        closeSession();
        return list;
    }

    public FileTypeGroup getFileTypeGroup(int fileTypeGroupId) {
        Session session = getCurrentSession();
        FileTypeGroup fileTypeGroup = (FileTypeGroup) session.get(FileTypeGroup.class, fileTypeGroupId);
        closeSession();
        return fileTypeGroup;
    }


    public FileTypeGroup saveFileTypeGroup(FileTypeGroup FileTypeGroup) {

        Session session = getCurrentSession();

        session.saveOrUpdate(FileTypeGroup);
        closeSession();
        return FileTypeGroup;
    }


    public void deleteFileTypeGroup(FileTypeGroup metaInfo) {
        Session session = getCurrentSession();
        session.delete(metaInfo);
        closeSession();
    }


}
