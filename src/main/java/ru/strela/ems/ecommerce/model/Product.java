package ru.strela.ems.ecommerce.model;


import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;


@XmlRootElement
//@XmlSeeAlso({Saleable.class})
public class
        Product implements Serializable {


    private String vendor;
    private String model;
    private String sku;
    private String upc;
    private String sellingUOM;

    private Double basePrice;
    private Double specialPrice;

    private Integer discount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private Integer id;
    private Integer minimumSellingUnits;
    private Integer onHandQuantity;

//    private Integer contentId;
//    private List catalogues;
//    private Saleable subject;


    /*public List getCatalogues() {
        return catalogues;
    }
*/

   /* public void setCatalogues(List catalogues) {
        this.catalogues = catalogues;
    }
*/

    public Product() {
        super();
    }


    /*public Saleable getSubject() {
        return subject;
    }


    public void setSubject(Saleable subject) {
        this.subject = subject;
    }*/


    public Double getBasePrice() {
        return basePrice;
    }


    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }


    public Double getSpecialPrice() {
        return specialPrice;
    }


    public void setSpecialPrice(Double specialPrice) {
        this.specialPrice = specialPrice;
    }


    public Integer getDiscount() {
        return discount;
    }


    public void setDiscount(Integer discount) {
        this.discount = discount;
    }


    public String getVendor() {
        return vendor;
    }


    public void setVendor(String vendor) {
        this.vendor = vendor;
    }


    public String getModel() {
        return model;
    }


    public void setModel(String model) {
        this.model = model;
    }


    public String getSku() {
        return sku;
    }


    public void setSku(String sku) {
        this.sku = sku;
    }


    public String getUpc() {
        return upc;
    }


    public void setUpc(String upc) {
        this.upc = upc;
    }


    public String getSellingUOM() {
        return sellingUOM;
    }


    public void setSellingUOM(String sellingUOM) {
        this.sellingUOM = sellingUOM;
    }


    public Integer getMinimumSellingUnits() {
        return minimumSellingUnits;
    }


    public void setMinimumSellingUnits(Integer minumumSellingUnits) {
        this.minimumSellingUnits = minumumSellingUnits;
    }


    public Integer getOnHandQuantity() {
        return onHandQuantity;
    }


    public void setOnHandQuantity(Integer onHandQuantity) {
        this.onHandQuantity = onHandQuantity;
    }


    public String getModelNumber() {
        return modelNumber;
    }


    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }


    private String modelNumber;


//    @XmlTransient
    /*public Integer getContentId() {
        return contentId;
    }


    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }*/

}