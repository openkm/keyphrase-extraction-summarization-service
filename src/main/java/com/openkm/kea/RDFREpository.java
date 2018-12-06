package com.openkm.kea;

import java.io.File;

/*-
 * #%L
 * kea-summarization
 * %%
 * Copyright (C) 2018 OpenKM Knowledge Management System S.L.
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.openrdf.query.BindingSet;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParseException;
import org.openrdf.sail.memory.MemoryStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.openkm.config.Config;
import com.openkm.kea.bean.Term;
import com.openkm.kea.tree.StringIgnoreCaseComparator;
import com.openkm.kea.tree.TermComparator;

/**
 * @author jllort
 *
 */
@Component
public class RDFREpository {
	private static Logger log = LoggerFactory.getLogger(RDFREpository.class);

    private static Repository SKOSRepository = null;
    private static Repository OWLRepository = null;
    private static RDFREpository instance;
    private static List<Term> terms = null;
    private static List<String> keywords = null;

    @Autowired
    private Config config;

    /**
     * getConnection
     *
     * @return
     * @throws RepositoryException
     */
    public RepositoryConnection getSKOSConnection() throws RepositoryException {
    	if (SKOSRepository!=null) {
    		return SKOSRepository.getConnection();
    	} else throw new RepositoryException("SKOS Repository not started");
    }

    /**
     * getConnection
     *
     * @return
     * @throws RepositoryException
     */
    public RepositoryConnection getOWLConnection() throws RepositoryException {
    	if (OWLRepository!=null) {
    		return OWLRepository.getConnection();
    	} else throw new RepositoryException("OWL Repository not started");
    }

    /**
     * RDFVocabulary
     */
    private RDFREpository() {
    }

    /**
     * getInstance
     *
     * @return
     */
    public static synchronized RDFREpository getInstance() {
        if (instance == null) {
            log.info("Starting RDF repository");
            instance = new RDFREpository();
        }
        return instance;
    }


    /**
     * getTerms
     *
     * @return
     */
    public List<Term> getTerms() {
    	if (terms==null || keywords==null) {
    		loadTerms();
    	}
    	return terms;
    }

    /**
     * getTerms
     *
     * @return
     */
    public List<String> getKeywords() {
    	if (terms==null || keywords==null) {
    		loadTerms();
    	}
    	return keywords;
    }

    /**
     * loadTerms
     *
     * @return
     */
    private void loadTerms() {
        terms = new ArrayList<Term>();
        keywords = new ArrayList<String>();
        RepositoryConnection con = null;
        TupleQuery query;

        log.info("Loading skos terms in memory");
        if (SKOSRepository!=null) {
	        try {
	            con = SKOSRepository.getConnection();
				query = con.prepareTupleQuery(QueryLanguage.SERQL, config.VOCABULARY_SERQL);
	            log.info("query:"+config.VOCABULARY_SERQL);
	            TupleQueryResult result;
				result = query.evaluate();
	            while (result.hasNext()) {
	                BindingSet bindingSet = result.next();
	                Term term = new Term(bindingSet.getValue("UID").stringValue(),"");
	                terms.add(term);
	                keywords.add(term.getUid());
	            }
	        } catch (RepositoryException e) {
	            log.error("could not obtain connection to respository",e);
	        } catch (MalformedQueryException e) {
	        	log.error(e.getMessage(),e);
			} catch (QueryEvaluationException e) {
				log.error(e.getMessage(),e);
			} finally {
	            try {
	                 con.close();
	            } catch (Throwable e) {
	                log.error("Could not close connection....", e);
	            }
	        }
        }
        // Sorting collections
        Collections.sort(terms, new TermComparator());
        Collections.sort(keywords, new StringIgnoreCaseComparator());
        log.info("Finished loading skos terms in memory");
    }

    /**
     * getSKOSMemStoreRepository
     *
     * @return
     */
    private Repository getSKOSMemStoreRepository() {
        InputStream is;
        Repository repository = null;
        String baseURL = config.THESAURUS_BASE_URL;

        log.info("Loading skos file in memory");
        try {
        	log.info(config.SKOS_FILE);
        	is = new FileInputStream(config.SKOS_FILE);
            repository = new SailRepository(new MemoryStore());
            repository.initialize();
            RepositoryConnection con = repository.getConnection();
            con.add(is, baseURL, RDFFormat.RDFXML);
            con.close();
            log.info("New SAIL memstore created for SKOS RDF");

        } catch (RepositoryException e) {
            log.error("Cannot make connection to RDF repository.", e);
        } catch (IOException e) {
            log.error("cannot locate/read file", e);
            e.printStackTrace();
        } catch (RDFParseException e) {
            log.error("Cannot parse file", e);
        } catch (Throwable t) {
            log.error("Unexpected exception loading repository",t);
        }
        log.info("Finished loading skos file in memory");
        return repository;
    }

    /**
     * getOWLMemStoreRepository
     *
     * @return
     */
    private Repository getOWLMemStoreRepository() {
        InputStream is;
        Repository repository = null;
        String baseURL = config.THESAURUS_BASE_URL;

        log.info("Loading owl file in memory");
        try {
        	log.info(config.THESAURUS_OWL_FILE);
        	is = new FileInputStream(config.THESAURUS_OWL_FILE);
            repository = new SailRepository(new MemoryStore());
            repository.initialize();
            RepositoryConnection con = repository.getConnection();
            con.add(is, baseURL, RDFFormat.RDFXML);
            con.close();
            log.info("New SAIL memstore created for OWL RDF");

        } catch (RepositoryException e) {
            log.error("Cannot make connection to RDF repository.", e);
        } catch (IOException e) {
            log.error("cannot locate/read file", e);
            e.printStackTrace();
        } catch (RDFParseException e) {
            log.error("Cannot parse file", e);
        } catch (Throwable t) {
            log.error("Unexpected exception loading repository",t);
        }
        log.info("Finished loading owl file in memory");
        return repository;
    }

    @PostConstruct
	public void RDFREpositoryInit() {
//		new Thread(() -> {
		try {
			log.info("*** Starting repository ... ***");

	    	if (!config.SKOS_FILE.equals("")) {
	    	    log.info("Loading SKOS to memory storage");
	    		SKOSRepository = getSKOSMemStoreRepository();
	    	    log.info("Loading terms");
	    		loadTerms();
	    	}
	        if (!config.THESAURUS_OWL_FILE.equals("")) {
	            log.info("Loading OWL to memory storage");
	        	OWLRepository = getOWLMemStoreRepository();
	        }
	        log.info("RDF repository started");

	        log.info("Check training folder");
	        File training = new File(config.getTrainingFolderPath());
	        if (training.exists() && training.isDirectory()) {
	        	log.info("Training folder exist:" + config.getTrainingFolderPath());
	        } else {
	        	training.mkdirs();
	        	log.info("Created training folder:" + config.getTrainingFolderPath());
	        }
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
//		}).start();
	}
}
