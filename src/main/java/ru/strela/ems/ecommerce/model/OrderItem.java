package ru.strela.ems.ecommerce.model;

//import org.jdom.JDOMException;

public class OrderItem {

//  private Order order;
    private int id;
    private Product product;
//    private org.jdom.Document item;
    private double unitPrice;
    private int quantity;
    private int orderId;
    private int productId;

    private double extendedPrice;
    private int orderItemNumber;

    public OrderItem() {
        super();
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }


    /**
     * Returns the value of field 'documentSource'.
     *
     * @return the value of field 'documentSource'.
     */
//    public org.jdom.Document getDocumentSource()
    /*public org.jdom.Document getItem() throws org.xml.sax.SAXException, IOException, JDOMException {
        return this.item;
    } //-- java.lang.String getSource()*/


    /*public void setItem(org.jdom.Document doc) {
        this.item = doc;
    }*/

    public void setProduct(Product product) {
        this.product = product;
        
        // Recalculate because the unit price may have changed now.
        calculateUnitPrice();
    }

    public Product getProduct() {
        return this.product;
    }


    /**
     * Set the order instance that is the owner for this line product.
     */
//  public void setOrder( Order order ){
//    this.order = order;
//  }

//  public Order getOrder(){
//    return order;
//  }

    public void setUnitPrice(double price) {
        this.unitPrice = price;
    }

    public double getUnitPrice() {
        return this.unitPrice;
    }

    /**
     * Set the number of items being ordered. Must always be greater than
     * or equal to zero, or it will default to this value. Maybe this should
     * have been greater than or equal to one, but there may be times where
     * a purchase order might show a zero order of items.
     */
    public void setQuantity(int qty) {
//    if ( qty != null && qty.intValue() < 0 ){
//      this.quantity = null;
//    }else{
        this.quantity = qty;
//    }
        // Recalculate because the unit price may have changed now.
        calculateUnitPrice();
    }

    public int getQuantity() {
        return this.quantity;
    }

    /**
     * Called when a change has occured either with the product or the quantity.
     */
    private void calculateUnitPrice() {
        // Get the base price of the product
        if (getProduct() != null && getProduct().getBasePrice() != null && getQuantity() > 0) {
            Double itemBasePrice = getProduct().getBasePrice();
            double unitPrice = itemBasePrice.doubleValue() * getQuantity();
            /* Unit price is base price X quantity. However, you must be very
             * careful when multiplying money values due to rounding errors. You
             * must trim to a certain number of precisions.
             */
            setUnitPrice(BOUtils.getRoundedDouble(unitPrice));
        } else {
            /* There was no product found or the price for the product was null. The
             * only thing we can do is to null out the unit price so that it will
             * alert someone to the problem.
             */
            setUnitPrice(0);
        }
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderId() {
        return this.orderId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getProductId() {
        return this.productId;
    }

    public void setExtendedPrice(double extendedPrice) {
        this.extendedPrice = extendedPrice;
    }

    public double getExtendedPrice() {
        return this.extendedPrice;
    }

    public void setOrderItemNumber(int orderItemNumber) {
        this.orderItemNumber = orderItemNumber;
    }

    public int getOrderItemNumber() {
        return this.orderItemNumber;
    }


}