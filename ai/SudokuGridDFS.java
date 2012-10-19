package ai;

import java.util.Arrays;

public class SudokuGridDFS {
	int m_size;
	int m_subRow;
	int m_subCol;
	
	Cell[][] m_grid;
	
	private SudokuGridDFS(Cell[][] grid, int subRow, int subCol) {
		m_size = grid.length;
		m_grid = grid;
		m_subRow = subRow;
		m_subCol = subCol;
	}
	
	public static SudokuGridDFS getGrid(String[] gridInput, int subRow, int subCol) {
		int n = gridInput.length;
		Cell[][] grid = new Cell[n][n]; 
		for(int i = 0; i < gridInput.length; i++) {
			String row = gridInput[i];
			String[] cellChars = row.split(",");
			for(int j = 0; j < cellChars.length; j++)
				grid[i][j] = Cell.getCell(cellChars[j].charAt(0), i, j);
		}
		return new SudokuGridDFS(grid, subRow, subCol);
	}
	
	int m_numNodesExpanded = 0;
	int numNodesExpanded() {
		return m_numNodesExpanded;
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

	private boolean valid(int row, int col, int k) {
		
		for(int ind = 0; ind < m_size; ind++) {
			if((row != ind && m_grid[ind][col].val() == k) || (col != ind && m_grid[row][ind].val() == k))
				return false;
		}
		int m = (row/m_subRow) * m_subRow;
		int n = (col/m_subCol) * m_subCol;
		for(int i = m; i < m + m_subRow; i++)
			for(int j = n; j < n + m_subCol; j++) {
				if(i == row && j == col) continue;
				if(this.m_grid[i][j].val() == k)
				return false;
			}
		return true;
	}
	
	@Override public String toString() {
		StringBuffer buff = new StringBuffer();
		for(Cell[] row: m_grid)
			buff.append(Arrays.toString(row) + "\n");
		return buff.toString();
	}
}
