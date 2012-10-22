package ai;

import java.util.List;

public class SudokuGridFwdChecking extends SudokuGrid {

	private SudokuGridFwdChecking(Cell[][] grid, int subRow, int subCol)  {
		super(grid, subRow, subCol);
 	}
	
	public static SudokuGrid getGrid(String[] gridInput, int subRow, int subCol)  {
		
		int n = gridInput.length;
		Cell[][] grid = new Cell[n][n]; 
		for(int i = 0; i < gridInput.length; i++) {
			String row = gridInput[i];
			String[] cellChars = row.split(",");
			for(int j = 0; j < cellChars.length; j++) {
				grid[i][j] = Cell.getCell(cellChars[j], i, j);
			}
		}
		return new SudokuGridFwdChecking(grid, subRow, subCol);
	}
	
	@Override public boolean solve() {
		m_numNodesExpanded++;
		Cell cell = this.getNextUnoccupiedCell();
		if(cell == null) return true;
		if(cell.row() == 0 && cell.col() == 0) 
			System.out.println("Deliberate break");
		for(int val = 1; val <= m_size; val++) {
			if(cell.constraints().contains(val)) continue;
			if(this.valid(cell.row(), cell.col(), val)) {
				int prevVal = cell.val();
				List<Cell> modifiedCells = null;
				boolean exc = false;
				modifiedCells = setVal(cell, val); //m_grid[row][col].setVal(val);
				this.m_constrainedCells.remove(cell);
				for(Cell modcel: modifiedCells)
					if(modcel.constraintSize() == this.m_size)
						exc = true;

				if(!exc && this.solve())
					return true;
				cell.setVal(prevVal);
				this.m_constrainedCells.add(cell);
         	    if(modifiedCells != null) resetVal(modifiedCells, val);
			}
		}
		return false;	
	}
    
}
