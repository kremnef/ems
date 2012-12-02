package ru.strela.ems.ecommerce.dao;


import ru.strela.ems.core.dao.SystemObjectDao;
import ru.strela.ems.ecommerce.model.Catalogue;

import java.util.List;


public interface CatalogueDao extends SystemObjectDao {


    Catalogue getCatalogue(int catalogueId);
    List getCatalogues(String owner);
    List getCatalogues();
    List findCatalogues(String[] descriptions);

}
