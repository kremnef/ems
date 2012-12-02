package ru.strela.ems.generator;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.*;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import java.io.CharArrayReader;


/**
 * User: hobal
 * Date: 17.05.2010
 * Time: 22:57:46
 */
public class InnerContentSaxHandler implements ContentHandler {

    private final static Logger log = LoggerFactory.getLogger(InnerContentSaxHandler.class);
    private ContentHandler contentHandler;
    private static final String XML_SOURCE_TITLE = "xmlSource";
    private boolean xmlContent;


    public InnerContentSaxHandler(ContentHandler contentHandler) {
        this.contentHandler = contentHandler;
    }


//    @Override
//    public void startDocument() throws SAXException {
//        //log.info("InnerContentSaxHandler.startDocument");
//    }
//
//
//    @Override
//    public void endDocument() throws SAXException {
//        //log.info("InnerContentSaxHandler.endDocument");
//    }


    public void setDocumentLocator(Locator locator) {
        contentHandler.setDocumentLocator(locator);
    }


    public void startDocument() throws SAXException {
    }


    public void endDocument() throws SAXException {
    }


    public void startPrefixMapping(String prefix, String uri) throws SAXException {
        contentHandler.startPrefixMapping(prefix, uri);
    }


    public void endPrefixMapping(String prefix) throws SAXException {
        contentHandler.endPrefixMapping(prefix);
    }


    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        if (localName.equals(XML_SOURCE_TITLE)) {
            xmlContent = true;
        }
        contentHandler.startElement(uri, localName, qName, atts);
    }


    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (localName.equals(XML_SOURCE_TITLE)) {
            xmlContent = false;
        }
        contentHandler.endElement(uri, localName, qName);
    }


    public void characters(char[] ch, int start, int length) throws SAXException {
        if (xmlContent) {
            xmlContent = false;
            parseContentAsXML(ch, start, length);
        }
        else {
            contentHandler.characters(ch, start, length);
        }
    }


    private void parseContentAsXML(char[] ch, int start, int length) throws SAXException {
        TransformerFactory transFactory = TransformerFactory.newInstance();
        SAXTransformerFactory saxTransFactory = (SAXTransformerFactory)transFactory;
        SAXResult saxResult = new SAXResult(this);
        try {
            Transformer trans = saxTransFactory.newTransformer();
            SAXSource source = new SAXSource(new InputSource(new CharArrayReader(ch, start, length)));
            trans.transform(source, saxResult);
        }
        catch (TransformerConfigurationException e) {
            throw new SAXException(e);
        }
        catch (TransformerException e) {
            throw new SAXException(e);
        }
    }


    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
        contentHandler.ignorableWhitespace(ch, start, length);
    }


    public void processingInstruction(String target, String data) throws SAXException {
        contentHandler.processingInstruction(target, data);
    }


    public void skippedEntity(String name) throws SAXException {
        contentHandler.skippedEntity(name);
    }
    
}