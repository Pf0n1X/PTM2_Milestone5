package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import view_model.MainWindowViewModel;

public class MainWindowController implements Observer {
	
	@FXML
	Button btnCalc, btnConnect;
	@FXML
	RadioButton radioBtnManual, radioBtnAutopilot;
	@FXML
	Label statlabel, airspeed, altitude;
	@FXML
	Slider sRudder, sThrottle;
	@FXML
	TextArea txtCommands;
	@FXML
	Circle circleJoystick, circleJoystickBorder;

	// Data Members
	@FXML
	Map map;
	
	private File txt;
	private File csv;
	MainWindowViewModel ViewModel;
	DoubleProperty alieronVal, elevatorVal, flapsval;
	Stage connectWindow;
	
	String MCL = "Manual Controls locked! - To manualy control the aircraft you need to press the Manual Controls button first";
	String APL = "Cannot Execute! = To use the AutoPilot option you need to press the AutoPilot mode Button first";
	String na = "N/A";
	// Constructors
	public MainWindowController() {
		// TODO Auto-generated constructor stub
		this.map = new Map();
//		this.map.paintMap();
	}
	
	// Methods
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	
	public void onMapClicked(MouseEvent event) {
		this.map.setDestination(event.getX(), event.getY());
	}
	
	public void onConnectButtonPressed() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ConnectWindow.fxml"));
			AnchorPane window = (AnchorPane) fxmlLoader.load();
			ConnectWindowController controller = fxmlLoader.getController();
			controller.setViewModel(this.ViewModel);
			Scene scene = new Scene(window);
			this.connectWindow = new Stage();
			connectWindow.setScene(scene);
			connectWindow.show();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void closeConnectWindow() {
		connectWindow.close();
	}
	
	public void setViewModel(MainWindowViewModel ViewModel) {
		this.ViewModel = ViewModel;
		this.alieronVal = new SimpleDoubleProperty();
		this.elevatorVal = new SimpleDoubleProperty();
		this.flapsval = new SimpleDoubleProperty();
		this.ViewModel.throttle.bind(this.sThrottle.valueProperty());
		this.ViewModel.rudder.bind(this.sRudder.valueProperty());
		this.ViewModel.alieron.bind(this.alieronVal);
		this.ViewModel.elevator.bind(this.elevatorVal);
		this.ViewModel.flaps.bind(this.flapsval);
		this.ViewModel.addObserver(this);
		
	}
	
	public void loadData() {
		this.csv = fileLoader();
		if (this.csv != null)
			this.map.paintMap(this.csv);
		
	}
	
	public void calculate() {
		
	}
	
	public void loadTextFile() throws IOException {
		this.txt = this.fileLoader();
		if (this.txt != null) {
			BufferedReader br = new BufferedReader(new FileReader(this.txt));
			StringBuilder sb = new StringBuilder();
			String st;
			while ((st = br.readLine()) != null) {
				sb.append(st + "\n");
			}
			this.txtCommands.setText(sb.toString());
			br.close();
			this.statlabel.setText("File Loaded Successfuly");
		} else
			this.statlabel.setText("Error with text file, Try Again");
	}

	public File fileLoader() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Select Text File:");
		fc.setInitialDirectory(new File("./resources/"));
		File chosen = fc.showOpenDialog(null);
		if (chosen == null)
			return null;
		return chosen;
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
	public void resetThrottle() {
		if(this.radioBtnManual.isSelected()) {
			this.sThrottle.setValue(0.0);
			this.ViewModel.throttleChanged();
			this.statlabel.setText("Throttle has been set back to 0");
		} else this.statlabel.setText(MCL);
	}
	public void resetRudder() {
		if(this.radioBtnManual.isSelected()) {
			this.sRudder.setValue(0.0);
			this.ViewModel.rudderChanged();
			this.statlabel.setText("Rudder has been set back to 0");
		} else this.statlabel.setText(MCL);
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
