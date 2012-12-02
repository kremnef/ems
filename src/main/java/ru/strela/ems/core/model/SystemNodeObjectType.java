package ru.strela.ems.core.model;


/**
 * User: hobal
 * Date: 08.08.2010
 * Time: 23:14:46
 */
public class SystemNodeObjectType extends SystemNodeObject {


    private ObjectType objectType;


    public SystemNodeObjectType() {
        super();
    }


    public ObjectType getObjectType() {
        return objectType;
    }


    public void setObjectType(ObjectType objectType) {
        if (objectType != null) {
            setObjectId(objectType.getId());
        }
        else{
            setObjectId(0);
        }
        this.objectType = objectType;
    }



}
