package com.openkm.rest.endpoint;

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

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openkm.bean.AppVersion;
import com.openkm.bean.SummarizationDocument;
import com.openkm.config.Config;
import com.openkm.kea.RDFREpository;
import com.openkm.kea.bean.MetadataDTO;
import com.openkm.kea.bean.Term;
import com.openkm.kea.metadata.MetadataExtractor;
import com.openkm.rest.RestException;

@RestController
@RequestMapping(value = "/rest/summarization")
public class SummarizationService {
	private static Logger log = LoggerFactory.getLogger(SummarizationService.class);

	@Autowired
	private Config config;

	@Autowired
	private RDFREpository rdfREpository;

	@PostMapping("/keywords")
	public List<String> getKeywords(@RequestBody SummarizationDocument doc) throws RestException {
		log.debug("getKeywords({},{})" + doc.getBody().length(), doc.isForceToLowerCase());
		List<String> keywords = new ArrayList<String>();

		if (!config.MODEL_FILE.equals("")) {
			File tmp = null;
			try {
				// Saving txt file localy
				tmp = File.createTempFile("okm", ".txt");
				FileOutputStream out = new FileOutputStream(tmp);
				out.write(doc.getBody().getBytes("UTF-8"));
				out.close();
				MetadataExtractor mdExtractor = new MetadataExtractor(Integer.parseInt(config.KEYWORD_EXTRACTION_NUMBER));
				MetadataDTO mdDTO = mdExtractor.extract(tmp);

				for (Term term : mdDTO.getSubjectsAsTerms()) {
					log.debug("Term:" + term.getText());

					if (!config.KEYWORD_EXTRACTION_RESTRICTION.isEmpty() && config.KEYWORD_EXTRACTION_RESTRICTION.equals("on")) {
						if (rdfREpository.getKeywords().contains(term.getText())) {
							String keyword = term.getText().replace(" ", "_");
							if (doc.isForceToLowerCase()) {
								keyword = keyword.toLowerCase();
							}
							keywords.add(keyword);
						}
					} else {
						String keyword = term.getText().replace(" ", "_");
						if (doc.isForceToLowerCase()) {
							keyword = keyword.toLowerCase();
						}
						keywords.add(keyword);
					}
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw new RestException(e);
			} finally {
				FileUtils.deleteQuietly(tmp);
			}
		}

		return keywords;
	}

	@GetMapping("/version")
	public AppVersion getAppVersion() throws RestException {
		try {
			log.debug("getAppVersion()");
			AppVersion ver = new AppVersion();
			ver.setMaintenance("keas");
			ver.setMajor("1");
			ver.setMinor("0");
			log.debug("getAppVersion: {}", ver);
			return ver;
		} catch (Exception e) {
			throw new RestException(e);
		}
	}
}
