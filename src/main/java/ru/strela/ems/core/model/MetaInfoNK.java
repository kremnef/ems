package ru.strela.ems.core.model;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: andrejkremnev
 * Date: 22.05.12
 * Time: 16:50
 * To change this template use File | Settings | File Templates.
 */
    // @Embeddable
public class MetaInfoNK implements Serializable {

    public MetaInfoNK() {

    }

    public MetaInfoNK(int objectId, String languageCode) {
        this.objectId = objectId;
        this.languageCode = languageCode;
    }


    //    @ManyToOne
    private int objectId;



//    private int id;

    //    @ManyToOne
    private String languageCode;

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }
/*
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }*/
}
