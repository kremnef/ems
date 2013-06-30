package ru.strela.ems.core.model;


import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;


/**
 * User: hobal
 * Date: 04.08.2010
 * Time: 16:47:52
 */
@XmlRootElement
public class ObjectLabel implements Serializable, Comparable<ObjectLabel> {


    private int id;
    private int objectId;
    private String languageCode;
    private String label;


    public ObjectLabel() {
//        this("", "");
    }


    /*public ObjectLabel(String languageCode, String label) {
        this(0, languageCode, label);
    }*/

    /*public ObjectLabel(String label, String languageCode, int objectId) {
        this(0,  label, languageCode, objectId);
    }*/

    public ObjectLabel(String label, String languageCode, int objectId) {
//        this.id = id;
        this.languageCode = languageCode;
        this.label = label;
        this.objectId = objectId;
//          log.warn("ObjectLabel -- this.id: "+this.id+ " label: "+this.label+" languageCode: "+this.languageCode+" objectId:"+this.objectId);
    }

    /*public ObjectLabel(int id, String languageCode, String label) {
        this.id = id;
        this.languageCode = languageCode;
        this.label = label;
    }
*/
   @XmlTransient
    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    @XmlTransient
    public String getLanguageCode() {
        if (languageCode == null) {
            languageCode = "";
        }
        return languageCode;
    }


    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }


    public String getLabel() {
        if (label == null) {
            label = "";
        }
        return label;
    }


    public void setLabel(String label) {
        this.label = label;
    }
    @XmlTransient
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
        code = multiplier * code + getId();
        code = multiplier * code + getLanguageCode().hashCode();
        code = multiplier * code + getLabel().hashCode();
        return code;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ObjectLabel) {
            ObjectLabel objectLabel = (ObjectLabel) obj;
            return id == objectLabel.id && languageCode.equals(objectLabel.languageCode) && label.equals(objectLabel.label);
        }
        return false;
    }


    public int compareTo(ObjectLabel o) {
        if (id == o.id) {
            if (languageCode.equals(o.languageCode)) {
                return label.compareTo(o.label);
            }
            else {
                return languageCode.compareTo(o.languageCode);
            }
        }
        else {
            return (int) Math.signum(id - o.id);
        }
    }

}
