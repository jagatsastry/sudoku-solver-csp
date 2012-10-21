package ai;

import java.util.ArrayList;
import java.util.List;

public class TestSudoku {

	public static enum Method {
		DFS, MRV, FWDCK;
	}
	
	static final Method METHOD = Method.MRV; 

	public static void main(String[] args) {
		List<String[]> gridStrs = getGrids();
		for(String[] gridStr: gridStrs) {
			SudokuGrid grid = SudokuGridMRVCP.getGrid(gridStr, 2, 3);
			switch(METHOD) {
				case MRV:
					break;
				case DFS:
					grid = SudokuGridDFS.getGrid(gridStr, 2, 3);
					break;
				case FWDCK:
					grid = SudokuGridFwdChecking.getGrid(gridStr, 2, 3);
					break;
			}
			grid.solve();
			System.out.println(grid);
			System.out.println("Method: " + METHOD);
			System.out.println("Solved? " + grid.solve());
			System.out.println("Nodes expanded: " + grid.numNodesExpanded());
		}

	}

	private static List<String[]> getGrids() {
		List<String[]> boards = new ArrayList<String[]>();
		
		List<String> board = new ArrayList<String>() {
			private static final long serialVersionUID = 1L;
			{
				
			add("_,_,6,_,5,_");}
			{add("_,1,_,2,_,_");}
			{add("_,_,1,_,_,_");}
			{add("_,_,_,3,_,_");}
			{add("_,_,4,_,1,_");}
			{add("_,2,_,4,_,_");}
			 //add("_,_,_,9,7,_,_,_,2"); }
			//{add("_,_,_,3,_,_,_,6,_"); }
			//{add("1,5,_,_,4,_,_,_,7"); }
			//{add("_,8,_,_,_,_,_,_,6"); }
			//{add("9,_,6,_,_,_,2,_,5"); }
			//{add("3,_,_,_,_,_,_,7,_"); }
			//{add("8,_,_,_,2,_,_,1,4"); }
			//{add("_,4,_,_,_,6,_,_,_"); }
			//{add("2,_,_,_,5,9,_,_,_"); }
				//{add("3,_,_,4"); }
				//{add("_,_,_,_"); }
				//{add("_,2,_,_"); }
				//{add("_,_,_,1"); }
				
			
		};
		boards.add(board.toArray(new String[0]));
		
		board = new ArrayList<String>() {
			private static final long serialVersionUID = 1L;
			{
			 add("_,_,3,_,_,_,_,_,2"); }
			{add("_,6,_,_,_,2,7,_,3"); }
			{add("2,_,_,_,_,_,_,1,_"); }
			{add("_,_,_,4,_,_,1,5,8"); }
			{add("_,_,_,8,_,3,_,_,_"); }
			{add("8,1,9,_,_,6,_,_,_"); }
			{add("_,7,_,_,2,_,_,_,1"); }
			{add("4,_,8,5,_,_,_,6,_"); }
			{add("9,_,_,_,_,_,2,_,_"); }
				//{add("3,_,_,4"); }
				//{add("_,_,_,_"); }
				//{add("_,2,_,_"); }
				//{add("_,_,_,1"); }
				
			
		};
		boards.add(board.toArray(new String[0]));
		return boards;
		
	}

}
