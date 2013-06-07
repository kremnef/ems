/*
 * Eberom: a CRM web application Copyright (C) 2006 Luk Morbee
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */
package ru.strela.ems.core.dao.hibernate;


import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.strela.ems.core.dao.FileObjectDao;
import ru.strela.ems.core.model.FileObject;
import ru.strela.ems.core.model.PathObject;
import ru.strela.ems.core.model.SystemObject;
import ru.strela.ems.core.model.TypifiedObject;
import ru.strela.ems.tools.ServerTools;
import ru.tastika.tools.file.FileAddition;

import java.io.File;
import java.util.List;


public class FileObjectDaoImpl extends SystemObjectDaoImpl implements FileObjectDao {


    private final static Logger log = LoggerFactory.getLogger(FileObjectDaoImpl.class);


    public FileObjectDaoImpl() {
        super();
    }


    public Class getEntityClass() {
        return FileObject.class;
    }


    public FileObject getFileObject(int fileObjectId) {
        return (FileObject) getTypifiedObject(fileObjectId);
    }


    public FileObject getFileObjectByPath(String filePath) {
        Session session = getCurrentSession();
        FileObject fileObject = null;
        List objects = session.createQuery("from FileObject f where f.path = '" + filePath + "'").list();
        if (objects.size() > 0) {
            fileObject = (FileObject) objects.get(0);
        }
        closeSession();
        return fileObject;
    }


    @Override
    protected void deleteObject(TypifiedObject typifiedObject) {
        Session session = getCurrentSession();

        PathObject file = (PathObject) typifiedObject;
        /*StringBuffer sb = new StringBuffer("delete from image_gallery_file_object where file_object_id = ");
        sb.append(typifiedObject.getId());

        session.createSQLQuery(sb.toString()).executeUpdate();*/
        StringBuffer sb = new StringBuffer("delete from document_file_object where file_object_id = ");
        sb.append(typifiedObject.getId());
        session.createSQLQuery(sb.toString()).executeUpdate();

        sb = new StringBuffer("update content set thumbnail_id = null where thumbnail_id = ");
        sb.append(typifiedObject.getId());
        session.createSQLQuery(sb.toString()).executeUpdate();

        super.deleteObject(typifiedObject);
        closeSession();

        String realRootPath = ServerTools.getContextRealPath();
        File realFile = new File(realRootPath, file.getPath());
        FileAddition.delete(realFile);

        String prePath = file.getPath().substring(0, file.getPath().lastIndexOf('/'));

        String square = prePath+"/thumbnails/"+typifiedObject.getId()+"-"+typifiedObject.getName()+"-square.jpg";
        File squareFile = new File(realRootPath, square);
        FileAddition.delete(squareFile);

        String small = prePath+"/thumbnails/"+typifiedObject.getId()+"-"+typifiedObject.getName()+"-small.jpg";
        File smallFile = new File(realRootPath, small);
        FileAddition.delete(smallFile);

        String medium = prePath+"/thumbnails/"+typifiedObject.getId()+"-"+typifiedObject.getName()+"-medium.jpg";
        File mediumFile = new File(realRootPath, medium);
        FileAddition.delete(mediumFile);

        String large = prePath+"/thumbnails/"+typifiedObject.getId()+"-"+typifiedObject.getName()+"-large.jpg";
        File largeFile = new File(realRootPath, large);
        FileAddition.delete(largeFile);


        if (typifiedObject instanceof SystemObject) {
            String systemName = typifiedObject.getSystemName();
            String extension = FileAddition.getFileExtension(realFile);
            File previewFile = new File(realFile.getParentFile(), systemName + (extension.length() > 0 ? ("." + extension) : ""));
            if (previewFile.exists()) {
                previewFile.delete();
            }
        }
//        tx.commit();
    }

}