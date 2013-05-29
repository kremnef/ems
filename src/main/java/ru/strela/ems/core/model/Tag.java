package ru.strela.ems.core.model;


import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@XmlRootElement
public class Tag implements java.io.Serializable{



//    private static final int PREVIEW_DIMENSION = 80;
    private Integer id;
    private String tag;



    public Tag() {
        super();
    }

    public Tag(Integer id, String tagName) {
        super();
        this.id = id;
        this.tag = tagName;
    }
    @XmlTransient
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }


    /**
     * Returns the value of field 'contentType'.
     * @return the value of field 'contentType'.
     */
//    @XmlAttribute(name="tag")
    public String getTag() {
        return this.tag;
    }




    /**
     * Sets the value of field 'contentType'.
     * @param tagName the value of field 'contentType'
     */
    public void setTag(String tagName) {
        this.tag = tagName;
    }

    /*public final static String getSearchfields(){
        String re = this.Tag;
        return re;

    }*/

}
