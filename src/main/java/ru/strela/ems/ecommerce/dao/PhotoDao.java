package ru.strela.ems.ecommerce.dao;


import ru.strela.ems.core.dao.TypifiedObjectDao;
import ru.strela.ems.ecommerce.model.Photo;

import java.util.List;


//public interface LanguageDao extends EmsObjectDao {
public interface PhotoDao extends TypifiedObjectDao {


    Photo getPhoto(int id);
    Photo getPhotoByName(String photoName);
    List getPhotos();
//    List<Photo> getVisiblePhotos();
    void deletePhoto(Photo photo);
    List findPhotos(String[] descriptions);

}