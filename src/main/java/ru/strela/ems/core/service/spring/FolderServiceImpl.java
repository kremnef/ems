package ru.strela.ems.core.service.spring;


import ru.strela.ems.core.dao.FolderDao;
import ru.strela.ems.core.model.Folder;
import ru.strela.ems.core.service.FolderService;

import java.util.List;


public class FolderServiceImpl extends SystemObjectServiceImpl implements FolderService {


    public FolderDao getFolderDao() {
        return (FolderDao) getTypifiedObjectDao();
    }


    public void setFolderDao(FolderDao folderDao) {
        setTypifiedObjectDao(folderDao);
    }


    public Folder getFolder(int folderId) {
        return (Folder) getTypifiedObject(folderId);
    }


    public List getFolders() {
        return getFolderDao().getFolders();
    }


    public List getFolders(int parentId) {
        return getFolderDao().getFolders(parentId);
    }

    public List getFoldersByFileTypeGroup(int parentId, int fileTypeGroupId) {
        return getFolderDao().getFoldersByFileTypeGroup(parentId, fileTypeGroupId);
    }

}
