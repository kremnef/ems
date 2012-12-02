package ru.strela.ems.core.service;


import org.hibernate.Session;
import ru.strela.ems.core.dao.FileTypeGroupDao;
import ru.strela.ems.core.model.FileTypeGroup;

import java.util.List;


public interface FileTypeGroupService {


    FileTypeGroupDao getFileTypeGroupDao();
    List getFileTypeGroups();
    void setFileTypeGroupDao(FileTypeGroupDao fileTypeGroupDao);
    FileTypeGroup getFileTypeGroup(int fileTypeGroupId);
    FileTypeGroup saveFileTypeGroup(FileTypeGroup fileTypeGroup);
    void deleteFileTypeGroup(FileTypeGroup fileTypeGroup);

}
