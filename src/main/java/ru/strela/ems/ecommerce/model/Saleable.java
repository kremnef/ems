package ru.strela.ems.ecommerce.model;


import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement
@XmlSeeAlso({Photo.class})
public abstract class Saleable {


    private Integer id;

//    private String name;

    public Saleable() {
        this.id = 0;
//        this.name = "";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

//    public String getName() {
//        return name;
//    }
//
//
//    public void setName(String name) {
//        if (name != null) {
//            this.name = name;
//        }
//    }


    /* void setActive(boolean active);
   boolean isActive();*/

}