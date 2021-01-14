package view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.io.InputStreamReader;
import server_side.BestFirstSearch;
import server_side.ClientHandler;
import server_side.FileCacheManager;
import server_side.MySerialServer;
import server_side.MyTestClientHandler;
import server_side.SearcherSolverAdapter;
import server_side.Server;
import view_model.MainWindowViewModel;

public class CalculatePathWindowController {
	
	// Data Members
	@FXML
	TextField txtIP;
	
	@FXML
	TextField txtPort;
	
	private MainWindowController mainController;
	private MainWindowViewModel viewModel;
	
	// Constructors
	public CalculatePathWindowController() {
	
	}
	
	// Methods
	public void onConnectButtonPressed() {
		this.viewModel.calculatePath();
		
//		int port = Integer.parseInt(txtPort.getText());
		
//		ClientHandler clientHandler = new MyTestClientHandler(new SearcherSolverAdapter(new BestFirstSearch()),
//				new FileCacheManager());
//		Server server = new MySerialServer();
//		server.open(port, clientHandler);
		
//		try {
//			Socket theServer = new Socket("127.0.0.1", 1111);
//			PrintWriter writer = new PrintWriter(theServer.getOutputStream());
//			BufferedReader reader = new BufferedReader(new InputStreamReader(theServer.getInputStream()));
//			String[] mat = {"1,2", "1,2"};
//			
////			writer.println("2,2");
//			
//			// Print the maze
//			for (String line : mat) {
//				writer.println(line);
//			}
//			
//			writer.println("end");
//			
//			// Starting point in maze.
//			writer.println("0,0");
//			
//			// Finish point in maze.
//			writer.println("1,1");
//			
//			writer.flush();
//			String responseLine;
//			while((responseLine = reader.readLine()) != "end") {
//				System.out.println("The response is");
//				System.out.println(responseLine);
//			}
//			System.out.println("The response is:" + responseLine);
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		Thread t = new Thread();
//		try {
//			t.sleep(100);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}
	
	// Getters & Setters
	public MainWindowController getMainController() {
		return mainController;
	}

	public void setMainController(MainWindowController mainController) {
		this.mainController = mainController;
		this.viewModel = mainController.ViewModel;
	}
	
}
