package ru.strela.ems.generator;


import org.apache.avalon.framework.parameters.ParameterException;
import org.apache.avalon.framework.parameters.Parameters;
import org.apache.cocoon.ProcessingException;
import org.apache.cocoon.ResourceNotFoundException;
import org.apache.cocoon.caching.CacheableProcessingComponent;
import org.apache.cocoon.environment.ObjectModelHelper;
import org.apache.cocoon.environment.Request;
import org.apache.cocoon.environment.SourceResolver;
import org.apache.cocoon.generation.AbstractGenerator;
import org.apache.cocoon.spring.configurator.WebAppContextUtils;
import org.apache.excalibur.source.SourceValidity;
import org.apache.excalibur.source.impl.validity.NOPValidity;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;
import ru.strela.ems.core.dao.ObjectTypeDao;
import ru.strela.ems.core.dao.SiteProcessorDao;
import ru.strela.ems.core.model.*;
import ru.strela.ems.tools.ServerTools;
import ru.tastika.tools.util.Utilities;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.util.JAXBSource;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXTransformerFactory;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Pattern;


/**
 * User: hobal
 * Date: 18.05.2010
 * Time: 1:11:55
 */
public class SitePageGenerator extends AbstractGenerator implements CacheableProcessingComponent {

    private final static org.slf4j.Logger log = LoggerFactory.getLogger(SitePageGenerator.class);
    public static final String CHILDREN_KEY = "children";
    public static final String OBJECTS_KEY = "objects";
    public static final String DOCUMENT_TYPES = "documentTypes";
    public static ArrayList<String> documentTypes = new ArrayList<String>();

    private Pattern systemNamesPattern = Pattern.compile("[^\\w/]");

    private static final String TYPES_ACTIONS = "typesActions";
    private static final String XSLT_TEMPLATE_FILE = "xsltTemplateFile";
    private static final String REAL_ROOT_PATH = "realRootPath";
    private SystemNodeObjects pageObjects;
    private SystemNode systemNode;
    private ChildrenMap children;
    private HashMap<String, HashMap<String, Object>> requestParams;
    private boolean omitRoot = false;


    public Serializable getKey() {
        return source;
    }


    public SourceValidity getValidity() {
        return NOPValidity.SHARED_INSTANCE;
    }


