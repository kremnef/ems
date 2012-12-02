package ru.strela.ems.core.dao;


import ru.strela.ems.core.model.Tag;

import java.util.List;


//public interface LanguageDao extends EmsObjectDao {
public interface TagDao {


    Tag getTag(int id);
//    Tag getCountryByName(String countryName);

    List<Tag> getTags();
    void deleteTag(Tag tag);
//    List findCountries(String[] descriptions);
    Tag saveTag(Tag tag);

}