package ru.strela.ems.core.service;


import com.sun.tools.internal.xjc.Language;
import org.hibernate.Session;
import ru.strela.ems.core.dao.MetaInfoDao;
import ru.strela.ems.core.model.MetaInfo;

import java.util.List;


public interface MetaInfoService {


    MetaInfoDao getMetaInfoDao();
    void setMetaInfoDao(MetaInfoDao metaInfoDao);
    MetaInfo getMetaInfo(int metaInfoId);
    MetaInfo getMetaInfoNaturalId(int objectId, String languageCode);
    MetaInfo saveMetaInfo(MetaInfo metaInfo);
    void deleteMetaInfo(MetaInfo metaInfo);
    void addTags(MetaInfo metaInfo, String newTags);
    void saveMetaInfoList(List MetaInfoList);
    void saveMetaInfoListSession(Session session, List metaInfoList);

}
