package ru.strela.ems.ecommerce.model;

//import ru.strela.ems.ecommerce.catalog.view.ItemDetailView;
//import org.exolab.castor.xml.Marshaller;
//import org.exolab.castor.xml.Unmarshaller;
//import org.jdom.JDOMException;
//import ru.strela.ems.cms.entities.content.Document;

/**
 * This wraps the actual item class to help decouple the shopping cart from
 * the actual items it stores.
 */
public class ShoppingCartItem {
    // Extended price is unit price * qty
    private double extendedPrice = 0.0;
    private double unitPrice = 0.0;
//  private ItemDetailView item = null;
//    private org.jdom.Document item;

    // Default qty to 1
    private int quantity;
    private int itemId;

    public ShoppingCartItem() {
        super();
    }

//
//  public void setProduct(ItemDetailView newItem) {
//    this.item = newItem;
//    calculateExtendedPrice();
//  }
    public void setExtendedPrice(double newPrice) {
        this.extendedPrice = newPrice;
    }

    public void setUnitPrice(double price) {
        this.unitPrice = price;
    }

    public double getUnitPrice() {
        return this.unitPrice;
    }

    public int getItemId() {
        return this.itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }


    /**
     * Returns the value of field 'documentSource'.
     *
     * @return the value of field 'documentSource'.
     */
//    public org.jdom.Document getDocumentSource()
    /*public org.jdom.Document getItem() throws org.xml.sax.SAXException, IOException, JDOMException {
        return this.item;
    } //-- java.lang.String getSource()


    public void setItem(org.jdom.Document doc) {
        this.item = doc;
    }*/
    //
    // kremnef end
//
//    public String getDescription() {
//    return this.item.getDescription();
//  }
//  public String getId() {
//    return this.item.getId();
//  }
//  public String getName() {
//    return this.item.getName();
//  }

// public ShoppingCartItem(ItemDetailView item, int qty) {
//    this.item = item;
//    this.quantity = qty;
//    calculateExtendedPrice();
//  }

//    public ShoppingCartItem(int itemId, int qty) {
////    this.item = item;
//        this.quantity = qty;
//        this.itemId = itemId;
//        calculateExtendedPrice();
//    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int newQuantity) {
        this.quantity = newQuantity;
        calculateExtendedPrice();
    }
//  public ItemDetailView getProduct() {
//    return this.item;
//  }


    public double getExtendedPrice() {
        return this.extendedPrice;
    }

    private void calculateExtendedPrice() {
//    if ( this.item.getUnitPrice() != null ){
//      this.extendedPrice = this.item.getUnitPrice().doubleValue() * getQuantity();
//    }
        if (this.getUnitPrice() > 0) {
            this.extendedPrice = this.getUnitPrice() * getQuantity();
        }
    }

}
