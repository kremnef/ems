package ru.strela.ems.actions;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.SearchFactory;
import org.hibernate.search.indexes.IndexReaderAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.transaction.annotation.Transactional;
import ru.strela.ems.core.dao.hibernate.HibernateUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: andrejkremnev
 * Date: 20.02.12
 * Time: 2:32
 * To change this template use File | Settings | File Templates.
 */
public class Suggestions {
    private final static Logger log = LoggerFactory.getLogger(ReindexAction.class);

//    @Transactional
    public synchronized List<String> getSuggestions(final String searchTerm) {
                Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//        SessionFactory sessionFactory;
        //Through an elaborate trail of calls, get a handle to an IndexSearcher
        FullTextSession fullTextSession =
                Search.getFullTextSession(session);
        SearchFactory searchFactory = fullTextSession.getSearchFactory();
        IndexReaderAccessor readerProvider = searchFactory.getIndexReaderAccessor();
//        ReaderProvider readerProvider = searchFactory.getReaderProvider();
        IndexReader indexReader =
                readerProvider.open(MyIndexedEntity.class);
//                        searchFactory.getDirectoryProviders(MyIndexedEntity.class)
//);
//                        searchFactory.get(MyIndexedEntity.class));
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        //Keep a list of suggestions retrieved by search over all fields
        List<String> suggestions = new ArrayList<String>();

        //Compose query for term over all fields in Compound
        String lowerCasedSearchTerm = searchTerm.toLowerCase();
        //The names of fields with the following annotations:
        //@Field(index=Index.TOKENIZED, store=Store.YES) &
        //@Analyzer(impl=MyAnalyzer.class)
        String[] searchFields = MyIndexedEntity.getSearchfields();
        for (String field : searchFields) {
            Query query = new TermQuery(new Term(field, lowerCasedSearchTerm));
            try {
                //Actually search for terms
                TopDocs topDocs = indexSearcher.search(query, 10);
                for (ScoreDoc doc : topDocs.scoreDocs) {
                    Document document = indexReader.document(doc.doc);
                    for (String value : document.getValues(field))
                        if (value.toLowerCase().contains(lowerCasedSearchTerm))
                            suggestions.add(value);
                }
            } catch (IOException e) {
                log.error("Failed to read index", e);
                //Do not continue with next field in for loop
                break;
            }
        }

        //IndexReaders should be closed after opening
        readerProvider.close(indexReader);

        //Return the composed list of suggestions, which might be empty
        return suggestions;
    }
//    @Override
    /*public TokenStream tokenStream(final String fieldName, final Reader reader) {
        TokenStream tokenStream = super.tokenStream(fieldName, reader);
        //The following two lines cut up words into partial words, which makes autocomplete work
        tokenStream = new EdgeNGramTokenFilter(tokenStream, EdgeNGramTokenizer.Side.FRONT, 1, 30);
        tokenStream = new EdgeNGramTokenFilter(tokenStream, EdgeNGramTokenizer.Side.BACK, 1, 30);
        return tokenStream;
    }*/
}
