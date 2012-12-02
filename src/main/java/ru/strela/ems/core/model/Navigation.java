package ru.strela.ems.core.model;
//chenged

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@XmlRootElement
public class Navigation extends TypifiedObject implements java.io.Serializable, SystemObject {


    private Integer systemNodeId;


    private String systemNodeName;
    @XmlTransient
    private SystemNode systemNode;
    private String outerURL;


    private String pathURL;
    private int rootId;
    //    private int position;
    private EmsObject emsObject;
    private int redirectTo;


    public Navigation() {
        super();
        setRootId(0);
        emsObject = new EmsObject();
        emsObject.setEntity(getClass().getSimpleName());
    }


    /**
     * @return the systemNode
     */
    @XmlTransient
    public SystemNode getSystemNode() {
        return systemNode;
    }


    /**
     * @param systemNode the systemNode to set
     */
    public void setSystemNode(SystemNode systemNode) {
        if (systemNode != null) {
            setSystemNodeId(systemNode.getId());
        } else {
            setSystemNodeId(null);
        }
        this.systemNode = systemNode;
    }


    /**
     * @return the rootId
     */
    public int getRootId() {
        return rootId;
    }


    /**
     * @param rootId the rootId to set
     */
    public void setRootId(int rootId) {
        this.rootId = rootId;
    }


    public Integer getSystemNodeId() {
        return systemNodeId;
    }


    public void setSystemNodeId(Integer systemNodeId) {
        if (systemNodeId != null && systemNodeId == 0) {
            systemNodeId = null;
        }
        this.systemNodeId = systemNodeId;
    }


    /* public int getPosition() {
        return position;
    }*/


    public String getOuterURL() {
        return outerURL;
    }


    /* public void setPosition(int position) {
        this.position = position;
    }*/


    public void setOuterURL(String outerURL) {
        this.outerURL = outerURL;
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


    public Integer getParentId() {
        return emsObject.getParentId();
    }


    public TypifiedObject getParent() {
        return emsObject.getParent();
    }


    @Override
    public String toString() {
        return getName();
    }


    public int getRedirectTo() {
        return redirectTo;
    }


    public void setRedirectTo(int redirectTo) {
        this.redirectTo = redirectTo;
    }


    public String getPathURL() {
        return pathURL;
    }

    public void setPathURL(String pathURL) {
        this.pathURL = pathURL;
    }

    public String getSystemNodeName() {
        return systemNodeName;
    }

    public void setSystemNodeName(String systemNodeName) {
        this.systemNodeName = systemNodeName;
    }
}