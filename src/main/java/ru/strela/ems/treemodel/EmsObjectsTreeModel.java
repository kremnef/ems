package ru.strela.ems.treemodel;


import org.apache.cocoon.forms.formmodel.tree.DefaultTreeModel;
import org.apache.cocoon.spring.configurator.WebAppContextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import ru.strela.ems.core.dao.SystemObjectDao;
import ru.strela.ems.core.model.SystemObject;
import ru.strela.ems.core.model.TypifiedObject;

import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * User: hobal
 * Date: 24.06.2010
 * Time: 23:47:38
 */
public class EmsObjectsTreeModel extends DefaultTreeModel {

    private final static Logger log = LoggerFactory.getLogger(EmsObjectsTreeModel.class);
    private SystemObjectDao systemObjectDao;
    private HashMap<String, HashMap<String, Pattern>> regExFilters;
    private HashMap<String, HashSet<Comparable>> excludeFilters;


    public EmsObjectsTreeModel() {
        super(new OrderedChildrenTreeNode("Objects"));
        //log.info("EmsObjectsTreeModel.EmsObjectsTreeModel");
        regExFilters = new HashMap<String, HashMap<String, Pattern>>();
        excludeFilters = new HashMap<String, HashSet<Comparable>>();
    }


    public void setEntity(String entity) {
        ((OrderedChildrenTreeNode) this.getRoot()).clearChildren();
        WebApplicationContext currentWebApplicationContext = WebAppContextUtils.getCurrentWebApplicationContext();
        systemObjectDao = (SystemObjectDao) currentWebApplicationContext.getBean(entity + "Dao");
    }


    @Override
    public boolean isLeaf(Object node) {
        if (node.equals(getRoot())) {
            return systemObjectDao.getChildrenCount(0) == 0;
        }
        else {
            return ((SystemObject) ((DefaultTreeNode) node).getData()).getEmsObject().getChildrenCount() == 0;
        }
    }


    public void addRegExFilter(String objectField, String pattern) {
        HashMap<String, Pattern> fieldPatterns = regExFilters.get(objectField);
        if (fieldPatterns == null) {
            fieldPatterns = new HashMap<String, Pattern>();
            regExFilters.put(objectField, fieldPatterns);
        }
        fieldPatterns.put(pattern, Pattern.compile(pattern));
    }


    public void removeRegExFilter(String objectField, String pattern) {
        HashMap<String, Pattern> fieldPatterns = regExFilters.get(objectField);
        if (fieldPatterns != null) {
            fieldPatterns.remove(pattern);
            if (fieldPatterns.size() == 0) {
                regExFilters.remove(objectField);
            }
        }
    }


    public void addExcludeFilter(String objectField, Comparable value) {
        HashSet<Comparable> values = excludeFilters.get(objectField);
        if (values == null) {
            values = new HashSet<Comparable>();
            excludeFilters.put(objectField, values);
        }
        values.add(value);
    }


    public void removeExcludeFilter(String objectField, Comparable value) {
        HashSet<Comparable> values = excludeFilters.get(objectField);
        if (values != null) {
            values.remove(value);
            if (values.size() == 0) {
                excludeFilters.remove(objectField);
            }
        }
    }


    private HashMap<String, Field> getAllFields(Object obj) {
        HashMap<String, Field> fields = new HashMap<String, Field>();
        Class cl = obj.getClass();
        while (cl != null) {
            for (Field field : cl.getDeclaredFields()) {
                fields.put(field.getName(), field);
            }
            cl = cl.getSuperclass();
        }
        return fields;
    }


    private Object getFieldValue(TypifiedObject typifiedObject, String field) {
        HashMap<String, Field> fields = getAllFields(typifiedObject);
        Field objectField = fields.get(field);
        if (objectField != null) {
            objectField.setAccessible(true);
            try {
                return objectField.get(typifiedObject);
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public void removeFilters() {
        regExFilters.clear();
        excludeFilters.clear();
    }


    public void removeRegExFilters() {
        regExFilters.clear();
    }


    public void removeExcludeFilters() {
        excludeFilters.clear();
    }


    @Override
    public Collection getChildren(Object parent) {
        OrderedChildrenTreeNode parentTreeNode = (OrderedChildrenTreeNode) parent;
        int parentId = (parentTreeNode.equals(getRoot()) ? 0 : ((TypifiedObject) parentTreeNode.getData()).getId());
        //log.info("parentId = " + parentId);
        List<TypifiedObject> children = systemObjectDao.getChildren(parentId);
        //log.info("children = " + children);
        //log.info(children.toString());
        for (TypifiedObject typifiedObject : children) {
            boolean included = true;
            for (Map.Entry<String, HashMap<String, Pattern>> entry : regExFilters.entrySet()) {
                String fieldName = entry.getKey();
                Object value = getFieldValue(typifiedObject, fieldName);
                if (value != null) {
                    String strValue = value.toString();
                    included = false;
                    for (Pattern pattern : entry.getValue().values()) {
                        Matcher matcher = pattern.matcher(strValue);
                        if (matcher.find()) {
                            included = true;
                            break;
                        }
                    }
                }
            }
            if (included) {
                for (Map.Entry<String, HashSet<Comparable>> entry : excludeFilters.entrySet()) {
                    String fieldName = entry.getKey();
                    Object value = getFieldValue(typifiedObject, fieldName);
                    if (value != null) {
                        for (Comparable excludedValue : entry.getValue()) {
                            if (excludedValue.equals(value)) {
                                included = false;
                                break;
                            }
                        }
                    }
                }
            }
            if (included) {
                parentTreeNode.add(typifiedObject.getName(), new OrderedChildrenTreeNode(typifiedObject));
            }
        }
        return parentTreeNode.getChildren();
    }


    private static class OrderedChildrenTreeNode extends DefaultTreeNode{


        public OrderedChildrenTreeNode(Object data) {
            this(data, new LinkedHashMap());
        }


        public OrderedChildrenTreeNode(Object data, Map children) {
            super(data, new LinkedHashMap(children));
        }


        public void clearChildren() {
            getChildren().clear();                
        }

    }

}
