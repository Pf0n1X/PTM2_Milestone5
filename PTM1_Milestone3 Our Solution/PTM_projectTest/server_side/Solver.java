// Tomer Erusalimsky 208667162 & Ron Avraham 206910663
package server_side;

public interface Solver<Problem, Solution> {
	public Solution solve(Problem problem);
}
