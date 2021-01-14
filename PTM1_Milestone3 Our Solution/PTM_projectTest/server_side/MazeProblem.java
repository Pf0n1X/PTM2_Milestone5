// Tomer Erusalimsky 208667162 & Ron Avraham 206910663
package server_side;

import java.util.Arrays;

public class MazeProblem {

	// Data Members
	private int[][] maze;
	private int startRow;
	private int startColumn;
	private int finishRow;
	private int finishColumn;

	// Constructors
	public MazeProblem(int[][] maze, int startRow, int startColumn, int finishRow, int finishColumn) {
		this.maze = maze;
		this.startRow = startRow;
		this.startColumn = startColumn;
		this.finishRow = finishRow;
		this.finishColumn = finishColumn;
	}

	public MazeProblem() {
	}

	// Methods
	public int[][] getMaze() {
		return maze;
	}

	public void setMaze(int[][] maze) {
		this.maze = maze;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getStartColumn() {
		return startColumn;
	}

	public void setStartColumn(int startColumn) {
		this.startColumn = startColumn;
	}

	public int getFinishRow() {
		return finishRow;
	}

	public void setFinishRow(int finishRow) {
		this.finishRow = finishRow;
	}

	public int getFinishColumn() {
		return finishColumn;
	}

	public void setFinishCol(int finishColumn) {
		this.finishColumn = finishColumn;
	}

	@Override
	public String toString() {
		return "MazeProblem{" + "maze=" + Arrays.toString(maze) + ", startRow=" + startRow + ", startCol=" + startColumn
				+ ", finishRow=" + finishRow + ", finishCol=" + finishColumn + '}';
	}
}
