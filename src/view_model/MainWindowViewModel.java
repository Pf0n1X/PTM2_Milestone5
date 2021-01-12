package view_model;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import interpreter.Interpreter;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MainWindowViewModel extends Observable implements Observer {

	// Data Members
	private Interpreter interpreter;
	public StringProperty clientIp;
	public StringProperty clientPort;
	public StringProperty autoPilotText;
	public DoubleProperty throttle, rudder, alieron, elevator, flaps;
	private volatile boolean isConnected = false;
	private HashMap<String, StringProperty> stringProperties;
	private HashMap<String, DoubleProperty> doubleProperties;
	
	// Constant Members
	public static final String AIRSPEED = "airspeed";
	public static final String ALT = "alt";
	public static final String LATITUDE = "lat";
	public static final String LONGITUDE = "long";
	
	// Constructors
	public MainWindowViewModel(Interpreter interpreter) {
		this.interpreter = interpreter;
		
		this.clientIp = new SimpleStringProperty();
		this.clientPort = new SimpleStringProperty();
		this.autoPilotText = new SimpleStringProperty();
		this.throttle = new SimpleDoubleProperty();
		this.rudder = new SimpleDoubleProperty();
		this.alieron = new SimpleDoubleProperty();
		this.elevator = new SimpleDoubleProperty();
		this.flaps = new SimpleDoubleProperty();
		
		this.stringProperties= new HashMap<String, StringProperty>();
		this.doubleProperties= new HashMap<String, DoubleProperty>();
		
		this.stringProperties.put(AIRSPEED, new SimpleStringProperty());
		this.stringProperties.put(ALT, new SimpleStringProperty());
		this.doubleProperties.put(LATITUDE, new SimpleDoubleProperty());
		this.doubleProperties.put(LONGITUDE, new SimpleDoubleProperty());
	}

	public void getAutoPilotText() {
		String[] lines = this.autoPilotText.getValue().split("\n");
		interpreter.interpret(lines);
	}
	 
	// Methods
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}
	
	public void sendCommandToInterpreter(String s) { //being Used mainly by Connect Command
		String newArray[] = { s };
		interpreter.interpret(newArray);
	}
	
	private String doubleToStringConv(double value) {
		if (Math.abs(value) < 0.0001) {
			return "0.0";
		}
		return Double.toString(value);
	}
	
	private void updateValueChange(String valName, double value) {
		sendCommandToInterpreter(valName + " = " + doubleToStringConv(value));
	}
	
	public void throttleChanged() {
		updateValueChange("throttle", this.throttle.get());

	}
	public void rudderChanged() {
		updateValueChange("rudder", this.rudder.get());
	}
	public void aileronChanged() {
		updateValueChange("aileron", this.alieron.get());
	}
	public void elevatorChanged() {
		updateValueChange("elevator", this.elevator.get());
	}
	public void flapsChanged() {
		updateValueChange("flaps", this.flaps.get());
	}
	
	public boolean isConnected() {
		return isConnected;
	}
	
//	TODO
	public void startTimerVals() {
		
	}
	
	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
		this.startTimerVals();
	}
	
	// Getters & Setters
	public DoubleProperty getDoubleProperty(String key) {
		return this.doubleProperties.get(key);
	}

}
