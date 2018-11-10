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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class RestClient {
	private static Logger log = LoggerFactory.getLogger(RestClient.class);
	public static final String FORMAT_JSON = "application/json";
	public static final String FORMAT_XML = "application/xml";
	public static final String FORMAT_TXT = "text/plain";
	private static final Gson gson = new Gson();
	private String username;
	private String password;

    public RestClient() {
        this.username = null;
        this.password = null;
    }

	public RestClient(String username, String password) {
		this.username = username;
		this.password = password;
	}

	/**
	 * GET binary data
	 */
	public byte[] getBinary(String uri) throws IOException {
		// Authentication
		HttpClient client = getHttpClient(uri);

		// Request
		GetMethod get = new GetMethod(uri);
		get.setDoAuthentication(true);

		try {
			int status = client.executeMethod(get);

			if (status == HttpStatus.SC_OK) {
				InputStream is = get.getResponseBodyAsStream();
				byte[] data = IOUtils.toByteArray(is);
				IOUtils.closeQuietly(is);
				log.debug("Status: {}, Data Lenght: {}", status, data.length);
				return data;
			} else {
				log.error("Unexpected status: {}", status);
				throw new IOException("Method failed: " + get.getStatusLine());
			}
		} finally {
			get.releaseConnection();
		}
	}

	/**
	 * GET
	 */
	public String get(String uri) throws IOException {
		return get(uri, RestClient.FORMAT_JSON);
	}

	/**
	 * GET
	 */
	public String get(String uri, String format) throws IOException {
		log.debug("get({}, {})", new Object[] { uri, format });

		// Authentication
		HttpClient client = getHttpClient(uri);

		// Request
		GetMethod get = new GetMethod(uri);
		get.setDoAuthentication(true);
		get.setRequestHeader("Accept", format);

        return handleResponse(client, get);
    }

    /**
	 * POST
	 */
	public String post(String uri) throws IOException {
		return post(uri, new HashMap<String, Object>(), RestClient.FORMAT_JSON);
	}

	/**
	 * POST
	 */
	public String post(String uri, Map<String, Object> data) throws IOException {
		return post(uri, data, RestClient.FORMAT_JSON);
	}

	/**
	 * POST
	 */
	public String post(String uri, Map<String, Object> data, String format) throws IOException {
		log.debug("post({}, {}, {})", new Object[] { uri, data, format });

		// Authentication
		HttpClient client = getHttpClient(uri);

		// Request
		PostMethod post = new PostMethod(uri);
		post.setDoAuthentication(true);
		post.setRequestHeader("Accept", format);

		if (data != null && !data.isEmpty()) {
			Part[] parts = new Part[data.size()];
			int idx = 0;

			for (Entry<String, Object> entry : data.entrySet()) {
				if (entry.getValue() instanceof String) {
					parts[idx++] = new StringPart(entry.getKey(), (String) entry.getValue());
				} else if (entry.getValue() instanceof File) {
					parts[idx++] = new FilePart(entry.getKey(), (File) entry.getValue());
				} else {
					String json = gson.toJson(entry.getValue());
					parts[idx++] = new StringPart(entry.getKey(), json);
				}
			}

			post.setRequestEntity(new MultipartRequestEntity(parts, post.getParams()));
		}

        return handleResponse(client, post);
	}

	/**
     * POST
     */
    public String post(String uri, String body, String format) throws IOException {
        log.debug("post({}, {}, {})", new Object[] { uri, body, format });

        // Authentication
        HttpClient client = getHttpClient(uri);

        // Request
        PostMethod post = new PostMethod(uri);
        post.setDoAuthentication(true);
        post.setRequestHeader("Accept", format);
        StringRequestEntity requestEntity = new StringRequestEntity(body, format, "UTF-8");
        post.setRequestEntity(requestEntity);
        return handleResponse(client, post);
    }

	/**
	 * PUT
	 */
	public String put(String uri, String message) throws IOException {
		return put(uri, RestClient.FORMAT_JSON, message);
	}

	/**
	 * PUT
	 */
	public String put(String uri, String format, String message) throws IOException {
		// Authentication
		HttpClient client = getHttpClient(uri);

		// Request
		PutMethod put = new PutMethod(uri);
		put.setDoAuthentication(true);
		put.setRequestHeader("Accept", format);
		put.setRequestEntity(new StringRequestEntity(message, format, "UTF-8"));

        return handleResponse(client, put);
	}

	/**
	 * DELETE
	 */
	public String delete(String uri, String format, String message) throws IOException {
		// Authentication
		HttpClient client = getHttpClient(uri);

		// Request
		DeleteMethod delete = new DeleteMethod(uri);
		delete.setDoAuthentication(true);
		delete.setRequestHeader("Accept", format);

        return handleResponse(client, delete);
	}

    /**
	 * Handle method response
     */
    private String handleResponse(HttpClient client, HttpMethodBase method) throws IOException {
        try {
            int status = client.executeMethod(method);

            if (status == HttpStatus.SC_OK) {
                String response = readJsonResponse(method);
                log.debug("Status: {}, Response: {}", status, response);
                return response;
            } else {
                log.error("Unexpected status: {}", status);
                throw new IOException("Method failed: " + method.getStatusLine());
            }
        } finally {
            method.releaseConnection();
        }
    }

	/**
	 * Create authenticated HTTP client
	 */
	private HttpClient getHttpClient(String uri) throws MalformedURLException {
		URL url = new URL(uri);
		HttpClient client = new HttpClient();

        if (username != null && password != null) {
            Credentials credentials = new UsernamePasswordCredentials(username, password);
            client.getState().setCredentials(new AuthScope(url.getHost(), url.getPort(), AuthScope.ANY_REALM), credentials);
            client.getParams().setAuthenticationPreemptive(true);
        }

		return client;
	}

	/**
	 * Read response from server
	 */
	private String readJsonResponse(HttpMethod method) throws IOException {
		InputStream ris = null;

		try {
			ris = method.getResponseBodyAsStream();
			return IOUtils.toString(ris);
		} finally {
			IOUtils.closeQuietly(ris);
		}
	}
}
