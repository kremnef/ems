package ru.strela.ems.ecommerce.model;


import ru.strela.ems.core.model.*;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.io.Serializable;
import java.util.List;


@XmlRootElement
@XmlSeeAlso({Product.class})
public class Catalogue extends TypifiedObject implements Serializable, SystemObject {


    //    String isFeatured = null;
//    List assignedItems = null;
    /*private Integer systemNodeId;
    private SystemNode systemNode;*/
    private EmsObject emsObject;

    private List items;


    public Catalogue() {
        super();
        emsObject = new EmsObject();
        emsObject.setEntity(getClass().getSimpleName());
//        assignedItems = new ArrayList();

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


    public List getItems() {
        return items;
    }


    public void setItems(List items) {
        this.items = items;
    }


    public void removeProducts(Product product) {
        items.remove(product);
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
}