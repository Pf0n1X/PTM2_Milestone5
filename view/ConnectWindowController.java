package view;

import java.util.Observable;
import java.util.Observer;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import math_expressions.SimulatorSymbolVariable;
import view_model.MainWindowViewModel;

public class ConnectWindowController implements Observer  {
	@FXML
	Button btnConnect;
	@FXML
	TextField txtIP, txtPort;
	@FXML
	Label lblStatus;
	// Data Members
	private String ip = null;
	private int port = 0;
	private MainWindowController mainWindow = null;
	private MainWindowViewModel viewModel = null;
	private final Pattern p = Pattern
			.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

	// Constructors
	public ConnectWindowController() {
		
	}
	public void setViewModel(MainWindowViewModel viewModel) {
		this.viewModel = viewModel;
	}
	// Methods
	public void onConnectButtonPressed() {
		if (ipValidityCheck(this.txtIP.getText())) {
			this.ip = txtIP.getText();
			try {
				this.port = Integer.parseInt(this.txtPort.getText());
			} catch (Exception e) {
				this.lblStatus.setText("Please enter a valid port");
				
				return;
			}
			if(connect(this.ip, this.port)) {
				this.viewModel.setConnected(true);
				this.mainWindow.closeConnectWindow();	
			}
			else lblStatus.setText("Connect Error");
		} else {
			lblStatus.setText("Please enter IP and port");
		}
		
	}

	
	private boolean connect(String ip, int telnetPort) {
		this.ip = ip;
		this.port = telnetPort;
		if(this.viewModel != null) {
			this.viewModel.resetInterpreter();
			
//		    Set the rate to 4 and the port to * TODO *
			this.viewModel.sendCommandToInterpreter("openDataServer " + 5400 + " " + 10);
			this.viewModel.sendCommandToInterpreter("connect "+ ip + " " + telnetPort);
			this.viewModel.putSymbolsInSymbolTable();
			
//			Set the ip and port as the user has entered

			this.viewModel.sendCommandToInterpreter("var throttle = bind \"/controls/engines/current-engine/throttle\"");
			this.viewModel.sendCommandToInterpreter("var rudder = bind \"/controls/flight/rudder\"");
			this.viewModel.sendCommandToInterpreter("var aileron = bind \"/controls/flight/aileron\"");
			this.viewModel.sendCommandToInterpreter("var elevator = bind \"/controls/flight/elevator\"");
			this.viewModel.sendCommandToInterpreter("var airspeed = bind \"/instrumentation/airspeed-indicator/indicated-speed-kt\"");
			this.viewModel.sendCommandToInterpreter("var alt = bind \"/instrumentation/altimeter/indicated-altitude-ft\"");
			this.viewModel.sendCommandToInterpreter("var pitch = bind \"/instrumentation/attitude-indicator/internal-pitch-deg\"");
			this.viewModel.sendCommandToInterpreter("var roll = bind \"/instrumentation/attitude-indicator/indicated-roll-deg\"");
			this.viewModel.sendCommandToInterpreter("var offset = bind \"/instrumentation/heading-indicator/offset-deg\"");

			return true;
		}
		return false;
	}

	
	private boolean ipValidityCheck(String ip) {
		return this.p.matcher(ip).matches();
	}

	public void setMainWindow(MainWindowController mainWindow) {
		this.mainWindow = mainWindow;
	}

	public String getIp() {
		return ip;
	}

	public void setDefualts() {
		this.txtIP.setText("127.0.0.1");
		this.txtPort.setText("5402");
	}
	public void clearTextandValues() {
		this.txtIP.clear();
		this.txtPort.clear();
		this.ip = null;
		this.setPort(0);
	}	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
}
