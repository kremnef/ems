package ru.strela.ems.core.model;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import java.util.*;


/**
 * User: hobal
 * Date: 11.10.2010
 * Time: 12:16:30
 */
public class SiteMap {


    private int id;
    private String url;
    private int systemNodeId;
    private Integer parentId;
    private int objectId;
    private int activeObjectPosition;
    private boolean mainObject;
    private int levels;
    private int redirectTo;
    private int navigationId;
    private Set<SiteMap> children;
    private Map<Integer, SiteMapObject> siteMapObjects;


    public SiteMap() {
        url = "";
        siteMapObjects = new HashMap<Integer, SiteMapObject>();
        children = new HashSet<SiteMap>();
    }

    public SiteMap(int id, String url, int systemNodeId, Integer parentId, int objectId, int activeObjectPosition, boolean mainObject, int levels, int redirectTo, int navigationId, Set<SiteMap> children, Map<Integer, SiteMapObject> siteMapObjects) {
        this.id = id;
        this.url = url;
        this.systemNodeId = systemNodeId;
        this.parentId = parentId;
        this.objectId = objectId;
        this.activeObjectPosition = activeObjectPosition;
        this.mainObject = mainObject;
        this.levels = levels;
        this.redirectTo = redirectTo;
        this.navigationId = navigationId;
        this.children = children;
        this.siteMapObjects = siteMapObjects;
    }
    @XmlTransient
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
        if (url == null) {
            url = "";
        }
        this.url = url;
    }

    @XmlAttribute(name="systemNodeId")
    public int getSystemNodeId() {
        return systemNodeId;
    }


    public void setSystemNodeId(int systemNodeId) {
        this.systemNodeId = systemNodeId;
    }

    @XmlAttribute(name="parentId")
    public Integer getParentId() {
        return parentId;
    }


    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }


    public Map<Integer, SiteMapObject> getSiteMapObjects() {
        return siteMapObjects;
    }


    public void setSiteMapObjects(Map<Integer, SiteMapObject> siteMapObjects) {
        this.siteMapObjects = siteMapObjects;
    }

    @XmlAttribute(name="objectId")
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
        if (obj instanceof SiteMap) {
            SiteMap siteMap = (SiteMap) obj;
            Integer parentId = getParentId();
            return getObjectId() == siteMap.getObjectId() && ((parentId != null && parentId.equals(siteMap.getParentId())) || (parentId == null && siteMap.getParentId() == null)) && getSystemNodeId() == siteMap.getSystemNodeId() && getUrl().equals(siteMap.getUrl());
        }
        return false;
    }


    public Set<SiteMap> getChildren() {
        return children;
    }


    public void setChildren(Set<SiteMap> children) {
        this.children = children;
    }


    public void addChild(SiteMap siteMap) {
        children.add(siteMap);
        //siteMap.setParentId(getId());
    }


    public SiteMapObject setSiteMapObject(int index, SiteMapObject siteMapObject) {
        return siteMapObjects.put(index, siteMapObject);
        //siteMapObject.setSiteMapId(getId());
    }

    @XmlAttribute(name="activeObjectPosition")
    public int getActiveObjectPosition() {
        return activeObjectPosition;
    }


    public void setActiveObjectPosition(int activeObjectPosition) {
        this.activeObjectPosition = activeObjectPosition;
    }

    @XmlAttribute(name="isMainObject")
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
        sb.append(",levels=");
        sb.append(levels);
        sb.append(",activeObjectPosition=");
        sb.append(activeObjectPosition);
        sb.append(",mainObject=");
        sb.append(mainObject);
        sb.append(",siteMapObjects=");
        sb.append(siteMapObjects);
        sb.append("]");
        return sb.toString();
    }


    public SiteMapObject getSiteMapObject(int position) {
        return siteMapObjects.get(position);
    }


    public Map<Integer, SiteMapObject> clearSiteMapObjects() {
        HashMap<Integer, SiteMapObject> mapObjectCollection = new HashMap<Integer, SiteMapObject>(siteMapObjects);
        siteMapObjects.clear();
        return mapObjectCollection;
    }

    @XmlAttribute(name="levels")
    public int getLevels() {
        return levels;
    }


    public void setLevels(int levels) {
        if (levels < 0) {
            levels = 0;
        }
        this.levels = levels;
    }

    @XmlAttribute(name="redirectTo")
    public int getRedirectTo() {
        return redirectTo;
    }


    public void setRedirectTo(int redirectTo) {
        this.redirectTo = redirectTo;
    }

    @XmlAttribute(name="navigationId")
    public int getNavigationId() {
        return navigationId;
    }


    public void setNavigationId(int navigationId) {
        this.navigationId = navigationId;
    }
}
