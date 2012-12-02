package ru.strela.ems.core.dao;


import ru.strela.ems.core.model.Language;

import java.util.List;


//public interface LanguageDao extends EmsObjectDao {
public interface LanguageDao extends TypifiedObjectDao {


    Language getLanguage(int id);
    Language getLanguageByName(String languageName);
    Language getLanguageByCode(String languageCode);
    List getLanguages();
    List<Language> getVisibleLanguages();
    void deleteLanguage(Language language);
    List findLanguages(String[] descriptions);

}