package ru.strela.ems.core.model;


import org.apache.cocoon.forms.formmodel.Group;
import org.apache.cocoon.forms.util.XMLAdapter;
import org.apache.xerces.dom.DocumentImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
//import java.util.Set;
//import java.util.TreeSet;

//import javax.persistence.ManyToMany;


/**
 * Class Document.
 *
 * @version $Revision$ $Date$
 */
@XmlRootElement
@XmlSeeAlso({Tag.class, Folder.class, FileObject.class})

//@Indexed(index = "indexes/document")
//extends DocumentSimple
public class Document implements java.io.Serializable {


    private final static Logger log = LoggerFactory.getLogger(Document.class);
    @Id
    private int id;
    @XmlTransient
    private String state;
//    @Field(index = Index.YES, analyze = Analyze.NO, store = Store.YES)
//    @DateBridge(resolution = Resolution.DAY)

/*
    @XmlTransient
    private Date modifiedDateTime;
    @XmlTransient
    private Date createdDateTime;
*/

    private String xmlSource;

    private String languageCode;
    @XmlTransient
    private DocumentImpl XML;
    /*@XmlTransient
        private int version;
    */

    private boolean isLastVersion;

//    private int languageId;

    //    private Language language;
    private List<FileObject> fileObjects;
//    private Set<FileObject> fileObjects;
    private List<Folder> folders;
//    private Set<Folder> folders;

    private List<FileSystemObject> fileSystemObjects;
    private Integer contentId;

    /*  public Document(Date modifiedDateTime, Integer contentId,
                int languageId,
                Integer version, DocumentImpl XML, String xmlSource) {
    this.contentId = contentId;
    this.modifiedDateTime = modifiedDateTime;
    this.languageId = languageId;
    this.XML = XML;
    *//*this.versionComment = versionComment;
        this.versionModifierId = versionModifierId;*//*
        this.version = version;
        this.xmlSource = xmlSource;
        fileObjects = new ArrayList();
        folders = new ArrayList();
        fileSystemObjects = new ArrayList();
    }*/


    public Document() {
        fileObjects = new ArrayList();
        folders = new ArrayList();
//        fileObjects = new TreeSet<FileObject>();
//        folders = new TreeSet<Folder>();
        fileSystemObjects = new ArrayList();
        state = "draft";
//        modifiedDateTime = createdDateTime = new Date();
    }


    public Document(Integer contentId, String languageCode, String xmlSource) {
        this.contentId = contentId;
        this.languageCode = languageCode;
        this.xmlSource = xmlSource;
//        fileObjects = new TreeSet<FileObject>();
//                folders = new TreeSet<Folder>();
        fileObjects = new ArrayList();
        folders = new ArrayList();
        fileSystemObjects = new ArrayList();
    }


    /*public Set<FileObject> getFileObjects() {
        return fileObjects;
    }*/
    public List getFileObjects() {
        return fileObjects;
    }



    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    /*

    public Set getDocumentSourceXML() {
    return this.documentSourceXML;
    }

    public void setDocumentSourceXML(Set documentSourceXML) {
    this.documentSourceXML = documentSourceXML;
    }
     */

    /*  @XmlTransient
        public boolean getIsActive() {
            return this.isActive;
        }

        @XmlTransient
        public int getIsCheckedOut() {
            return this.isCheckedOut;
        }
    */
    /*@XmlTransient
    public Language getLanguage() {
        return this.language;
    }*/

    /*@XmlTransient
    public int getLanguageId() {
        return this.languageId;
    }
*/
    @XmlTransient
    public String getState() {
        return this.state;
    }


    @XmlTransient
    public Integer getContentId() {
        return this.contentId;
    }


    /*public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }*/


