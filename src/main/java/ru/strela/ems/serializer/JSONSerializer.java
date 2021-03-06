package ru.strela.ems.serializer;


import net.sf.json.JSONObject;
import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.apache.cocoon.serialization.AbstractTextSerializer;
import org.apache.commons.lang.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.Iterator;
import java.util.Stack;


/**
 * User: hobal
 * Date: 12.03.12
 * Time: 17:05
 */
public class JSONSerializer extends AbstractTextSerializer {


    /**
     * Object sued for JSON serialization
     */
    protected XML2JSONObject _current;

    /**
     * XML2JSONObject stack
     */
    protected Stack<XML2JSONObject> _stack;

    /**
     * Name of the attribute holding the node text value type
     */
    protected String _jsonTypeAttributeName = "jsonType";

    /**
     * Escape root parameter
     */
    protected boolean _escapeRoot;
    protected boolean stripWhiteSpaces;

    private boolean _inCharacters;
    private StringBuffer _charactersBuffer;


    @Override
    public void configure(Configuration configuration) throws ConfigurationException {
        super.configure(configuration);
        _escapeRoot = configuration.getChild("escapeRoot", true).getValueAsBoolean(false);
        stripWhiteSpaces = configuration.getChild("stripWhiteSpaces", true).getValueAsBoolean(false);
    }


    private void _init() {
        _stack = new Stack<XML2JSONObject>();
        _current = null;
    }


    @Override
    public void startDocument() throws SAXException {
        _init();
        super.startDocument();
    }


    @Override
    public void startElement(String uri, String loc, String raw, Attributes attributes) throws SAXException {
        super.startElement(uri, loc, raw, attributes);
        XML2JSONObject child = new XML2JSONObject();
        child.setName(loc);
        if (_current != null) {
            // append child only if it is not
            // the first node
            _current.addChild(child);
            _stack.push(_current);
        }
        _current = child;

        // manage attributes
        if (attributes != null && attributes.getLength() > 0) {
            String name;
            String value;
            XML2JSONObject jsonAtt;
            for (int i = 0; i < attributes.getLength(); i++) {
                name = attributes.getQName(i);
                if (!StringUtils.isBlank(name)) {
                    value = attributes.getValue(i);

                    if (!StringUtils.isBlank(value)) {

                        if (!isNodeTypeAttribute(name, value)) {
                            jsonAtt = new XML2JSONObject(name, value);
                            _current.addChild(jsonAtt);
                        }
                    }
                }
            }
        }
    }


    /**
     * Set the type of the current XML2JSON Object
     *
     * @param attributeName
     */
    private boolean isNodeTypeAttribute(String attributeName, String attributeValue) {
        boolean match = false;
        if (!StringUtils.isBlank(attributeName) && !StringUtils.isBlank(attributeValue) && !StringUtils.isBlank(getJsonTypeAttributeName())) {
            if (attributeName.equalsIgnoreCase(getJsonTypeAttributeName())) {
                if (attributeValue.equalsIgnoreCase("boolean")) {
                    _current.setType(XML2JSONObject.Type.Boolean);
                }
                else if (attributeValue.equalsIgnoreCase("number")) {
                    _current.setType(XML2JSONObject.Type.Number);
                }
                else if (attributeValue.equalsIgnoreCase("array")) {
                    _current.setType(XML2JSONObject.Type.Array);
                }
                match = true;

            }
        }
        return match;
    }


    @Override
    public void endElement(String uri, String loc, String raw) throws SAXException {
        if (_charactersBuffer != null) {
            _current.setTextValue(_charactersBuffer.toString());
            _inCharacters = false;
            _charactersBuffer = null;
        }
        super.endElement(uri, loc, raw);
        // only pop the stack
        if (!_stack.isEmpty()) {
            _current = _stack.pop();
        }
    }


    @Override
    public void characters(char[] c, int start, int len) throws SAXException {
        if (!_inCharacters) {
            _inCharacters = true;
            _charactersBuffer = new StringBuffer();
        }

        super.characters(c, start, len);
        String textValue = new String(c).substring(start, start + len);
        if (stripWhiteSpaces) {
            textValue = textValue.trim();
        }
        _charactersBuffer.append(textValue);
    }


    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        if (_current != null) {
            try {
                if (_escapeRoot) {
                    JSONObject json = _current.toJSON();
                    String childs = "";

                    Iterator it = json.keys();
                    if (it.hasNext()) {
                        childs = json.get(it.next()).toString();
                    }

                    output.write(childs.getBytes("UTF-8"));
                }
                else {
                    output.write(_current.toJSON().toString().getBytes("UTF-8"));
                }
            }
            catch (IOException e) {
//                getLogger().error("Error while serializing to JSON", e);
                System.out.println("Error while serializing to JSON" + e);
            }
        }
    }


    /**
     * Get the JSON type attribute name
     *
     * @return the JSON type attribute name
     */
    public String getJsonTypeAttributeName() {
        return _jsonTypeAttributeName;
    }


    /**
     * Setter for the jsonTypeAttributeNam.
     * Default value is jsonType
     *
     * @param jsonTypeAttributeName The attribute name to set
     */
    public void setJsonTypeAttributeName(String jsonTypeAttributeName) {
        this._jsonTypeAttributeName = jsonTypeAttributeName;
    }

}