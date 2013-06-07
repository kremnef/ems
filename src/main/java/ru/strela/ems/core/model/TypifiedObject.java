package ru.strela.ems.core.model;


import ru.strela.ems.security.model.Customer;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * User: hobal
 * Date: 02.08.2010
 * Time: 1:29:32
 */
@XmlRootElement
@XmlSeeAlso({ObjectLabel.class})
public abstract class TypifiedObject {


    public static final int URL_RELATIVITY_ABSOLUTE = 0;
    public static final int URL_RELATIVITY_RELATIVE = 1;

    public static final String ORDER_ASC = "asc";
    public static final String ORDER_DESC = "desc";

    public static final String ORDER_FIELD_ID = "id";
    public static final String ORDER_FIELD_POSITION = "position";

    private Integer id;
    private String name;
    private int objectTypeId;
    private ObjectType objectType;
    private int ownerId;
    private Customer owner;
    private String baseURL;
    private String objectURL;

    private Set<ObjectLabel> labels;
    private ArrayList<MapEntryObject<String, String, String>> labelsList;
    private int position = 1;


    private MetaInfo metaInfo;
    private List<MetaInfo> metaInfoList;
    private ObjectLabel objectLabel;
    private List<ObjectLabel> objectLabelList;


    public TypifiedObject() {
        this.name = "";
        this.id = 0;
        labels = new HashSet<ObjectLabel>();
        labelsList = new ArrayList<MapEntryObject<String, String, String>>();

        objectLabelList = new ArrayList<ObjectLabel>();
        objectLabel = new ObjectLabel();

        metaInfoList = new ArrayList<MetaInfo>();
        metaInfo = new MetaInfo();

    }

    /*protected TypifiedObject(Integer id, String name, int objectTypeId, ObjectType objectType, int ownerId, Customer owner, String baseURL, String objectURL, Set<ObjectLabel> labels, ArrayList<MapEntryObject<String, String, String>> labelsList, MetaInfo metaInfo, List<MetaInfo> metaInfoList, int position) {
        this.id = id;
        this.name = name;
        this.objectTypeId = objectTypeId;
        this.objectType = objectType;
        this.ownerId = ownerId;
        this.owner = owner;
        this.baseURL = baseURL;
        this.objectURL = objectURL;
        this.labels = labels;
        this.labelsList = labelsList;
        this.position = position;
        this.metaInfo = metaInfo;
        this.metaInfoList = metaInfoList;
    }
*/
    @XmlAttribute(name="id")
    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        if (name != null) {
            this.name = name;
        }
    }


    public ObjectType getObjectType() {
        return objectType;
    }


    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
    }

    @XmlTransient
    public int getOwnerId() {
        return ownerId;
    }


    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }


    @XmlTransient
    public Customer getOwner() {
        return owner;
    }


    public void setOwner(Customer owner) {
        this.owner = owner;
        if (owner != null) {
            setOwnerId(owner.getId());
        } else {
            setOwnerId(0);
        }
    }


    public String getBaseURL() {
//        switch (urlRelativity) {
//            case URL_RELATIVITY_RELATIVE:
//                return baseURL;
//            case URL_RELATIVITY_ABSOLUTE:
//                if (relativeURL.length() == 0 || relativeURL.equals("/")) {
//                    return "";
//                }
//                else if (!relativeURL.endsWith("/")) {
//                    return relativeURL + "/";
//                }
//        }
        return baseURL;
    }


    public void setBaseURL(String baseURL) {
        if (baseURL == null || baseURL.length() == 0 || baseURL.equals("/")) {
            baseURL = "";
        } else if (!baseURL.endsWith("/") && !baseURL.equals("-")) {
            baseURL += "/";
        }
        this.baseURL = baseURL;
    }


    public String getObjectURL() {
//        String fullPath = Utilities.removeTrailingSlash(baseURL + objectURL);
//        String currentObjectURL = objectURL;
//        switch (urlRelativity) {
//            case URL_RELATIVITY_RELATIVE:
//                currentObjectURL = objectURL;
//                break;
//            case URL_RELATIVITY_ABSOLUTE:
//                currentObjectURL = fullPath.replaceFirst("^" + relativeURL, "");
//                break;
//        }
//        if (currentObjectURL != null && currentObjectURL.length() > 0 && currentObjectURL.startsWith("/")) {
//            currentObjectURL = currentObjectURL.substring(1);
//        }
        return objectURL;
    }


    public void setObjectURL(String objectURL) {
        if (objectURL == null) {
            objectURL = "";
        } else if (objectURL.length() > 0 && objectURL.startsWith("/")) {
            objectURL = objectURL.substring(1);
        }
        this.objectURL = objectURL;
    }


    public Set<ObjectLabel> getLabels() {
        return labels;
    }


    public void setLabels(Set<ObjectLabel> labels) {
        this.labels = labels;
    }


    @XmlTransient
    public ArrayList<MapEntryObject<String, String, String>> getLabelsList() {
        return labelsList;
    }

    public void setLabelsList(ArrayList<MapEntryObject<String, String, String>> labelsList) {
        this.labelsList = labelsList;
    }


    public void setParent(TypifiedObject typifiedObject) {
    }


    public void setSystemName(String systemName) {
    }

