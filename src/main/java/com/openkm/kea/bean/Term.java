package com.openkm.kea.bean;

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
 * Term
 *
 * @author jllort
 *
 */
public class Term implements Serializable {

	private static final long serialVersionUID = 290660580424913769L;

	private String text;
    private String uid;

	/**
     * Term
     */
    public Term() {
    }

    /**
     * Term
     * @param text
     * @param uid
     */
    public Term(String uid, String text) {
    	this.uid = uid;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

    @Override
	public boolean equals(Object o) {
        if (this == o) return true;
        if ((o == null) || !(o instanceof Term)) return false;

        Term term = (Term) o;

        if (uid != null ? !uid.equals(term.uid) : term.uid != null) return false;
        if (text != null ? !text.equals(term.text) : term.text != null) return false;

        return true;
    }

    @Override
	public int hashCode() {
        int result;
        result = (text != null ? text.hashCode() : 0);
        result = 31 * result + (uid != null ? uid.hashCode() : 0);
        return result;
    }
}
