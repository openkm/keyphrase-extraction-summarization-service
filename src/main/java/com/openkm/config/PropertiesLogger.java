package com.openkm.config;

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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;

@Configuration
public class PropertiesLogger {
	private static Logger log = LoggerFactory.getLogger(PropertiesLogger.class);

	@Autowired
	private AbstractEnvironment environment;

	@PostConstruct
	public void printProperties() {
		log.info("**** APPLICATION PROPERTIES SOURCES ****");

		Set<String> properties = new TreeSet<>();
		for (PropertiesPropertySource p : findPropertiesPropertySources()) {
			log.info(p.toString());
			properties.addAll(Arrays.asList(p.getPropertyNames()));
		}

		log.info("**** APPLICATION PROPERTIES VALUES ****");
		print(properties);
	}

	private List<PropertiesPropertySource> findPropertiesPropertySources() {
		List<PropertiesPropertySource> propertiesPropertySources = new LinkedList<>();
		for (PropertySource<?> propertySource : environment.getPropertySources()) {
			if (propertySource instanceof PropertiesPropertySource) {
				propertiesPropertySources.add((PropertiesPropertySource) propertySource);
			}
		}
		return propertiesPropertySources;
	}

	private void print(Set<String> properties) {
		for (String propertyName : properties) {
			log.info("{} = {}", propertyName, environment.getProperty(propertyName));
		}
	}
}
