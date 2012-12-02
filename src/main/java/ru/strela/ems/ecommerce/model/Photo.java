package ru.strela.ems.ecommerce.model;


import ru.strela.ems.core.model.EmsObject;
import ru.strela.ems.core.model.FileObject;
import ru.strela.ems.core.model.SystemObject;
import ru.strela.ems.core.model.TypifiedObject;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;


@XmlRootElement
public class Photo extends TypifiedObject implements java.io.Serializable, SystemObject {


    private Date date;
    private String format;
    private String daytime;
    private String season;
    private EmsObject emsObject;
    //    private int contentId;
    private int fileId;
    private Product product;
    private Country country;
    private int countryId;
    private int regionId;
    private int cityId;
    private FileObject file;
    private int authorId;



    public Photo() {
        super();
        emsObject = new EmsObject();
        emsObject.setEntity(getClass().getSimpleName());
        date = new Date();
        format = daytime = season = "";

    }

    public EmsObject getEmsObject() {
            if (emsObject == null) {
                emsObject = new EmsObject();
            }
            return emsObject;
        }


        public void setEmsObject(EmsObject emsObject) {
            this.emsObject = emsObject;
        }

    public Product getProduct() {
        return product;
    }


    public void setProduct(Product product) {
        this.product = product;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public FileObject getFile() {
        return file;
    }


    public void setFile(FileObject file) {
        this.file = file;
    }


    public Date getDate() {
        return date;
    }


    public void setDate(Date date) {
        this.date = date;
    }


    public String getFormat() {
        return format;
    }


    public void setFormat(String format) {
        this.format = format;
    }


    public String getSeason() {
        return season;
    }


    public void setSeason(String season) {
        this.season = season;
    }

    /*   public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }*/


    public int getFileId() {
        return fileId;
    }


    public void setFileId(int fileId) {
        this.fileId = fileId;
    }




    public int getRegionId() {
        return regionId;
    }


    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }


    public int getCityId() {
        return cityId;
    }


    public void setCityId(int cityId) {
        this.cityId = cityId;
    }


    public int getAuthorId() {
        return authorId;
    }


    public void setAuthorId(int authorId) {
        authorId = authorId;
    }


    public String getDaytime() {
        return daytime;
    }


    public void setDaytime(String daytime) {
        this.daytime = daytime;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
    /* public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }
    */
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


        public Integer getParentId() {
            return emsObject.getParentId();
        }


        public TypifiedObject getParent() {
            return emsObject.getParent();
        }

}
