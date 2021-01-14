// Tomer Erusalimsky 208667162 & Ron Avraham 206910663
package server_side;

import java.io.InputStream;
import java.io.OutputStream;

public interface ClientHandler {
	
	public void handleClient(InputStream i_stream, OutputStream o_stream);
}