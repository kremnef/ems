package ru.strela.ems.core.dao;


import java.util.HashMap;
import java.util.TreeMap;


/**
 * User: hobal
 * Date: 12.05.2010
 * Time: 23:54:43
 */
public interface SiteProcessorDao {


//    TreeMap<String, Object> getSystemObjects(String systemNamesPath, String indexPage, String languageCode);
//    TreeMap<String, Object> getSystemObjects(String systemNamesPath, String indexPage, int languageId);
    TreeMap<String, Object> getSystemObjects(String systemNamesPath, String indexPage, String languageCode);
    TreeMap<String, Object> getSystemObjects(String systemNamesPath, String indexPage, String languageCode, String requestQueryString);

}
