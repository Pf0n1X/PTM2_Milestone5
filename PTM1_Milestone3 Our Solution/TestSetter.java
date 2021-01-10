// Tomer Erusalimsky 208667162 & Ron Avraham 206910663
package test;
import java.util.List;

import server_side.BestFirstSearch;
import server_side.ClientHandler;
import server_side.FileCacheManager;
import server_side.MySerialServer;
import server_side.MyTestClientHandler;
import server_side.SearcherSolverAdapter;
import server_side.Server;

public class TestSetter {

	static Server server;

	public static void runServer(int port) {
		ClientHandler clientHandler = new MyTestClientHandler(new SearcherSolverAdapter(new BestFirstSearch()),
				new FileCacheManager());
		server = new MySerialServer();
		server.open(port, clientHandler);
	}

	public static void stopServer() {
		server.stop();
	}

}
