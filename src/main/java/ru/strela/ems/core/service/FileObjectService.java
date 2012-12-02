package ru.strela.ems.core.service;


import ru.strela.ems.core.dao.FileObjectDao;
import ru.strela.ems.core.model.FileObject;


public interface FileObjectService extends SystemObjectService {


    FileObjectDao getFileObjectDao();
    void setFileObjectDao(FileObjectDao fileObjectDao);
    FileObject getFileObject(int fileObjectId);
    FileObject getFileObjectByPath(String filePath);

}