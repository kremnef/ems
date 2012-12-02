package ru.strela.ems.core.dao;


import org.hibernate.Session;
import ru.strela.ems.core.model.ObjectLabel;

import java.util.List;


public interface ObjectLabelDao {


    ObjectLabel getObjectLabel(int objectLabelId);
    ObjectLabel getObjectLabelNaturalId(int objectId, String languageCode);

    ObjectLabel saveObjectLabel(ObjectLabel objectLabel);
    void deleteObjectLabel(ObjectLabel objectLabel);
    void saveObjectLabelList(List objectLabelList);
    void saveObjectLabelListSession(Session session, List objectLabelList);
}