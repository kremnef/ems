package ru.strela.ems.core.model;


import javax.xml.bind.annotation.XmlAttribute;

/**
 * User: hobal
 * Date: 01.05.2010
 * Time: 2:40:00
 */
public abstract class SystemNodeObject implements java.io.Serializable {


    private int id;
    private int systemNodeId;
    private int objectId;
    private int position;
    private int typeActionId;
    private ObjectTypeAction typeAction;
    private int levels;
    private int itemsOnPage;
    private String sortField;

    private String sortDirection;
    private Integer tagId;

    private String renderLike;

//    private int childrenCount;
    private int totalPages;

    @XmlAttribute(name="systemNodeId")
    public int getSystemNodeId() {
        return systemNodeId;
    }


    public void setSystemNodeId(int systemNodeId) {
        this.systemNodeId = systemNodeId;
    }

    @XmlAttribute(name="objectId")
    public int getObjectId() {
        return objectId;
    }


    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

    @XmlAttribute(name="position")
    public int getPosition() {
        return position;
    }


    public void setPosition(int position) {
        this.position = position;
    }

    @XmlAttribute(name="id")
    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("SystemNodeObject [id = ");
        sb.append(id);
        sb.append(", systemNodeId = ");
        sb.append(systemNodeId);
        sb.append(", objectId = ");
        sb.append(objectId);
        sb.append(", position = ");
        sb.append(position);
        sb.append("]");
        return sb.toString();
    }

    @XmlAttribute(name="typeActionId")
    public int getTypeActionId() {
        return typeActionId;
    }


    public void setTypeActionId(int typeActionId) {
        this.typeActionId = typeActionId;
    }

    @XmlAttribute(name="levels")
    public int getLevels() {
        return levels;
    }


    public void setLevels(int levels) {
        this.levels = levels;
    }


    public ObjectTypeAction getTypeAction() {
        return typeAction;
    }


    public void setTypeAction(ObjectTypeAction typeAction) {
        this.typeAction = typeAction;
    }

    @XmlAttribute(name="itemsOnPage")
    public int getItemsOnPage() {
        return itemsOnPage;
    }

    public void setItemsOnPage(int itemsOnPage) {
        this.itemsOnPage = itemsOnPage;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }
    @XmlAttribute(name="tagId")
    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getRenderLike() {
        return renderLike;
    }

    public void setRenderLike(String renderLike) {
        this.renderLike = renderLike;
    }



//    kremnef add

    /**
        * @return the childrenCount
        *//*
       public int getChildrenCount() {
           return childrenCount;
       }

       *//**
        * @param childrenCount the childrenCount to set
        *//*
       public void setChildrenCount(int childrenCount) {
           this.childrenCount = childrenCount;
       }*/
    /**
        * @return the totalPages
        */

        @XmlAttribute(name="totalPages")
       public int getTotalPages() {
//           totalPages = Math.round(childrenCount/itemsOnPage);
           return totalPages;
       }

       /**
        * @param totalPages the totalPages to set
        */
       public void setTotalPages(int totalPages) {
           this.totalPages = totalPages;
       }
}
