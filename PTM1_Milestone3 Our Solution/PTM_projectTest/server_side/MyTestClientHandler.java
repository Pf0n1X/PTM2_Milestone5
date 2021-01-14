// Tomer Erusalimsky 208667162 & Ron Avraham 206910663
package server_side;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MyTestClientHandler implements ClientHandler {

	// Data Members
	private Solver<MazeProblem, List<String>> solver;
	private CacheManager<String, String> cacheManager;

	// Constructors
	public MyTestClientHandler(Solver<MazeProblem, List<String>> solver, CacheManager<String, String> cacheManager) {
		this.solver = solver;
		this.cacheManager = cacheManager;
	}

	// Methods
	@Override
	public void handleClient(InputStream inputStream, OutputStream outputStream) {

		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
		PrintWriter out = new PrintWriter(outputStream);

		String problem;
		int[][] maze = null;
		int index = 0;
		MazeProblem mazeProblem = new MazeProblem();

		try {
			while ((problem = in.readLine()) != null) {

				if (maze == null) {
					String[] dimensions = problem.split(",");
					maze = new int[dimensions[0]][dimensions[1]];
					continue;
				}

				if (problem.equals("end")) {
					problem = in.readLine();
					String[] start = problem.split(",");
					mazeProblem.setStartRow(Integer.parseInt(start[0]));
					mazeProblem.setStartColumn(Integer.parseInt(start[1]));

					problem = in.readLine();
					String[] finish = problem.split(",");
					mazeProblem.setFinishRow(Integer.parseInt(finish[0]));
					mazeProblem.setFinishCol(Integer.parseInt(finish[1]));
					break;
				}

				String[] row = problem.split(",");
				int[] array = Arrays.asList(row).stream().mapToInt(Integer::parseInt).toArray();

				maze[index] = array;
				index++;
			}

			mazeProblem.setMaze(maze);
			String solutionFromCache = cacheManager.get(String.valueOf(mazeProblem.getMaze().hashCode()));

			if (solutionFromCache == null) {
				List<String> solution = solver.solve(mazeProblem);
				String solString = solution.stream().collect(Collectors.joining(","));
				cacheManager.save(String.valueOf(mazeProblem.getMaze().hashCode()), solString);
				out.println(solString);
			} else {
				out.println(solutionFromCache);
			}
			out.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
