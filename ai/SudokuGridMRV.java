package ai;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

public class SudokuGridMRV extends SudokuGrid {

	TreeSet<Cell> m_constrainedCells = new TreeSet<Cell>();
	
	private SudokuGridMRV(Cell[][] grid, int subRow, int subCol) {
		m_size = grid.length;
		m_grid = grid;
		m_subRow = subRow;
		m_subCol = subCol;
		for(Cell[] row: grid)
			for(Cell cell: row) {
				if(!new HashSet<Cell>(this.m_constrainedCells).contains(cell)) {
					this.m_constrainedCells.add(cell);
					if(cell.solved()) {
						this.setVal(cell, cell.val());
					}
				}
			}
	}
	private Cell getNextUnoccupiedCell() {
		
		for(Iterator<Cell> iter = this.m_constrainedCells.descendingIterator();
				iter.hasNext();) {
			Cell cell = iter.next();
			if(!cell.solved())
				return cell;
		}
		return null;
	}
	
	public static SudokuGridMRV getGrid(String[] gridInput, int subRow, int subCol) {
		
		int n = gridInput.length;
		Cell[][] grid = new Cell[n][n]; 
		for(int i = 0; i < gridInput.length; i++) {
			String row = gridInput[i];
			String[] cellChars = row.split(",");
			for(int j = 0; j < cellChars.length; j++) {
				grid[i][j] = Cell.getCell(cellChars[j].charAt(0), i, j);
			}
		}
		return new SudokuGridMRV(grid, subRow, subCol);
	}
	
	@Override public boolean solve() {
		m_numNodesExpanded++;
		Cell cell = this.getNextUnoccupiedCell();
		if(cell == null) return true;
		
		for(int val = 1; val <= m_size; val++) {
			if(cell.constraints().contains(val)) continue;
			if(this.valid(cell.row(), cell.col(), val)) {
				int prevVal = cell.val();
				List<Cell> modifiedCells = setVal(cell, val); //m_grid[row][col].setVal(val);
				if(this.solve())
					return true;
				cell.setVal(prevVal);
				resetVal(modifiedCells, val); //m_grid[row][col].setVal(Cell.DEF);
			}
		}
		return false;	
	}
    
	private void resetVal(List<Cell> modifiedCells, int k) {
		for(Cell cell: modifiedCells) {
			cell.constraints().remove(k);
			updateTree(cell);
		}
	}
	
	private List<Cell> setVal(Cell cell, int val) {
		cell.setVal(val);
		List<Cell> constraints = addConstraints(cell, val);
		for(Cell cell1: constraints)
			this.updateTree(cell1);
		return constraints;
	}
	
	
	private void updateTree(Cell cell) {
		
		if(new HashSet<Cell>(this.m_constrainedCells).contains(cell)) {
			this.m_constrainedCells.remove(cell);
			this.m_constrainedCells.add(cell);
		}
	}
	
	private List<Cell> addConstraints(Cell cell, int k) {
		
		List<Cell> updatedCells = new ArrayList<Cell>(m_size);
		for(int ind = 0; ind < m_size; ind++) {
			Cell colCell = m_grid[ind][cell.col()];
			if(cell.row() != ind && !colCell.constraints().contains(k)) {
				colCell.constraints().add(k);
				updatedCells.add(colCell);
				updateTree(colCell);
			}
			Cell rowCell = m_grid[cell.row()][ind];
			if(cell.col() != ind && !rowCell.constraints().contains(k)) {
				rowCell.constraints().add(k);
				updatedCells.add(rowCell);
				updateTree(rowCell);
			}
		}
		int m = (cell.row()/m_subRow) * m_subRow;
		int n = (cell.col()/m_subCol) * m_subCol;
		for(int i = m; i < m + m_subRow; i++)
			for(int j = n; j < n + m_subCol; j++) {
				if(i == cell.row() && j == cell.col()) continue;
				Cell groupCell = m_grid[i][j];
				if(!groupCell.constraints().contains(k)) {
					groupCell.constraints().add(k);
					updatedCells.add(groupCell);
					updateTree(groupCell);
				}
			}
		return updatedCells;
	}
	
	private boolean valid(int row, int col, int k) {
		
		for(int ind = 0; ind < m_size; ind++) {
			if((row != ind && m_grid[ind][col].val() == k) || 
					(col != ind && m_grid[row][ind].val() == k))
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
	
}
