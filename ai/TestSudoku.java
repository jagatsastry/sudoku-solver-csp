package ai;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class TestSudoku {

	public static enum Method {
		DFS, MRV, FWDCK, MRVCP;
	}
	
	static final Method METHOD = Method.MRV;
	private static final long MEGABYTE = 1024L * 1024L;

	public static void main(String[] args) throws IOException {
		String filename = null;
		if(args.length > 0) 
			filename = args[0];
		Map<String[], Integer[]> gridStrs = getGrids(filename);
		for(Map.Entry<String[], Integer[]> gridEntry: gridStrs.entrySet()) {
			long st = System.nanoTime();
			String[] gridStr = gridEntry.getKey();
			int m = gridEntry.getValue()[0];
			int k = gridEntry.getValue()[1];
			SudokuGrid grid = null; 
			switch(METHOD) {
				case MRV:
					grid = SudokuGridMRV.getGrid(gridStr, m, k);
					break;
				case DFS:
					grid = SudokuGridDFS.getGrid(gridStr, m, k);
					break;
				case FWDCK:
					grid = SudokuGridFwdChecking.getGrid(gridStr, m, k);
					break;
				case MRVCP:
					grid = SudokuGridMRVCP.getGrid(gridStr, m, k);
					break;
			}
			System.out.println("Grid before: ");
			System.out.println(grid);
			boolean solved = grid.solve();
			System.out.println(grid);
			System.out.println("Method: " + METHOD);
			System.out.println("Solved? " + solved);
			System.out.println("Nodes expanded: " + grid.numNodesExpanded());
			System.out.printf("Time taken: %.2fms%n", (System.nanoTime() - st)/1000000.0);
			Runtime rt = Runtime.getRuntime();
			System.out.printf("Memory used: %.2f MB%n", (rt.totalMemory() - rt.freeMemory())/(float)MEGABYTE);
		}
	}

	private static Map<String[], Integer[]> getGrids(String filename) throws IOException {
		
		int n, m, k;
		BufferedReader buff = null;
		if(filename == null) buff = new BufferedReader(new InputStreamReader(System.in));
		else buff = new BufferedReader(new FileReader(filename));
		Map<String[], Integer[]> boards = new HashMap<String[], Integer[]>();
		
		while(true) {
			String[] nmk = buff.readLine().split("\\s+");
			n = Integer.parseInt(nmk[0]);
			m = Integer.parseInt(nmk[1]);
			k = Integer.parseInt(nmk[2]);
			if(n == 0 || m == 0 || k == 0) break;
			
			String[] board = new String[n];
			for(int i = 0; i < n; i++) 
				board[i] = buff.readLine();
			boards.put(board, new Integer[]{m, k});
		}
//		List<String> board = new ArrayList<String>() {
//			private static final long serialVersionUID = 1L;
//			{
//				
//			add("_,_,6,_,5,_");}
//			{add("_,1,_,2,_,_");}
//			{add("_,_,1,_,_,_");}
//			{add("_,_,_,3,_,_");}
//			{add("_,_,4,_,1,_");}
//			{add("_,2,_,4,_,_");}
//			 //add("_,_,_,9,7,_,_,_,2"); }
//			//{add("_,_,_,3,_,_,_,6,_"); }
//			//{add("1,5,_,_,4,_,_,_,7"); }
//			//{add("_,8,_,_,_,_,_,_,6"); }
//			//{add("9,_,6,_,_,_,2,_,5"); }
//			//{add("3,_,_,_,_,_,_,7,_"); }
//			//{add("8,_,_,_,2,_,_,1,4"); }
//			//{add("_,4,_,_,_,6,_,_,_"); }
//			//{add("2,_,_,_,5,9,_,_,_"); }
//				//{add("3,_,_,4"); }
//				//{add("_,_,_,_"); }
//				//{add("_,2,_,_"); }
//				//{add("_,_,_,1"); }
//				
//			
//		};
//		boards.add(board.toArray(new String[0]));
//		
//		board = new ArrayList<String>() {
//			private static final long serialVersionUID = 1L;
//			{
//			 add("_,_,3,_,_,_,_,_,2"); }
//			{add("_,6,_,_,_,2,7,_,3"); }
//			{add("2,_,_,_,_,_,_,1,_"); }
//			{add("_,_,_,4,_,_,1,5,8"); }
//			{add("_,_,_,8,_,3,_,_,_"); }
//			{add("8,1,9,_,_,6,_,_,_"); }
//			{add("_,7,_,_,2,_,_,_,1"); }
//			{add("4,_,8,5,_,_,_,6,_"); }
//			{add("9,_,_,_,_,_,2,_,_"); }
//				//{add("3,_,_,4"); }
//				//{add("_,_,_,_"); }
//				//{add("_,2,_,_"); }
//				//{add("_,_,_,1"); }
//				
//			
//		};
//		boards.add(board.toArray(new String[0]));
		return boards;
		
	}

}
