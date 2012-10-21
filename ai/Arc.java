package ai;

public class Arc {
	
	private Cell cellx;
	private Cell cellj;
	
	public Arc(Cell cellx, Cell cellj){
		this.cellx=cellx;
		this.cellj=cellj;
	}
	public Cell getCellx() {
		return cellx;
	}
	public void setCellx(Cell cellx) {
		this.cellx = cellx;
	}
	public Cell getCellj() {
		return cellj;
	}
	public void setCellj(Cell cellj) {
		this.cellj = cellj;
	}
	
	@Override public boolean equals(Object arcObj) {
		if(!(arcObj instanceof Cell)) return false;
		Arc arc = (Arc)arcObj;
		return this.cellx.equals(arc.cellx) && this.cellj.equals(arc.cellj);
	}
}
