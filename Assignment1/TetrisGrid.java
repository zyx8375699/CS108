//
// TetrisGrid encapsulates a tetris board and has
// a clearRows() capability.
package assign1;


public class TetrisGrid {
	
	private boolean[][] grid;
	
	/**
	 * Constructs a new instance with the given grid.
	 * Does not make a copy.
	 * @param grid
	 */
	public TetrisGrid(boolean[][] grid) {
		this.grid = grid;
	}
	
	
	/**
	 * Does row-clearing on the grid (see handout).
	 */
	public void clearRows() {
		int totalFullRow = 0;
		int height = 0;
		if(grid.length > 0){
			height = grid[0].length;
		}
		int currRow = 0;
		while(currRow < height - totalFullRow) {
			if(fullRow(currRow)) {
				for(int i = 0; i < grid.length; i++) {
					for(int j = currRow; j < height - 1- totalFullRow; j++) {
						grid[i][j] = grid[i][j + 1];
					}
				}
				emptyRow(height - 1 - totalFullRow);
				totalFullRow++;
			} else {
				currRow++;
			}
		}
	}
	
	/**
	 * Returns the internal 2d grid array.
	 * @return 2d grid array
	 */
	boolean[][] getGrid() {
		return grid;
	}
	
	/*
	 * The helper function which determines whether 
	 * a row of the grid is a full row.
	 * Returns true if it is a full row.
	 */
	private boolean fullRow(int row) {
		for(int i = 0; i < grid.length; i++) {
			if(!grid[i][row]) return false;
		}
		return true;
	}
	
	/* 
	 * The helper function which emptys the whole row.
	 */
	private void emptyRow(int row) {
		for(int i = 0; i < grid.length; i++){
			grid[i][row] = false;
		}
	}
	
}
