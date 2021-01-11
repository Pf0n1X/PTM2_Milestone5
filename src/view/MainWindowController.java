package view;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainWindowController implements Observer {
	
	// Data Members
	@FXML
	Map map;
	
	// Constructors
	public MainWindowController() {
		this.map = new Map();
		this.map.paintMap();
	}
	
	// Methods
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	
	public void onConnectButtonPressed() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ConnectWindow.fxml"));
			AnchorPane window = (AnchorPane) fxmlLoader.load();
			ConnectWindowController controller = fxmlLoader.getController();
			Scene scene = new Scene(window);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadData() {
		this.map.paintMap();
	}
	
	public void calculate() {
		
	}
	
	public void loadTextFile() {
		
	}
	
	public void toggleAutoPilot() {
		
	}
	
	public void runCodeCommands() {
		
	}
	
	public void toggleManualPilot() {
		
	}
	
	public void onRudderDrag() {
		
	}
	
	public void onThrottleDrag() {
		
	}
	
	public void onRudderRelease() {
		
	}
	
	public void onThrottleRelease() {
		
	}
	
	public void onJoystickRelease() {
		
	}
	
	public void onJoystickDrag() {
		
	}
}
