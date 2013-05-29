package ru.strela.ems.core.model;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.*;
//import org.hibernate.cache.spi.NaturalIdCacheKey

@XmlRootElement
@XmlSeeAlso({ru.strela.ems.core.model.Document.class, ru.strela.ems.core.model.DocumentType.class})

//@Indexed(index = "indexes/content")
public class Content extends TypifiedObject implements Serializable, SystemObject {


    public static final String ORDER_FIELD_PUBLISH_DATE_TIME = "publishDateTime";

    private Date publishDateTime;
    private Date expireDateTime;
    private int documentTypeId;
    private DocumentType documentType;
    private Set documents;
    //    private Set content ;
    private EmsObject emsObject;

    private Integer thumbnailId;
    private FileObject thumbnail;

//    private List<MetaInfo> metaList;

    private MetaInfo metaInfo;
    private int homeId;

    private Document document;
    private List<Content> linked;





    public Content() {
//        metaInfo = new MetaInfo();
        emsObject = new EmsObject();
        emsObject.setEntity(getClass().getSimpleName());
        linked = new ArrayList();
//        metaList = new ArrayList();
        documents = new HashSet<Document>();
    }


    public Content(int documentTypeId, String name) {
        this.documentTypeId = documentTypeId;
        this.setName(name);
        linked = new ArrayList();
    }

    public Content(int documentTypeId, String name, Integer thumbnailId) {
        this.documentTypeId = documentTypeId;
        this.thumbnailId = thumbnailId;
        this.setName(name);
        linked = new ArrayList();
    }

    @XmlAttribute(name="publishDateTime")
    public Date getPublishDateTime() {
        return this.publishDateTime;
    }


    public void setPublishDateTime(Date publishDateTime) {
        this.publishDateTime = publishDateTime;
    }

    @XmlAttribute(name="expireDateTime")
    public Date getExpireDateTime() {
        return this.expireDateTime;
    }


    public void setExpireDateTime(Date expireDateTime) {
        this.expireDateTime = expireDateTime;
    }

   /* public List<MetaInfo> getMetaList() {
        return metaList;
    }

    public void setMetaList(List<MetaInfo> metaList) {
        this.metaList = metaList;
    }*/

    /*public boolean getIsProtected() {
        return this.isProtected;
    }*/


    /* public void setIsProtected(boolean isProtected) {
        this.isProtected = isProtected;
    }*/

    @XmlAttribute(name="documentTypeId")
    public Integer getDocumentTypeId() {
        return this.documentTypeId;
    }


    public void setDocumentTypeId(Integer documentTypeId) {
        this.documentTypeId = documentTypeId;
    }


    public Set getDocuments() {
        return this.documents;
    }


   /* public Set getLastVersionDocuments() {
        return this.documents;
    }*/


    public void setDocuments(Set documents) {
        this.documents = documents;
    }


    /* public Set getContent() {
        return this.content;
    }


    public void setContent(Set content) {
        this.content = content;
    }*/


    public String toString() {
        return getName();
    }


    public EmsObject getEmsObject() {
        return emsObject;
    }


    public void setEmsObject(EmsObject emsObject) {
        this.emsObject = emsObject;
    }

    @XmlAttribute(name="systemName")
    public String getSystemName() {
        return getEmsObject().getSystemName();
    }


    public void setSystemName(String systemName) {
        getEmsObject().setSystemName(systemName);
    }


    @Override
    public void setParent(TypifiedObject typifiedObject) {
        if (typifiedObject instanceof SystemObject) {
            emsObject.setParent(typifiedObject);
        }
    }

    @XmlAttribute(name="parentId")
    public Integer getParentId() {
        return emsObject.getParentId();
    }


    public TypifiedObject getParent() {
        return emsObject.getParent();
    }

    public MetaInfo getMetaInfo() {
        return metaInfo;
    }

    public void setMetaInfo(MetaInfo metaInfo) {
        this.metaInfo = metaInfo;
    }


    @XmlAttribute(name="thumbnailId")
    public Integer getThumbnailId() {
        return this.thumbnailId;
    }


    public void setThumbnailId(Integer thumbnailId) {
        this.thumbnailId = thumbnailId;
    }

    public FileObject getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(FileObject thumbnail) {
        if (thumbnail != null) {
            setThumbnailId(thumbnail.getId());
        }
        this.thumbnail = thumbnail;
    }


    /*public HashMap<Integer, TreeMap<Integer, String>> getDocumentVersions() {
        HashMap<Integer, TreeMap<Integer, String>> documentVersions = new HashMap<Integer, TreeMap<Integer, String>>();
        for (Object docObject : documents) {
            Document doc = (Document) docObject;
            TreeMap<Integer, String> versions = documentVersions.get(doc.getLanguageId());
            if (versions == null) {
                versions = new TreeMap<Integer, String>();
                documentVersions.put(doc.getLanguageId(), versions);
            }
            versions.put(doc.getVersion(), doc.getVersionComment());
        }
        return documentVersions;
    }*/


    /*public EmsSelectionList getVersionSelectionList(int languageId) {
        EmsSelectionList emsSelectionList = new EmsSelectionList();
        List<Map<String, Object>> versions = new ArrayList<Map<String, Object>>();
        HashMap<Integer, TreeMap<Integer, String>> documentVersions = getDocumentVersions();
        TreeMap<Integer, String> docVersions = documentVersions.get(languageId);
        HashMap<String, Object> newVersionHashMap = new HashMap<String, Object>();
        newVersionHashMap.put("label", "New");
        newVersionHashMap.put("value", 0);
        versions.add(newVersionHashMap);
        if (docVersions != null) {
            for (Integer version : docVersions.keySet()) {
                HashMap<String, Object> versionHashMap = new HashMap<String, Object>();
                versionHashMap.put("label", String.valueOf(version));
                versionHashMap.put("value", version);
                versions.add(versionHashMap);
            }
        }
        emsSelectionList.setItems(versions);
        return emsSelectionList;
    }

       public int getLastVersion(int languageId) {
        HashMap<Integer, TreeMap<Integer, String>> documentVersions = getDocumentVersions();
        TreeMap<Integer, String> versions = documentVersions.get(languageId);
        return versions != null ? versions.lastKey() : 0;
    }
 */


    //   linked Contents
    public List<Content> getLinked() {
        return linked;
    }


    public void setLinked(List<Content> linked) {
        this.linked = linked;
    }


    public void removeLink(Content link) {
        this.linked.remove(link);
    }


    public void addLink(Content link) {
        if (!linked.contains(link)) {
            linked.add(link);
        }
    }


    public DocumentType getDocumentType() {
        return documentType;
    }


    public void setDocumentType(DocumentType documentType) {
        if (documentType != null) {
            setDocumentTypeId(documentType.getId());
        }
        this.documentType = documentType;
    }

/*

    @Override
    @XmlTransient
    public String getOrder() {
        return ORDER_DESC;
    }


    @Override
    @XmlTransient
    public String getOrderField() {
        return ORDER_FIELD_PUBLISH_DATE_TIME;
    }
*/

    @XmlTransient
    public int getHomeId() {
        return homeId;
    }


    public void setHomeId(int homeId) {
        this.homeId = homeId;
    }

    @XmlTransient
    public Document getDocument() {
        return document;
    }


    public void setDocument(Document document) {
        this.document = document;
        documents.clear();
        documents.add(document);
    }


}