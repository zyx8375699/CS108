// HW1 2-d array Problems
// CharGrid encapsulates a 2-d grid of chars and supports
// a few operations on the grid.

package assign1;

public class CharGrid {
	private char[][] grid;

	/**
	 * Constructs a new CharGrid with the given grid.
	 * Does not make a copy.
	 * @param grid
	 */
	public CharGrid(char[][] grid) {
		this.grid = grid;
	}
	
	/**
	 * Returns the area for the given char in the grid. (see handout).
	 * @param ch char to look for
	 * @return area for given char
	 */
	public int charArea(char ch) {
		int minX = grid.length;
		int maxX = 0;
		int minY = 0;
		if(minX > 0) minY = grid[0].length;
		int maxY = 0;
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				char c = grid[i][j];
				if(c == ch) {
					minX = Math.min(minX, i);
					maxX = Math.max(maxX, i + 1);
					minY = Math.min(minY, j);
					maxY = Math.max(maxY, j + 1);
				}
			}
		}
		if(maxX == 0) return 0;
		else return (maxX - minX) * (maxY - minY);
	}
	
	/**
	 * Returns the count of '+' figures in the grid (see handout).
	 * @return number of + in grid
	 */
	public int countPlus() {
		int count = 0;
		for(int i = 1; i < grid.length -1; i++) {
			for(int j = 1; j < grid[0].length - 1; j++) {
				if(grid[i][j] != ' ' && ifPlus(i, j)) count++;
			}
		}
		return count; 
	}
	
	/* 
	 * The helper function which determines whether the plus of
	 * the center(row, col) is a plus by finding if the 4 wings of
	 * the plus has the same length.
	 * Returns true if it is a plus.
	 */
	private boolean ifPlus(int row, int col) {
		int leftWing = countWing(row, col, 1);
		if(leftWing == 0) return false;
		int rightWing = countWing(row, col, 2);
		int upWing = countWing(row, col, 3);
		int downWing = countWing(row, col, 4);
		return (leftWing == rightWing && leftWing == upWing && leftWing == downWing);
	}
	
	/* 
	 * The helper function which counts the length of a wing of the plus.
	 * The function has three variables, the position(row, col) of the center and
	 * the type of the wing.
	 * 1 means left wing, 2 means right wing, 3 means up wing and 4 means down wing.
	 */
	private int countWing(int row, int col, int type) {
		char center = grid[row][col];
		int result = 0;
		int i = 0;
		int j = 0;
		int len = 1;
		
		//determine which wing to count
		if(type == 1) i = -1;
		if(type == 2) i = 1;
		if(type == 3) j = -1;
		if(type == 4) j = 1;
		
		while(true) {
			if(outBounds(row + i * len, col + j * len)) return result;
			if(grid[row + i * len][col + j * len] != center) return result;
			result++;
			len++;
		}
	}
	
	/*
	 * The helper function which determines a point is out of bound of the grid.
	 * Return true if the point is out of bound.
	 */
	private boolean outBounds(int row, int col) {
		if(row < 0) return true;
		if(row >= grid.length) return true;
		if(col < 0) return true;
		if(col >= grid[0].length) return true;
		return false;
	}

	

	
}
