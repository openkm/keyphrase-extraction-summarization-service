package com.openkm.rest;

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

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author jllort
 */
@XmlRootElement(name = "restException")
public class RestException extends Exception {
	private static final long serialVersionUID = 1L;
	public static String HEADER_ERROR_NAME = "errorName";
	public static String HEADER_ERROR_MESSAGE = "errorMessage";

	private String simpleName;
	private String message;

	/**
	 * RestException
	 */
	public RestException() {
	}

	/**
	 * RestException
	 */
	public RestException(Exception e) {
		super(e);
		this.simpleName = e.getClass().getSimpleName();
		this.message = e.getMessage();
	}

	public ResponseEntity<RestException> getResponseEntity() {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set(RestException.HEADER_ERROR_NAME, simpleName);
		responseHeaders.set(RestException.HEADER_ERROR_MESSAGE, message);
		return new ResponseEntity<RestException>(this, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("simpleName=").append(simpleName);
		sb.append("message=").append(message);
		sb.append("{");
		return sb.toString();
	}
}
