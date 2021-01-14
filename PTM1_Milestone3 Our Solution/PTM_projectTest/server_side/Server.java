// Tomer Erusalimsky 208667162 & Ron Avraham 206910663
package server_side;

public interface Server 
{
	public void open(int port, ClientHandler c);
	public void stop();
}
