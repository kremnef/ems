package ru.strela.ems.core.service.spring;


import ru.strela.ems.core.dao.SiteProcessorDao;
import ru.strela.ems.core.service.SiteProcessorService;

import java.util.HashMap;
import java.util.TreeMap;


/**
 * User: hobal
 * Date: 12.05.2010
 * Time: 18:19:33
 */
public class SiteProcessorServiceImpl implements SiteProcessorService {


    protected SiteProcessorDao siteProcessorDao = null;


    public SiteProcessorDao getSiteProcessorDao() {
        return siteProcessorDao;
    }


    public void setSiteProcessorDao(SiteProcessorDao siteProcessorDao) {
        this.siteProcessorDao = siteProcessorDao;
    }


   /* public TreeMap<String, Object> getSystemObjects(String systemNamesPath, String indexPage, String languageCode) {
        return siteProcessorDao.getSystemObjects(systemNamesPath, indexPage, languageCode);
    }*/

    /*public TreeMap<String, Object> getSystemObjects(String systemNamesPath, String indexPage, int languageId) {
        return siteProcessorDao.getSystemObjects(systemNamesPath, indexPage, languageId);
    }*/
    public TreeMap<String, Object> getSystemObjects(String systemNamesPath, String indexPage, String languageCode) {
        return siteProcessorDao.getSystemObjects(systemNamesPath, indexPage, languageCode);
    }

    public TreeMap<String, Object> getSystemObjects(String systemNamesPath, String indexPage, String languageCode, String requestQueryString) {
        return siteProcessorDao.getSystemObjects(systemNamesPath, indexPage, languageCode, requestQueryString);
    }

}
