package ru.strela.ems.ecommerce.service;


import ru.strela.ems.core.service.SystemObjectService;
import ru.strela.ems.ecommerce.dao.CatalogueDao;
import ru.strela.ems.ecommerce.model.Catalogue;

import java.util.List;


public interface CatalogueService extends SystemObjectService {


    CatalogueDao getCatalogueDao();
    void setCatalogueDao(CatalogueDao catalogueDao);
    Catalogue getCatalogue(int catalogueId);
    List getCatalogues();
    List getCatalogues(String owner);
    List findCatalogues(String[] descriptions);

}
