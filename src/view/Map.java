package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Map extends Canvas {
	
	// Data Members
	private double[][] matrix = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
	private double maxVal;

	// Constructors
	public Map() {
		this.maxVal = 0;
	}
	
	// Methods
	public void paintMap(File csv) {
		
		getArrayFromFile(csv);
		
		GraphicsContext graphicsContext = getGraphicsContext2D();
		Color color;
		
		// Get the map control's width.
		double ctrlWidth = getWidth();
		
		// Get the map contro's height.
		double ctrlHeight = getHeight();
		
		// Clear the block.
		graphicsContext.clearRect(0, 0, ctrlWidth, ctrlHeight);
		
		// Calculate a square's height
		double sqrHeight = ctrlHeight / this.matrix.length;
				
		// Calculate a square's width
		double sqrWidth = ctrlWidth / this.matrix[0].length;
		
		// Calculate the max value of the matrix.
		calculateMaxValue();
		
		for (int i = 0; i < matrix.length; i ++) {
			for (int j = 0; j < matrix[i].length; j ++) {
				
				// Calculate the color.
				color = calculateColor(matrix[i][j], this.maxVal);
				
				// Set the color
				graphicsContext.setFill(color);
				graphicsContext.fillRect(j * sqrWidth, i * sqrHeight, sqrWidth, sqrHeight);
				
				// Type the text.
				graphicsContext.setFont(new Font("TimesRoman", sqrHeight / 5));
				graphicsContext.setFill(Color.BLACK);
				graphicsContext.fillText(matrix[i][j] + "", (j + 0.5) * sqrWidth, (i + 0.5) * sqrHeight);
			}
		}
	}
	
	private Color calculateColor(double val, double max) {
		int red, green, blue;
		double percent = (val / max) * 100;
		
		if (val == max) {
			red = 255;
			green = 0;
			blue = 0;
		} else if (percent < 50) {
			red = (int)(255 * (percent / 50));
			green = 255;
			blue = 0;
		} else {
			red = 255;
			green = (int)(255 * ((50 - percent % 50) / 50));
			blue = 0;
		}
		
		return Color.rgb(red, green, blue);
	}
	
	private void calculateMaxValue( ) {
		
		// Go through the whole matrix and find the max value.
		for (int i = 0; i < matrix.length; i ++) {
			for (int j = 0; j < matrix[i].length; j ++) {
				if (matrix[i][j] > this.maxVal) {
					this.maxVal = matrix[i][j];
				}
			}
		}
	}
	
	// Getters & Setters
	public double[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(double[][] matrix) {
		this.matrix = matrix;
	}

	public double getMaxVal() {
		return maxVal;
	}
	
	public void getArrayFromFile(File file) {
		try {
			Scanner scanner = new Scanner(file);
			scanner.useDelimiter("\r\n");
			
			scanner.next();
			scanner.next();
			LinkedList<String> lines = new LinkedList<String>();
			
			while(scanner.hasNext()) {
				lines.add(scanner.next());
			}
			
			scanner.close();
			this.matrix = new double[lines.size()][lines.get(0).length()];
			for (int i = 0; i < lines.size(); i ++) {
				String line = lines.get(i);
				double[] lineCells = Arrays.asList(line.split(",")).stream().mapToDouble(cell -> Double.parseDouble(cell)).toArray();
				this.matrix[i] = lineCells;
				System.out.println(lineCells.toString());
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
