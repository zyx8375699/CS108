package assign1;

import java.util.*;

public class Appearances {

	/**
	 * Returns the number of elements that appear the same number
	 * of times in both collections. Static method. (see handout).
	 * @return number of same-appearance elements
	 */
	public static <T> int sameCount(Collection<T> a, Collection<T> b) {
		int result = 0;
		//create the appearance map of collection a and b
		Map<T, Integer> mapA = countAppearence(a);
		Map<T, Integer> mapB = countAppearence(b);
		
		for(T element: mapA.keySet()) {
			if(mapB.containsKey(element)) {
				if(mapA.get(element) == mapB.get(element)) result++;
			}
		}
		return result; 
	}

	/*
	 * The helper function which counts the appearance of one element in
	 * the collection, and put the element as key and the appearance time 
	 * as value into a hash map.
	 * Returns the appearance hash map.
	 */
	private static <T> Map<T, Integer> countAppearence(Collection<T> a) {
		Map<T, Integer> map = new HashMap<>();
		for(T elem: a) {
			if(!map.containsKey(elem)) {
				int count = 0;
				for(T t: a) {
					if(t.equals(elem)) count++;
				}
				map.put(elem, count);
			}
		}
		return map;
	}

}
