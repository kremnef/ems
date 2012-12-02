package ru.strela.ems.core.model;


/**
 * Class Template.
 *
 * @version $Revision$ $Date$
 */
public class Template implements java.io.Serializable {


    public static final int INHERIT_ID = 0;

    private Integer id;
    private String name;
    private String URI;
    private int positionsAmount;


    public Template() {
        super();
    }

    public Template(Integer id, String name, String URI, int positionsAmount) {
        this.id = id;
        this.name = name;
        this.URI = URI;
        this.positionsAmount = positionsAmount;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }


    /**
     * @param id the id to set
     */
    private void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }


    public String getURI() {
        return this.URI;
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setURI(String URI) {
        this.URI = URI;

    }


    public int getPositionsAmount() {
        return positionsAmount;
    }


    public void setPositionsAmount(int positionsAmount) {
        this.positionsAmount = positionsAmount;
    }

}