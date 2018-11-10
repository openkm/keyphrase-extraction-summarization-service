package com.openkm.kea.tree;

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

import java.util.Comparator;

/**
 * StringIgnoreCaseComparator
 *
 * @author jllort
 *
 */
public class StringIgnoreCaseComparator implements Comparator<String>  {

    @Override
	public int compare(String arg0, String arg1) {
    	// Only compares initial terms
    	int length = 0;
    	if (arg0.length()>arg1.length()) {
    		length = arg1.length();
    	} else {
    		length = arg0.length();
    	}
        return arg0.substring(0,length).toLowerCase().compareTo(arg1.substring(0,length).toLowerCase());
    }
}
