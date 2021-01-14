package server_side;

import java.io.*;
import java.util.HashMap;

public class FileCacheManager implements CacheManager<String, String> {

	private HashMap<String, String> allSolutions;

	public FileCacheManager() {
		if (this.allSolutions == null)
			this.allSolutions = new HashMap<String, String>();
	}

	@Override
	public String get(String problem) {

		String solutionFile = this.allSolutions.get(problem);

		if (solutionFile != null) {
			try {
				File file = new File(solutionFile);

				BufferedReader in = new BufferedReader(new FileReader(file));
				StringBuilder builder = new StringBuilder();

				String line;

				while ((line = in.readLine()) != null)
					builder.append(line);

				in.close();
				return builder.toString();
			} catch (Exception e) {
				return null;
			}

		}
		return null;
	}

	@Override
	public void save(String problem, String solution) {

		this.allSolutions.put(problem, solution);

		try {
			FileWriter output = new FileWriter(problem, true);
			BufferedWriter out = new BufferedWriter(output);
			out.write(solution);
			out.close();
		} catch (Exception e) {
			return;
		}
	}

}
