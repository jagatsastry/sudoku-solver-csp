package ai;

public class Cell {
	public static final int DEF = 0;
	int m_val;
	boolean m_preFilled = false;
	
	private Cell(int val, boolean preFilled) {
		m_val = val;
		m_preFilled = preFilled;
	}

	public static Cell getCell(char charInp) {
		
		switch(charInp) {
			case '_': return new Cell(-1, false);
			default: return new Cell(Integer.parseInt(String.valueOf(charInp)), true);
		}
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
		return String.valueOf(m_val);
	}

}
