package com.project.createview;

public class StringUtils {
	
	
	public static final int INDEX_NOT_FOUND = -1;
	public static int countMatches(CharSequence str, CharSequence sub) {
        if (new String(str.toString()).isEmpty() || new String(str.toString()).isEmpty()) {
            return 0;
        }
        int count = 0;
        int idx = 0;
        while ((idx = CharSequenceUtils.indexOf(str, sub, idx)) != INDEX_NOT_FOUND) {
            count++;
            idx += sub.length();
        }
        return count;
    }
}
    