package view_model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import interpreter.Interpreter;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import view.MainWindowController;
import view.Map;
import math_expressions.SimulatorSymbolVariable;

public class MainWindowViewModel extends Observable implements Observer {

	// Data Members
	private double[][] map;
	private DoubleProperty srcX;
	private DoubleProperty srcY;
	private DoubleProperty destX;
	private DoubleProperty destY;
	private StringProperty pathSolverIP;
	private StringProperty pathSolverPort;
	private Interpreter interpreter;
	public StringProperty clientIp;
	public StringProperty clientPort;
	public StringProperty autoPilotText;
	public DoubleProperty throttle, rudder, alieron, elevator, flaps;
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

	public void startTimerVals() {
		
	}
	
	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
		this.startTimerVals();
	}
	
	public void calculatePath() {
		try {
			Socket theServer = new Socket("127.0.0.1", 1111);
			PrintWriter writer = new PrintWriter(theServer.getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(theServer.getInputStream()));
//			String[] mat = {"1,2", "1,2"};
			
			String[] newMat = new String[map.length];
			
			for (int i = 0; i < newMat.length; i++) {
				newMat[i] = "";
			}
			
			for (int i = 0; i < map.length; i ++) {
				for (int j = 0; j < map[i].length; j++) {
					if (j == map[i].length - 1) {
						newMat[i] = newMat[i].concat((int)map[i][j] + "");
					} else {
						newMat[i] = newMat[i].concat((int)map[i][j] + ",");
					}
				}
			}
			
			writer.println(map.length + "," + map[0].length);
			
			// Print the maze
			for (String line : newMat) {
				writer.println(line);
			}
			
			writer.println("end");
			
			// Starting point in maze.
			
			// TODO: Delete this
			srcY.set(map.length / 2);
			srcX.set(map[0].length / 2);
			destY.set(this.getDestY());
			destX.set(this.getDestX());
			writer.println(srcY.intValue() + "," + srcX.intValue());
			
			// Finish point in maze.
			writer.println(destY.intValue() + "," + destX.intValue());
			
			writer.flush();
			String responseLine;
//			while((responseLine = reader.readLine()) != "end") {
				responseLine = reader.readLine();
				System.out.println("The response is");
				System.out.println(responseLine);
//			}
			
			System.out.println("The response is:" + responseLine);
			int[] srcArr = {srcY.intValue(), srcX.intValue()};
			int[] destArr = {destY.intValue(), destX.intValue()};
			int[][] path = buildPathFromResponse(srcArr, destArr, responseLine);
			this.mapView.paintPath(path);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Thread t = new Thread();
		try {
			t.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public int[][] buildPathFromResponse(int[] src, int[] dest, String response) {
		String[] path = response.split(",");
		int [][] result = new int[path.length + 1][2];
		
		result[0] = src;
		for (int i = 1; i <= path.length; i++) {
			switch (path[i - 1]) {
				case "Up":
					result[i][0] = result[i - 1][0] - 1;
					result[i][1] = result[i - 1][1];
					break;
				case "Down":
					result[i][0] = result[i - 1][0] + 1;
					result[i][1] = result[i - 1][1];
					break;
				case "Left":
					result[i][0] = result[i - 1][0];
					result[i][1] = result[i - 1][1] - 1;
					break;
				case "Right":
					result[i][0] = result[i - 1][0];
					result[i][1] = result[i - 1][1] + 1;
					break;
			}
		}
		
		return result;
	}
	
	// Getters & Setters
	public DoubleProperty getDoubleProperty(String key) {
		return this.doubleProperties.get(key);
	}
	
	public void setMap(double[][] map) {
		this.map = map;
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
	}
	
	public void setDestY(double destY) {
		this.destY.set(destY);
	}
	
	public void setSrcX(double srcX) {
		this.srcX.set(srcX);
	}
	
	public void setSrcY(double srcY) {
		this.srcY.set(srcY);
	}
}