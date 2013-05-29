package ru.strela.ems.core.model;


import javax.xml.bind.annotation.XmlAttribute;

/**
 * User: hobal
 * Date: 11.10.2010
 * Time: 15:36:28
 */
public class SiteMapObject implements Cloneable {


    private int id;
    private int objectId;
    private int mainObjectId;
    private int siteMapId;
    private String baseUrl;
    private String objectUrl;


    public SiteMapObject() {
        this(0, 0, "", "");
    }


    public SiteMapObject(int objectId, int mainObjectId, String baseUrl, String objectUrl) {
        this.objectId = objectId;
        this.mainObjectId = mainObjectId;
        this.baseUrl = baseUrl;
        this.objectUrl = objectUrl;
    }

    @XmlAttribute(name="id")
    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    @XmlAttribute(name="objectId")
    public int getObjectId() {
        return objectId;
    }


    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    @XmlAttribute(name="siteMapId")
    public int getSiteMapId() {
        return siteMapId;
    }


    public void setSiteMapId(int siteMapId) {
        this.siteMapId = siteMapId;
    }


    public String getBaseUrl() {
        return baseUrl;
    }


    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }


    public String getObjectUrl() {
        return objectUrl;
    }


    public void setObjectUrl(String objectUrl) {
        this.objectUrl = objectUrl;
    }


    @Override
    public int hashCode() {
        final int multiplier = 23;
        int code = 133;
        code = multiplier * code + getObjectId();
        code = multiplier * code + getSiteMapId();
        return code;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SiteMapObject) {
            SiteMapObject siteMapObject = (SiteMapObject) obj;
            return getObjectId() == siteMapObject.getObjectId() && getSiteMapId() == siteMapObject.getSiteMapId();
        }
        return false;
    }

    @XmlAttribute(name="mainObjectId")
    public int getMainObjectId() {
        return mainObjectId;
    }


    public void setMainObjectId(int mainObjectId) {
        this.mainObjectId = mainObjectId;
    }


    @Override
    public Object clone() {
        return new SiteMapObject(objectId, mainObjectId, baseUrl, objectUrl);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("SiteMapObject[id=");
        sb.append(id);
        sb.append(",objectId=");
        sb.append(objectId);
        sb.append(",mainObjectId=");
        sb.append(mainObjectId);
        sb.append(",siteMapId=");
        sb.append(siteMapId);
        sb.append(",baseUrl='");
        sb.append(baseUrl);
        sb.append("',objectUrl='");
        sb.append(objectUrl);
        sb.append("']");
        return sb.toString();
    }
}
