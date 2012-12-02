package ru.strela.ems.core.service.spring;


import ru.strela.ems.core.dao.FileObjectDao;
import ru.strela.ems.core.model.FileObject;
import ru.strela.ems.core.service.FileObjectService;


public class FileObjectServiceImpl extends SystemObjectServiceImpl implements FileObjectService {


    public FileObjectDao getFileObjectDao() {
        return (FileObjectDao) super.getTypifiedObjectDao();
    }


    public void setFileObjectDao(FileObjectDao fileObjectDao) {
        setTypifiedObjectDao(fileObjectDao);
    }


    public FileObject getFileObject(int fileObjectId) {
        return (FileObject) typifiedObjectDao.getTypifiedObject(fileObjectId);
    }


    public FileObject getFileObjectByPath(String filePath) {
        return getFileObjectDao().getFileObjectByPath(filePath);
    }

}