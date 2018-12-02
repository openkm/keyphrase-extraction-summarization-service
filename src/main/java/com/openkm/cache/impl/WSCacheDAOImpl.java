package com.openkm.cache.impl;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.openkm.cache.WSCacheDAO;
import com.openkm.config.Config;
import com.openkm.sdk4j.OKMWebservices;
import com.openkm.sdk4j.OKMWebservicesFactory;

/**
 * Spring MVC component to manage caching of OKMWebservices object
 */
@Service("wsCache")
public class WSCacheDAOImpl implements WSCacheDAO {
	private static final Logger logger = LoggerFactory.getLogger(WSCacheDAOImpl.class);

	public WSCacheDAOImpl() {
	}

	@Autowired
	private Config config;

	@Override
	@Cacheable(value = "wsCache", key = "#username")
	public OKMWebservices getOKMWebservices(String username) {
		OKMWebservices ws = null;

		if (username.equals(ADMIN_USER)) {
			ws = OKMWebservicesFactory.newInstance(config.OPENKM_URL, config.ADMIN_USER, config.ADMIN_PASSWORD);
		} else {
			String password = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getPassword();
			ws = OKMWebservicesFactory.newInstance(config.OPENKM_URL, username, password);
		}

		return ws;
	}


	@Override
	@CacheEvict(value = "wsCache", key = "#username")
	public void evictOKMWebservices(String username) {
		logger.info("evict cache for " + username);
	}

	@Override
	@Cacheable(value = "wsCache", key = "#username")
	public OKMWebservices setOKMWebservices(String username, String password) {
		logger.info("set OKMWebservices object for the first time in the cache for username " + username);
		OKMWebservices ws = OKMWebservicesFactory.newInstance(config.OPENKM_URL, username, password);
		return ws;
	}
}
