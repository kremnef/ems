package ru.strela.ems.core.model;


/**
 * User: hobal
 * Date: 08.08.2010
 * Time: 23:12:40
 */
public class SystemNodeTypifiedObject extends SystemNodeObject {


    private TypifiedObject typifiedObject;


    public SystemNodeTypifiedObject() {
        super();
    }


    public TypifiedObject getTypifiedObject() {
        return typifiedObject;
    }


    public void setTypifiedObject(TypifiedObject typifiedObject) {
        if (typifiedObject != null) {
            setObjectId(typifiedObject.getId());
        }
        else{
            setObjectId(0);
        }
        this.typifiedObject = typifiedObject;
    }
}
