package view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
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
		this.viewModel.setPathSolverIP(txtIP.getText());
		this.viewModel.setPathSolverPort(txtPort.getText());
		this.viewModel.calculatePath();
		this.mainController.closeCalculatePathWindow();
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