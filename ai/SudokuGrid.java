package ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class SudokuGrid {
	int m_size;
	int m_subRow;
	int m_subCol;
	int m_numNodesExpanded = 0;
	
	Cell[][] m_grid;
	protected Set<Cell> m_constrainedCells = new HashSet<Cell>(200);

	public SudokuGrid(Cell[][] grid, int subRow, int subCol) {
		m_size = grid.length;
		m_grid = grid;
		m_subRow = subRow;
		m_subCol = subCol;
		for(Cell[] row: grid)
			for(Cell cell: row) {
				if(!cell.solved())
					this.m_constrainedCells.add(cell);
				else 
						this.setVal(cell, cell.val());
			}

	}
	
	public abstract boolean solve();
	int numNodesExpanded() {
		return this.m_numNodesExpanded;
	}
	
	protected void resetVal(List<Cell> modifiedCells, int k) {
		for(Cell cell: modifiedCells) {
			cell.constraints().remove(k);
//			updateTree(cell);
		}
	}
	protected boolean valid(int row, int col, int k) {
		
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
	
	protected List<Cell> addConstraints(Cell cell, int k) {
		
		List<Cell> updatedCells = new ArrayList<Cell>(m_size);
		for(int ind = 0; ind < m_size; ind++) {
			Cell colCell = m_grid[ind][cell.col()];
			if(!colCell.solved() && cell.row() != ind && !colCell.constraints().contains(k)) {
				colCell.constraints().add(k);
				if(!updatedCells.contains(colCell))
						updatedCells.add(colCell);
			}
			Cell rowCell = m_grid[cell.row()][ind];
			if(!rowCell.solved() && cell.col() != ind && !rowCell.constraints().contains(k)) {
				rowCell.constraints().add(k);
				if(!updatedCells.contains(rowCell))
				updatedCells.add(rowCell);
			}
		}
		int m = (cell.row()/m_subRow) * m_subRow;
		int n = (cell.col()/m_subCol) * m_subCol;
		for(int i = m; i < m + m_subRow; i++)
			for(int j = n; j < n + m_subCol; j++) {
				if(m_grid[i][j].solved() || (i == cell.row() && j == cell.col())) continue;
				Cell groupCell = m_grid[i][j];
				if(!groupCell.constraints().contains(k)) {
					groupCell.constraints().add(k);
					if(!updatedCells.contains(groupCell))
					updatedCells.add(groupCell);
				}
			}
		return updatedCells;
	}

	protected List<Cell> setVal(Cell cell, int val) {
		cell.setVal(val);
		List<Cell> constraints = addConstraints(cell, val);
//		for(Cell cell1: constraints)
//			this.updateTree(cell1);
		return constraints;
	}
	
	protected Cell getNextUnoccupiedCell() {
		
		if(this.m_constrainedCells.size() == 0) return null;
		
		Cell max = Collections.max(this.m_constrainedCells);
//		for(Cell cell: this.m_constrainedCells)
//			if(cell.constraints().size() > max.constraints().size())
//				max = cell;
				
//		if(this.m_constrainedCells.size() != 0)
//			return this.m_constrainedCells.last();
//		for(Iterator<Cell> iter = this.m_constrainedCells.descendingIterator();
//				iter.hasNext();) {
//			Cell cell = iter.next();
//			if(!cell.solved())
//				return cell;
//		}
		return max;
	}

	@Override public String toString() {
		StringBuffer buff = new StringBuffer();
		for(Cell[] row: m_grid) {
			for(Cell cell: row)
				buff.append(cell.val() + ",");
			buff.append("\n");
		}
		return buff.toString();
		
	}


}
