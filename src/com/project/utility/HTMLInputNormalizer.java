package com.project.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author Jean-Dominique NGUELE
 *
 */

public class HTMLInputNormalizer {
	/**
	  * Replace the document links in a snippet of HTML with corresponding
	  * fragment links, which start with the # sign, and refer to labelled
	  * locations within a single document.
	  */
	  public static String normalize(String htmlWithInputs){
	    Pattern pattern = Pattern.compile(INPUT_PATTERN);
	    Matcher matcher = pattern.matcher(htmlWithInputs);
	    return matcher.replaceAll(INPUT_REPLACEMENT);
	  }

	  /**
	  * The single matching group of this regex are the digits ((?:\\d){1,3}),
	  * which correspond to group 1.
	  */
	  private static String INPUT_PATTERN = "<(input|br)((?:[^<])+)>";

	  /**
	  * The "$1" refers to matching group 1 of fLINK (the digits).
	  */
	  private static String INPUT_REPLACEMENT = "<$1$2/>";

}
