package com.openkm.util.tags;

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

import java.util.Collection;

public class UtilFunctions {
	/**
	 * Check if collection contains an element.
	 */
	public static boolean contains(Collection<?> collection, Object obj) {
		if (collection != null) {
			/*if (collection instanceof PersistentSet) {
				for (Object elto : collection) {
					if (elto.equals(obj)) {
						return true;
					}
				}

				return false;
			} else {
				return collection.contains(obj);
			}*/
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Replace string
	 */
	public static String replace(String input, String regex, String replacement) {
		if (input != null && !input.isEmpty()) {
			return input.replaceAll(regex, replacement);
		} else {
			return null;
		}
	}
}
