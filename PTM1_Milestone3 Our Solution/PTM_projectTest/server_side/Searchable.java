// Tomer Erusalimsky 208667162 & Ron Avraham 206910663
package server_side;

import java.util.List;

public interface Searchable {
	public List<String> search(MazeProblem problem);
}