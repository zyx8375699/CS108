// Test cases for CharGrid -- a few basic tests are provided.
package assign1;

import static org.junit.Assert.*;
import org.junit.Test;

public class CharGridTest {
	
	@Test
	public void testCharArea1() {
		char[][] grid = new char[][] {
				{'a', 'y', ' '},
				{'x', 'a', 'z'},
			};
		
		
		CharGrid cg = new CharGrid(grid);
				
		assertEquals(4, cg.charArea('a'));
		assertEquals(1, cg.charArea('z'));
	}
	
	
	@Test
	public void testCharArea2() {
		char[][] grid = new char[][] {
				{'c', 'a', ' '},
				{'b', ' ', 'b'},
				{' ', ' ', 'a'}
			};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(6, cg.charArea('a'));
		assertEquals(3, cg.charArea('b'));
		assertEquals(0, cg.charArea('d'));
	}
	
	@Test
	public void testCharArea3() {
		char[][] grid = new char[][] {
		};
		
		CharGrid cg = new CharGrid(grid);
		assertEquals(0, cg.charArea('a'));
	}
	
	@Test
	public void testCharArea4() {
		char[][] grid = new char[][] {
			{'a', 'b', 'c', 'd'},
			{'b', 'c', ' ', 'f'},
			{'b', 'a', 'a', ' '},
			{'f', 'd', 'e', 'a'},
		};
		
		CharGrid cg = new CharGrid(grid);
		assertEquals(16, cg.charArea('a'));
		assertEquals(6, cg.charArea('b'));
		assertEquals(4, cg.charArea('c'));
		assertEquals(12, cg.charArea('d'));
		assertEquals(12, cg.charArea('f'));
		assertEquals(1, cg.charArea('e'));
		assertEquals(0, cg.charArea('x'));
	}
	@Test
	public void testCountPlus1() {
		char[][] grid = new char[][] {
			{' ', ' ', 'p', ' ', ' ', ' ', ' ', ' ', ' '},
			{' ', ' ', 'p', ' ', ' ', ' ', ' ', 'x', ' '},
			{'p', 'p', 'p', 'p', 'p', 'y', 'x', 'x', 'x'},
			{' ', ' ', 'p', ' ', ' ', 'y', ' ', 'x', ' '},
			{' ', ' ', 'p', 'y', 'y', 'y', 'y', 'y', ' '},
			{'z', 'z', 'z', 'z', 'z', 'y', 'z', 'z', 'z'},
			{' ', ' ', 'x', 'x', ' ', 'y', ' ', ' ', ' '},
		};
		
		CharGrid cg = new CharGrid(grid);
		assertEquals(3, cg.countPlus());
	}
	
	@Test
	public void testCountPlus2() {
		char[][] grid = new char[][] {
			{'a', 'b', ' ', ' ', ' '},
			{' ', 'b', 'p', ' ', ' '},
			{'p', 'p', 'p', 'p', 'p'},
			{' ', 'd', 'p', 'y', ' '},
			{' ', ' ', 'p', 'y', 'y'},
		};
		
		CharGrid cg = new CharGrid(grid);
		assertEquals(0, cg.countPlus());
	}
}
