package com.openkm.config.auth;

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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Service;

import com.openkm.cache.WSCacheDAO;

/**
 * This class extends and implements spring-security classes to intercept and handle the logout process
 * in order to execute custom actions, for example, to evict cache of logged out user and to control
 * the template to see after a successful logout
 */
@Service("customLogoutSuccessHandler")
public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
	private static final Logger log = LoggerFactory.getLogger(CustomLogoutSuccessHandler.class);

	@Autowired
	private WSCacheDAO wsCache;

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException {
		if (authentication != null && authentication.getDetails() != null) {
			wsCache.evictOKMWebservices(authentication.getName());
			log.debug("session before invalidate : " + request.getSession().getCreationTime());
			request.getSession(false).invalidate();
			log.debug("invalidate session");
			log.debug("session after invalidate : " + request.getSession(false));
			log.info(authentication.getName() + " successfully logged out");
		}

		String url = request.getContextPath() + "/";
		response.sendRedirect(url);
	}
}
