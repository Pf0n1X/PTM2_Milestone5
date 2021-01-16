package view;
	
import interpreter.Interpreter;
import javafx.application.Application;
import javafx.stage.Stage;
import view_model.MainWindowViewModel;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader; 

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
//			Implement new interpreter into viewModel class
			Interpreter interpreter = new Interpreter();
			MainWindowViewModel viewModel = new MainWindowViewModel(interpreter);
			
//			Get the fxml controller into out controller and put the view model in the controller
			FXMLLoader fxml = new FXMLLoader();
			BorderPane root = (BorderPane) fxml.load(getClass().getResource("MainWindow.fxml").openStream());
			MainWindowController vc = fxml.getController();
			vc.setViewModel(viewModel);
			
			Scene scene = new Scene(root, 870, 320);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
