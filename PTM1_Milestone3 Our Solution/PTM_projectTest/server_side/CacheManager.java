// Tomer Erusalimsky 208667162 & Ron Avraham 206910663
package server_side;

public interface CacheManager<Problem, Solution> {

//	public void fetch();

	public void save(Problem key, Solution value);

//	public void put(String key, String value);

	public String get(Problem key);
}
