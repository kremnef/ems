package ru.strela.ems.core.dao.hibernate;


import ru.strela.ems.core.dao.SiteDao;
import ru.strela.ems.core.model.TypifiedObject;

import java.util.ArrayList;
import java.util.List;


/**
 * User: hobal
 * Date: 25.03.11
 * Time: 11:20
 */
public class SiteDaoImpl extends TypifiedObjectDaoImpl implements SiteDao {


    public List<TypifiedObject> getObjects(int start, int quantity, final String sortName, final boolean desc) {
        return new ArrayList<TypifiedObject>();
    }


}
