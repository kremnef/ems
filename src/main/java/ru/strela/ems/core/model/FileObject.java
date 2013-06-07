package ru.strela.ems.core.model;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.strela.ems.actions.ThumbnailAction;
import ru.strela.ems.tools.ServerTools;
import ru.tastika.tools.file.FileAddition;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.File;
import java.io.IOException;


@XmlRootElement
public class FileObject extends TypifiedObject implements java.io.Serializable, SystemObject, PathObject {


    private static final String CONTEXT_TYPE_IMAGE_PREFIX = "image/";

    private String path;
    private String contentType;
    private int size;

    private int used;
    private EmsObject emsObject;
    private final static Logger log = LoggerFactory.getLogger(FileObject.class);


    private int fileTypeGroupId;






    public FileObject() {
        super();
        path = contentType = "";
        emsObject = new EmsObject();
        emsObject.setEntity(getClass().getSimpleName());

        log.debug("NEW FILE IS UPLOADED");
    }

    public FileObject(String path, String contentType, int size, int used, int fileTypeGroupId, EmsObject emsObject) {
        this.path = path;
        this.contentType = contentType;
        this.size = size;
        this.used = used;
        this.fileTypeGroupId = fileTypeGroupId;
        this.emsObject = emsObject;
    }


    public void setId(Integer id) {
        super.setId(id);
    }


    /**
     * Returns the value of field 'contentType'.
     *
     * @return the value of field 'contentType'.
     */
    public String getContentType() {
        return this.contentType;
    }


    /**
     * Returns the value of field 'path'.
     *
     * @return the value of field 'path'.
     */
    public java.lang.String getPath() {
        return this.path;
    }


    /**
     * Returns the value of field 'size'.
     *
     * @return the value of field 'size'.
     */


    public int getSize() {
        return this.size;
    }


    /**
     * Sets the value of field 'contentType'.
     *
     * @param contentType the value of field 'contentType'
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }


    /**
     * Sets the value of field 'path'.
     *
     * @param path the value of field 'filePath'.
     */
    public void setPath(String path) {
        this.path = path;
    }


    /**
     * Sets the value of field 'size'.
     *
     * @param size the value of field 'size'.
     */
    public void setSize(int size) {
        this.size = size;
    }


    /**
     * Returns the value of field 'used'.
     *
     * @return the value of field 'used'.
     */
    @XmlTransient
    public int getUsed() {
        return used;
    }

    /**
     * Sets the value of field 'used'.
     *
     * @param used the value of field 'used'.
     */

    public void setUsed(int used) {
        this.used = used;
    }

    /**
     * Returns the value of field 'fileTypeGroupId'.
     *
     * @return the value of field 'fileTypeGroupId'.
     */

    @XmlAttribute(name = "fileTypeGroupId")
    public int getFileTypeGroupId() {
        return fileTypeGroupId;
    }

    /**
     * Sets the value of field 'fileTypeGroupId'.
     *
     * @param fileTypeGroupId the value of field 'fileTypeGroupId'.
     */

    public void setFileTypeGroupId(int fileTypeGroupId) {
        this.fileTypeGroupId = fileTypeGroupId;
    }


    public boolean createPreview(String contextRealPath) {
        int square = Integer.parseInt(ServerTools.getGlobalParameter("image.icon.square").toString());
        int small = Integer.parseInt(ServerTools.getGlobalParameter("image.icon.small").toString());
        int medium = Integer.parseInt(ServerTools.getGlobalParameter("image.icon.medium").toString());
        int large = Integer.parseInt(ServerTools.getGlobalParameter("image.icon.large").toString());
        String aspectRatio = ServerTools.getGlobalParameter("image.aspectRatio").toString();
//        String mediatekaFolder = ServerTools.getGlobalParameter("mediatekaFolder").toString();


        log.warn("aspectRatio-" + aspectRatio);
        boolean success = false;
        if (getContentType().startsWith(CONTEXT_TYPE_IMAGE_PREFIX)) {

//            folder
//            File thumbnailFolder = new File(contextRealPath, getPath()+"/thumbnails");


            log.warn("getPath()-" + getPath());


            File sourceFile = new File(contextRealPath, getPath());

            if (sourceFile.isFile()) {
                try {
//                    BufferedImage image = GraphicsUtilities.getBufferedImage(sourceFile);
//                    int minDimension = Math.min(image.getWidth(), image.getHeight());
//                    double scaleFactor = (double) PREVIEW_DIMENSION / minDimension;
//                    image = GraphicsUtilities.scaleBufferedImage(image, scaleFactor);
//                    int x = (image.getWidth() - PREVIEW_DIMENSION) / 2;
//                    int y = (image.getHeight() - PREVIEW_DIMENSION) / 2;
//                    image = image.getSubimage(x, y, PREVIEW_DIMENSION, PREVIEW_DIMENSION);
//                    File previewFile = new File(sourceFile.getParentFile(), emsObject.getSystemName() + "." + PNG_FORMAT);
//                    ImageIO.write(image, PNG_FORMAT, previewFile);
//                    SSSS
                    String fileName = sourceFile.getName();
                    String localName = FileAddition.getFileBaseName(fileName);

                    ThumbnailAction thumbnail = new ThumbnailAction();
                    thumbnail.createThumbnail(sourceFile, localName + "-square.jpg", square, "1:1");
                    thumbnail.createThumbnail(sourceFile, localName + "-small.jpg", small, aspectRatio);
                    thumbnail.createThumbnail(sourceFile, localName + "-medium.jpg", medium, aspectRatio);
                    thumbnail.createThumbnail(sourceFile, localName + "-large.jpg", large, aspectRatio);

                    success = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return success;
    }


    public EmsObject getEmsObject() {
        if (emsObject == null) {
            emsObject = new EmsObject();
        }
        return emsObject;
    }


    public void setEmsObject(EmsObject emsObject) {
        this.emsObject = emsObject;
    }


    public String getSystemName() {
        return emsObject.getSystemName();
    }


    public void setSystemName(String systemName) {
        emsObject.setSystemName(systemName);
    }


    /*@Override
    public void setParent(TypifiedObject typifiedObject) {
        if (typifiedObject instanceof SystemObject) {
            emsObject.setParent(typifiedObject);
        }
    }


    @XmlAttribute(name = "parentId")
    public Integer getParentId() {
        return emsObject.getParentId();
    }


    public TypifiedObject getParent() {
        return emsObject.getParent();
    }*/


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FileObject) {
            FileObject fileObject = (FileObject) obj;
            return getName().equals(fileObject.getName()) && getPath().equals(fileObject.getPath()) && getSize() == fileObject.getSize();
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
        return getPath();
    }
}
