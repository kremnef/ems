package ru.strela.ems.core.service.spring;


import ru.strela.ems.core.dao.LanguageDao;
import ru.strela.ems.core.model.Language;
import ru.strela.ems.core.service.LanguageService;

import java.util.List;


//public class LanguageServiceImpl  extends EmsObjectServiceImpl implements LanguageService {
public class LanguageServiceImpl extends TypifiedObjectServiceImpl implements LanguageService {


    public LanguageDao getLanguageDao() {
        return (LanguageDao) typifiedObjectDao;
    }


    public void setLanguageDao(LanguageDao languageDao) {
        setTypifiedObjectDao(languageDao);
    }


    public Language getLanguage(int id) {
        return getLanguageDao().getLanguage(id);
    }


    /*public Language saveLanguage(Language language) {
        return (Language) getLanguageDao().save(language);
    }*/


    public List getLanguages() {
        return getLanguageDao().getLanguages();
    }


    public List getVisibleLanguages() {
        return getLanguageDao().getVisibleLanguages();
    }


    public void deleteLanguage(Language language) {
        getLanguageDao().deleteLanguage(language);
    }


    public List findLanguages(String[] descriptions) {
        return getLanguageDao().findLanguages(descriptions);
    }

}
