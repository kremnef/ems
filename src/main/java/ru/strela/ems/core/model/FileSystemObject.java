package ru.strela.ems.core.model;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

/**
 * User: hobal
 * Date: 27.09.2010
 * Time: 16:45:30
 */
public class FileSystemObject {


    private static final String TYPE_FOLDER = "folder";
    private int id;
    private String name;
    private String systemName;
    private String fileType;
    private int size;
    private String path;
    private boolean folder;



    public FileSystemObject() {
        this(0, "", "", "", 0, "", false);
    }


    public FileSystemObject(FileObject fileObject) {
        this(fileObject.getId(), fileObject.getName(), fileObject.getSystemName(), fileObject.getContentType(), fileObject.getSize(), fileObject.getPath(), false);
    }


    public FileSystemObject(Folder folder) {
        this(folder.getId(), folder.getName(), folder.getSystemName(), TYPE_FOLDER, 0, folder.getPath(), true);
    }



    public FileSystemObject(int id, String name, String systemName, String fileType, int size, String path, boolean folder) {
        this.id = id;
        this.name = name;
        this.systemName = systemName;
        this.fileType = fileType;
        this.size = size;
        this.path = path;
        this.folder = folder;
    }


    @XmlAttribute(name="id")
    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getSystemName() {
        return systemName;
    }


    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }


    public String getFileType() {
        return fileType;
    }


    public void setFileType(String fileType) {
        this.fileType = fileType;
    }


    public int getSize() {
        return size;
    }


    public void setSize(int size) {
        this.size = size;
    }


    public String getPath() {
        return path;
    }


    public void setPath(String path) {
        this.path = path;
    }

    @XmlTransient
    public boolean isFolder() {
        return folder;
    }


    public void setFolder(boolean folder) {
        this.folder = folder;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FileSystemObject) {
            FileSystemObject fileSystemObject = (FileSystemObject) obj;
            return getName().equals(fileSystemObject.getName()) && getPath().equals(fileSystemObject.getPath()) && getSize() == fileSystemObject.getSize();
        }
        return false;
    }


    @Override
    public int hashCode() {
        final int multiplier = 23;
        int code = 133;
        code = multiplier * code + getId();
        code = multiplier * code + getName().hashCode();
        code = multiplier * code + getPath().hashCode();
        code = multiplier * code + getSize();
        return code;
    }


    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("FileSystemNode[id=");
        sb.append(id);
        sb.append(",name='");
        sb.append(name);
        sb.append("',systemName='");
        sb.append(systemName);
        sb.append("',fileType='");
        sb.append(fileType);
        sb.append("',size=");
        sb.append(size);
        sb.append(",path='");
        sb.append(path);
        sb.append("',folder=");
        sb.append(folder);
        sb.append("]");
        return sb.toString();

    }

}
