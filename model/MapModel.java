package model;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import commands.ConnectCommand;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import view_model.MainWindowViewModel;

public class MapModel {
	
	// Data Members
	private int[][] planePath;
	private double[][] heightMap;
	private Point src;
	private Point dest;
	private Point zeroLocation;
	private String serverPort;
	private String serverIP;
	private double simKMPerBlock;
	public MainWindowViewModel viewModel;
	
	// Constructors
	public MapModel(MainWindowViewModel viewModel) {
		this.viewModel = viewModel;
	}
	
	// Methods
	public void calculateShortestPath() {
		try {
//			Socket theServer = new Socket("127.0.0.1", 1111);
			Socket theServer = new Socket(this.getServerIP(), Integer.parseInt(this.getServerPort()));
			PrintWriter writer = new PrintWriter(theServer.getOutputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(theServer.getInputStream()));
			
			String[] newMat = new String[heightMap.length];
			
			for (int i = 0; i < newMat.length; i++) {
				newMat[i] = "";
			}
			
			for (int i = 0; i < this.heightMap.length; i ++) {
				for (int j = 0; j < this.heightMap[i].length; j++) {
					if (j == this.heightMap[i].length - 1) {
						newMat[i] = newMat[i].concat((int)this.heightMap[i][j] + "");
					} else {
						newMat[i] = newMat[i].concat((int)this.heightMap[i][j] + ",");
					}
				}
			}
			
			writer.println(heightMap.length + "," + heightMap[0].length);
			
			// Print the maze
			for (String line : newMat) {
				writer.println(line);
			}
			
			writer.println("end");
			
			// Starting point in maze.
//			this.setSrc(new Point(this.heightMap[0].length / 2, this.heightMap[0].length / 2));
			writer.println((int) src.getY() + "," + (int) src.getX());
			
			System.out.println("shortest path");
			System.out.println((int) src.getY() + "," + (int) src.getX());
			System.out.println((int) dest.getY() + "," + (int) dest.getX());
			
			
			// Finish point in maze.
			writer.println((int) dest.getY() + "," + (int) dest.getX());
			
			writer.flush();
			String responseLine;
			responseLine = reader.readLine();
			System.out.println("The response is");
			System.out.println(responseLine);
			
			this.setPlanePath(buildPathFromResponse(responseLine));
		} catch (IOException e) {
			e.printStackTrace();
		}

		Thread t = new Thread();
		try {
			t.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private int[][] buildPathFromResponse(String response) {
		String[] path = response.split(",");
		int [][] result = new int[path.length + 1][2];
		
		result[0][0] = (int) src.getX();
		result[0][1] = (int) src.getY();
		for (int i = 1; i <= path.length; i++) {
			switch (path[i - 1]) {
				case "Up":
					result[i][0] = result[i - 1][0];
					result[i][1] = result[i - 1][1] - 1;
					break;
				case "Down":
					result[i][0] = result[i - 1][0];
					result[i][1] = result[i - 1][1] + 1;
					break;
				case "Left":
					result[i][0] = result[i - 1][0] - 1;
					result[i][1] = result[i - 1][1];
					break;
				case "Right":
					result[i][0] = result[i - 1][0] + 1;
					result[i][1] = result[i - 1][1];
					break;
			}
		}
		
		return result;
	}
	
	public void retrieveMapFromFile(File file) {
		
	}
	
	// Getters & Setters
	public int[][] getPlanePath() {
		return planePath;
	}

	public void setPlanePath(int[][] planePath) {
		this.planePath = planePath;
	}

	public double[][] getHeightMap() {
		return heightMap;
	}

	public void setHeightMap(double[][] heightMap) {
		this.heightMap = heightMap;
	}

	public Point getSrc() {
		return src;
	}

	public void setSrc(Point src) {
		this.src = src;
	}

	public Point getDest() {
		return dest;
	}

	public void setDest(Point dest) {
		this.dest = dest;
	}

	public String getServerPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

	public String getServerIP() {
		return serverIP;
	}

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	public void setSimKMPerBlock(double simKMPerBlock) {
		this.simKMPerBlock = simKMPerBlock;
	}
	
	public double getSimKMPerBlock() {
		return this.simKMPerBlock;
	}
	
	public void runLocationRetrieval() {
		Timeline fourSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(4),
				new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
//						ConnectCommand.get("dump /position");
						
						double lon = ConnectCommand.get("get /position/longitude-deg");
						double lat = ConnectCommand.get("get /position/latitude-deg");
//						System.out.println("Test1");
//						System.out.println(lon);
//						System.out.println(lat);
//						System.out.println("Test");
//						System.out.println(simKMPerBlock);
//						System.out.println((lon - zeroLocation.x) / simKMPerBlock);
//						System.out.println((lat - zeroLocation.y) / simKMPerBlock);
						setSrc(new Point((int)((lon - zeroLocation.x) * simKMPerBlock), (int)((lat - zeroLocation.y) * simKMPerBlock)));
						viewModel.srcX.set((lon - zeroLocation.x) * simKMPerBlock);
						viewModel.srcY.set((lat - zeroLocation.y) * simKMPerBlock);
						viewModel.notifySourceChanged();
					}			
		}));
		
		fourSecondsWonder.setCycleCount(Timeline.INDEFINITE);
		fourSecondsWonder.play();
//		AnimationTimer animTimer = new AnimationTimer() {
//			long lastUpdate = 0;
//			
//			@Override
//			public void handle(long now) {
//		    	double lon = ConnectCommand.get("get /position/longitude-deg");
//				double lat = ConnectCommand.get("get /position/latitude-deg");
////				System.out.println("Test");
////				System.out.println(lon);
////				System.out.println(lat);
////				System.out.println((lon - zeroLocation.x) / simKMPerBlock);
////				System.out.println((lat - zeroLocation.y) / simKMPerBlock);
//				setSrc(new Point((int)((lon - zeroLocation.x) / simKMPerBlock), (int)((lat - zeroLocation.y) / simKMPerBlock)));
//				viewModel.srcX.set((lon - zeroLocation.x) / simKMPerBlock);
//				viewModel.srcY.set((lon - zeroLocation.y) / simKMPerBlock);
//				viewModel.notifySourceChanged();
//				
//			}
//		};
//		Timer t = new Timer( );
//		t.scheduleAtFixedRate(new TimerTask() {
//
//		    @Override
//		    public void run() {
//		    	double lon = ConnectCommand.get("get /position/longitude-deg");
//				double lat = ConnectCommand.get("get /position/latitude-deg");
////				System.out.println("Test");
////				System.out.println(lon);
////				System.out.println(lat);
////				System.out.println((lon - zeroLocation.x) / simKMPerBlock);
////				System.out.println((lat - zeroLocation.y) / simKMPerBlock);
//				setSrc(new Point((int)((lon - zeroLocation.x) / simKMPerBlock), (int)((lat - zeroLocation.y) / simKMPerBlock)));
//				viewModel.srcX.set((lon - zeroLocation.x) / simKMPerBlock);
//				viewModel.srcY.set((lon - zeroLocation.y) / simKMPerBlock);
//				viewModel.notifySourceChanged();
//		    }
//		}, 0, 30000);
	}

	public void setZeroLocation(Point point) {
		this.zeroLocation = point;
	}
	
	public Point getZeroLocation() {
		return this.zeroLocation;
	}
}