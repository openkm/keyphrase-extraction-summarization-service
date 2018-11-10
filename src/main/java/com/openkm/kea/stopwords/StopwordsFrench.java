package com.openkm.kea.stopwords;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;

import com.openkm.config.Config;
import com.openkm.util.ContextWrapper;

/**
 * Class that can test whether a given string is a stop word.
 * Lowercases all words before the test.
 *
 * @author Eibe Frank (eibe@cs.waikato.ac.nz)
 * @version 1.0
 */
public class StopwordsFrench extends Stopwords {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	/** The hashtable containing the list of stopwords */
	private static Hashtable<String,Double> m_Stopwords = null;

	public StopwordsFrench() {

		if (m_Stopwords == null) {
			m_Stopwords = new Hashtable<String,Double>();
			Double dummy = new Double(0);
			Config config = ContextWrapper.getContext().getBean(Config.class);
			File txt = new File(config.STOP_WORDS_FILE);
			InputStreamReader is;
			String sw = null;
			try {
				is = new InputStreamReader(new FileInputStream(txt), "UTF-8");
				BufferedReader br = new BufferedReader(is);
				while ((sw=br.readLine()) != null)  {
					m_Stopwords.put(sw, dummy);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * Returns true if the given string is a stop word.
	 */
	@Override
	public boolean isStopword(String str) {
		return m_Stopwords.containsKey(str.toLowerCase());
	}
}


