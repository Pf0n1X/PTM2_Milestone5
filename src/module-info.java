module PTM2_MS5 {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	requires javafx.media;
	requires javafx.swing;
	requires javafx.web;
	requires javafx.swt;
	requires java.desktop;
	requires interpreter;
	
	opens view to javafx.graphics, javafx.fxml;
}
