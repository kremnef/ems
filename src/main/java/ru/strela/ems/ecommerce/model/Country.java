package ru.strela.ems.ecommerce.model;


import java.io.Serializable;


//@XmlRootElement
//@XmlSeeAlso({Saleable.class})
public class Country implements Serializable {

    private Integer id;
    private String name;
    private String isocode;
    private String currency;
    private String symbol;
    private String symbol_html;
    private String code;
    private String tax;
    private String continent;
    private boolean hasRegions;
    private boolean visible;
    private boolean isDefaultCountry;




    public Country() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsocode() {
        return isocode;
    }

    public void setIsocode(String isocode) {
        this.isocode = isocode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol_html() {
        return symbol_html;
    }

    public void setSymbol_html(String symbol_html) {
        this.symbol_html = symbol_html;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public boolean getHasRegions() {
        return hasRegions;
    }

    public void setHasRegions(boolean hasRegions) {
        this.hasRegions = hasRegions;
    }

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean getIsDefaultCountry() {
        return isDefaultCountry;
    }

    public void setIsDefaultCountry(boolean defaultCountry) {
        isDefaultCountry = defaultCountry;
    }
}