package ru.strela.ems.ecommerce.model;

//import org.exolab.castor.xml.Marshaller;
//import org.exolab.castor.xml.Unmarshaller;

/**
 * An abstract super class that many business objects will extend.
 */
abstract public class BaseBusinessObject implements java.io.Serializable {
  private int id;
  private String displayLabel;
  private String description;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public void setDisplayLabel(String label) {
    this.displayLabel = label;
  }

  public String getDisplayLabel() {
    return displayLabel;
  }
}

