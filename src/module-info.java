module PTM2_MS5 {
	requires javafx.controls;
	requires javafx.fxml;
	
	opens view to javafx.graphics, javafx.fxml;
}
