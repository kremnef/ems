package ru.strela.ems.core.model;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.strela.ems.security.model.Customer;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.*;


/**
 * Class SystemNode.
 *
 * @version $Revision$ $Date$
 */
@XmlRootElement
public class SystemNode extends TypifiedObject implements java.io.Serializable, SystemObject {
    private final static Logger log = LoggerFactory.getLogger(SystemNode.class);


    private boolean isBranch;
    private Integer repositoryId;
    private Template template;
    private Template realTemplate;
    private Integer templateId;
    private int redirectTo;
    private String fullURL;
//    todo: не вызывать полностью такую херь, если нет в этом необходимости

    private Set content;
    private Set navigation;
    private Integer nodeTypeId;
    private Set<SystemNodeTypifiedObject> typifiedObjects;
    private Set<SystemNodeObjectType> objectTypes;
    private List<SystemNodeObjectsData> objectsDataList;



    private List<SystemNodeObjectsData> parentObjectsDataList;
    private EmsObject emsObject;


    /*private MetaInfo metaInfo;
    private List<MetaInfo> metaInfoList;*/
//     private int metaInfoId;*/

    private int emsObjectId;



    public SystemNode() {
        super();
      /*  metaInfoList = new ArrayList<MetaInfo>();
        metaInfo = new MetaInfo();*/
        emsObject = new EmsObject();
        emsObject.setEntity(getClass().getSimpleName());
        typifiedObjects = new TreeSet<SystemNodeTypifiedObject>();
        objectTypes = new TreeSet<SystemNodeObjectType>();
        parentObjectsDataList = new ArrayList<SystemNodeObjectsData>();

    }

    public Set getContent() {
        return this.content;
    }


//    public SystemNode(boolean branch, Integer repositoryId, Template template, Template realTemplate, Integer templateId, int redirectTo, String fullURL, Set content, Set navigation, Integer nodeTypeId, Set<SystemNodeTypifiedObject> typifiedObjects, Set<SystemNodeObjectType> objectTypes, List<SystemNodeObjectsData> objectsDataList, List<SystemNodeObjectsData> parentObjectsDataList, EmsObject emsObject, MetaInfo metaInfo, List<MetaInfo> metaInfoList, int emsObjectId) {
    public SystemNode(boolean branch, Integer repositoryId, Template template, Template realTemplate, Integer templateId, int redirectTo, String fullURL, Set content, Set navigation, Integer nodeTypeId, Set<SystemNodeTypifiedObject> typifiedObjects, Set<SystemNodeObjectType> objectTypes, List<SystemNodeObjectsData> objectsDataList, List<SystemNodeObjectsData> parentObjectsDataList, EmsObject emsObject,  int emsObjectId) {
        super();
        isBranch = branch;
        this.repositoryId = repositoryId;
        this.template = template;
        this.realTemplate = realTemplate;
        this.templateId = templateId;
        this.redirectTo = redirectTo;
        this.fullURL = fullURL;
        this.content = content;
        this.navigation = navigation;
        this.nodeTypeId = nodeTypeId;
        this.typifiedObjects = typifiedObjects;
        this.objectTypes = objectTypes;
        this.objectsDataList = objectsDataList;
        this.parentObjectsDataList = parentObjectsDataList;
        this.emsObject = emsObject;
        /*this.metaInfo = metaInfo;
        this.metaInfoList = metaInfoList;*/
        this.emsObjectId = emsObjectId;
    }


    public void setContent(Set content) {
        this.content = content;
    }


    public Set getNavigation() {
        return this.navigation;
    }


    public void setNavigation(Set navigation) {
        this.navigation = navigation;
    }

    @XmlAttribute(name="repositoryId")
    public Integer getRepositoryId() {
        return this.repositoryId;
    }

    @XmlAttribute(name="templateId")
    public Integer getTemplateId() {
        return this.templateId;
    }

    @XmlAttribute(name="isBranch")
    public boolean getIsBranch() {
        return this.isBranch;
    }


    public Template getTemplate() {
        return this.template;
    }

    @XmlAttribute(name="nodeTypeId")
    public Integer getNodeTypeId() {
        return this.nodeTypeId;
    }


