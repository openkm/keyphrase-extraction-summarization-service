package com.openkm.bean;

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

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "appVersion")
public class AppVersion {
	private String major = "0";
	private String minor = "0";
	private String maintenance = "0";
	private String build = "0";

	public static AppVersion parse(String str) {
		AppVersion appVer = new AppVersion();

		if (str != null) {
			String[] version = str.split("\\.");

			if (version.length > 0 && version[0] != null) {
				appVer.setMajor(version[0]);
			}

			if (version.length > 1 && version[1] != null) {
				appVer.setMinor(version[1]);
			}

			if (version.length > 2 && version[2] != null && !version[2].equals("")) {
				appVer.setMaintenance(version[2]);
			}
		}

		return appVer;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getMinor() {
		return minor;
	}

	public void setMinor(String minor) {
		this.minor = minor;
	}

	public String getMaintenance() {
		return maintenance;
	}

	public void setMaintenance(String maintenance) {
		this.maintenance = maintenance;
	}

	public String getBuild() {
		return build;
	}

	public void setBuild(String build) {
		this.build = build;
	}

	public String getVersion() {
		return major + "." + minor + "." + maintenance;
	}

	@Override
	public String toString() {
		return major + "." + minor + "." + maintenance + " (build: " + build + ")";
	}
}
