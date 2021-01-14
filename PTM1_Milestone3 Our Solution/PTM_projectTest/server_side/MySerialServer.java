// Tomer Erusalimsky 208667162 & Ron Avraham 206910663
package server_side;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class MySerialServer implements Server {

	// Data Members
	private volatile boolean stop = false;

	// Methods
	public void open(int port, ClientHandler c) {
		new Thread(() -> {
			try {
				runServer(port, c);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
	}

	public void stop() {
		stop = true;
	}

	private void runServer(int port, ClientHandler c) throws Exception {
		ServerSocket server = new ServerSocket(port);
		server.setSoTimeout(1000);

		while (!stop) {

			try {
				Socket client = server.accept();

				try {
					c.handleClient(client.getInputStream(), client.getOutputStream());
					// client.getInputStream().close();
					// client.getOutputStream().close();
					// client.close();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}
			} catch (SocketTimeoutException te) {
				te.printStackTrace();
			}
		}
		server.close();
	}
}