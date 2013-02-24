package ru.strela.ems.core.service;


import java.util.HashMap;
import java.util.TreeMap;


/**
 * User: hobal
 * Date: 12.05.2010
 * Time: 18:18:54
 */
public interface SiteProcessorService {


    public TreeMap<String, Object> getSystemObjects(String systemNamesPath, String indexPage, String languageCode);
    public TreeMap<String, Object> getSystemObjects(String systemNamesPath, String indexPage, String languageCode, String requestQueryString);
//    public TreeMap<String, Object> getSystemObjects(String systemNamesPath, String indexPage, int languageId);


}
