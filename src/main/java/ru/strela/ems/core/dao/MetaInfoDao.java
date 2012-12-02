package ru.strela.ems.core.dao;


import org.hibernate.Session;
import ru.strela.ems.core.model.MetaInfo;

import java.util.List;


public interface MetaInfoDao {


    MetaInfo getMetaInfo(int MetaInfoId);
    MetaInfo getMetaInfoNaturalId(int objectId, String languageCode);

    MetaInfo saveMetaInfo(MetaInfo MetaInfo);
    void deleteMetaInfo(MetaInfo MetaInfo);
    void addTags(MetaInfo MetaInfo, String newTags);
    void saveMetaInfoList(List metaInfoList);
    void saveMetaInfoListSession(Session session, List metaInfoList);
}