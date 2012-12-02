package ru.strela.ems.core.service.spring;


import org.hibernate.Session;
import ru.strela.ems.core.dao.MetaInfoDao;
import ru.strela.ems.core.model.MetaInfo;
import ru.strela.ems.core.service.MetaInfoService;

import java.util.List;


public class MetaInfoServiceImpl implements MetaInfoService {


    private MetaInfoDao metaInfoDao = null;


    public MetaInfoDao getMetaInfoDao() {
        return metaInfoDao;
    }


    public void setMetaInfoDao(MetaInfoDao metaInfoDao) {
        this.metaInfoDao = metaInfoDao;
    }


    public MetaInfo getMetaInfo(int metaInfoId) {
        return metaInfoDao.getMetaInfo(metaInfoId);
    }

    public MetaInfo getMetaInfoNaturalId(int objectId, String languageCode) {
           return metaInfoDao.getMetaInfoNaturalId(objectId, languageCode);
       }



    public MetaInfo saveMetaInfo(MetaInfo MetaInfo) {
        return metaInfoDao.saveMetaInfo(MetaInfo);
    }


    public void deleteMetaInfo(MetaInfo metaInfo) {
        metaInfoDao.deleteMetaInfo(metaInfo);
    }

    public void addTags(MetaInfo metaInfo, String newTags) {
        metaInfoDao.addTags(metaInfo, newTags);
    }
    public void saveMetaInfoList(List MetaInfoList){
        metaInfoDao.saveMetaInfoList(MetaInfoList);
    }

    public void saveMetaInfoListSession(Session session, List metaInfoList){
        metaInfoDao.saveMetaInfoListSession(session, metaInfoList);
    }



}
