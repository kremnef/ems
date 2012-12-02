package ru.strela.ems.core.service;


import ru.strela.ems.core.dao.FolderDao;
import ru.strela.ems.core.model.Folder;

import java.util.List;


public interface FolderService extends SystemObjectService {

    
    FolderDao getFolderDao();
    void setFolderDao(FolderDao folderDao);
    Folder getFolder(int folderId);
    List getFolders();
    List getFolders(int parentId);
    List getFoldersByFileTypeGroup(int parentId, int fileTypeGroupId);

}