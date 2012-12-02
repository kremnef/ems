package ru.strela.ems.core.dao;


import ru.strela.ems.core.model.Folder;

import java.util.List;


public interface FolderDao extends SystemObjectDao {


    Folder getFolder(int folderId);
	List getFolders();
    List getFolders(int parentId);
    List getFoldersByFileTypeGroup(int parentId, int fileTypeGroupId);

}