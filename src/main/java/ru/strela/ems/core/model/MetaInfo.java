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
//        super();
        tags = new ArrayList<Tag>();
    }


    public MetaInfo(String title, String description, String keywords, String languageCode, int objectId) {
//        this.id = 0;
        this.title = title;
        this.description = description;
        this.languageCode = languageCode;
        this.keywords = keywords;
        this.objectId = objectId;
        tags = new ArrayList<Tag>();
      /*  System.out.println("title: "+this.title+" languageCode: "+this.languageCode+" objectId:"+this.objectId);*/
    }

    @XmlTransient
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        //log.info(" getTitle()1 -"+this.title);
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
        return this.tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }


    public void addTag(Tag tag) {
//        System.out.println("Add New Tag"+ tag.getTag());
        if (!tags.contains(tag)) {
            tags.add(tag);
        }
    }

    public void removeTag(Tag tag) {
           tags.remove(tag);
       }


   /* public void addTag(MetaInfo metaInfo) {
        List<Tag> tagList = metaInfo.getTags();
        for (Object docObject : tag) {

            System.out.println("Add New Tag"+ tag.getTag());
        if (!tags.contains(tag)) {
            tags.add(tag);
        }
        }

    }*/
  /*  public void addTag(String newTtag) {
        Tag tag = new Tag();
        tag.setTag(newTtag);
        if (!tags.contains(tag)) {
            tags.add(tag);
        }
    }*/


    public String getKeywords() {
        return this.keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

//    @XmlTransient
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
   /* public MetaInfoNK getMetaInfoNK() {
        return metaInfoNK;
    }

    public void setMetaInfoNK(MetaInfoNK metaInfoNK) {
        this.metaInfoNK = metaInfoNK;
    }*/
}


