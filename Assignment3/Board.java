// Board.java
package edu.stanford.cs108.tetris;

/**
 CS108 Tetris Board.
 Represents a Tetris board -- essentially a 2-d grid
 of booleans. Supports tetris pieces and row clearing.
 Has an "undo" feature that allows clients to add and remove pieces efficiently.
 Does not do any drawing or have any idea of pixels. Instead,
 just represents the abstract 2-d board.
 */
public class Board	{
	// Some ivars are stubbed out for you:
	private int width;
	private int height;
	private boolean[][] grid;
	private boolean DEBUG = true;
	boolean committed;
	
	private int[] widths;
	private int[] heights;
	private int maxHeight;

	
	//backup Variables
	private boolean[][] xGrid;	
	private int[] xWidths;
	private int[] xHeights;
	private int xMaxHeight;


	// Here a few trivial methods are provided:

	/**
	 Creates an empty board of the given width and height
	 measured in blocks.
	 */
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		committed = true;
		
		grid = new boolean[width][height];
		xGrid = new boolean[width][height];
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				grid[i][j] = false;
			}
		}
		
		widths = new int[height];
		xWidths = new int[height];
		for(int i = 0; i < height; i++) {
			widths[i] = 0;
		}

		heights = new int[width];
		xHeights = new int[width];
		for(int i = 0; i < width; i++) {
			heights[i] = 0;
		}
		
		maxHeight = 0;
	}


	/**
	 Returns the width of the board in blocks.
	 */
	public int getWidth() {
		return width;
	}


	/**
	 Returns the height of the board in blocks.
	 */
	public int getHeight() {
		return height;
	}


	/**
	 Returns the max column height present in the board.
	 For an empty board this is 0.
	 */
	public int getMaxHeight() {
		return maxHeight;
	}


	/**
	 Checks the board for internal consistency -- used
	 for debugging.
	 */
	public void sanityCheck() {
		if (DEBUG) {
			//check height and maxHeight
			int max = 0;
			for(int i = 0; i < width; i++) {
				int correctHeight = 0;
				for(int j = 0; j < height; j++) {
					if(grid[i][j]) correctHeight = j + 1;
				}
				if(correctHeight != heights[i]) throw new RuntimeException("description");
				max = Math.max(max, correctHeight);
			}
			
			if(max != maxHeight) throw new RuntimeException("description");
			
			//check width
			for(int i = 0; i < height; i++) {
				int correctWidth = 0;
				for(int j = 0; j < width; j++) {
					if(grid[j][i]) correctWidth++;
				}
				if(correctWidth != widths[i]) throw new RuntimeException("description");
			}
		}
	}

	/**
	 Given a piece and an x, returns the y
	 value where the piece would come to rest
	 if it were dropped straight down at that x.

	 <p>
	 Implementation: use the skirt and the col heights
	 to compute this fast -- O(skirt length).
	 */
	public int dropHeight(Piece piece, int x) {
		int max = 0;
		int[] skirt = piece.getSkirt();
		for(int i = 0; i < piece.getWidth(); i++) {
			int currentHeight = getColumnHeight(x + i) - skirt[i];
			if(currentHeight > max) {
				max = currentHeight;
			}
		}
		return max;
	}


	/**
	 Returns the height of the given column --
	 i.e. the y value of the highest block + 1.
	 The height is 0 if the column contains no blocks.
	 */
	public int getColumnHeight(int x) {
		return heights[x];
	}


	/**
	 Returns the number of filled blocks in
	 the given row.
	 */
	public int getRowWidth(int y) {
		return widths[y];
	}


	/**
	 Returns true if the given block is filled in the board.
	 Blocks outside of the valid width/height area
	 always return true.
	 */
	public boolean getGrid(int x, int y) {	
		return outBounds(x, y) || grid[x][y];

	}


	public static final int PLACE_OK = 0;
	public static final int PLACE_ROW_FILLED = 1;
	public static final int PLACE_OUT_BOUNDS = 2;
	public static final int PLACE_BAD = 3;

	/**
	 Attempts to add the body of a piece to the board.
	 Copies the piece blocks into the board grid.
	 Returns PLACE_OK for a regular placement, or PLACE_ROW_FILLED
	 for a regular placement that causes at least one row to be filled.

	 <p>Error cases:
	 A placement may fail in two ways. First, if part of the piece may falls out
	 of bounds of the board, PLACE_OUT_BOUNDS is returned.
	 Or the placement may collide with existing blocks in the grid
	 in which case PLACE_BAD is returned.
	 In both error cases, the board may be left in an invalid
	 state. The client can use undo(), to recover the valid, pre-place state.
	 */
	public int place(Piece piece, int x, int y) {
		// flag !committed problem
		if (!committed) throw new RuntimeException("place commit problem");
		committed = false;
		backup();
		int result = PLACE_OK;

		if(outBounds(x, y)) return PLACE_OUT_BOUNDS;
		if(outBounds(x + piece.getWidth() - 1, y + piece.getHeight() - 1)) return PLACE_OUT_BOUNDS;

		TPoint[] points = piece.getBody();
		for(int i = 0; i < points.length; i++) {
			TPoint point = points[i];
			if(grid[x + point.x][y + point.y]) return PLACE_BAD;
			grid[x + point.x][y + point.y] = true;
			widths[y + point.y]++;
			heights[x + point.x] = Math.max(heights[x + point.x], y + point.y + 1);
			maxHeight = Math.max(maxHeight, heights[x + point.x]);
			if(filledRow(y + point.y)) result = PLACE_ROW_FILLED;
		}
		sanityCheck();
		return result;
	}

	private boolean outBounds(int x, int y) {
		if(x < 0 || x >= width) return true;
		if(y < 0 || y >= height) return true;
		return false;
	}


	/**
	 Deletes rows that are filled all the way across, moving
	 things above down. Returns the number of rows cleared.
	 */
	public int clearRows() {
		if(committed) {
			backup();
			committed = false;
		}
		int toRow = findFirstFilledRow();
		if(toRow == -1) {
			sanityCheck();
			return 0;
		}
		int fromRow = toRow + 1;
		int rowsCleared = clearFilledRows(toRow, fromRow);
		
		updateHeight();
		sanityCheck();
		return rowsCleared;
	}

	private boolean filledRow(int y) {
		return getRowWidth(y) == width;
	}
	
	private int findFirstFilledRow() {
		for(int i = 0; i < maxHeight; i++) {
			if(filledRow(i)) return i;
		}
		return -1;
	}
	
	private int clearFilledRows(int toRow, int fromRow) {
		int rowsCleared = 1;
		while(toRow < maxHeight) {
			while(fromRow < maxHeight) {
				if(!filledRow(fromRow)) break;
				rowsCleared++;
				fromRow++;
			}
			if(fromRow < maxHeight) {
				for(int i = 0; i < width; i++) {
					grid[i][toRow] = grid[i][fromRow];
				}
				widths[toRow] = widths[fromRow];
				fromRow++;
			} else {
				for(int i = 0; i < width; i++) {
					grid[i][toRow] = false;
				}
				widths[toRow] = 0;
			}
			toRow++;
		}
		return rowsCleared;
	}
	
	private void updateHeight() {
		maxHeight = 0;
		for(int i = 0; i < width; i++) {
			heights[i] = 0;
			for(int j = 0; j < height; j++) {
				if(grid[i][j]) heights[i] = j + 1;
			}
			maxHeight = Math.max(maxHeight, heights[i]);
		}
	}

	/**
	 Reverts the board to its state before up to one place
	 and one clearRows();
	 If the conditions for undo() are not met, such as
	 calling undo() twice in a row, then the second undo() does nothing.
	 See the overview docs.
	 */
	public void undo() {
		if(!committed) {
			committed = true;
			boolean[][] temp = grid;
			grid = xGrid;
			xGrid = temp;
			
			int[] tempWidth = widths;
			widths = xWidths;
			xWidths = tempWidth;
			
			int[] tempHeight = heights;
			heights = xHeights;
			xHeights = tempHeight;
			
			maxHeight = xMaxHeight;
		}
	}
	
	private void backup() {
		for(int i = 0; i < width; i++) {
			System.arraycopy(grid[i], 0, xGrid[i], 0, height);
		}
		System.arraycopy(widths, 0, xWidths, 0, height);
		System.arraycopy(heights, 0, xHeights, 0, width);
		xMaxHeight = maxHeight;
	}


	/**
	 Puts the board in the committed state.
	 */
	public void commit() {
		committed = true;
	}



	/*
	 Renders the board state as a big String, suitable for printing.
	 This is the sort of print-obj-state utility that can help see complex
	 state change over time.
	 (provided debugging utility) 
	 */
	public String toString() {
		StringBuilder buff = new StringBuilder();
		for (int y = height-1; y>=0; y--) {
			buff.append('|');
			for (int x=0; x<width; x++) {
				if (getGrid(x,y)) buff.append('+');
				else buff.append(' ');
			}
			buff.append("|\n");
		}
		for (int x=0; x<width+2; x++) buff.append('-');
		return(buff.toString());
	}
}


