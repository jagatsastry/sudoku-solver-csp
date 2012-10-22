package ai;

public class SudokuGridDFS extends SudokuGrid {
	
	private SudokuGridDFS(Cell[][] grid, int subRow, int subCol) {
		super(grid, subRow, subCol);
	}
	
	@Override public boolean solve() {
		return solve(0, 0);
	}
	
	public static SudokuGridDFS getGrid(String[] gridInput, int subRow, int subCol) {
		int n = gridInput.length;
		Cell[][] grid = new Cell[n][n]; 
		for(int i = 0; i < gridInput.length; i++) {
			String row = gridInput[i];
			String[] cellChars = row.split(",");
			for(int j = 0; j < cellChars.length; j++)
				grid[i][j] = Cell.getCell(cellChars[j], i, j);
		}
		return new SudokuGridDFS(grid, subRow, subCol);
	}
	
	public boolean solve(int row, int col) {
		m_numNodesExpanded++;
		if(row == m_size) return true;
		if(m_grid[row][col].preFilled())
			return this.solve(col==m_size-1?row+1:row, col==m_size-1?0:col+1);
		
		for(int val = 1; val <= m_size; val++) {
			if(this.valid(row, col, val)) {
				m_grid[row][col].setVal(val);
				if(this.solve(col==m_size-1?row+1:row, col==m_size-1?0:col+1))
					return true;
				m_grid[row][col].setVal(Cell.DEF);
			}
		}
		return false;	
	}
}
