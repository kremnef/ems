package ru.strela.ems.core.model;


import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.Set;
import java.util.TreeMap;


/**
 * User: hobal
 * Date: 18.05.2010
 * Time: 0:51:25
 */
@XmlRootElement
public class SystemNodeObjects {


    private TreeMap<Integer, SystemNodeObject> object;


    public SystemNodeObjects() {
        object = new TreeMap<Integer, SystemNodeObject>();
    }


    public void put(Integer position, SystemNodeObject nodeObject) {
        object.put(position, nodeObject);
    }


    public Collection<SystemNodeObject> values() {
        return object.values();
    }


    public Set<Integer> keySet() {
        return object.keySet();
    }


    public TreeMap<Integer, SystemNodeObject> getObject() {
        return object;
    }


    public void setObject(TreeMap<Integer, SystemNodeObject> object) {
        this.object = object;
    }
}
