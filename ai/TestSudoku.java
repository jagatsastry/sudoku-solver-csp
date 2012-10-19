package ai;

import java.util.ArrayList;
import java.util.List;

public class TestSudoku {

	public static enum Method {
		DFS, MRV, FWDCK;
	}
	
	static final Method METHOD = Method.FWDCK; 

	public static void main(String[] args) {
		String[] gridStr = getGrid();
		SudokuGrid grid = SudokuGridMRV.getGrid(gridStr, 3, 3);
		switch(METHOD) {
			case MRV:
				break;
			case DFS:
				grid = SudokuGridDFS.getGrid(gridStr, 3, 3);
				break;
			case FWDCK:
				grid = SudokuGridFwdChecking.getGrid(gridStr, 3, 3);
				break;
		}
		grid.solve();
		System.out.println(grid);
		System.out.println("Method: " + METHOD);
		System.out.println("Solved? " + grid.solve());
		System.out.println("Nodes expanded: " + grid.numNodesExpanded());

	}

	private static String[] getGrid() {
		List<String> board = new ArrayList<String>() {
			private static final long serialVersionUID = 1L;
			{
				{add("_,_,_,9,7,_,_,_,2"); }
				{add("_,_,_,3,_,_,_,6,_"); }
				{add("1,5,_,_,4,_,_,_,7"); }
				{add("_,8,_,_,_,_,_,_,6"); }
				{add("9,_,6,_,_,_,2,_,5"); }
				{add("3,_,_,_,_,_,_,7,_"); }
				{add("8,_,_,_,2,_,_,1,4"); }
				{add("_,4,_,_,_,6,_,_,_"); }
				{add("2,_,_,_,5,9,_,_,_"); }
			}
		};
		return board.toArray(new String[0]);
	}

}
