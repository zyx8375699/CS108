package assign1;

import java.util.HashSet;
import java.util.Set;

// CS108 HW1 -- String static methods

public class StringCode {

	/**
	 * Given a string, returns the length of the largest run.
	 * A a run is a series of adajcent chars that are the same.
	 * @param str
	 * @return max run length
	 */
	public static int maxRun(String str) {
		if(str == "" || str == null) return 0;
		
		//indicate current and max length of run
		int current = 1;
		int max = 1;
		
		for(int i = 1; i < str.length(); i++) {
			if(str.charAt(i) == str.charAt(i - 1)) {
				current++;
			} else {
				max = Math.max(max, current);
				current = 1;
			}
		}
		return max; 
	}

	
	/**
	 * Given a string, for each digit in the original string,
	 * replaces the digit with that many occurrences of the character
	 * following. So the string "a3tx2z" yields "attttxzzz".
	 * @param str
	 * @return blown up string
	 */
	public static String blowup(String str) {
		if (str == null) return null;
		if (str == "") return "";
		StringBuilder result = new StringBuilder();
		for(int i = 0; i < str.length() - 1; i++) {
			char c = str.charAt(i);
			
			//if c is a digit, append next character c times
			if(c >= '0' && c <= '9') {
				for(int j = 0; j < c - '0'; j++) {
					result.append(str.charAt(i + 1));
				}
			} else { //else, just append c
				result.append(c);
			}
		}
		//append last char of the string
		char c = str.charAt(str.length() - 1);
		if(c < '0' || c > '9') result.append(c);
		
		return result.toString();
	}
	
	/**
	 * Given 2 strings, consider all the substrings within them
	 * of length len. Returns true if there are any such substrings
	 * which appear in both strings.
	 * Compute this in linear time using a HashSet. Len will be 1 or more.
	 */
	public static boolean stringIntersect(String a, String b, int len) {
		if(len > a.length()) return false;
		if(len > b.length()) return false;
		
		//create a hash set storing all the substrings of string a
		Set<String> subStrings = new HashSet<>();
		for(int i = 0; i < a.length() - len + 1; i++) {
			String sub = a.substring(i, i + len);
			subStrings.add(sub);
		}
		
		//find if any substring of string b is in the hash set
		for(int i = 0; i < b.length() - len + 1; i++) {
			String sub2 = b.substring(i, i + len);
			if(subStrings.contains(sub2)) return true;
		}
		return false;
	}
}
