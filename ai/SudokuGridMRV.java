package ai;

import java.util.List;

public class SudokuGridMRV extends SudokuGrid {

	
	private SudokuGridMRV(Cell[][] grid, int subRow, int subCol) {
		super(grid, subRow, subCol);
	}
	
//	private Cell getNextUnoccupiedCell() {
//		
//		if(this.m_constrainedCells.size() == 0) return null;
//		
//		Cell max = this.m_constrainedCells.get(0);
//		for(Cell cell: this.m_constrainedCells)
//			if(cell.constraints().size() > max.constraints().size())
//				max = cell;
//				
////		if(this.m_constrainedCells.size() != 0)
////			return this.m_constrainedCells.last();
////		for(Iterator<Cell> iter = this.m_constrainedCells.descendingIterator();
////				iter.hasNext();) {
////			Cell cell = iter.next();
////			if(!cell.solved())
////				return cell;
////		}
//		return max;
//	}
	
	public static SudokuGridMRV getGrid(String[] gridInput, int subRow, int subCol) {
		
		int n = gridInput.length;
		Cell[][] grid = new Cell[n][n]; 
		for(int i = 0; i < gridInput.length; i++) {
			String row = gridInput[i];
			String[] cellChars = row.split(",");
			for(int j = 0; j < cellChars.length; j++) {
				grid[i][j] = Cell.getCell(cellChars[j], i, j);
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
				this.m_constrainedCells.remove(cell);
				List<Cell> modifiedCells = setVal(cell, val); //m_grid[row][col].setVal(val);
				if(this.solve())
					return true;
				cell.setVal(prevVal);
				this.m_constrainedCells.add(cell);
				resetVal(modifiedCells, val); //m_grid[row][col].setVal(Cell.DEF);
			}
		}
		return false;	
	}
    
	
	

}
