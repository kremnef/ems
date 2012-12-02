package ru.strela.ems.core.model;


import org.hibernate.collection.internal.PersistentSet;
//import org.hibernate.collection.PersistentSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.strela.ems.selectionlist.EmsSelectionList;

import java.util.*;


/**
 * User: hobal
 * Date: 05.05.2010
 * Time: 17:38:12
 */
public class SystemNodeObjectsData implements java.io.Serializable, Cloneable {


    private final static Logger log = LoggerFactory.getLogger(SystemNodeObjectsData.class);

    private int id;
    private int objectId;
    private String name;
    private int position;
    private String typeName;
    private int typeId;
    private int typeActionId;
    private List<Map<String, Object>> typeActionsData;
    private EmsSelectionList typeActionsSelectionList;
    private int levels;
    private int itemsOnPage;
    private boolean objectType;
    private Integer tagId;
    private String sortField;
    private String sortDirection;

    public SystemNodeObjectsData() {
    }


    public SystemNodeObjectsData(int id, int objectId, String name, int position, int typeId, String typeName, int typeActionId, Set<ObjectTypeAction> typeActions, int levels, boolean objectType, int itemsOnPage, String sortField, String sortDirection, Integer tagId) {
        setData(id, objectId, name, position, typeId, typeName, typeActionId, typeActions, levels, objectType, itemsOnPage, sortField, sortDirection, tagId);
    }


    public void setData(int id, int objectId, String name, int position, int typeId, String typeName, int typeActionId, Set<ObjectTypeAction> typeActions, int levels, boolean objectType, int itemsOnPage, String sortField, String sortDirection, Integer tagId) {
        this.id = id;
        this.objectId = objectId;
        this.name = name;
        this.position = position;
        this.typeId = typeId;
        this.typeName = typeName;
        this.typeActionId = typeActionId;
        this.typeActionsData = formatTypeActions(typeActions);
        this.levels = levels;
        this.objectType = objectType;
        this.itemsOnPage = itemsOnPage;

        this.sortField = sortField;
        this.sortDirection = sortDirection;
        this.tagId = tagId;
    }


    private List<Map<String, Object>> formatTypeActions(Set<ObjectTypeAction> typeActions) {
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        if (typeActions instanceof PersistentSet) {

//            //log.info("typeId = " + typeId);
//            //log.info("typeName = " + typeName);
//            //log.info("typeActions = " + typeActions);
            PersistentSet persistentSet = (PersistentSet) typeActions;
//            //log.info("persistentSet.getSession().isOpen() = " + persistentSet.getSession().isOpen());
            boolean b = persistentSet.getSession().getPersistenceContext().containsCollection((PersistentSet) typeActions);
//            //log.info("b = " + b);
        }
        for (ObjectTypeAction objectTypeAction : typeActions) {
            TreeMap<String, Object> dataMap = new TreeMap<String, Object>();
            dataMap.put("label", objectTypeAction.getName());
            dataMap.put("value", objectTypeAction.getId());
            data.add(dataMap);
        }
        return data;
    }


    public void setId(int id) {
        this.id = id;
    }


    public int getId() {
        return this.id;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getName() {
        return this.name;
    }


    public void setPosition(int position) {
        this.position = position;
    }


    public int getPosition() {
        return this.position;
    }


    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }


    public String getTypeName() {
        return this.typeName;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("SystemNodeObjectsData [id = ");
        sb.append(getId());
        sb.append(", objectId= ");
        sb.append(getObjectId());
        sb.append(", name= '");
        sb.append(getName());
        sb.append("', position = ");
        sb.append(getPosition());
        sb.append(", typeActionId = ");
        sb.append(getTypeActionId());
        sb.append(", typeActionsData = ");
        sb.append(getTypeActionsData());
        sb.append("]");
        return sb.toString();
    }


    public int getTypeActionId() {
        return typeActionId;
    }


    public void setTypeActionId(int typeActionId) {
        this.typeActionId = typeActionId;
    }


    public List<Map<String, Object>> getTypeActionsData() {
        return typeActionsData;
    }


    public int getLevels() {
        return levels;
    }


    public void setLevels(int levels) {
        this.levels = levels;
    }


    public int getObjectId() {
        return objectId;
    }


    public void setObjectId(int emsObjectId) {
        this.objectId = emsObjectId;
    }


    public int getTypeId() {
        return typeId;
    }


    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }


    public void setTypeActionsData(List<Map<String, Object>> typeActionsData) {
        this.typeActionsData = typeActionsData;
    }


    public EmsSelectionList getTypeActionsSelectionList() {
        if (typeActionsSelectionList == null) {
            typeActionsSelectionList = new EmsSelectionList();
            typeActionsSelectionList.setItems(getTypeActionsData());
        }
        return typeActionsSelectionList;
    }


    @Override
    public Object clone() {
        List<Map<String, Object>> clonedTypeActionsData = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> typeActions : this.typeActionsData) {
            clonedTypeActionsData.add(new TreeMap<String, Object>(typeActions));
        }
        SystemNodeObjectsData data = new SystemNodeObjectsData(this.id, this.objectId, this.name, this.position, this.typeId, this.typeName, this.typeActionId, new HashSet<ObjectTypeAction>(), this.levels, this.objectType, this.itemsOnPage, this.sortField, this.sortDirection, this.tagId);
        data.typeActionsData = clonedTypeActionsData;
        return data;
    }


    public boolean isObjectType() {
        return objectType;
    }


    public void setObjectType(boolean objectType) {
        this.objectType = objectType;
    }


    public int getItemsOnPage() {
        return itemsOnPage;
    }


    public void setItemsOnPage(int itemsOnPage) {
        this.itemsOnPage = itemsOnPage;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }
}
