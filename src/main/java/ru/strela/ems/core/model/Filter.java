package ru.strela.ems.core.model;


/**
 * User: andrejkremnev
 * Date: 08.02.12
 * Time: 13:05
 */
public class Filter implements java.io.Serializable {


    private String entity;
    private String condition;
    private String field;
    private String fieldValue;


    public Filter() {
        super();
    }


    public Filter(String entity, String condition, String field, String fieldValue) {
        this.entity = entity;
        this.condition = condition;
        this.field = field;
        this.fieldValue = fieldValue;
    }


    public String getEntity() {
        return entity;
    }


    public void setEntity(String entity) {
        this.entity = entity;
    }


    public String getCondition() {
        return condition;
    }


    public void setCondition(String condition) {
        this.condition = condition;
    }


    public String getField() {
        return field;
    }


    public void setField(String field) {
        this.field = field;
    }


    public String getFieldValue() {
        return fieldValue;
    }


    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }
}
