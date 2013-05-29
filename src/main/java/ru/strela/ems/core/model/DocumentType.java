package ru.strela.ems.core.model;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Class DocumentType.
 *
 * @version $Revision$ $Date$
 */
public class DocumentType implements java.io.Serializable {


    private int id;
    private String name;
    private String path;


    private String description;
    private int type;
    private boolean advertisement;


    public DocumentType(int id, String name, String path, String description, int type, boolean advertisement) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.description = description;
        this.type = type;
        this.advertisement = advertisement;
    }

    public DocumentType() {
    }

    @XmlAttribute(name="id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public String getPath() {
        return this.path;
    }

    @XmlTransient
    public Integer getType() {
        return this.type;
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setPath(String path) {
        this.path = path;

    }


    public void setType(Integer type) {
        this.type = type;
    }

//    @XmlTransient
    @XmlAttribute(name="advertisement")
    public boolean isAdvertisement() {
        return advertisement;
    }


    public void setAdvertisement(boolean advertisement) {
        this.advertisement = advertisement;
    }

    @XmlTransient
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
