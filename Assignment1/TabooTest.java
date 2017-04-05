// TabooTest.java
// Taboo class tests -- nothing provided.
package assign1;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.*;

public class TabooTest {
	@Test
	public void testTaboo1() {
		List<String> list = new ArrayList<>();
		list.add("a");
		list.add("c");
		list.add("a");
		list.add("b");
		Taboo<String> t = new Taboo<String>(list);
		Set<String> set1 = t.noFollow("a");
		Set<String> noFollowSet1 = new HashSet<>();
		noFollowSet1.add("c");
		noFollowSet1.add("b");
		assertEquals(noFollowSet1, set1);
		List<String> list2 = new ArrayList<>();
		list2.add("a");
		list2.add("c");
		list2.add("b");
		list2.add("x");
		list2.add("c");
		list2.add("a");
		t.reduce(list2);
		assertEquals(list2, Arrays.asList("a", "x", "c"));
	}
	
	@Test
	public void testTaboo2() {
		List<String> list = new ArrayList<>();
		list.add("a");
		list.add("c");
		list.add(null);
		list.add("a");
		list.add("b");
		list.add("t");
		Taboo<String> t = new Taboo<String>(list);
		Set<String> set1 = t.noFollow("a");
		Set<String> noFollowSet1 = new HashSet<>();
		noFollowSet1.add("c");
		noFollowSet1.add("b");
		assertEquals(noFollowSet1, set1);
		Set<String> set2 = t.noFollow("c");
		assertEquals(new HashSet<String>(), set2);
		Set<String> set3 = new HashSet<>();
		set3.add("t");
		assertEquals(t.noFollow("b"), set3);
		List<String> list2 = new ArrayList<>();
		list2.add("a");
		list2.add("c");
		list2.add("b");
		list2.add("x");
		list2.add("c");
		list2.add("a");
		t.reduce(list2);
		assertEquals(list2, Arrays.asList("a", "x", "c", "a"));
		
		List<String>list3 = new ArrayList<>();
		list3.add("b");
		list3.add("t");
		list3.add("a");
		list3.add("c");
		list3.add("a");
		list3.add("B");
		t.reduce(list3);
		assertEquals(list3, Arrays.asList("b", "a", "a", "B"));
	}
}
