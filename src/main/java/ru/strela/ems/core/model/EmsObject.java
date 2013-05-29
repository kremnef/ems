package ru.strela.ems.core.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


/**
 * @author hobal
 */
@XmlRootElement
public class EmsObject implements java.io.Serializable {


    private final static Logger log = LoggerFactory.getLogger(EmsObject.class);
    private int id;
    private String entity;
    private int objectId;
    private String systemName;
    private Integer parentId;
    private TypifiedObject parent;
//    private Set<SystemObject> children;
    private int childrenCount;
    protected boolean isProtected;
    protected boolean isPublished;


    public EmsObject(int id, String entity, int objectId, String systemName, Integer parentId, TypifiedObject parent, int childrenCount, boolean aProtected, boolean published) {
        this.id = id;
        this.entity = entity;
        this.objectId = objectId;
        this.systemName = systemName;
        this.parentId = parentId;
        this.parent = parent;
        this.childrenCount = childrenCount;
        isProtected = aProtected;
        isPublished = published;
    }

    public EmsObject() {
        super();
        this.systemName = "";
    }


    /**
     * @return the systemName
     */

    @XmlTransient
    public String getSystemName() {
        return systemName;
    }


    /**
     * @param systemName the systemName to set
     */
    public void setSystemName(String systemName) {
        if (systemName != null) {
            this.systemName = systemName;
        }
    }


    /**
     * @return the parent
     */
    @XmlTransient
    public TypifiedObject getParent() {
        return parent;
    }


    /**
     * @param parent the parent to set
     */
    public void setParent(TypifiedObject parent) {
        this.parent = parent;
        ////log.info("parent = " + parent);
        if (parent != null) {
            setParentId(parent.getId());
        }
        else {
            setParentId(null);
        }
    }


    /**
     * @return the parentId
     */
    @XmlAttribute(name="parentId")
    public Integer getParentId() {
        return parentId;
    }


    /**
     * @param parentId the parentId to set
     */
    public void setParentId(Integer parentId) {
        if (parentId != null && parentId == 0) {
            parentId = null;
        }
        ////log.info("parentId = " + parentId);
        this.parentId = parentId;
    }


    /**
     * @return the isProtected
     */
    @XmlTransient
    public boolean getIsProtected() {
        return this.isProtected;
    }

    /**
     * @return the isPublished
     */
    @XmlTransient
    public boolean getIsPublished() {
        return this.isPublished;
    }


//    /**
//     * @return the children
//     */
//    @XmlTransient
//    public Set<SystemObject> getChildren() {
//        return children;
//    }

//    /**
//     * @param children the children to set
//     */
//    public void setChildren(Set<SystemObject> children) {
//        this.children = children;
//    }

    /**
     * @return the childrenCount
     */
    @XmlAttribute(name="childrenCount")
    public int getChildrenCount() {
        return childrenCount;
    }

    /**
     * @param childrenCount the childrenCount to set
     */
    public void setChildrenCount(int childrenCount) {
        this.childrenCount = childrenCount;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EmsObject) {
            EmsObject emsObject = (EmsObject) obj;
            return getId() == emsObject.getId() && systemName.equals(emsObject.getSystemName());
        }
        return false;
    }


    @Override
    public int hashCode() {
        final int multiplier = 23;
        int code = 133;
        code = multiplier * code + getId();
        code = multiplier * code + systemName.hashCode();
        return code;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName());
        sb.append("[id=");
        sb.append(getId());
        sb.append(",systemName='");
        sb.append(systemName);
        sb.append("',parentId=");
        sb.append(objectId);
        sb.append(",objectId=");
        sb.append(parentId);
        sb.append("]");
        return sb.toString();
    }


    public void setIsProtected(boolean isProtected) {
         this.isProtected = isProtected;
    }


    public void setIsPublished(boolean isPublished) {
         this.isPublished = isPublished;
    }

    @XmlAttribute(name="id")
    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    @XmlTransient
    public String getEntity() {
        return entity;
    }


    public void setEntity(String entity) {
        this.entity = entity;
    }

    @XmlTransient
    public int getObjectId() {
        return objectId;
    }


    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }


}

