package ru.strela.ems.core.model;


import javax.persistence.Transient;

/**
 * User: hobal
 * Date: 09.05.2010
 * Time: 17:53:53
 */
public class ObjectTypeAction implements java.io.Serializable {


    private int id;
    private int objectTypeId;
    private ObjectType objectType;
    private String name;
    private String xsltPath;



    public ObjectTypeAction() {
        this.name = this.xsltPath = "";
    }


    /**
     * @return the id
     */
    public int getId() {
        return id;
    }


    /**
     * @param id the id to set
     */
    private void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return getName();
    }

    @Transient
    public int getObjectTypeId() {
        return objectTypeId;
    }


    public void setObjectTypeId(int objectTypeId) {
        this.objectTypeId = objectTypeId;
    }

    @Transient
    public ObjectType getObjectType() {
        return objectType;
    }


    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getXsltPath() {
        return xsltPath;
    }


    public void setXsltPath(String xsltPath) {
        this.xsltPath = xsltPath;
    }
}

