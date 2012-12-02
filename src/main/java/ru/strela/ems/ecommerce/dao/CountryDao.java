package ru.strela.ems.ecommerce.dao;


import ru.strela.ems.ecommerce.model.Country;

import java.util.List;


//public interface LanguageDao extends EmsObjectDao {
public interface CountryDao {


    Country getCountry(int id);
    Country getCountryByName(String countryName);
    List getCountries();
    List getCountryNames();
    List<Country> getVisibleCountries();
    void deleteCountry(Country country);
    List findCountries(String[] descriptions);
    Country saveCountry(Country country);

}