    public void setup(SourceResolver sourceResolver, Map model, String src, Parameters params) throws IOException, ProcessingException, SAXException {
        super.setup(sourceResolver, model, src, params);
        this.parameters = params;

        WebApplicationContext currentWebApplicationContext = WebAppContextUtils.getCurrentWebApplicationContext();
        SiteProcessorDao siteProcessorDao = (SiteProcessorDao) currentWebApplicationContext.getBean("siteProcessorDao");

        Request request = ObjectModelHelper.getRequest(model);

        ServerTools.updateLocaleFromParameter(request);
        ////log.info("request.getContextPath() = " + request.getContextPath());
        ////log.info("request.getPathInfo() = " + request.getPathInfo());
        ////log.info("request.getPathTranslated() = " + request.getPathTranslated());
        ////log.info("request.getHeaders() = " + request.getHeaders());
        ////log.info("request.getQueryString() = " + request.getQueryString());
//        System.out.println("request.getQueryString() = " + request.getQueryString());
        ////log.info("request.getRequestURI() = " + request.getRequestURI());
        ////log.info("request.getServerName() = " + request.getServerName());
        ////log.info("request.getServletPath() = " + request.getServletPath());
        ////log.info("request.getSitemapPath() = " + request.getSitemapPath());
        ////log.info("request.getSitemapURIPrefix() = " + request.getSitemapURIPrefix());
        ////log.info("request.getScheme() = " + request.getScheme());


//        int languageId = ServerTools.checkLocaleWithLanguageId(request);
        String languageCode = ServerTools.checkLocaleWithLanguageCode(request);
        String indexPage = "";
        try {
            indexPage = params.getParameter("index");
        } catch (ParameterException e) {
            //
        }
        omitRoot = false;
        try {
            omitRoot = Utilities.parseStringToBoolean(params.getParameter("omitRoot"));
        } catch (ParameterException e) {
            //
        }


        if (src.trim().length() > 0) {
            if (src.startsWith("/")) {
                src = src.substring(1);
            }
            if (src.endsWith("/")) {
                src = src.substring(0, src.length() - 1);
            }
        }
        String systemNamesPath = src;
//        String systemNamesPath = systemNamesPattern.matcher(src).replaceAll("");
//        //log.info("systemNamesPath = " + systemNamesPath);
//        //log.info("systemNamesPath SRC = " + src);
//        String[] systemNames = systemNamesPath.split("/");

//        //log.info("systemNamesPath = " + systemNamesPath);
//        //log.info("indexPage = " + indexPage);
//        System.out.println("REQUETS PARAMS --:  "+request.getParameters());
        requestParams = fillRequestParams(request, src);
        TreeMap<String, Object> map = siteProcessorDao.getSystemObjects(systemNamesPath, indexPage, languageCode, request.getParameters());
//        TreeMap<String, Object> map = siteProcessorDao.getSystemObjects(systemNamesPath, indexPage, languageCode, request.getQueryString());

//        TreeMap<String, Object> map = siteProcessorDao.getSystemObjects(systemNamesPath, indexPage, languageId);
//        //log.info("map = " + map);

//        siteMap = siteProcessorDao.getSiteMap();
//        //log.info("map = " + map);
//        Navigation navigation = (Navigation) map.get(Navigation.class.getSimpleName());
        systemNode = (SystemNode) map.get(SystemNode.class.getSimpleName());
//        //log.info("systemNode = " + systemNode);
        Template template = (Template) map.get(Template.class.getSimpleName());
        log.warn("template = " + template);
//        //log.info("template = " + template);
        if (systemNode == null || template == null) {
            throw new ResourceNotFoundException(String.format("Could not find system object: %s", source));
        }
//        int i;
//        String navigationSystemName = navigation.getSystemName();
//        for (i = 0; i < systemNames.length; i++) {
//            String currentSystemName = systemNames[i];
//            if (currentSystemName.equals(navigationSystemName)) {
//                break;
//            }
//        }
//        EmsObject parent = navigation;
//        for (int j = i - 1; 0 <= j; j-- ) {
//            parent = parent.getParent();
//            if (!parent.getSystemName().equals(systemNames[j])) {
//                throw new ResourceNotFoundException(String.format("Could not find path: %s", source));
//            }
//        }


        children = (ChildrenMap) map.get(CHILDREN_KEY);

        TreeMap<Integer, String> typeActionIds = new TreeMap<Integer, String>();

        pageObjects = new SystemNodeObjects();
        Collection<SystemNodeObject> systemNodeObjects = (Collection<SystemNodeObject>) map.get(OBJECTS_KEY);
//        //log.info("systemNodeObjects = " + systemNodeObjects);

        if (systemNodeObjects != null) {
            for (SystemNodeObject systemNodeObject : systemNodeObjects) {
                ObjectType objectType = systemNodeObject instanceof SystemNodeObjectType ? ((SystemNodeObjectType) systemNodeObject).getObjectType() : ((SystemNodeTypifiedObject) systemNodeObject).getTypifiedObject().getObjectType();
                typeActionIds.put(systemNodeObject.getTypeActionId(), objectType.getName());
                String typeActionName = systemNodeObject.getTypeAction().getName();
                runTypeAction(systemNodeObject, typeActionName, model, src);
                pageObjects.put(systemNodeObject.getPosition(), systemNodeObject);
            }
        }

//        //log.info("template.getURI() = " + template.getURI());
        request.setAttribute(XSLT_TEMPLATE_FILE, template.getURI());

        ObjectTypeDao objectTypeDao = (ObjectTypeDao) currentWebApplicationContext.getBean("objectTypeDao");
        List objectTypeActions = objectTypeDao.getObjectTypeActions(typeActionIds.keySet());

        ArrayList<String> typesActions = new ArrayList<String>();

        for (Object obj : objectTypeActions) {
            ObjectTypeAction typeAction = (ObjectTypeAction) obj;
            String typeName = typeActionIds.get(typeAction.getId());
            String typeActionString = typeName + ":" + typeAction.getXsltPath() + ":" + typeAction.getName()  + ":" + typeAction.getRenderLike();
            log.warn("typeActionString :"+ typeActionString);
            if (!typesActions.contains(typeActionString)) {
                typesActions.add(typeActionString);
            }
        }
        request.setAttribute(TYPES_ACTIONS, Utilities.implode(typesActions, ","));
        request.setAttribute(DOCUMENT_TYPES, Utilities.implode(documentTypes, ","));
        log.warn("DOCUMENT_TYPES :"+ documentTypes);

//        //log.info("request.getAttribute(TYPES_ACTIONS) = " + request.getAttribute(TYPES_ACTIONS));
        request.setAttribute(REAL_ROOT_PATH, currentWebApplicationContext.getServletContext().getRealPath("/"));

        request.getSession().setAttribute(ServerTools.LOCALE_TITLE, request.getAttribute(ServerTools.LOCALE_TITLE));



    }


