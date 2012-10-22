package ai;

import java.util.HashSet;
import java.util.Set;

public class Cell implements Comparable<Cell> {
	public static final int DEF = 0;
	int m_val;
	boolean m_preFilled = false;
	Set<Integer> m_constraints = new HashSet<Integer>(); 
	int m_row, m_col;

	int row() {
		return m_row;
	}
	
	int col() {
		return m_col;
	}
	Set<Integer> constraints() {
		return m_constraints;
	}
	
	boolean solved() {
		return m_val != DEF;
	}
	
	int constraintSize() {
		return m_constraints.size();  
	}
	
	private Cell(int val, int row, int col, boolean preFilled) {
		m_val = val;
		m_preFilled = preFilled;
		m_row = row;
		m_col = col;
	}

	public static Cell getCell(String c, int row, int col) {
		if("_".equals(c))
			return new Cell(DEF, row, col, false);
		else
			return new Cell(Integer.parseInt(c), row, col, true);
	}

	int val() {
		return m_val;
	}

	void setVal(int m_val) {
		this.m_val = m_val;
	}

	boolean preFilled() {
		return m_preFilled;
	}
	
	@Override public String toString() {
		return "" + m_val + "[" + m_row + "," + m_col + "]";
	}

	@Override
	public int compareTo(Cell cell2) {
		if(this.constraintSize() == cell2.constraintSize()) 
			return 1;
		return (this.constraintSize() - cell2.constraintSize()) * 10000;// - this.constraintSize();
	}
	
	@Override public boolean equals(Object cellObj) {
		if(!(cellObj instanceof Cell)) return false;
		Cell cell = (Cell)cellObj;
		return cell.row() == m_row && cell.col() == m_col;
	}
}
