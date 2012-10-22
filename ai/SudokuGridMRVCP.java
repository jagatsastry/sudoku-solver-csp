package ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class SudokuGridMRVCP extends SudokuGrid {

	private SudokuGridMRVCP(Cell[][] grid, int subRow, int subCol) {
		super(grid, subRow, subCol);
	}
	
	public static SudokuGridMRVCP getGrid(String[] gridInput, int subRow, int subCol) {
		
		int n = gridInput.length;
		Cell[][] grid = new Cell[n][n]; 
		for(int i = 0; i < gridInput.length; i++) {
			String row = gridInput[i];
			String[] cellChars = row.split(",");
			for(int j = 0; j < cellChars.length; j++) {
				grid[i][j] = Cell.getCell(cellChars[j], i, j);
			}
		}
		return new SudokuGridMRVCP(grid, subRow, subCol);
	}
	
	@Override public boolean solve() {
		Cell cell = this.getNextUnoccupiedCell();
		if(cell == null) return true;
		HashMap<Cell, List<Integer>> prevStateMap=new HashMap<Cell,List<Integer>>();
		try {
			consPropogate(prevStateMap);
		} catch (ConstrainedFailedException e) {
			// TODO Auto-generated catch block
			Set<Cell> cellSet = prevStateMap.keySet();
			for(Cell cellKey:cellSet){
				List<Integer> listInteger =prevStateMap.get(cellKey);
				for(Integer iconst:listInteger){
					cellKey.constraints().remove(iconst);
				}
			}
			//System.out.println("In the catch block");
			return false;
		}
		m_numNodesExpanded++;
		for(int val = 1; val <= m_size; val++) {
			if(cell.constraints().contains(val)) continue;
			if(this.valid(cell.row(), cell.col(), val)) {
				int prevVal = cell.val();
				List<Cell> modifiedCells = setVal(cell, val); //m_grid[row][col].setVal(val);
				this.m_constrainedCells.remove(cell);
				if(this.solve())
					return true;
				cell.setVal(prevVal);
				this.m_constrainedCells.add(cell);
				resetVal(modifiedCells, val); //m_grid[row][col].setVal(Cell.DEF);
			}
		}
		Set<Cell> cellSet = prevStateMap.keySet();
		for(Cell cellKey:cellSet){
			List<Integer> listInteger =prevStateMap.get(cellKey);
			for(Integer iconst:listInteger){
				cellKey.constraints().remove(iconst);
			}
		}
		return false;	
	}
	
	private void consPropogate(HashMap<Cell,List<Integer>> map) throws ConstrainedFailedException{
		Queue<Arc> arcQueue = new LinkedList<Arc>(); 
		generateArc(arcQueue);
		while(!arcQueue.isEmpty()){
			Arc arc = arcQueue.poll();
			if(checkArcConstraint(arc,map)){
				generateNeighbor(arc.getCellx(),arcQueue);
			}
		}
		
	}
	
	private void generateNeighbor(Cell cell, Queue<Arc> arcQueue){
		for (int ind = 0; ind < m_size; ind++) {
			Cell colCell = m_grid[ind][cell.col()];
			if (cell.row() != ind && !m_grid[ind][cell.col()].solved()){
				Arc newArc= new Arc(colCell,cell);
				if(!arcQueue.contains(newArc))
					arcQueue.add(newArc);
			}
			Cell rowCell = m_grid[cell.row()][ind];
			if (cell.col() != ind && !m_grid[cell.row()][ind].solved()) {
				Arc newArc= new Arc(rowCell,cell);
				if(!arcQueue.contains(newArc))
				arcQueue.add(newArc);
			}
		}

		int m = (cell.row() / m_subRow) * m_subRow;
		int n = (cell.col() / m_subCol) * m_subCol;
		for (int i = m; i < m + m_subRow; i++)
			for (int j = n; j < n + m_subCol; j++) {
				if (i == cell.row() && j == cell.col())
					continue;
				Cell groupCell = m_grid[i][j];
				Arc newArc= new Arc(groupCell,cell);
				if(!arcQueue.contains(newArc))
					arcQueue.add(newArc);
			}
		
	}
	
	private boolean checkArcConstraint(Arc arc,HashMap<Cell,List<Integer>> map) throws ConstrainedFailedException{
		boolean added = false;
		for(int val = 1; val <= m_size ; val++){
			if(arc.getCellx().constraints().contains(val)){
				continue;
			}
			if (!arc.getCellj().constraints().contains(val)
					&& arc.getCellj().constraints().size() == m_size - 1) {
				added =true;
				
				if(arc.getCellx().constraints().size()==m_size-1){
					throw new ConstrainedFailedException();
				}
				if(!map.containsKey(arc.getCellx())){
					List<Integer> list = new ArrayList<Integer>();
					list.add(val);
					map.put(arc.getCellx(),list);
				}else{
					List<Integer> list =map.get(arc.getCellx());
					list.add(val);
					map.put(arc.getCellx(),list);
				}
				
				arc.getCellx().constraints().add(val);
				break;
			}
		}
		return added;
		
	}

	private void generateArc(Queue<Arc> arcQueue) {
		for (int io = 0; io < m_size; io++) {
			for (int jo = 0; jo < m_size; jo++) {
				Cell cell = m_grid[io][jo];
				if(cell.solved()) continue;
				if (!cell.preFilled()) {
					for (int ind = 0; ind < m_size; ind++) {
						if (cell.row() != ind && !m_grid[ind][cell.col()].solved()) {
							arcQueue.add(new Arc(cell, m_grid[ind][cell.col()]));
						}
						if (cell.col() != ind && !m_grid[cell.row()][ind].solved()) {
							arcQueue.add(new Arc(cell, m_grid[cell.row()][ind]));
						}
					}
					int m = (cell.row() / m_subRow) * m_subRow;
					int n = (cell.col() / m_subCol) * m_subCol;
					for (int i = m; i < m + m_subRow; i++)
						for (int j = n; j < n + m_subCol; j++) {
							if (m_grid[i][j].solved() || (i == cell.row() && j == cell.col()))
								continue;
							arcQueue.add(new Arc(cell, m_grid[i][j]));
						}
				}
			}
		}

	}

	public static class ConstrainedFailedException extends Exception {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		public ConstrainedFailedException() {
		}
		
	}
	
}
