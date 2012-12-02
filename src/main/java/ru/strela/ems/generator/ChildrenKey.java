package ru.strela.ems.generator;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * User: hobal
 * Date: 20.02.11
 * Time: 14:23
 */
@XmlRootElement
public class ChildrenKey implements Comparable<ChildrenKey> {


    private int parentId;
    private String systemName;
    private int blockNumber;


    public ChildrenKey() {
        this(0, "", 0);
    }


    public ChildrenKey(int parentId, String systemName, int blockNumber) {
        this.parentId = parentId;
        setSystemName(systemName);
        this.blockNumber = blockNumber;
    }


    public int compareTo(ChildrenKey childrenKey) {
        int compare = this.parentId - childrenKey.parentId;
        compare = compare == 0 ? this.systemName.compareTo(childrenKey.systemName) : compare;
        return compare == 0 ? this.blockNumber - childrenKey.blockNumber : compare;
    }


    @Override
    public int hashCode() {
        final int multiplier = 23;
        int code = 133;
        code = multiplier * code + parentId;
        code = multiplier * code + systemName.hashCode();
        code = multiplier * code + blockNumber;
        return code;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ChildrenKey) {
            ChildrenKey childrenKey = (ChildrenKey) obj;
            return this.parentId == childrenKey.parentId && this.systemName.equals(childrenKey.systemName) && this.blockNumber == childrenKey.blockNumber;
        }
        return false;
    }

    public int getParentId() {
        return parentId;
    }


    public void setParentId(int parentId) {
        this.parentId = parentId;
    }


    public int getBlockNumber() {
        return blockNumber;
    }


    public void setBlockNumber(int blockNumber) {
        this.blockNumber = blockNumber;
    }


    public String getSystemName() {
        return systemName;
    }


    public void setSystemName(String systemName) {
        if (systemName == null) {
            systemName = "";
        }
        this.systemName = systemName;
    }


    @Override
    public String toString() {
        return "{parentId=" + parentId + ",systemName='" + systemName + "',blockNumber=" + blockNumber + "}";
    }
}
