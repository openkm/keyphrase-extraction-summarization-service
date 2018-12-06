package com.openkm.controller;

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

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.TypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.openkm.bean.SummarizationDocument;
import com.openkm.bean.TrainingDocument;
import com.openkm.config.Config;
import com.openkm.config.auth.CustomUser;
import com.openkm.util.PrincipalUtils;
import com.openkm.util.RestClient;

/**
 * Created by pavila on 17/06/16.
 */
@Controller
public class DefaultController {
	private static Logger log = LoggerFactory.getLogger(DefaultController.class);

	@Autowired
	private Config config;

	@GetMapping("/")
	public String login(Model model) {
		model.addAttribute("fecha", new Date(System.currentTimeMillis()));
		return "index";
	}

	@GetMapping("/header")
	public String header() {
		return "include/header";
	}

	@GetMapping("/menu")
	public String menu(Model model) {
		CustomUser user = PrincipalUtils.getUser();
		model.addAttribute("user", user);
		return "include/menu";
	}

	@GetMapping("/login")
	public String login(@RequestParam(value = "error", required = false) String error, Model model) {
		if (error != null) {
			model.addAttribute("error", "User and password not valid.");
		}

		return "login";
	}

	@GetMapping("/test")
	public String test(Model model) throws IOException {
		InputStream is = null;
		try {
			is = TypeReference.class.getResourceAsStream("/testFile.txt");
			String content = IOUtils.toString(is, StandardCharsets.UTF_8);
			SummarizationDocument sd = new SummarizationDocument();
			sd.setBody(content);
	        sd.setForceToLowerCase(true);
	        RestClient rc = new RestClient();
	        Gson gson = new Gson();
	        String json = gson.toJson(sd);
	        String response = rc.post(config.getSummarizationUrl(), json, RestClient.FORMAT_JSON);
	        String[] keywords = new Gson().fromJson(response, String[].class);
	        for (String keyword : keywords)  {
	           log.debug("keyword:" + keyword);
	        }
			model.addAttribute("content", content.replaceAll("\n", "<br/>"));
			model.addAttribute("keywords", keywords);
			return "test";
		} finally {
			IOUtils.closeQuietly(is);
		}
	}

	@GetMapping("/testTraining")
	public String testTraining(Model model) throws IOException {
		InputStream is = null;
		try {
			is = TypeReference.class.getResourceAsStream("/trainingFile.txt");
			String content = IOUtils.toString(is, StandardCharsets.UTF_8);
			TrainingDocument td = new TrainingDocument();
			td.setBody(content);
			File keywordsFile = new File(TypeReference.class.getResource("/trainingFile.key").getPath());
			List<String> keywords = FileUtils.readLines(keywordsFile, StandardCharsets.UTF_8);
	        td.setKeywords(keywords);
	        td.setForceKeywordsToUpperCase(true);
	        RestClient rc = new RestClient();
	        Gson gson = new Gson();
	        String json = gson.toJson(td);
	        String response = rc.post(config.getTrainingUrl(), json, RestClient.FORMAT_JSON);
			model.addAttribute("content", content.replaceAll("\n", "<br/>"));
			model.addAttribute("keywords", keywords);
			model.addAttribute("uuid", response);
			return "testTraining";
		} finally {
			IOUtils.closeQuietly(is);
		}
	}
}
