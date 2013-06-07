package ru.strela.ems.core.model;


/**
 * User: hobal
 * Date: 03.08.2010
 * Time: 2:54:13
 */
public interface SystemObject {


    EmsObject getEmsObject();
    void setEmsObject(EmsObject emsObject);
    String getSystemName();
    void setSystemName(String systemName);
    Integer getId();
    /*void setParent(TypifiedObject parent);
    Integer getParentId();
    TypifiedObject getParent();*/

//    String toExtendedString();
}
    