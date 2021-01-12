package view;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainWindowController implements Observer {
	@FXML
	RadioButton radioBtnManual, radioBtnAutopilot;
	@FXML
	Label statlabel, airspeed, altitude;
	
	String na = "N/A";
	// Constructors
	public MainWindowController() {
		// TODO Auto-generated constructor stub
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
		
	}
	
	public void calculate() {
		
	}
	
	public void loadTextFile() {
		
	}
	
	public void clearAttributesLabels() {
		this.airspeed.textProperty().unbind();
		this.altitude.textProperty().unbind();
		this.airspeed.setText(na);
		this.altitude.setText(na);
	}
	
	public void toggleManualPilot(){
		if(this.radioBtnAutopilot.isSelected()) {
			this.radioBtnAutopilot.setSelected(false);
		}
		this.radioBtnManual.setSelected(true);
		this.statlabel.setText("Manual Controls Are ON");
	}
	
	public void toggleAutoPilot() {
		if(this.radioBtnManual.isSelected()) {
			this.radioBtnManual.setSelected(false);	
			this.clearAttributesLabels();

		}
		this.radioBtnAutopilot.setSelected(true);
		this.statlabel.setText("AutoPilot mode is ON");
	}
	
	public void runCodeCommands() {
		
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
