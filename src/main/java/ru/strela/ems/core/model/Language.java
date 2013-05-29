package ru.strela.ems.core.model;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


/**
 * Class Language.
 *
 * @version $Revision$ $Date$
 */
@XmlRootElement
public class Language extends TypifiedObject implements java.io.Serializable {


    private String code;
    private String charset;
    private String name;
    private boolean isPublished;

    private boolean isDefaultLang;


    public Language(String code, String name, String charset, boolean isPublished, boolean isDefaultLang) {
        this.code = code;
        this.name = name;
        this.charset = charset;
        this.isPublished = isPublished;
        this.isDefaultLang = isDefaultLang;
    }


    public Language() {
        code = name = "";
        charset = "UTF-8";
    }

    @XmlTransient
    public String getName() {
        return this.name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getCode() {
        return this.code;
    }


    public void setCode(String code) {
        this.code = code;
    }

    @XmlAttribute(name="charset")
    public String getCharset() {
        return this.charset;
    }


    public void setCharset(String charset) {
        this.charset = charset;
    }


    public String toString() {
        return name;
    }

    @XmlTransient
    public boolean getIsPublished() {
        return this.isPublished;
    }


    public void setIsPublished(boolean isPublished) {
        this.isPublished = isPublished;
    }

    @XmlTransient
    public boolean getIsDefaultLang() {
        return this.isDefaultLang;
    }


    public void setIsDefaultLang(boolean isDefaultLang) {
        this.isDefaultLang = isDefaultLang;
    }
}
