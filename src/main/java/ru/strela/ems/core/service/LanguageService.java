package ru.strela.ems.core.service;


import ru.strela.ems.core.dao.LanguageDao;
import ru.strela.ems.core.model.Language;

import java.util.List;


public interface LanguageService extends TypifiedObjectService {


    LanguageDao getLanguageDao();
    void setLanguageDao(LanguageDao languageDao);
    Language getLanguage(int id);
    List getLanguages();
    List getVisibleLanguages();
//    Language saveLanguage(Language language);
    void deleteLanguage(Language language);
    List findLanguages(String[] descriptions);
    
}