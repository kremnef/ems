package ru.strela.ems.core.service.spring;


import org.hibernate.Session;
import ru.strela.ems.core.dao.FileTypeGroupDao;
import ru.strela.ems.core.model.FileTypeGroup;
import ru.strela.ems.core.service.FileTypeGroupService;

import java.util.List;


public class FileTypeGroupServiceImpl implements FileTypeGroupService {


    private FileTypeGroupDao fileTypeGroupDao = null;


    public FileTypeGroupDao getFileTypeGroupDao() {
        return fileTypeGroupDao;
    }


    public void setFileTypeGroupDao(FileTypeGroupDao fileTypeGroupDao) {
        this.fileTypeGroupDao = fileTypeGroupDao;
    }


    public FileTypeGroup getFileTypeGroup(int fileTypeGroupId) {
        return fileTypeGroupDao.getFileTypeGroup(fileTypeGroupId);
    }

    public List getFileTypeGroups() {
            return getFileTypeGroupDao().getFileTypeGroups();
        }



    public FileTypeGroup saveFileTypeGroup(FileTypeGroup FileTypeGroup) {
        return fileTypeGroupDao.saveFileTypeGroup(FileTypeGroup);
    }


    public void deleteFileTypeGroup(FileTypeGroup fileTypeGroup) {
        fileTypeGroupDao.deleteFileTypeGroup(fileTypeGroup);
    }




}
