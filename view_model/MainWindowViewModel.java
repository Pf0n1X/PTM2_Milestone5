package view_model;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import commands.ConnectCommand;
import interpreter.Interpreter;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import view.MainWindowController;
import view.Map;
import math_expressions.SimulatorSymbolVariable;
import model.MapModel;

public class MainWindowViewModel extends Observable implements Observer {

	// Data Members
	private double[][] map;
	public DoubleProperty srcX;
	public DoubleProperty srcY;
	private DoubleProperty destX;
	private DoubleProperty destY;
	private DoubleProperty zeroLocationX;
	private DoubleProperty zeroLocationY;
	private StringProperty pathSolverIP;
	private StringProperty pathSolverPort;
	private Interpreter interpreter;
	public StringProperty clientIp;
	public StringProperty clientPort;
	public StringProperty autoPilotText;
	public DoubleProperty throttle, rudder, alieron, elevator, flaps;
	public DoubleProperty SimKMPerBlock;
	private volatile boolean isConnected = false;
	private HashMap<String, StringProperty> stringProperties;
	public HashMap<String, StringProperty> getStringProperties() {
		return stringProperties;
	}

	public void setStringProperties(HashMap<String, StringProperty> stringProperties) {
		this.stringProperties = stringProperties;
	}

	public HashMap<String, DoubleProperty> getDoubleProperties() {
		return doubleProperties;
	}

	public void setDoubleProperties(HashMap<String, DoubleProperty> doubleProperties) {
		this.doubleProperties = doubleProperties;
	}

	private HashMap<String, DoubleProperty> doubleProperties;
	private MainWindowController mainWindowController;
	private Map mapView;
	private MapModel mapModel;
	
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
		this.pathSolverIP = new SimpleStringProperty();
		this.pathSolverPort = new SimpleStringProperty();
		this.srcX = new SimpleDoubleProperty();
		this.srcY = new SimpleDoubleProperty();
		this.destX = new SimpleDoubleProperty();
		this.destY = new SimpleDoubleProperty();
		this.SimKMPerBlock = new SimpleDoubleProperty();
		this.zeroLocationX = new SimpleDoubleProperty();
		this.zeroLocationY = new SimpleDoubleProperty();
		
		this.stringProperties= new HashMap<String, StringProperty>();
		this.doubleProperties= new HashMap<String, DoubleProperty>();
		
		this.stringProperties.put(AIRSPEED, new SimpleStringProperty());
		this.stringProperties.put(ALT, new SimpleStringProperty());
		this.doubleProperties.put(LATITUDE, new SimpleDoubleProperty());
		this.doubleProperties.put(LONGITUDE, new SimpleDoubleProperty());
		this.mapModel = new MapModel(this);
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
	
	public void putSymbolsInSymbolTable () {
		
		this.interpreter.getSimulatorSymbolTable().put("/controls/flight/speedbrake", new SimulatorSymbolVariable("/controls/flight/speedbrake", 0.0));
		this.interpreter.getSimulatorSymbolTable().put("/controls/engines/current-engine/throttle", new SimulatorSymbolVariable("/controls/engines/current-engine/throttle", 0.0));
		this.interpreter.getSimulatorSymbolTable().put("/instrumentation/heading-indicator/offset-deg", new SimulatorSymbolVariable("/instrumentation/heading-indicator/offset-deg", 0.0));
		this.interpreter.getSimulatorSymbolTable().put("/instrumentation/airspeed-indicator/indicated-speed-kt", new SimulatorSymbolVariable("/instrumentation/airspeed-indicator/indicated-speed-kt", 0.0));
		this.interpreter.getSimulatorSymbolTable().put("/instrumentation/attitude-indicator/indicated-roll-deg", new SimulatorSymbolVariable("/instrumentation/attitude-indicator/indicated-roll-deg", 0.0));
		this.interpreter.getSimulatorSymbolTable().put("/instrumentation/attitude-indicator/internal-pitch-deg", new SimulatorSymbolVariable("/instrumentation/attitude-indicator/internal-pitch-deg", 0.0));
		this.interpreter.getSimulatorSymbolTable().put("/controls/flight/rudder", new SimulatorSymbolVariable("/controls/flight/rudder", 0.0));
		this.interpreter.getSimulatorSymbolTable().put("/controls/flight/aileron", new SimulatorSymbolVariable("/controls/flight/aileron", 0.0));
		this.interpreter.getSimulatorSymbolTable().put("/controls/flight/elevator", new SimulatorSymbolVariable("/controls/flight/elevator", 0.0));
		this.interpreter.getSimulatorSymbolTable().put("/instrumentation/altimeter/indicated-altitude-ft", new SimulatorSymbolVariable("/instrumentation/altimeter/indicated-altitude-ft", 0.0));
		this.interpreter.getSimulatorSymbolTable().put("simX", new SimulatorSymbolVariable("simX", 0.0));
		this.interpreter.getSimulatorSymbolTable().put("simY", new SimulatorSymbolVariable("simY", 0.0));
		this.interpreter.getSimulatorSymbolTable().put("simZ", new SimulatorSymbolVariable("simZ", 0.0));
	}
	