    public void setRepositoryId(Integer repositoryId) {
        this.repositoryId = repositoryId;
    }


    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }


    public void setIsBranch(boolean isBranch) {
        this.isBranch = isBranch;
    }


    public void setTemplate(Template template) {
        this.template = template;
        if (template != null) {
            setTemplateId(template.getId());
            setRealTemplate(template);
        } else {
            setTemplateId(0);
        }
        setSystemNodeObject();
    }


    public void setNodeTypeId(Integer nodeTypeId) {
        this.nodeTypeId = nodeTypeId;
    }

   /* public MetaInfo getMetaInfo() {
        return metaInfo;
    }

    public void setMetaInfo(MetaInfo metaInfo) {
        this.metaInfo = metaInfo;
    }*/

    @Override
    public String toString() {
        return getName();
    }


    public Set<SystemNodeObjectType> getObjectTypes() {
        return objectTypes;
    }


    @XmlTransient
    public Set<SystemNodeTypifiedObject> getTypifiedObjects() {
        return typifiedObjects;
    }


    public ArrayList synchronizeObjectsWithDataList() {
        TreeMap<Integer, SystemNodeObjectsData> datas = new TreeMap<Integer, SystemNodeObjectsData>();
        List<SystemNodeObjectsData> currentObjectDataList = getObjectsDataList();

        ArrayList<Object> updatedObjects = new ArrayList<Object>();

        if (parentObjectsDataList.size() > 0) {
            int i = 0;
            for (Iterator<SystemNodeObjectsData> iterator = currentObjectDataList.iterator(); iterator.hasNext(); ) {
                SystemNodeObjectsData data = iterator.next();
                SystemNodeObjectsData parentObjectsData = parentObjectsDataList.get(i);
                if (parentObjectsData.getId() > 0 && data.getId() == parentObjectsData.getId()) {
                    iterator.remove();
                }
                i++;
            }
        }
        for (SystemNodeObjectsData systemNodeObjectsData : currentObjectDataList) {
            if (systemNodeObjectsData.getObjectId() > 0) {
                datas.put(systemNodeObjectsData.getPosition(), systemNodeObjectsData);
            }
        }
        for (Iterator<SystemNodeTypifiedObject> iterator = typifiedObjects.iterator(); iterator.hasNext(); ) {
            SystemNodeTypifiedObject systemNodeTypifiedObject = iterator.next();
            int position = systemNodeTypifiedObject.getPosition();
            SystemNodeObjectsData data = datas.remove(position);
            if (data != null && !data.isObjectType()) {
                systemNodeTypifiedObject.setSystemNodeId(getId());
                systemNodeTypifiedObject.setObjectId(data.getObjectId());
                systemNodeTypifiedObject.setPosition(position);
                systemNodeTypifiedObject.setTypeActionId(data.getTypeActionId());
                systemNodeTypifiedObject.setLevels(data.getLevels());
                systemNodeTypifiedObject.setItemsOnPage(data.getItemsOnPage());

                systemNodeTypifiedObject.setSortField(data.getSortField());
                systemNodeTypifiedObject.setSortDirection(data.getSortDirection());
//                todo: помянть на  many-to-many
                systemNodeTypifiedObject.setTagId(data.getTagId());
            } else {
                iterator.remove();
            }
        }
        for (Iterator<SystemNodeObjectType> iterator = objectTypes.iterator(); iterator.hasNext(); ) {
            SystemNodeObjectType systemNodeObjectType = iterator.next();
            int position = systemNodeObjectType.getPosition();
            SystemNodeObjectsData data = datas.remove(position);
            if (data != null && data.isObjectType()) {
                systemNodeObjectType.setSystemNodeId(getId());
                systemNodeObjectType.setObjectId(data.getObjectId());
                systemNodeObjectType.setPosition(position);
                systemNodeObjectType.setTypeActionId(data.getTypeActionId());
                systemNodeObjectType.setLevels(data.getLevels());
                systemNodeObjectType.setItemsOnPage(data.getItemsOnPage());
                systemNodeObjectType.setSortField(data.getSortField());
                systemNodeObjectType.setSortDirection(data.getSortDirection());
//                todo: помянть на  many-to-many
                systemNodeObjectType.setTagId(data.getTagId());
            } else {
                iterator.remove();
            }
        }
        for (SystemNodeObjectsData systemNodeObjectsData : datas.values()) {
            SystemNodeObject systemNodeObject = systemNodeObjectsData.isObjectType() ? new SystemNodeObjectType() : new SystemNodeTypifiedObject();
            systemNodeObject.setSystemNodeId(getId());
            systemNodeObject.setObjectId(systemNodeObjectsData.getObjectId());
            systemNodeObject.setPosition(systemNodeObjectsData.getPosition());
            systemNodeObject.setTypeActionId(systemNodeObjectsData.getTypeActionId());
            systemNodeObject.setLevels(systemNodeObjectsData.getLevels());
            systemNodeObject.setItemsOnPage(systemNodeObjectsData.getItemsOnPage());
            if (systemNodeObjectsData.isObjectType()) {
                objectTypes.add((SystemNodeObjectType) systemNodeObject);
                int[] updatedObjectData = new int[]{
                        systemNodeObjectsData.getObjectId(),
                        1
                };
                updatedObjects.add(updatedObjectData);
            } else {
                int[] updatedObjectData = new int[]{
                        systemNodeObjectsData.getObjectId(),
                        0
                };
                updatedObjects.add(updatedObjectData);
                typifiedObjects.add((SystemNodeTypifiedObject) systemNodeObject);
            }
        }
        return updatedObjects;
    }


    public void setTypifiedObjects(Set<SystemNodeTypifiedObject> typifiedObjects) {
        this.typifiedObjects = typifiedObjects;
        //setSystemNodeObject();
    }


    public void setObjectTypes(Set<SystemNodeObjectType> objectTypes) {
        this.objectTypes = objectTypes;
        //setSystemNodeObject();
    }


    private void setSystemNodeObject() {
        int amount = 0;
        if (getTemplate() != null) {
            amount = getTemplate().getPositionsAmount();
        }
        initObjectsDataList(amount);
        //////log.info("typifiedObjects = " + typifiedObjects);
        for (SystemNodeTypifiedObject systemNodeTypifiedObject : typifiedObjects) {
            log.warn("systemNodeTypifiedObject = " + systemNodeTypifiedObject);
            int position = systemNodeTypifiedObject.getPosition();
            log.warn("position = " + position);
            if (amount <= position) {
                expandObjectsDataList(position + 1);
            }
            TypifiedObject typifiedObject = systemNodeTypifiedObject.getTypifiedObject();
            log.warn("typifiedObject = " + typifiedObject);
            Integer typifiedObjectId = typifiedObject.getId();
            int id = systemNodeTypifiedObject.getId();
            String name = typifiedObject.getName();
            int typeId = 0;
            String typeName = "";
            int typeActionId = systemNodeTypifiedObject.getTypeActionId();
            int levels = systemNodeTypifiedObject.getLevels();
            int itemsOnPage = systemNodeTypifiedObject.getItemsOnPage();
            Set<ObjectTypeAction> typeActions = new TreeSet<ObjectTypeAction>();
            if (typifiedObject.getObjectType() != null) {
                typeId = typifiedObject.getObjectType().getId();
                typeName = typifiedObject.getObjectType().getName();
                typeActions = typifiedObject.getObjectType().getTypeActions();
            }

            String sortField = systemNodeTypifiedObject.getSortField();
            String sortDirection = systemNodeTypifiedObject.getSortDirection();
            Integer tagId = systemNodeTypifiedObject.getTagId();
            SystemNodeObjectsData data = objectsDataList.get(position);
            data.setData(id, typifiedObjectId, name, position, typeId, typeName, typeActionId, typeActions, levels, false, itemsOnPage, sortField, sortDirection, tagId);
        }
        //////log.info("objectTypes = " + objectTypes);
        for (SystemNodeObjectType systemNodeObjectType : objectTypes) {
            int position = systemNodeObjectType.getPosition();
            if (amount <= position) {
                expandObjectsDataList(position + 1);
            }
            ObjectType objectType = systemNodeObjectType.getObjectType();
            Integer objectTypeId = objectType.getId();
            int id = systemNodeObjectType.getId();
            String name = objectType.getName();
            int typeId = 0;
            String typeName = "";
            int typeActionId = systemNodeObjectType.getTypeActionId();
            int levels = systemNodeObjectType.getLevels();
            int itemsOnPage = systemNodeObjectType.getItemsOnPage();

            String sortField = systemNodeObjectType.getSortField();
            String sortDirection = systemNodeObjectType.getSortDirection();
            Integer tagId = systemNodeObjectType.getTagId();

            Set<ObjectTypeAction> typeActions = new TreeSet<ObjectTypeAction>();
            typeId = objectType.getId();
            typeName = objectType.getName();
            typeActions = objectType.getTypeActions();


            SystemNodeObjectsData data = objectsDataList.get(position);
            data.setData(id, objectTypeId, name, position, typeId, typeName, typeActionId, typeActions, levels, true, itemsOnPage, sortField, sortDirection, tagId);
        }
    }


    private void initObjectsDataList(int size) {
        objectsDataList = new ArrayList<SystemNodeObjectsData>(size);
        expandObjectsDataList(size);
    }


    private void expandObjectsDataList(int size) {
        for (int i = objectsDataList.size(); i < size; i++) {
            objectsDataList.add(getEmptyObjectsData(i));
        }
    }


    private void expandParentObjectsDataList(int size) {
        for (int i = parentObjectsDataList.size(); i < size; i++) {
            parentObjectsDataList.add(getEmptyObjectsData(i));
        }
    }


    @XmlTransient
    public List<SystemNodeObjectsData> getObjectsDataList() {
        if (objectsDataList == null) {
            setSystemNodeObject();
        }
        return objectsDataList;
    }


    public void setObjectsData(int position, SystemNodeObjectsData systemNodeObjectsData) {
        List<SystemNodeObjectsData> currentObjectDataList = getObjectsDataList();
        if (currentObjectDataList.size() <= position) {
            expandObjectsDataList(position + 1);
        }
        currentObjectDataList.set(position, systemNodeObjectsData);
    }


    public void setObjectsData(int position) {
        setObjectsData(position, getEmptyObjectsData(position));
    }


    public void setObjectsDataList(List<SystemNodeObjectsData> objectsDataList) {
        this.objectsDataList = objectsDataList;
    }


    @XmlTransient
    public List<SystemNodeObjectsData> getParentObjectsDataList() {
        return parentObjectsDataList;
    }


    public void setParentObjectsDataList(List<SystemNodeObjectsData> parentObjectsDataList) {
        this.parentObjectsDataList = parentObjectsDataList;
    }


    public void setParentObjectsData(int position, SystemNodeObjectsData systemNodeObjectsData) {
        if (parentObjectsDataList.size() <= position) {
            expandParentObjectsDataList(position + 1);
        }
        parentObjectsDataList.set(position, systemNodeObjectsData);
    }


    public void setParentObjectsData(int position) {
        setParentObjectsData(position, getEmptyObjectsData(position));
    }


    public Template getRealTemplate() {
        return realTemplate;
    }


    public void setRealTemplate(Template realTemplate) {
        this.realTemplate = realTemplate;
    }


    private SystemNodeObjectsData getEmptyObjectsData(int position) {
        return new SystemNodeObjectsData(0, 0, "Empty", position, 0, "", 0, new TreeSet<ObjectTypeAction>(), 0, false, 0, null, null, null);
    }

    @XmlTransient
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

    @XmlAttribute(name="emsObjectId")
    public int getEmsObjectId() {
        return emsObjectId;
    }


    public void setEmsObjectId(int emsObjectId) {
        this.emsObjectId = emsObjectId;
    }

    @XmlAttribute(name="redirectTo")
    public int getRedirectTo() {
        return redirectTo;
    }


    public void setRedirectTo(int redirectTo) {
        this.redirectTo = redirectTo;
    }


    public String getFullURL() {
        return fullURL;
    }


    public void setFullURL(String fullURL) {
        this.fullURL = fullURL;
    }

}