/*
    @XmlTransient
    public Integer getParentId() {
        return null;
    }
*/


    public String getSystemName() {
        return "";
    }

    /*public void synchronizeLabels() {
        labels.clear();
        for (MapEntryObject<String, String, String> entryObject : labelsList) {
            String label = entryObject.getValue2() != null ? entryObject.getValue2().trim() : "";
            if (!label.isEmpty()) {
                labels.add(new ObjectLabel(entryObject.getKey(), label));
            }
        }
    }*/

    public List<ObjectLabel> getObjectLabelList() {
        return this.objectLabelList;
    }

    public void setObjectLabelList(List<ObjectLabel> objectLabelList) {
        this.objectLabelList = objectLabelList;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TypifiedObject) {
            TypifiedObject typifiedObject = (TypifiedObject) obj;
            return id == typifiedObject.getId() && getName().equals(typifiedObject.getName());
        }
        return false;
    }


    @Override
    public int hashCode() {
        final int multiplier = 23;
        int code = 133;
        code = multiplier * code + getId();
        code = multiplier * code + getName().hashCode();
        return code;
    }


    @Override
    public String toString() {
        return getName();
//        StringBuilder sb = new StringBuilder(getClass().getSimpleName());
//        sb.append("[id=");
//        sb.append(id);
//        sb.append("',name='");
//        sb.append(name);
//        sb.append("']");
//        return sb.toString();
    }


    public String toExtendedString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName());
        sb.append("[id=");
        sb.append(id);
        sb.append(",name='");
        sb.append(name);
        sb.append("',system_name='");
        sb.append(getSystemName());
        sb.append("',objectTypeId=");
        sb.append(objectTypeId);
        /*sb.append(",parentId=");
        sb.append(getParentId());*/
        sb.append("]");

        return sb.toString();
    }

    @XmlAttribute(name="position")
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @XmlTransient
    public int getObjectTypeId() {
        return objectTypeId;
    }


    public void setObjectTypeId(int objectTypeId) {
        this.objectTypeId = objectTypeId;
    }

    /*@XmlTransient
    public String getOrder() {
        return ORDER_ASC;
    }


    @XmlTransient
    public String getOrderField() {
        return ORDER_FIELD_POSITION;
    }*/

    public MetaInfo getMetaInfo() {
        return this.metaInfo;
    }

    public void setMetaInfo(MetaInfo metaInfo) {
        this.metaInfo = metaInfo;
    }

    public ObjectLabel getObjectLabel() {
        return this.objectLabel;
    }

    public void setObjectLabel(ObjectLabel objectLabel) {
        this.objectLabel = objectLabel;
    }

    @XmlTransient
    public List<MetaInfo> getMetaInfoList() {
        return this.metaInfoList;
    }

    public void setMetaInfoList(List<MetaInfo> metaInfoList) {
        this.metaInfoList = metaInfoList;
    }

    public void addObjectLabel(ObjectLabel objectLabel) {
//        System.out.println("Add New objectLabel"+ objectLabel.getLanguageCode()+"-"+objectLabel.getObjectId());
        if (!this.objectLabelList.contains(objectLabel)) {
            this.objectLabelList.add(objectLabel);
        }
    }
    public void addMetaInfo(MetaInfo metainfo) {
//        System.out.println("Add New metainfo"+ metainfo.getTitle());
        if (!this.metaInfoList.contains(metainfo)) {
            this.metaInfoList.add(metainfo);
        }
    }

    public void printList(List list){
//        System.out.println("T PRINT LIST");
        for (Object obj : list) {
            ObjectLabel objectLabel = (ObjectLabel) obj;
//            System.out.println("id:"+objectLabel.getId());
            System.out.println("label: "+objectLabel.getLabel());
//            System.out.println("languageCode: "+objectLabel.getLanguageCode());
//            System.out.println("objectId: "+objectLabel.getObjectId());
        }
    }

}
