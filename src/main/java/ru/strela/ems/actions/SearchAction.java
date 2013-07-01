package ru.strela.ems.actions;


import org.apache.cocoon.environment.Request;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.strela.ems.core.dao.ContentDao;
import ru.strela.ems.core.dao.hibernate.ContentDaoImpl;
import ru.strela.ems.core.dao.hibernate.HibernateUtil;
import ru.strela.ems.core.model.Document;
import ru.strela.ems.tools.ServerTools;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * User: andrejkremnev
 * Date: 18.02.12
 * Time: 19:10
 */
public class SearchAction {

    private final static Logger log = LoggerFactory.getLogger(SearchAction.class);


    public List search(Request request) {
        String match = request.getParameter("query");
//        log.warn("Charset.defaultCharset() = " + Charset.defaultCharset());
//        log.warn("1. match = " + match);
//        log.warn("request.getCharacterEncoding() = " + request.getCharacterEncoding());
        try {
            match = new String(match.getBytes(), request.getCharacterEncoding());
        } catch (java.io.UnsupportedEncodingException ex) {
            System.err.println(ex);
        }
//        log.warn("2. match = " + match);
        String languageCode = ServerTools.checkLocaleWithLanguageCode(request);
//        log.warn("languageCode = " + languageCode);
        List<Document> correctedResult = new ArrayList<Document>();
        if (match != null) {
            match = match.trim();
            if (match.length() > 1) {
                Session session = HibernateUtil.getSessionFactory().openSession();
                log.warn("openSession");


                FullTextSession fullTextSession = Search.getFullTextSession(session);
                Transaction tx = fullTextSession.beginTransaction();
                List result = null;
                try {
                    // create native Lucene query unsing the query DSL
                    // alternatively you can write the Lucene query using the Lucene query parser
                    // or the Lucene programmatic API. The Hibernate Search DSL is recommended though
                    QueryBuilder qb = fullTextSession.getSearchFactory()
                            .buildQueryBuilder().forEntity(Document.class).get();
                    org.apache.lucene.search.Query query = qb
                            .keyword()
                                    //                    .onFields("title", "description", "xmlSource", "modifiedDateTime")
                            .onFields("title", "description", "xmlSource")
                            .matching(match)
                            .createQuery();

//                    log.warn("match = " + match);
                    // wrap Lucene query in a org.hibernate.Query
                    org.hibernate.Query hibQuery =
                            fullTextSession.createFullTextQuery(query, Document.class);
                    // execute search
                    result = hibQuery.list();
                    tx.commit();
                    session.close();
                    log.warn("closeSession");

                    /*HashMap<Integer, Integer> documentVersions = new HashMap<Integer, Integer>();
                    for (Object docObject : result) {
                        Document doc = (Document) docObject;
                        *//*if (doc.getLanguageCode() == languageCode) {
                            Integer currentVersion = documentVersions.get(doc.getContentId());
                            if (currentVersion == null || currentVersion < doc.getVersion()) {
                                documentVersions.put(doc.getContentId(), doc.getVersion());
                            }
                        }*//*
                    }*/


                    ContentDao contentDao = new ContentDaoImpl();
//                    HashMap<Integer, Integer> versions = contentDao.getDocumentMaxVersions(documentVersions.keySet(), languageCode);
                    //            HashSet<Integer> contentIds = new HashSet<Integer>();
                    //            for (Integer contentId : versions.keySet()) {
                    //                int maxVersion = versions.get(contentId);
                    //                int version = documentVersions.get(contentId);
                    //                if (maxVersion == version) {
                    //                    contentIds.add(contentId);
                    //                }
                    //            }
                    //            HashMap<Integer,ArrayList<String[]>> pathsForContents = contentDao.getPathsForContents(contentIds, languageCode);
                    for (Object docObject : result) {
                        Document doc = (Document) docObject;
                        /*if (doc.getLanguageCode() == languageCode) {
                            Integer maxVersion = versions.get(doc.getContentId());
                            if (maxVersion != null && maxVersion == doc.getVersion()) {
                                correctedResult.add(doc);
                            }
                        }*/
                    }
                } catch (HibernateException he) {
                    if (tx != null) tx.rollback();
                    throw he;
                }
                log.warn("SEARCH RESULT:" + result);
            }
        }
        return correctedResult;
    }


}
