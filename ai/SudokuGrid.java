package ai;

public abstract class SudokuGrid {
	int m_size;
	int m_subRow;
	int m_subCol;
	int m_numNodesExpanded = 0;
	
	Cell[][] m_grid;
	
	public abstract boolean solve();
	int numNodesExpanded() {
		return this.m_numNodesExpanded;
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
