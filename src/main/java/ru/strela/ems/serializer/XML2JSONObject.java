package ru.strela.ems.serializer;


import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * User: hobal
 * Date: 12.03.12
 * Time: 17:11
 */
public class XML2JSONObject {


    /**
     * Type manage for simple node
     */
    public enum Type {
        /**
         * String type
         */
        String,
        /**
         * Boolean type
         */
        Boolean,
        /**
         * Number type
         */
        Number,

        /**
         * Array type
         */
        Array;
        // values
        static final int STRING = 0;

        static final int BOOLEAN = 1;

        static final int NUMBER = 2;

        static final int ARRAY = 3;


        /**
         * Return the value corresponding to the given type
         */
        int getValue() {
            switch (this) {
                case Boolean:
                    return BOOLEAN;
                case Number:
                    return NUMBER;
                case Array:
                    return ARRAY;
                default:
                    return STRING;
            }
        }
    }


    /**
     * name of the node
     */
    private String _name;

    /**
     * String text value of the node
     */
    private String _textValue;

    /**
     * List of childs
     */
    private List<XML2JSONObject> _childs;

    /**
     * text value type
     */
    private Type _type;


    /**
     * Empty constructor
     */
    public XML2JSONObject() {
        this("");
    }


    /**
     * Create a new instance with the given name
     *
     * @param name
     */
    public XML2JSONObject(String name) {
        setName(name);
        setType(Type.String);
    }


    /**
     * Create a new instance with the given name and text value
     *
     * @param name
     * @param textValue
     */
    public XML2JSONObject(String name, String textValue) {
        setName(name);
        setTextValue(textValue);
        setType(Type.String);
    }


    /**
     * Return the JSONObject corresponding to this instance
     *
     * @return the JSONObject corresponding to this instance
     */
    public JSONObject toJSON() {
        return toJSON(new JSONObject());
    }


    /**
     * Return the JSonObject corresponding to this instance as child as the
     * parent given in parameter
     *
     * @param parent the parent JSonObject
     * @return the JSonObject corresponding to this instance
     */
    public JSONObject toJSON(JSONObject parent) {
        JSONObject current = parent;

        if (_name != null) {

            if (_hasChild()) {
                current = new JSONObject();

                for (XML2JSONObject child : _childs) {
                    child.toJSON(current);
                }
                // append text value if exists
                if (hasTextValue()) {
                    current.accumulate(_getJSONTextValuePropertyName(), _getJSONTextValue());
                }

                if (getType() == Type.Array && _childs.size() == 1) {
                    // Manage the array type with one element
                    // indeed accumulate creates array when same key is used
                    // but when there is only one item in the list the result is
                    // not an array
                    JSONArray array = new JSONArray();
                    String firstChildName = _childs.get(0).getName();
                    XML2JSONObject firstChild = _childs.get(0);
                    if (firstChild != null
                            && (firstChild.getChildren() != null && !firstChild.getChildren().isEmpty() || (firstChild.hasTextValue() && !firstChild.getTextValue().equals("")))) {
                        array.add(current.get(firstChildName));
                    }

                    JSONObject o = new JSONObject().accumulate(firstChildName, array);
                    parent.accumulate(_name, o);
                }
                else {
                    parent.accumulate(_name, current);
                }
            }
            else if (hasTextValue()) {
                current.accumulate(_getJSONTextValuePropertyName(), _getJSONTextValue());
            }

        }

        return parent;
    }


    /**
     * Return the json property name for the text node If no child it returns
     * the name else "#text"
     *
     * @return
     */
    private String _getJSONTextValuePropertyName() {
        if (_name != null) {
            if (_hasChild()) {
                return "#text";
            }
        }
        return _name;
    }


    /**
     * Get the good object value concerning the Type Boolean, Number or String
     *
     * @return String textValue
     */
    private Object _getJSONTextValue() {
        if (_textValue != null) {
            switch (_type.getValue()) {
                case Type.BOOLEAN:
                    return Boolean.valueOf(_textValue);
                case Type.NUMBER:
                    return Double.valueOf(_textValue);
                default:
                    return _textValue;
            }
        }
        return JSONNull.getInstance().toString();
    }


    /**
     * Determines if the element has at least one child
     *
     * @return true if the element has at least one child
     */
    private boolean _hasChild() {
        boolean ret = false;
        if (_childs != null && _childs.size() > 0) {
            ret = true;
        }

        return ret;
    }


    /**
     * Get the <code>Type</code>
     *
     * @return The <code>Type</code>
     */
    public Type getType() {
        return _type;
    }


    /**
     * Set the <code>Type</code>
     *
     * @param type
     */
    public void setType(Type type) {
        this._type = type;
    }


    /**
     * Get the name
     *
     * @return The name
     */
    public String getName() {
        return _name;
    }


    /**
     * Set the name
     *
     * @param name The name to set
     */
    public void setName(String name) {
        this._name = name;
    }


    /**
     * Get the text value
     *
     * @return the text value
     */
    public String getTextValue() {
        return _textValue;
    }


    /**
     * Determines if the text value is not null
     *
     * @return true if the text value is not null
     */
    public boolean hasTextValue() {
        return getTextValue() != null;
        // return StringUtils.isNotBlank(getTextValue());
    }


    /**
     * Set the text value
     *
     * @param textValue The value to set
     */
    public void setTextValue(String textValue) {
        this._textValue = textValue;
    }


    /**
     * Get the <code>XML2JSONObject</code> children in a <code>List</code>
     *
     * @return the <code>XML2JSONObject</code> children in a <code>List</code>
     */
    public List<XML2JSONObject> getChildren() {
        return _childs;
    }


    /**
     * childs setter
     *
     * @param childs
     */
    public void setChilds(List<XML2JSONObject> childs) {
        this._childs = childs;
    }


    /**
     * Add a child
     *
     * @param child The <code>XML2JSONObject</code> to add
     */
    public void addChild(XML2JSONObject child) {
        if (_childs == null) {
            _childs = new ArrayList<XML2JSONObject>();
        }
        if (child != null) {
            _childs.add(child);
        }
    }

}