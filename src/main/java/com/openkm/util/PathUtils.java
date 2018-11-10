package com.openkm.util;

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

public class PathUtils {
	/**
	 * Get parent node.
	 */
	public static String getParent(String path) {
		int lastSlash = path.lastIndexOf('/');
		String ret = (lastSlash > 0) ? path.substring(0, lastSlash) : "/";
		return ret;
	}

	/**
	 * Get node name.
	 */
	public static String getName(String path) {
		String ret = path.substring(path.lastIndexOf('/') + 1);
		return ret;
	}

	/**
	 * Returns the name without the extension.
	 */
	public static String getNameWithoutExtension(String name) {
		int idx = name.lastIndexOf(".");
		String ret = idx >= 0 ? name.substring(0, idx) : name;
		return ret;
	}

	/**
	 * Shorten final name
	 */
	public static String shortenFileName(String path, int maxLength) {
		String fileName = getName(path);
		if (fileName.length() > maxLength) {
			StringBuilder sb = new StringBuilder();
			sb.append(fileName.substring(0, maxLength - 10)).append("...")
					.append(fileName.substring(fileName.length() - 7, fileName.length()));
			return sb.toString();
		} else {
			return fileName;
		}
	}

	/**
	 * Encode entities to avoid problems with & and &amp;. For example:
	 * - "/okm:root/a & b" => "/okm:root/a &amp; b"
	 * - "/okm:root/a &amp; b" => "/okm:root/a &amp; b"
	 * - "/okm:root/a <> b" => "/okm:root/a &lt;&gt; b"
	 * - "/okm:root/a &lt;&gt; b" => "/okm:root/a &lt;&gt; b"
	 *
	 * @param path Path or string to encode.
	 * @return The string with the encoded entities.
	 */
	public static String encodeEntities(String path) {
		String tmp = path.replaceAll("&(?![alg#][mt0-9][0-9]?p?;)", "&amp;");
		tmp = tmp.replaceAll("<", "&lt;");
		tmp = tmp.replaceAll(">", "&gt;");
		tmp = tmp.replaceAll("\"", "&#34;");
		tmp = tmp.replaceAll("'", "&#39;");
		return tmp;
	}

	/**
	 * Reverse of encodeEntities. For example:
	 * - "/okm:root/a &amp; b" => "/okm:root/a & b"
	 *
	 * @param path Path or string to decode.
	 * @return The string with the decoded entities.
	 */
	public static String decodeEntities(String path) {
		String tmp = path.replaceAll("&amp;", "&");
		tmp = tmp.replaceAll("&lt;", "<");
		tmp = tmp.replaceAll("&gt;", ">");
		tmp = tmp.replaceAll("&#34;", "\"");
		tmp = tmp.replaceAll("&#39;", "'");
		return tmp;
	}
}
