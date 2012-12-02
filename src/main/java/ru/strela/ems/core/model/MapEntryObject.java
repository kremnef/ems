package ru.strela.ems.core.model;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: hobal
 * Date: 28.06.2010
 * Time: 0:40:45
 */
public class MapEntryObject<K extends Comparable<K>, V1, V2> {

    private final static Logger log = LoggerFactory.getLogger(MapEntryObject.class);
    private K key;
    private V1 value1;
    private V2 value2;


    public MapEntryObject() {
        //log.info("MapEntryObject.MapEntryObject");
    }


    public MapEntryObject(K key, V1 value1, V2 value2) {
        this.key = key;
        this.value1 = value1;
        this.value2 = value2;
    }


    public K getKey() {
        return key;
    }


    public void setKey(K key) {
        this.key = key;
        ////log.info("MapEntryObject.setKey");
        ////log.info("key = " + key);
    }


    public V1 getValue1() {
        return value1;
    }


    public void setValue1(V1 value1) {
        this.value1 = value1;
        ////log.info("MapEntryObject.setValue");
        ////log.info("value = " + value);
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MapEntryObject) {
            MapEntryObject entryObject = (MapEntryObject) obj;
            return getKey().equals(entryObject.getKey()) && getValue1().equals(entryObject.getValue1()) && getValue2().equals(entryObject.getValue2());
        }
        return false;
    }


    @Override
     public String toString() {
        StringBuilder sb = new StringBuilder("[key = '");
        sb.append(getKey());
        sb.append("', value1='");
        sb.append(getValue1());
        sb.append("', value2='");
        sb.append(getValue2());
        sb.append("']");
        return sb.toString();
    }


    public V2 getValue2() {
        return value2;
    }


    public void setValue2(V2 value2) {
        this.value2 = value2;
    }
}
