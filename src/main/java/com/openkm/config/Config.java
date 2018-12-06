package com.openkm.config;

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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by pavila on 15/05/17.
 */
@Component
@ConfigurationProperties
public class Config {
	@Value("${openkm.url}")
	public String OPENKM_URL;

	@Value("${base.openkm.url}")
	public String BASE_OPENKM_URL;

	@Value("${admin.user}")
	public String ADMIN_USER;

	@Value("${admin.password}")
	public String ADMIN_PASSWORD;

	@Value("${kea.summarization.thesaurus.skos.file}")
	public String SKOS_FILE;

	@Value("${kea.summarization.thesaurus.vocabulary.serql}")
	public String VOCABULARY_SERQL;

	@Value("${kea.summarization.model.file}")
	public String MODEL_FILE;

	@Value("${kea.summarization.stopwords.file}")
	public String STOP_WORDS_FILE;

	@Value("${kea.summarization.automatic.keyword.extraction.number}")
	public String KEYWORD_EXTRACTION_NUMBER;

	@Value("${kea.summarization.automatic.keyword.extraction.restriction}")
	public String KEYWORD_EXTRACTION_RESTRICTION;

	@Value("${kea.summarization.thesaurus.owl.file}")
	public String THESAURUS_OWL_FILE;

	@Value("${kea.summarization.thesaurus.base.url}")
	public String THESAURUS_BASE_URL;

	@Value("${kea.summarization.thesaurus.tree.root}")
	public String THESAURUS_TREE_ROOT;

	@Value("${kea.summarization.thesaurus.tree.childs}")
	public String THESAURUS_TREE_CHILDS;

	@Value("${catalina.home}")
	public String TOMCAT_HOME;

	public String getTrainingFolderPath() {
		return TOMCAT_HOME + File.separator + "training";
	}

	@Value("${application.test.url}")
	public String APPLICATION_TEST_URL;

	public String getSummarizationUrl() {
		String url = APPLICATION_TEST_URL;
		if (!url.endsWith("/")) {
			url += "/";
		}
		return url + "rest/summarization/keywords";
	}

	public String getTrainingUrl() {
		String url = APPLICATION_TEST_URL;
		if (!url.endsWith("/")) {
			url += "/";
		}
		return url + "rest/training/file";
	}
}
