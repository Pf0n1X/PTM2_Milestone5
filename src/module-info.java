module PTM2_MS5 {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires interpreter;
	requires javafx.base;
	requires PTM.projectTest;
	requires java.desktop;
	
	opens view to javafx.graphics, javafx.fxml;
}
