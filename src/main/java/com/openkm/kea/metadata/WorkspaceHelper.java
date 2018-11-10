package com.openkm.kea.metadata;

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

import com.openkm.config.Config;
import com.openkm.util.ContextWrapper;

/**
 * WorkspaceHelper
 *
 * @author jllort
 *
 */
public class WorkspaceHelper {
	// Language
	public static final String KEA_LANGUAGE = getLanguage();

	// Stop words class name
	public static final String KEA_STOPWORDS_CLASSNAME = getStopWordsClassName();

	/**
	 * getLanguage
	 *
	 * @return The language
	 */
	public static String getLanguage() {
		String lang = "";
		Config config = ContextWrapper.getContext().getBean(Config.class);
		if (!config.STOP_WORDS_FILE.equals("")) {
			lang = config.STOP_WORDS_FILE.substring(config.STOP_WORDS_FILE.lastIndexOf("_") + 1,
					config.STOP_WORDS_FILE.lastIndexOf("."));
		}

		return lang;
	}

	/**
	 * getStopWordsClassName
	 *
	 * @return The class name
	 */
	public static String getStopWordsClassName() {
		String className = null;

		if (KEA_LANGUAGE.equals("en")) {
			className = "com.openkm.kea.stopwords.StopwordsEnglish";
		} else if (KEA_LANGUAGE.equals("es")) {
			className = "com.openkm.kea.stopwords.StopwordsSpanish";
		} else if (KEA_LANGUAGE.equals("de")) {
			className = "com.openkm.kea.stopwords.StopwordsGerman";
		} else if (KEA_LANGUAGE.equals("fr")) {
			className = "com.openkm.kea.stopwords.StopwordsFrench";
		} else {
			className = "com.openkm.kea.stopwords.Stopwords";
		}

		return className;
	}
}
