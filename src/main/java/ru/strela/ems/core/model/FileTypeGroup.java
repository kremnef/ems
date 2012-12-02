package ru.strela.ems.core.model;


import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;



@XmlRootElement
public class FileTypeGroup implements Serializable {


    private int id;
    private String name;


    public FileTypeGroup() {
    }


    public FileTypeGroup(String name) {
        this.name = name;
    }

    @XmlTransient
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


}
