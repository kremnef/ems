package ru.strela.ems.ecommerce.service;


import ru.strela.ems.core.service.TypifiedObjectService;
import ru.strela.ems.ecommerce.dao.PhotoDao;
import ru.strela.ems.ecommerce.model.Photo;

import java.util.List;


public interface PhotoService extends TypifiedObjectService {


    PhotoDao getPhotoDao();
    void setPhotoDao(PhotoDao photoDao);
    Photo getPhoto(int id);
    List getPhotos();

    void deletePhoto(Photo photo);
    List findPhotos(String[] descriptions);
    
}