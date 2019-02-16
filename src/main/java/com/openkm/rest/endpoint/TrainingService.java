package com.openkm.rest.endpoint;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

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

import java.util.UUID;

import com.openkm.util.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openkm.bean.TrainingDocument;
import com.openkm.config.Config;
import com.openkm.rest.RestException;

@RestController
@RequestMapping(value = "/rest/training")
public class TrainingService {

	private static Logger log = LoggerFactory.getLogger(SummarizationService.class);

	@Autowired
	private Config config;

	@PostMapping("/file")
	public String file(@RequestBody TrainingDocument doc) throws RestException {
		log.debug("file({},{})" + doc.getBody().length(), doc.getKeywords());
		String uuid = "";
		OutputStream os = null;
		InputStream is = null;
		PrintWriter pw = null;
		try {
			uuid = UUID.randomUUID().toString();

			// Txt file
			File trainingTextFile = new File(config.getTrainingFolderPath() + File.separator + uuid + ".txt");
			os = new FileOutputStream(trainingTextFile);
			is = new ByteArrayInputStream(doc.getBody().getBytes(StandardCharsets.UTF_8));
			IOUtils.copy(is, os);

			// Key file
			pw = new PrintWriter(new FileWriter(config.getTrainingFolderPath() + File.separator + uuid + ".key"));
			for (String keyword : doc.getKeywords()) {
				if (doc.isForceKeywordsToUpperCase()) {
					pw.println(keyword.toUpperCase());
				} else {
					pw.println(keyword);
				}
			}

		} catch (Exception e) {
			new RestException(e);
		} finally {
			IOUtils.closeQuietly(os);
			IOUtils.closeQuietly(is);
			IOUtils.closeQuietly(pw);
		}
		return uuid;
	}
}
