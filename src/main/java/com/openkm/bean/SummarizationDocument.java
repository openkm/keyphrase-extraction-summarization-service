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

/**
 * SummarizationDocument
 */
public class SummarizationDocument implements Serializable {
    private static final long serialVersionUID = 1L;

    private String body = "";
    private boolean forceToLowerCase = false;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isForceToLowerCase() {
        return forceToLowerCase;
    }

    public void setForceToLowerCase(boolean forceToLowerCase) {
        this.forceToLowerCase = forceToLowerCase;
    }

    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("body=").append(body.length());
        sb.append(",forceToLowerCase=").append(forceToLowerCase);
        sb.append("}");
        return sb.toString();
    }
}
