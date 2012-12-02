package ru.strela.ems.core.dao.hibernate;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.strela.ems.core.dao.MetaInfoDao;
import ru.strela.ems.core.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

//
//import org.springframework.orm.hibernate4.support.HibernateDaoSupport;


public class MetaInfoDaoImpl implements MetaInfoDao {


    private final static Logger log = LoggerFactory.getLogger(MetaInfoDaoImpl.class);
//    private SessionFactoryStub sessionFactory;

    public MetaInfoDaoImpl() {
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


    public MetaInfo getMetaInfo(int MetaInfoId) {
        Session session = getCurrentSession();
        MetaInfo MetaInfo = (MetaInfo) session.get(MetaInfo.class, MetaInfoId);
        closeSession();
        return MetaInfo;
    }

    /*  public MetaInfo getMetaInfo(int MetaInfoId, String languageCode) {
        Session session = getCurrentSession();
        MetaInfo MetaInfo = (MetaInfo) session.get(MetaInfo.class, MetaInfoId);
        closeSession();
        return MetaInfo;
    }*/

    public MetaInfo getMetaInfoNaturalId(int objectId, String languageCode) {
        Session session = getCurrentSession();
        MetaInfo MetaInfo = (MetaInfo) session.byNaturalId(MetaInfo.class)
                .using("languageCode", languageCode)
                .using("objectId", objectId)
                .load();
        closeSession();
        return MetaInfo;
    }

    public void saveMetaInfoListSession(Session session, List metaInfoList) {
        for (Object obj : metaInfoList) {
            MetaInfo metaInfo = (MetaInfo) obj;
//            System.out.print("saveMetaInfoList - metaInfo ID:"+metaInfo.getId());
            session.saveOrUpdate(metaInfo);
        }

    }


    public void saveMetaInfoList(List metaInfoList) {
        Session session = getCurrentSession();
        for (Object obj : metaInfoList) {
            MetaInfo metaInfo = (MetaInfo) obj;

            if (metaInfo.getTitle() == null && metaInfo.getDescription() == null && metaInfo.getKeywords() == null && metaInfo.getTags() == null) {
                System.out.print("metaInfo NOT Saved");

            } else {
                session.saveOrUpdate(metaInfo);
            }

        }

        closeSession();
    }


    public MetaInfo saveMetaInfo(MetaInfo MetaInfo) {

        Session session = getCurrentSession();

        session.saveOrUpdate(MetaInfo);
        closeSession();
        return MetaInfo;
    }

    public List<MetaInfo> getMetaInfoList(TypifiedObject typifiedObject) {
        int objectId = typifiedObject.getId();
//        if (typifiedObject instanceof SystemObject) {
        Session session = getCurrentSession();
        Criteria criteria = session.createCriteria(MetaInfo.class);
        criteria.add(Restrictions.eq("objectId", objectId));
        return criteria.list();
    }

    public void deleteMetaInfo(MetaInfo metaInfo) {
        Session session = getCurrentSession();
        session.delete(metaInfo);
        closeSession();
    }


    public void addTags(MetaInfo metaInfo, String newTags) {
        Session session = getCurrentSession();
        //log.info("TAG newTags: " + newTags);
        HashSet<String> hashSet = new HashSet(Arrays.asList(newTags.split(",")));
        ArrayList<Tag> tempArray = new ArrayList<Tag>();

        if (metaInfo.getTags() != null) {


            for (Object tagObj : metaInfo.getTags()) {
                //log.info("TAG CICLE 1 in");
                Tag MetaInfoTag = (Tag) tagObj;
                String tagName = MetaInfoTag.getTag();
                //log.info("TAG CICLE 1 tagName:" + tagName);
                if (hashSet.contains(tagName)) {
                    //log.info("TAG CICLE 1 tempArray.add:" + MetaInfoTag);
                    tempArray.add(MetaInfoTag);
                    //log.info("TAG CICLE 1 hashSet.remove:" + tagName);
                    hashSet.remove(tagName);

                }
                //log.info("TAG CICLE 1 out");
            }

            if (hashSet.size() > 0) {
                //log.info("TAG CICLE 2 in");
                List list = new ArrayList();
                try {
                    StringBuffer sb = new StringBuffer();
                    for (String tagName : hashSet) {
                        sb.append(",'").append(tagName).append("'");
                    }
                    String inCondition = sb.substring(1);
//                list = getHibernateTemplate().find("from Tag f where f.id in (" + Utilities.implode(hashSet, ",") + ")");
                    //log.info("TAG CICLE 2 inCondition" + inCondition);
                    list = session.createQuery("from Tag t where t.tag in (" + inCondition + ")").list();
//                list = getHibernateTemplate().find("from Tag t where t.tag in (" + hashSet + ")");
                } catch (Throwable e) {
                    e.printStackTrace();
                }


                for (Object tagObj : list) {
                    Tag MetaInfoTag = (Tag) tagObj;
                    String tagName = MetaInfoTag.getTag();
                    //log.info("TAG CICLE 2 tempArray.add:" + MetaInfoTag);
                    tempArray.add(MetaInfoTag);
                    //log.info("TAG CICLE 2 hashSet.remove:" + tagName);
                    hashSet.remove(tagName);


                }
                //log.info("TAG CICLE 2 out");
            }

            for (String tagStr : hashSet) {
                //log.info("TAG CICLE 3 in");
                Tag tag = new Tag();
                //log.info("TAG CICLE 3 tag.setTag:" + tagStr);
                tag.setTag(tagStr);
//            tagService.saveTag(tag);
                //log.info("TAG CICLE 2 tempArray.add:" + tag);
                tempArray.add(tag);

                //log.info("TAG CICLE 3 out");
            }

            try {
                if (tempArray.isEmpty()) {
                    //log.info("TAG Step 4: tempArray.isEmpty");
                } else {
                    //log.info("TAG Step 4: tempArray" + tempArray);
                }
                metaInfo.setTags(tempArray);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        } else {
            //log.info("TAG doc.getTags: ==null");
        }
        closeSession();
    }


}
