package ru.strela.ems.core.model;


import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.HashSet;
import java.util.Set;


/**
 * User: hobal
 * Date: 11.10.2010
 * Time: 12:16:30
 */
@XmlRootElement
@XmlSeeAlso( {ObjectLabel.class} )
public class SiteMapView {


    private int id;
    private String url;
    private int systemNodeId;
    private Integer parentId;
    private int objectId;
    private int activeObjectPosition;
    private boolean mainObject;
    private String objectName;
    private Set<ObjectLabel> objectLabels;


    public SiteMapView() {
        this(0, "", 0, null, 0, 0, false, "");
    }


    public SiteMapView(int id, String url, int systemNodeId, Integer parentId, int objectId, int activeObjectPosition, boolean mainObject, String objectName) {
        this.id = id;
        this.url = url;
        this.systemNodeId = systemNodeId;
        this.parentId = parentId;
        this.objectId = objectId;
        this.activeObjectPosition = activeObjectPosition;
        this.mainObject = mainObject;
        this.objectName = objectName;
        objectLabels = new HashSet<ObjectLabel>();
    }



    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getUrl() {
        return url;
    }


    public void setUrl(String url) {
        this.url = url;
    }


    public int getSystemNodeId() {
        return systemNodeId;
    }


    public void setSystemNodeId(int systemNodeId) {
        this.systemNodeId = systemNodeId;
    }


    public Integer getParentId() {
        return parentId;
    }


    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }


    public int getObjectId() {
        return objectId;
    }


    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }


    @Override
    public int hashCode() {
        final int multiplier = 23;
        int code = 133;
        code = multiplier * code + getObjectId();
        code = multiplier * code + (getParentId() != null ? getParentId() : 0);
        code = multiplier * code + getSystemNodeId();
        code = multiplier * code + getUrl().hashCode();
        return code;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SiteMapView) {
            SiteMapView siteMap = (SiteMapView) obj;
            Integer parentId = getParentId();
            return getObjectId() == siteMap.getObjectId() && ((parentId != null && parentId.equals(siteMap.getParentId())) || (parentId == null && siteMap.getParentId() == null)) && getSystemNodeId() == siteMap.getSystemNodeId() && getUrl().equals(siteMap.getUrl());
        }
        return false;
    }


    public int getActiveObjectPosition() {
        return activeObjectPosition;
    }


    public void setActiveObjectPosition(int activeObjectPosition) {
        this.activeObjectPosition = activeObjectPosition;
    }


    public boolean isMainObject() {
        return mainObject;
    }


    public void setMainObject(boolean mainObject) {
        this.mainObject = mainObject;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("SiteMap [id=");
        sb.append(id);
        sb.append(",url='");
        sb.append(url);
        sb.append("',systemNodeId=");
        sb.append(systemNodeId);
        sb.append(",parentId=");
        sb.append(parentId);
        sb.append(",objectId=");
        sb.append(objectId);
        sb.append(",activeObjectPosition=");
        sb.append(activeObjectPosition);
        sb.append(",mainObject=");
        sb.append(mainObject);
        sb.append(",objectName='");
        sb.append(objectName);
        sb.append("']");
        return sb.toString();
    }


    public String getObjectName() {
        return objectName;
    }


    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }


    public Set<ObjectLabel> getObjectLabels() {
        return objectLabels;
    }


    public void setObjectLabels(Set<ObjectLabel> objectLabels) {
        this.objectLabels = objectLabels;
    }


    public void addObjectLabel(ObjectLabel objectLabel) {
        objectLabels.add(objectLabel);
    }
}
