/*
 HW1 Taboo problem class.
 Taboo encapsulates some rules about what objects
 may not follow other objects.
 (See handout).
*/
package assign1;

import java.util.*;

public class Taboo<T> {
	
	private Map<T, Set<T>> map = new HashMap<>();
	/**
	 * Constructs a new Taboo using the given rules (see handout.)
	 * @param rules rules for new Taboo
	 */
	public Taboo(List<T> rules) {
		for(int i = 0; i < rules.size() - 1; i++) {
			T elem = rules.get(i);
			if(elem != null) {
				T nextElem = rules.get(i + 1);
				if(nextElem != null) {
					if(map.containsKey(elem)) {
						map.get(elem).add(nextElem);
					} else {
						Set<T> set = new HashSet<>();
						set.add(nextElem);
						map.put(elem, set);
					}
				}
			}
		}
	}
	
	/**
	 * Returns the set of elements which should not follow
	 * the given element.
	 * @param elem
	 * @return elements which should not follow the given element
	 */
	public Set<T> noFollow(T elem) {
		if(map.containsKey(elem)) return map.get(elem);
		return new HashSet<T>();
	}
	
	/**
	 * Removes elements from the given list that
	 * violate the rules (see handout).
	 * @param list collection to reduce
	 */
	public void reduce(List<T> list) {
		int i = 0;
		while(i < list.size() - 1) {
			T elem = list.get(i);
			Set<T> noFollowSet = noFollow(elem);
			T nextElem = list.get(i + 1);
			if(noFollowSet.contains(nextElem)) {
				list.remove(i + 1);
			} else {
				i++;
			}
		}
	}
}