    public void setState(String state) {
        this.state = state;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

/*    public void setFileObjects(Set<FileObject> fileObjects) {
            this.fileObjects = fileObjects;
            updateFileSystemObjects();
        }*/

    public void setFileObjects(List fileObjects) {
        this.fileObjects = fileObjects;
        updateFileSystemObjects();
    }



    /*public Set<Folder> getFolders() {
        return folders;
    }*/


    /*public void setFolders(Set<Folder> folders) {
        this.folders = folders;
        updateFileSystemObjects();
    }*/


    public List getFolders() {
        return folders;
    }


    public void setFolders(List folders) {
        this.folders = folders;
        updateFileSystemObjects();
    }




    public void removeFileObjects(FileObject fileObject) {
        fileObjects.remove(fileObject);
    }


    public void addFileSystemObject(FileSystemObject fileSystemObject) {
        if (!fileSystemObjects.contains(fileSystemObject)) {
            fileSystemObjects.add(fileSystemObject);
        }
    }

    @XmlTransient
    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getXmlSource() {
        return xmlSource;
    }


    public void setXmlSource(String xmlSource) {

        this.xmlSource = xmlSource;
    }


    @XmlTransient
    public DocumentImpl getXML() throws ParserConfigurationException, IOException, SAXException {
        javax.xml.parsers.DocumentBuilder builder;
        DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
        if (xmlSource != null && xmlSource.length() > 0) {
            this.XML = (DocumentImpl) builder.parse(new org.xml.sax.InputSource(new java.io.StringReader(getXmlSource())));
        }
        return this.XML;
    }


    public String setForm(Group groupWidget) throws SAXException, IOException, TransformerConfigurationException {

        SAXTransformerFactory transformerFactory = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
        TransformerHandler serializer = transformerFactory.newTransformerHandler();

        serializer.getTransformer().setOutputProperty(OutputKeys.METHOD, "xml");
        serializer.getTransformer().setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

        StringWriter writer = new StringWriter();
        serializer.setResult(new StreamResult(writer));
        XMLAdapter xmlAdapter = new XMLAdapter(groupWidget);
        xmlAdapter.toSAX(serializer);

        log.warn("xmlForm" + writer.toString());
        return writer.toString();

    }


    public void setXML(DocumentImpl XML) {
        this.XML = XML;
    }


//    public void setVersionModifierId(int versionModifierId) {
//        this.versionModifierId = versionModifierId;
//    }

    /*@XmlTransient
    public int getVersion() {
        return this.version;
    }


    public void setVersion(int version) {
        this.version = version;
    }*/



    @XmlTransient
    public List getFileSystemObjects() {
//        System.out.print("!_!getFileSystemObjects enter" );
        return fileSystemObjects;
    }


    public void setFileSystemObjects(List fileSystemObjects) {
        this.fileSystemObjects = fileSystemObjects;
    }


    private void updateFileSystemObjects() {
        fileSystemObjects.clear();
        for (Object obj : fileObjects) {
            FileObject fileObject = (FileObject) obj;
            fileSystemObjects.add(new FileSystemObject(fileObject));
        }
        for (Object obj : folders) {
            Folder folder = (Folder) obj;
            fileSystemObjects.add(new FileSystemObject(folder));
        }
    }


    public void synchronizeFileSystemObjects() {
        fileObjects.clear();
        folders.clear();
        for (Object obj : fileSystemObjects) {
            FileSystemObject fileSystemObject = (FileSystemObject) obj;
            if (fileSystemObject.isFolder()) {
                Folder folder = new Folder();
                folder.setId(fileSystemObject.getId());
                folder.setName(fileSystemObject.getName());
                folder.setSystemName(fileSystemObject.getSystemName());
                folder.setPath(fileSystemObject.getPath());
                folders.add(folder);
            } else {
                FileObject fileObject = new FileObject();
                fileObject.setId(fileSystemObject.getId());
                fileObject.setName(fileSystemObject.getName());
                fileObject.setSystemName(fileSystemObject.getSystemName());
                fileObject.setPath(fileSystemObject.getPath());
                fileObject.setContentType(fileSystemObject.getFileType());
                fileObject.setSize(fileSystemObject.getSize());
                fileObjects.add(fileObject);
            }
        }
    }


    public static class String2CDATA extends XmlAdapter<String, String> {


        private static final String CDATA_START = "<![CDATA[";
        private static final String CDATA_END = "]]>";


        public String2CDATA() {
            super();
        }


        @Override
        public String unmarshal(String v) throws Exception {
            if (v != null && v.length() > 0) {
                if (v.startsWith(CDATA_START)) {
                    v = v.substring(CDATA_START.length());
                }
                if (v.endsWith(CDATA_END)) {
                    v = v.substring(0, v.length() - CDATA_END.length());
                }
            }
            return v;
        }


        @Override
        public String marshal(String v) throws Exception {
            return "<![CDATA[" + v + "]]>";
        }

    }

    @XmlTransient
    public boolean getIsLastVersion() {
        return isLastVersion;
    }

    public void setIsLastVersion(boolean lastVersion) {
        this.isLastVersion = lastVersion;
    }
}