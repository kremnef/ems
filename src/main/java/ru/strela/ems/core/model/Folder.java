package ru.strela.ems.core.model;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * User: hobal
 * Date: 18.06.2010
 * Time: 13:10:19
 */
@XmlRootElement
@XmlSeeAlso({FileObject.class})
public class Folder extends TypifiedObject implements java.io.Serializable, SystemObject, PathObject {


    private String path;
    private EmsObject emsObject;


    public Folder()  {
        super();
        emsObject = new EmsObject();
        emsObject.setEntity(getClass().getSimpleName());
    }


    public String getPath() {
        return path;
    }


    public void setPath(String path) {
        this.path = path;
    }


    public EmsObject getEmsObject() {
        if (emsObject == null) {
            emsObject = new EmsObject();
        }
        return emsObject;
    }


    public void setEmsObject(EmsObject emsObject) {
        this.emsObject = emsObject;
    }


    public String getSystemName() {
        return getEmsObject().getSystemName();
    }


    public void setSystemName(String systemName) {
        getEmsObject().setSystemName(systemName);
    }


    @Override
    public void setParent(TypifiedObject typifiedObject) {
        if (typifiedObject instanceof SystemObject) {
            emsObject.setParent(typifiedObject);
        }
    }

    @XmlAttribute(name="parentId")
    public Integer getParentId() {
        return emsObject.getParentId();
    }


    public TypifiedObject getParent() {
        return emsObject.getParent();
    }

}