	public void changeRadioButtonExec(String action) {
		this.interpreter.reset();
			putSymbolsInSymbolTable();
	}

	public void startTimerVals() {
		
	}
	
	public Double getSimKMPerBlock() {
		return SimKMPerBlock.get();
	}

	public void setSimKMPerBlock(double simKMPerBlock) {
		SimKMPerBlock.set(simKMPerBlock);
		this.mapModel.setSimKMPerBlock(simKMPerBlock);
	}

	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
		this.startTimerVals();
		this.mapModel.runLocationRetrieval();
//		Double lat = ConnectCommand.get("get /position/latitude-deg");
//		System.out.println(lat + "");
	}
	
	public void calculatePath() {
		this.mapModel.calculateShortestPath();
		int[][] path = this.mapModel.getPlanePath();
		this.mapView.setPath(path);
//		this.mapView.setPath(path);
//		this.mapView.paintAll();
	}
	
	// Getters & Setters
	public DoubleProperty getDoubleProperty(String key) {
		return this.doubleProperties.get(key);
	}
	
	public void setMap(double[][] map) {
		this.map = map;
		this.mapModel.setHeightMap(map);
	}
	
	public double[][] getMap() {
		return this.map;
	}

	public MainWindowController getMainWindowController() {
		return mainWindowController;
	}

	public void setMainWindowController(MainWindowController mainWindowController) {
		this.mainWindowController = mainWindowController;
	}

	public Map getMapView() {
		return mapView;
	}

	public void setMapView(Map mapView) {
		this.mapView = mapView;
	}
	
	public double getSrcX() {
		return this.srcX.get();
	}
	
	public double getSrcY() {
		return this.srcY.get();
	}
	
	public double getDestX() {
		return this.destX.get();
	}
	
	public double getDestY() {
		return this.destY.get();
	}
	
	public void setDestX(double destX) {
		this.destX.set(destX);
		this.mapModel.setDest(new Point((int) destX, (int) this.destY.get()));
	}
	
	public void setDestY(double destY) {
		this.destY.set(destY);
		this.mapModel.setDest(new Point((int)this.destX.get(), (int)destY));
	}
	
	public void setSrcX(double srcX) {
		this.srcX.set(srcX);
		this.mapModel.setSrc(new Point((int) srcX, (int) this.srcY.get()));
		this.mapView.setSrcX(srcX);;
	}
	
	public void setSrcY(double srcY) {
		this.srcY.set(srcY);
		this.mapModel.setSrc(new Point((int)this.srcX.get(), (int)srcY));
		this.mapView.setSrcY(srcY);
	}

	public String getPathSolverIP() {
		return pathSolverIP.get();
	}

	public void setPathSolverIP(String pathSolverIP) {
		this.pathSolverIP.set(pathSolverIP);
		this.mapModel.setServerIP(pathSolverIP);
	}

	public String getPathSolverPort() {
		return pathSolverPort.get();
	}

	public void setPathSolverPort(String pathSolverPort) {
		this.pathSolverPort.set(pathSolverPort);
		this.mapModel.setServerPort(pathSolverPort);
	}

	public void setZeroLocationX(double zeroLocationX) {
		this.zeroLocationX.set(zeroLocationX);
		this.mapModel.setZeroLocation(new Point((int) zeroLocationX, (int) this.zeroLocationY.get()));
	}
	
	public void setZeroLocationY(double zeroLocationY) {
		this.zeroLocationY.set(zeroLocationY);
		this.mapModel.setZeroLocation(new Point((int)this.zeroLocationX.get(), (int)zeroLocationY));
	}

	public void notifySourceChanged() {
		this.mapView.setSrcX(getSrcX());
		this.mapView.setSrcY(getSrcY());
		this.mapView.paintAll();
	}
	
	public void resetInterpreter() {
		this.interpreter.reset();
	}
}