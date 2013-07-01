package ru.strela.ems.core.model;

import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: andrejkremnev
 * Date: 08.05.12
 * Time: 18:01
 * To change this template use File | Settings | File Templates.
 */
public class MetaInfo {

    /*  @Id */
    private int id;
//    @NaturalId
    private int objectId;
    private String languageCode;

    private String title;
    private String description;
    private String keywords;

    private List<Tag> tags;

    public MetaInfo() {
        tags = new ArrayList<Tag>();
    }


    public MetaInfo(String title, String description, String keywords, String languageCode, int objectId) {
//        this.id = 0;
        tags = new ArrayList<Tag>();
        this.title = title;
        this.description = description;
        this.languageCode = languageCode;
        this.keywords = keywords;
        this.objectId = objectId;

      /*  log.warn("title: "+this.title+" languageCode: "+this.languageCode+" objectId:"+this.objectId);*/
    }

    @XmlTransient
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }


    public String getDescription() {
        return this.description;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }


    public void addTag(Tag tag) {
        if (!tags.contains(tag)) {
            tags.add(tag);
        }
    }

    public void removeTag(Tag tag) {
           tags.remove(tag);
       }



    public String getKeywords() {
        return this.keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @XmlTransient
    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    @XmlTransient
    public int getObjectId() {
        return objectId;
    }

    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }

}


