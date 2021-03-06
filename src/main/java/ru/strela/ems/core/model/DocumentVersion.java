package ru.strela.ems.core.model;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.strela.ems.security.model.Customer;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Date;

//import javax.persistence.ManyToMany;


/**
 * Class DocumentVersion.
 *
 * @version $Revision$ $Date$
 */
@XmlRootElement

public class DocumentVersion implements java.io.Serializable {


    private final static Logger log = LoggerFactory.getLogger(DocumentVersion.class);
    private int id;
    private String state;
    private Date modifiedDateTime;
    private Date createdDateTime;
    private Customer versionModifier;
    private int version;
    private String versionComment;
    private int versionModifierId;

    private int isCheckedOut;
    private boolean isActive;
    private int languageId;
    private String languageCode;
//    private Language language;
    private boolean isLastVersion;
    private Integer contentId;



    public DocumentVersion() {
        state = "draft";
        modifiedDateTime = createdDateTime = new Date();
    }


    public DocumentVersion(Integer contentId, int languageId) {
        this.contentId = contentId;
        this.languageId = languageId;
    }
    @XmlAttribute(name="id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @XmlTransient
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @XmlAttribute(name="modifiedDateTime")
    public Date getModifiedDateTime() {
        return modifiedDateTime;
    }

    public void setModifiedDateTime(Date modifiedDateTime) {
        this.modifiedDateTime = modifiedDateTime;
    }

    @XmlAttribute(name="createdDateTime")
    public Date getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(Date createdDateTime) {
        this.createdDateTime = createdDateTime;
    }
    @XmlTransient
    public Customer getVersionModifier() {
        return versionModifier;
    }

    public void setVersionModifier(Customer versionModifier) {
        this.versionModifier = versionModifier;
    }

    @XmlTransient
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @XmlTransient
    public String getVersionComment() {
        return versionComment;
    }

    public void setVersionComment(String versionComment) {
        this.versionComment = versionComment;
    }
    @XmlTransient
    public int getVersionModifierId() {
        return versionModifierId;
    }

    public void setVersionModifierId(int versionModifierId) {
        this.versionModifierId = versionModifierId;
    }
    @XmlTransient
    public int getCheckedOut() {
        return isCheckedOut;
    }

    public void setCheckedOut(int checkedOut) {
        isCheckedOut = checkedOut;
    }
    @XmlTransient
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
    /*public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }
*/
    @XmlTransient
    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    @XmlAttribute(name="contentId")
    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }
    @XmlTransient
    public boolean getIsLastVersion() {
        return isLastVersion;
    }

    public void setIsLastVersion(boolean isLastVersion) {
        this.isLastVersion = isLastVersion;
    }
}