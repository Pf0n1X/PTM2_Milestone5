module PTM2_MS5 {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	requires java.desktop;
	requires interpreter;
	
	opens view to javafx.graphics, javafx.fxml;
}
