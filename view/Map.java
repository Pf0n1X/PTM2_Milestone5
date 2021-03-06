package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import view_model.MainWindowViewModel;

public class Map extends Canvas {
	
	// Data Members
	private double[][] matrix;
	private double maxVal;
	private double minVal;
	private MainWindowViewModel viewModel;
	private double destX;
	private double destY;
	private double srcX;
	private double srcY;
	private File csv;
	private double ctrlWidth;
	private double ctrlHeight;
	double sqrHeight;
	double sqrWidth;
	private int[][] path;
	
	// Constant Members
	private static final int MARKER_SIZE = 15;

	// Constructors
	public Map() {
		this.maxVal = 0;
		this.path = new int[0][];
	}
	
	// Methods
	public void paintMap() {
		
		// TODO: Delete this
//		this.viewModel.setSrcX(this.matrix[0].length);
//		this.viewModel.setSrcY(this.matrix.length);
		
		GraphicsContext graphicsContext = getGraphicsContext2D();
		Color color;
		
		// Get the map control's width.
		this.ctrlWidth = getWidth();
		
		// Get the map contro's height.
		this.ctrlHeight = getHeight();
		
//		// Clear the block.
//		graphicsContext.clearRect(0, 0, ctrlWidth, ctrlHeight);
		
		// Calculate a square's height
		this.sqrHeight = ctrlHeight / this.matrix.length;
				
		// Calculate a square's width
		this.sqrWidth = ctrlWidth / this.matrix[0].length;
		
		// Calculate the max value of the matrix.
		calculateMaxValue();
		
		// Calculate the min value of the matrix.
		calculateMinValue();
		
		for (int i = 0; i < matrix.length; i ++) {
			for (int j = 0; j < matrix[i].length; j ++) {
				
				// Calculate the color.
				color = calculateColor(matrix[i][j], this.maxVal, this.minVal);
				
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
	
	public void paintPath() {
		GraphicsContext graphicsContext = getGraphicsContext2D();
		
		// Calculate a square's height
		double sqrHeight = getHeight() / this.matrix.length;
						
		// Calculate a square's width
		double sqrWidth = getWidth() / this.matrix[0].length;
		if (path != null) {
			for (int i = 0; i < path.length; i++) {
				graphicsContext.setFill(Color.WHITE);
				graphicsContext.fillOval(sqrWidth * path[i][0], sqrHeight * path[i][1], 2.5, 2.5);
			}
		}
	}
	
	private Color calculateColor(double val, double max, double min) {
		int red, green, blue;
		double percent = ((val - min) / (max - min)) * 100;
		
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
	
	private void calculateMaxValue() {
		
		// Set the initial value.
		this.maxVal = matrix[0][0];
		
		// Go through the whole matrix and find the max value.
		for (int i = 0; i < matrix.length; i ++) {
			for (int j = 0; j < matrix[i].length; j ++) {
				if (matrix[i][j] > this.maxVal) {
					this.maxVal = matrix[i][j];
				}
			}
		}
	}
	
	private void calculateMinValue() {
		
		// Set the initial value.
		this.minVal = matrix[0][0];
		
		// Go through the whole matrix and find the max value.
				for (int i = 0; i < matrix.length; i ++) {
					for (int j = 0; j < matrix[i].length; j ++) {
						if (matrix[i][j] < this.minVal) {
							this.minVal = matrix[i][j];
						}
					}
				}
	}
	
	// Getters & Setters
	public double[][] getMatrix() {
		return matrix;
	}

	public double getCtrlWidth() {
		return ctrlWidth;
	}
	
	public double getSrcX() {
		return srcX;
	}

	public void setSrcX(double srcX) {
		this.srcX = srcX;
	}

	public double getSrcY() {
		return srcY;
	}

	public void setSrcY(double srcY) {
		this.srcY = srcY;
	}

	public void setCtrlWidth(double ctrlWidth) {
		this.ctrlWidth = ctrlWidth;
	}

	public double getCtrlHeight() {
		return ctrlHeight;
	}

	public void setCtrlHeight(double ctrlHeight) {
		this.ctrlHeight = ctrlHeight;
	}

	public double getSqrHeight() {
		return sqrHeight;
	}

	public void setSqrHeight(double sqrHeight) {
		this.sqrHeight = sqrHeight;
	}

	public double getSqrWidth() {
		return sqrWidth;
	}

	public void setSqrWidth(double sqrWidth) {
		this.sqrWidth = sqrWidth;
	}

	public void setMatrix(double[][] matrix) {
		this.matrix = matrix;
	}

	public double getMaxVal() {
		return maxVal;
	}
	
	public void setViewModel(MainWindowViewModel viewModel) {
		this.viewModel = viewModel;
		viewModel.getDoubleProperty(MainWindowViewModel.LATITUDE).addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				// TODO Auto-generated method stub
				
			}
		});
		viewModel.getDoubleProperty(MainWindowViewModel.LONGITUDE).addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public File getCsv() {
		return this.csv;
	}

	public void setCsv(File csv) {
		this.csv = csv;
		getArrayFromFile(csv);
	}

	// Methods
	public void getArrayFromFile(File file) {
		try {
			Scanner scanner = new Scanner(file);
			scanner.useDelimiter("\r\n");
			
			double[] zeroLocation = Arrays.asList(scanner.next().split(",")).stream().mapToDouble(cell -> Double.parseDouble(cell)).toArray();
			double kmPerBlock = Arrays.asList(scanner.next().split(",")).stream().mapToDouble(cell -> Double.parseDouble(cell)).toArray()[0];
			
//			System.out.println("Map content");
//			System.out.println(src[0] + "," + src[1]);
//			System.out.println(kmPerBlock);
			this.viewModel.setZeroLocationX(zeroLocation[0]);
			this.viewModel.setZeroLocationY(zeroLocation[1]);
			this.viewModel.setSimKMPerBlock(kmPerBlock);
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
//				System.out.println(lineCells.toString());
			}
			
			this.viewModel.setMap(this.matrix);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void setDestination(double x, double y) {
		this.destX = x;
		this.destY = y;
		this.viewModel.setDestX(x / this.sqrWidth);
		this.viewModel.setDestY(y / this.sqrHeight);
		this.setCsv(csv);
		getArrayFromFile(csv);
		paintMap();
		paintDestination();
	}
	
	private void paintDestination() {
//		GraphicsContext graphics = getGraphicsContext2D();
		try {
//			System.out.println("Painting image at " + destY + " and " + destX);
//			graphics.drawImage(new Image(new FileInputStream("./resources/destination.png")), destY, destX);
			GraphicsContext gc = getGraphicsContext2D();
			gc.drawImage(new Image(new FileInputStream("./resources/destination.png")), 
					destX - MARKER_SIZE / 2,
					destY - MARKER_SIZE / 2,
					MARKER_SIZE, 
					MARKER_SIZE);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void paintSource() {
//		GraphicsContext graphics = getGraphicsContext2D();
		try {
//			System.out.println("Painting image at " + destY + " and " + destX);
//			graphics.drawImage(new Image(new FileInputStream("./resources/destination.png")), destY, destX);
			
			System.out.println("painting source");
			System.out.println(this.srcX);
			System.out.println(this.srcY);
			GraphicsContext gc = getGraphicsContext2D();
			System.out.println("TT");
			System.out.println(getWidth());
			System.out.println(sqrWidth  * srcX);
			System.out.println(getHeight());
			System.out.println(sqrHeight * srcY);
			gc.drawImage(new Image(new FileInputStream("./resources/source.png")), 
					sqrWidth * srcX,
					sqrHeight * srcY,
					MARKER_SIZE, 
					MARKER_SIZE);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void paintAll() {
		getGraphicsContext2D().clearRect(0, 0, getWidth(), getHeight());
		System.out.println("Painting map");
		paintMap();
		System.out.println("Painting path");
		paintPath();
		System.out.println("Painting source");
		paintSource();
		System.out.println("Painting destination");
		paintDestination();
	}

	public void setPath(int[][] path) {
		this.path = path;
	}
	
	public int[][] getPath() {
		return this.getPath();
	}
}
