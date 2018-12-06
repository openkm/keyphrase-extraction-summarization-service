package com.openkm.bean;

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

import java.io.Serializable;
import java.util.List;

/**
 * TrainingDocument
 */
public class TrainingDocument implements Serializable {
    private static final long serialVersionUID = 1L;

    private String body = "";
    private List<String> keywords;
    private boolean forceKeywordsToUpperCase = false;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	public boolean isForceKeywordsToUpperCase() {
		return forceKeywordsToUpperCase;
	}

	public void setForceKeywordsToUpperCase(boolean forceKeywordsToUpperCase) {
		this.forceKeywordsToUpperCase = forceKeywordsToUpperCase;
	}

	@Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("body=").append(body.length());
        sb.append(",keywords=").append(keywords);
        sb.append(",forceKeywordsToUpperCase=").append(forceKeywordsToUpperCase);
        sb.append("}");
        return sb.toString();
    }
}
