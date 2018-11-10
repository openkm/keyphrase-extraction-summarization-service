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

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.openkm.cache.WSCacheDAO;
import com.openkm.sdk4j.OKMWebservices;

/**
 * Implements method from AuthenticationProvider in order to capture and store the password from spring-security
 * objects.
 * It returns an Authentication object filled with username, password and roles assigned to the user. This case,
 * this method intercepts Authentication process from spring-security, contacts to OKM using webservices calls and sets
 * roles to the user. It also stores OKM ws object in cache and it is also able to store somewhere credentials for
 * further access by means of SecurityContextHolder
 * <p>
 * http://www.baeldung.com/spring-security-authentication-provider and http://www.baeldung.com/spring-security-login for
 * configuration
 * <p>
 * It is also possible to extend DAOAuthenticationProvider
 * (http://stackoverflow.com/questions/18220556/how-to-implement-custom-authentication-in-spring-security-3#comment27182355_18224564)
 */
@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {
	private static final Logger log = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

	@Autowired
	private WSCacheDAO wsCache;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();

		try {
			wsCache.evictOKMWebservices(username); // Remove any previous cached username
			OKMWebservices ws = wsCache.setOKMWebservices(username, password);

			String userFullName = ws.getName(username);

			// set roles for spring-security according to those received from OKM ws response
			List<GrantedAuthority> authorities = new ArrayList<>();
			List<String> roles = ws.getRolesByUser(username);

			for (String role : roles) {
				authorities.add(new SimpleGrantedAuthority(role));
			}

			log.info("Loaded authorities for user: " + authorities);
			CustomUser user = new CustomUser(username, password, true, true, true, true, authorities, userFullName);
			log.info("logging in " + user);
			return new UsernamePasswordAuthenticationToken(user, password, authorities);
		} catch (Exception e) {
			log.warn(e.getMessage(), e);
			throw new BadCredentialsException("Login failed...!");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
