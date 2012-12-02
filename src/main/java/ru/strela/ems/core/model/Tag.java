package ru.strela.ems.core.model;


import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@XmlRootElement
public class Tag implements java.io.Serializable{



//    private static final int PREVIEW_DIMENSION = 80;
    private Integer id;
    private String Tag;



    public Tag() {
        super();
    }

    public Tag(Integer id, String tag) {
        super();
        this.id = id;
        Tag = tag;
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
    public String getTag() {
        return this.Tag;
    }




    /**
     * Sets the value of field 'contentType'.
     * @param tag the value of field 'contentType'
     */
    public void setTag(String tag) {
        this.Tag = tag;
    }

    /*public final static String getSearchfields(){
        String re = this.Tag;
        return re;

    }*/

}
