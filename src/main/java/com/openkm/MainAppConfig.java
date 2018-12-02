package com.openkm;

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

import java.util.Properties;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Created by pavila on 17/06/16.SpringBootApplication
 * <p>
 * https://spring.io/guides/gs/spring-boot/
 * <p>
 * By default all packages below your main configuration class (the one annotated with @EnableAutoConfiguration
 * or @SpringBootApplication) will be searched.
 */

@SpringBootApplication(exclude = JmsAutoConfiguration.class)
public class MainAppConfig extends SpringBootServletInitializer {
	public static void main(String[] args) {
		new SpringApplicationBuilder(MainAppConfig.class).sources(MainAppConfig.class).properties(getProperties())
		.run(args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MainAppConfig.class).properties(getProperties());
	}

	// Inspired in
	// https://stackoverflow.com/questions/31017064/how-to-externalize-spring-boot-application-properties-to-tomcat-lib-folder/31027378#31027378
	// application.properties can not ve overwritten by @PropertySource or similar,
	// read https://github.com/spring-projects/spring-boot/issues/3842
	static Properties getProperties() {
		Properties props = new Properties();
		props.put("spring.config.location", "classpath:/application.properties, file:${catalina.home}/keas.properties");
		return props;
	}
}
