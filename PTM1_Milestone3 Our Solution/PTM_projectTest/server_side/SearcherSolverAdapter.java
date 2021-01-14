// Tomer Erusalimsky 208667162 & Ron Avraham 206910663
package server_side;

import java.util.List;

public class SearcherSolverAdapter implements Solver<MazeProblem, List<String>> {

	// Data Members
	private Searchable problem;

	// Methods
	public SearcherSolverAdapter(Searchable problem) {
		this.problem = problem;
	}

	@Override
	public List<String> solve(MazeProblem problem) {
		return this.problem.search(problem);
	}

}
