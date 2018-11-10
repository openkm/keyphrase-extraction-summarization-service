package com.openkm.kea.stemmers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implements the iterated version of the Lovins stemmer.
 *
 * @author Eibe Frank (eibe@cs.waikato.ac.nz)
 * @version 1.0
 */
public class IteratedLovinsStemmer extends LovinsStemmer {

  /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private static Logger log = LoggerFactory.getLogger(IteratedLovinsStemmer.class);

/**
   * Iterated stemming of the given word.
   */
  @Override
public String stem(String str) {

    if (str.length() <= 2) {
      return str;
    }
    String stemmed = super.stem(str);
    while (!stemmed.equals(str)) {
      str = stemmed;
      stemmed = super.stem(stemmed);
    }
    return stemmed;
  }

  /**
   * Stems text coming into stdin and writes it to stdout.
   */
  public static void main(String[] ops) {

    IteratedLovinsStemmer ls = new IteratedLovinsStemmer();

    try {
      int num;
      StringBuffer wordBuffer = new StringBuffer();
      while ((num = System.in.read()) != -1) {
	char c = (char)num;
	if (((num >= 'A') && (num <= 'Z')) ||
	    ((num >= 'a') && (num <= 'z'))) {
	  wordBuffer.append(c);
	} else {
	  if (wordBuffer.length() > 0) {
	    log.info(ls.stem(wordBuffer.toString().
				     toLowerCase()));
	    wordBuffer = new StringBuffer();
	  }
	  log.info(""+c);
	}
      }
    } catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }
}


