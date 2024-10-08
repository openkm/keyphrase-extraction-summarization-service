package com.openkm.kea.metadata;

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

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openkm.config.Config;
import com.openkm.kea.filter.KEAFilter;
import com.openkm.kea.stemmers.SremovalStemmer;
import com.openkm.kea.stemmers.Stemmer;
import com.openkm.kea.stopwords.Stopwords;
import com.openkm.util.ContextWrapper;

/**
 * KEAFilterBank
 *
 * @author jllort
 *
 */
public class KEAFilterBank {

	private static Logger log = LoggerFactory.getLogger(KEAFilterBank.class);

    private static KEAFilterBank instance;
    private KEAFilter filter;

    /**
     * KEAFilterBank
     *
     * @return
     * @throws MetadataExtractionException
     */
    public static synchronized KEAFilterBank getInstance() throws MetadataExtractionException {
        if (instance == null) {
            instance = new KEAFilterBank();
        }
        return instance;
    }

    /**
     * KEAFilterBank
     *
     * @throws MetadataExtractionException
     */
    private KEAFilterBank() throws MetadataExtractionException {
        Date start = new Date();

        Config config = ContextWrapper.getContext().getBean(Config.class);
        String modelPath = config.MODEL_FILE;
        String vocabularyPath = config.SKOS_FILE;
        int numPhrases = 5;

        String className = WorkspaceHelper.KEA_STOPWORDS_CLASSNAME;
        Stopwords stopwords = null;
		if (className != null) {
			try {
				Class<?> clazz = Class.forName(className);
				stopwords = (Stopwords) clazz.newInstance();
			} catch (Exception e) {
				log.error("Error creating class instance", e);
			}
		}

        filter = buildFilter(modelPath, vocabularyPath, "skos", WorkspaceHelper.KEA_LANGUAGE, new SremovalStemmer(), stopwords, numPhrases);

        Date stop = new Date();
        long time = (stop.getTime() - start.getTime());
        log.info("KEA filters built in " + time + "ms");
    }

    /**
     * getFilter
     *
     * @return
     * @throws MetadataExtractionException
     */
    public static KEAFilter getFilter() throws MetadataExtractionException {
        return getInstance().filter;
    }

    /**
     * buildFilter
     *
     * @param modelPath
     * @param vocabularyPath
     * @param vocabularyFormat
     * @param language
     * @param stemmer
     * @param stopwords
     * @param numPhrases
     *
     * @return
     * @throws MetadataExtractionException
     */
    private KEAFilter buildFilter(String modelPath, String vocabularyPath, String vocabularyFormat,
    							  String language, Stemmer stemmer, Stopwords stopwords, int numPhrases) throws MetadataExtractionException {

        KEAFilter newFilter = null;
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(modelPath));
            ObjectInputStream ois = new ObjectInputStream(bis);
            newFilter = (KEAFilter) ois.readObject();

            newFilter.setVocabulary(vocabularyPath);
            newFilter.setVocabularyFormat(vocabularyFormat);
            newFilter.setDocumentLanguage(language);
            newFilter.setStemmer(stemmer);
            newFilter.setStopwords(stopwords);
            newFilter.loadThesaurus(stemmer,stopwords);
            newFilter.setNumPhrases(numPhrases);

            return newFilter;

        } catch (FileNotFoundException e) {
            log.error("Unable to find KEA model file");
            log.error(e.getMessage(), e);
            throw new MetadataExtractionException("Subject Extraction failed (see trace for details.");
        } catch (IOException e) {
            log.error("Cannot read KEA model from stream");
            log.error(e.getMessage(), e);
            throw new MetadataExtractionException("Subject Extraction failed (see trace for source.");
        } catch (ClassNotFoundException e) {
            log.error("Class cast- KEA model.",e);
            log.error(e.getMessage(), e);
            throw new MetadataExtractionException("Subject Extraction failed (see trace for source.");
        } catch (Throwable e) {
        	log.error("Unexpected error with model");
        	log.error(e.getMessage(), e);
            throw new MetadataExtractionException("Subject Extraction failed (see trace for source.");
        }
    }

}
