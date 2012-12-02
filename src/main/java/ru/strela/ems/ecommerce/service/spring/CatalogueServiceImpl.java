package ru.strela.ems.ecommerce.service.spring;
//

import ru.strela.ems.core.service.spring.SystemObjectServiceImpl;
import ru.strela.ems.ecommerce.dao.CatalogueDao;
import ru.strela.ems.ecommerce.model.Catalogue;
import ru.strela.ems.ecommerce.service.CatalogueService;

import java.util.List;


public class CatalogueServiceImpl extends SystemObjectServiceImpl implements CatalogueService {


    public CatalogueDao getCatalogueDao() {
        return (CatalogueDao) getTypifiedObjectDao();
    }


    public void setCatalogueDao(CatalogueDao catalogueDao) {
        setTypifiedObjectDao(catalogueDao);
    }


    public Catalogue getCatalogue(int catalogueId) {
        return (Catalogue) getTypifiedObject(catalogueId);
    }


    public List getCatalogues(String owner) {
        return getCatalogueDao().getCatalogues(owner);
    }


    public List getCatalogues() {
        return getCatalogueDao().getCatalogues();
    }




    public List findCatalogues(String[] descriptions) {
        return getCatalogueDao().findCatalogues(descriptions);
    }

}
