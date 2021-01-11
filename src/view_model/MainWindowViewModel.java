package view_model;

import java.util.Observable;
import java.util.Observer;

import interpreter.Interpreter;

public class MainWindowViewModel extends Observable implements Observer {
	
	// Data Members
	private Interpreter interpreter;
	
	// Constructors
	public MainWindowViewModel() {
		
	}
	
	// Methods
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
