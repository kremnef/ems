package ru.strela.ems.core.model;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Set;


/**
 * User: hobal
 * Date: 03.05.2010
 * Time: 23:53:34
 */
public class ObjectType implements java.io.Serializable {


    private Integer id;
    private String className;
    private String name;
    private boolean embedded;
    private boolean hasChildren;

    private Set<ObjectTypeAction> typeActions;


    public ObjectType() {
        this.className = this.name = "";
    }


    /**
     * @return the id
     */
    @XmlAttribute(name="id")
    public Integer getId() {
        return id;
    }


    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    @XmlTransient
    public String getClassName() {
        return className;
    }


    public void setClassName(String className) {
        this.className = className;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public boolean isEmbedded() {
        return embedded;
    }


    public void setEmbedded(boolean embedded) {
        this.embedded = embedded;
    }


    @Override
    public String toString() {
        return getName();
    }


    @XmlTransient
    public Set<ObjectTypeAction> getTypeActions() {
        return typeActions;
    }


    public void setTypeActions(Set<ObjectTypeAction> typeActions) {
        this.typeActions = typeActions;
    }

    @XmlTransient
    public boolean isHasChildren() {
        return hasChildren;
    }


    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    @XmlTransient
    public boolean isHierarchical() {
        boolean hierarchical = false;
        try {
            Class typeClass = Class.forName(getClassName());
            Class[] interfaces = typeClass.getInterfaces();
            for (Class parentInterface : interfaces) {
                if (parentInterface == SystemObject.class) {
                    hierarchical = true;
                    break;
                }
            }
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return hierarchical;
    }


}

