package com.openkm.cache;

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

import com.openkm.sdk4j.OKMWebservices;

/**
 * @author agallego
 */
public interface WSCacheDAO {
	String ANONYMOUS_USER = "anonymousUser";
	String ADMIN_USER = "admin";

	/**
	 * It creates the OKMWebservices with a key prefix (username) to distinguish between different users' cache.
	 * The first time method completes caches the OKMWebservices object and from now returns object from cache.
	 * In case object has been evicted from cache, the OKMWebservices object is rebuilt
	 *
	 * @param username as the user's username
	 */
	OKMWebservices getOKMWebservices(String username);

	/**
	 * It removes explicitly all items related to the referenced key (username) from cache.
	 *
	 * @param username as the user's username
	 */
	void evictOKMWebservices(String username);

	/**
	 * The same functionality as getOKMWebservices object, but this concrete method is intended to be called only
	 * the first time OKMWebservices object has to be created (for example, at the moment of successful login when
	 * SecurityContextHolder.getContext().getAuthentication() is null, so it does not yet contains credentials from user).
	 * <p>
	 * It also caches OKMWebservices object.
	 */
	OKMWebservices setOKMWebservices(String username, String password);

}
