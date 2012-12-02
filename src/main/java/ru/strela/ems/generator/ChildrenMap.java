package ru.strela.ems.generator;


import ru.strela.ems.core.model.TypifiedObject;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;
import java.util.TreeMap;


/**
 * User: hobal
 * Date: 13.06.2010
 * Time: 21:21:33
 */
@XmlRootElement
public class ChildrenMap {


    protected TreeMap<ChildrenKey, TypifiedObject[]> children;


    public ChildrenMap() {
        children = new TreeMap<ChildrenKey, TypifiedObject[]>();
    }


    public void put(int parentId, String key, TypifiedObject[] childrenArray) {
        children.put(new ChildrenKey(parentId, key, 0), childrenArray);
    }


    public void put(int parentId, String key, int blockNumber, TypifiedObject[] childrenArray) {
        children.put(new ChildrenKey(parentId, key, blockNumber), childrenArray);
    }


    public void putAll(ChildrenMap children) {
        this.children.putAll(children.children);
    }


    public int size() {
        return children.size();
    }


    public void setChildren(TreeMap<ChildrenKey, TypifiedObject[]> children) {
        this.children = children;
    }


    public TreeMap<ChildrenKey, TypifiedObject[]> getChildren() {
        return children;
    }


    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("{");
        for (ChildrenKey key : children.keySet()) {
            sb.append(key);
            sb.append("=");
            sb.append(Arrays.asList(children.get(key)));
        }
        sb.append("}");
        return sb.toString();
    }
    
}
