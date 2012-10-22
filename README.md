Sudoku explained:   

Sudoku is a number placement puzzle. In this puzzle you are given an N x N grid
of cells. The grid itself is composed of M x K sub-grids. You can place a single
digit, drawn from 1 to N, in any cell. Initially the grid will have some of its cells
partially filled. The objective of the puzzle is to complete the grid so that:
1. Every cell contains a digit.
2. No digit appears twice in any row, column of the N x N grid or in any row,
column of any of the M x K sub-grid.

Sudoku as a Constraint Satisfaction Problem:    

Each cell on the N x N grid can be thought of as a variable. Constraint on each
variable is that no two variables in the same row, column or M x k sub-grid can
have the same number([1-N]) assigned to it. In this problem each constraint is a
binary constraint as only two variables are involved in each constraint.
We have solved Sudoku by modeling it as a Constraint Satisfaction Problem by
using the four methods which are explained in detail in the pdf file provided.

Running the code

Assign one of (DFS, MRV, FWDCK, MRVCP) as required to TestSudoku.METHOD  code and call the
java code in the following manner
java ai.TestSudoku test_case_file
where test_case_file has entry like
6 2 3
_,_,6,_,5,_
_,1,_,2,_,_
_,_,1,_,_,_
_,_,_,3,_,_
_,_,4,_,1,_
_,2,_,4,_,_
000

