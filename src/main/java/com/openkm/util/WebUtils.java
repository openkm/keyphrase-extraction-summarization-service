package com.openkm.util;

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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by pavila on 26/05/17.
 */
public class WebUtils {
	private static Logger log = LoggerFactory.getLogger(WebUtils.class);
	public static final String EMPTY_STRING = "";

	/**
	 * Prepare to send the file.
	 */
	public static void prepareSendFile(HttpServletRequest request, HttpServletResponse response, String fileName,
	                                   String mimeType, boolean inline) throws UnsupportedEncodingException {
		String userAgent = getHeader(request, "user-agent").toLowerCase();
		fileName = PathUtils.decodeEntities(fileName);

		//Allow to use files in different domains
		response.setHeader("Access-Control-Allow-Origin", "*");

		// Set MIME type
		response.setContentType(mimeType);

		if (userAgent.contains("msie") || userAgent.contains("trident")) {
			log.debug("Agent: Explorer ({})", request.getServerPort());
			fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", " ");

			if (request.getServerPort() == 443) {
				log.debug("HTTPS detected! Apply IE workaround...");
				response.setHeader("Cache-Control", "max-age=1");
				response.setHeader("Pragma", "public");
			}
		} else if (userAgent.contains("iphone") || userAgent.contains("ipad")) {
			log.debug("Agent: iPhone - iPad");
			// Do nothing
		} else if (userAgent.contains("android")) {
			log.debug("Agent: Android");
			fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", " ");
		} else if (userAgent.contains("mozilla")) {
			log.debug("Agent: Mozilla");
			fileName = MimeUtility.encodeText(fileName, "UTF-8", "B");
		} else {
			log.debug("Agent: Unknown");
		}

		if (inline) {
			response.setHeader("Content-disposition", "inline; filename=\"" + fileName + "\"");
		} else {
			response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + "\"");
		}
	}

	/**
	 * Get HTTP header.
	 */
	public static String getHeader(HttpServletRequest request, String name) {
		String value = request.getHeader(name);
		if (value != null) {
			return value;
		} else {
			return EMPTY_STRING;
		}
	}

	/**
	 * getJSessionIdFromUrl
	 */
	public static String getJSessionIdFromUrl(String url) {
		if (url.lastIndexOf(";jsessionid=") != -1) {
			return url.substring(url.lastIndexOf(";jsessionid=") + ";jsessionid=".length());
		}
		return null;
	}
}
