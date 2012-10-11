package ai;

import java.util.ArrayList;
import java.util.List;

public class TestSudoku {

	public static void main(String[] args) {
		
		String[] gridStr = getGrid();
		SudokuGrid grid = SudokuGrid.getGrid(gridStr, 3, 3);
		grid.solve(0, 0);
		System.out.println(grid);

	}

	private static String[] getGrid() {
		List<String> board = new ArrayList<String>() {
			private static final long serialVersionUID = 1L;
			{
				{add("3,_,_,_,_,_,_,_,_"); }
				{add("_,_,_,_,_,_,_,_,_"); }
				{add("_,_,_,_,_,_,_,_,_"); }
				{add("_,_,_,_,_,_,_,_,_"); }
				{add("_,_,_,_,_,_,_,_,_"); }
				{add("_,_,_,_,_,_,_,_,_"); }
				{add("_,_,_,_,_,_,_,_,_"); }
				{add("_,_,_,_,_,_,_,_,_"); }
				{add("_,_,_,_,_,_,_,_,_"); }
			}
		};
		return board.toArray(new String[0]);
	}

}
