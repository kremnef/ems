package ru.strela.ems.ecommerce.service.spring;


import ru.strela.ems.core.service.spring.TypifiedObjectServiceImpl;
import ru.strela.ems.ecommerce.dao.PhotoDao;
import ru.strela.ems.ecommerce.model.Photo;
import ru.strela.ems.ecommerce.service.PhotoService;

import java.util.List;


//public class ProductServiceImpl  extends EmsObjectServiceImpl implements ProductService {
public class PhotoServiceImpl extends TypifiedObjectServiceImpl implements PhotoService {


    public PhotoDao getPhotoDao() {
        return (PhotoDao) typifiedObjectDao;
    }


    public void setPhotoDao(PhotoDao photoDao) {
        setTypifiedObjectDao(photoDao);
    }


    public Photo getPhoto(int id) {
        return getPhotoDao().getPhoto(id);
    }



    public List getPhotos() {
        return getPhotoDao().getPhotos();
    }



    public void deletePhoto(Photo photo) {
        getPhotoDao().deletePhoto(photo);
    }


    public List findPhotos(String[] descriptions) {
        return getPhotoDao().findPhotos(descriptions);
    }

}