    private void runTypeAction(SystemNodeObject nodeObject, String typeActionName, Map model, String src) {
//        log.warn("1-runTypeAction. System.currentTimeMillis() = " + System.currentTimeMillis());
        String objectName;
        Object obj;
        if (nodeObject instanceof SystemNodeObjectType) {
            SystemNodeObjectType systemNodeObjectType = (SystemNodeObjectType) nodeObject;
            obj = systemNodeObjectType.getObjectType();
            objectName = systemNodeObjectType.getObjectType().getName();
        } else {
            SystemNodeTypifiedObject systemNodeTypifiedObject = (SystemNodeTypifiedObject) nodeObject;
            obj = systemNodeTypifiedObject.getTypifiedObject();
            objectName = systemNodeTypifiedObject.getTypifiedObject().getClass().getSimpleName();
        }
        objectName = objectName.substring(0, 1).toLowerCase() + objectName.substring(1);
        WebApplicationContext currentWebApplicationContext = WebAppContextUtils.getCurrentWebApplicationContext();
        Object bean = currentWebApplicationContext.getBean(objectName + "Dao");
//        //log.info("obj = " + obj.getClass());
//        //log.info("objectName = " + objectName);

//        //log.info("typeActionName = " + typeActionName);
        try {
            Method method = bean.getClass().getMethod(typeActionName, Map.class, Object.class, String.class);
//            //log.info("method = " + method);
            method.invoke(bean, model, obj, src);
        } catch (NoSuchMethodException e) {
            //Logger.getLogger(SitePageGenerator.class).warn(e.getMessage(), e);
        } catch (InvocationTargetException e) {
            Logger.getLogger(SitePageGenerator.class).warn(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            Logger.getLogger(SitePageGenerator.class).warn(e.getMessage(), e);
        }
        /*log.warn("2-runTypeAction. System.currentTimeMillis() = " + System.currentTimeMillis());*/
    }


    public void generate() throws IOException, SAXException, ProcessingException {
        log.warn("1XML " + System.currentTimeMillis());
        contentHandler.startDocument();
        if (!omitRoot) {
            contentHandler.startElement("", "root", "root", new AttributesImpl());
        }
        generateRequestParams();

        InnerContentSaxHandler innerContentSaxHandler = new InnerContentSaxHandler(contentHandler);

        ArrayList<Class> classes = new ArrayList<Class>();
//        classes.add(DocumentType.class);
        classes.add(SiteMapViewList.class);
        classes.add(ChildrenMap.class);
        classes.add(SystemNode.class);
        classes.add(SystemNodeTypifiedObject.class);
        classes.add(SystemNodeObjectType.class);
        classes.add(pageObjects.getClass());

        for (SystemNodeObject nodeObject : pageObjects.values()) {
            Object obj;
            if (nodeObject instanceof SystemNodeObjectType) {
                ObjectType objectType = ((SystemNodeObjectType) nodeObject).getObjectType();
                obj = objectType;
                try {
                    classes.add(Class.forName(objectType.getClassName()));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            } else {
                obj = ((SystemNodeTypifiedObject) nodeObject).getTypifiedObject();
            }
            classes.add(obj.getClass());
            if (obj instanceof Content) {

                classes.add(Document.class);
                classes.add(DocumentType.class);
                classes.add(Tag.class);
                classes.add(Folder.class);
                classes.add(FileObject.class);

                 /*   String documentTypeName = ((Content) obj).getDocumentType().getName();
                    if (!documentTypes.contains(documentTypeName)) {
                        documentTypes.add(documentTypeName);
                    }*/


            }
        }

        log.warn("2XML " + System.currentTimeMillis());
        TransformerFactory transFactory = TransformerFactory.newInstance();
        SAXTransformerFactory saxTransFactory = (SAXTransformerFactory) transFactory;
        SAXResult saxResult = new SAXResult(innerContentSaxHandler);

        try {
            JAXBContext jaxbContext
                    = JAXBContext.newInstance(classes.toArray(new Class[classes.size()]));
            JAXBSource jaxbSource = new JAXBSource(jaxbContext, systemNode);
            Transformer trans = saxTransFactory.newTransformer();
            trans.transform(jaxbSource, saxResult);

            log.warn("3XML " + System.currentTimeMillis());
            if (children != null) {
                jaxbSource = new JAXBSource(jaxbContext, children);
                trans.transform(jaxbSource, saxResult);
            }
            log.warn("4XML " + System.currentTimeMillis());

//            if (siteMap != null) {
//                jaxbSource = new JAXBSource(jaxbContext, siteMap);
//                trans.transform(jaxbSource, saxResult);
//            }

            jaxbSource = new JAXBSource(jaxbContext, pageObjects);
            trans.transform(jaxbSource, saxResult);
            log.warn("5XML " + System.currentTimeMillis());

        } catch (JAXBException e) {
            throw new IOException(e);
        } catch (TransformerConfigurationException e) {
            throw new IOException(e);
        } catch (TransformerException e) {
            throw new SAXException(e);
        }

        if (!omitRoot) {
            contentHandler.endElement("", "root", "root");
        }
        contentHandler.endDocument();
        log.warn("6XML " + System.currentTimeMillis());
    }

    private HashMap<String, HashMap<String, Object>> fillRequestParams(Request request, String src) {
        HashMap<String, HashMap<String, Object>> requestParams = new HashMap<String, HashMap<String, Object>>();
        HashMap<String, Object> params = new HashMap<String, Object>();

        requestParams.put("src", params);
        params.put("src", src);

        params = new HashMap<String, Object>();
        requestParams.put("request", params);
        //params.put("characterEncoding", request.getCharacterEncoding());
        //params.put("contentType", request.getContentType());
        //params.put("contextPath", request.getContextPath());
        //params.put("method", request.getMethod());
        params.put("pathInfo", request.getPathInfo());
        //params.put("pathTranslated", request.getPathTranslated());
        params.put("protocol", request.getProtocol());
        params.put("queryString", request.getQueryString());
        params.put("params", request.getParameters());
        params.put("remoteAddr", request.getRemoteAddr());
        params.put("remoteHost", request.getRemoteHost());
        //params.put("requestedSessionId", request.getRequestedSessionId());
        params.put("requestURI", request.getRequestURI());
        //params.put("scheme", request.getScheme());
        params.put("serverName", request.getServerName());
        params.put("serverPort", request.getServerPort());
        params.put("servletPath", request.getServletPath());
        params.put("sitemapPath", request.getSitemapPath());
        //params.put("sitemapURI", request.getSitemapURI());
        params.put("sitemapURIPrefix", request.getSitemapURIPrefix());
//        params = new HashMap<String, Object>();
//        requestParams.put("attributes", params);
//
//        for (Enumeration enumeration = request.getAttributeNames(); enumeration.hasMoreElements();) {
//            String attributeName = String.valueOf(enumeration.nextElement());
//            String attribute = String.valueOf(request.getAttribute(attributeName));
//            params.put(attributeName, attribute);
//        }
        params = new HashMap<String, Object>();
        requestParams.put("parameters", params);
        params.putAll(request.getParameters());

        /*for (Enumeration enumeration = request.getParameterNames(); enumeration.hasMoreElements(); ) {
            String parameterName = String.valueOf(enumeration.nextElement());
            ArrayList<String> parameters = new ArrayList<String>();
            params.put(parameterName, parameters);
            String[] parameterValues = request.getParameterValues(parameterName);
            if (parameterValues != null) {
                parameters.addAll(Arrays.asList(parameterValues));
            }
        }*/

        //HttpSession session = request.getSession();
        return requestParams;
    }


    private void generateRequestParams() throws SAXException {
        contentHandler.startElement("", "params", "params", new AttributesImpl());

        for (String containerKey : requestParams.keySet()) {
            HashMap<String, Object> params = requestParams.get(containerKey);
            contentHandler.startElement("", containerKey, containerKey, new AttributesImpl());
            for (String paramKey : params.keySet()) {
                Object paramValueObj = params.get(paramKey);
                AttributesImpl attributes = new AttributesImpl();
                attributes.addAttribute("", "name", "name", "string", paramKey);
                if (paramValueObj instanceof Collection) {
                    for (Object paramValue : (Collection) paramValueObj) {
                        String paramValueStr = (String) paramValue;
                        contentHandler.startElement("", "param", "param", attributes);
                        contentHandler.characters(paramValueStr.toCharArray(), 0, paramValueStr.length());
                        contentHandler.endElement("", "param", "param");
                    }
                } else {
                    String paramValueStr = String.valueOf(paramValueObj);
                    contentHandler.startElement("", "param", "param", attributes);
                    contentHandler.characters(paramValueStr.toCharArray(), 0, paramValueStr.length());
                    contentHandler.endElement("", "param", "param");
                }
            }
            contentHandler.endElement("", containerKey, containerKey);
        }

        contentHandler.endElement("", "params", "params");
    }


    @Override
    public void recycle() {
        pageObjects = null;
        systemNode = null;
        children = null;
//        siteMap = null;
        requestParams = null;
        super.recycle();
    }

    /*public setDocumentTypesNames(ArrayList<String> documentTypes) {
        this.documentTypes = documentTypes;
    }*/


}
