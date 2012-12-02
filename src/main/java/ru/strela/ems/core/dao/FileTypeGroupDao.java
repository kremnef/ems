package ru.strela.ems.core.dao;


import org.hibernate.Session;
import ru.strela.ems.core.model.FileTypeGroup;

import java.util.List;


public interface FileTypeGroupDao {

    FileTypeGroup getFileTypeGroup(int fileTypeGroupId);
    List getFileTypeGroups();
    FileTypeGroup saveFileTypeGroup(FileTypeGroup fileTypeGroup);
    void deleteFileTypeGroup(FileTypeGroup fileTypeGroup);
}