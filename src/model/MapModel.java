package model;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MapModel {
	
	// Data Members
	private int[][] planePath;
	private double[][] heightMap;
	private Point src;
	private Point dest;
	private String serverPort;
	private String serverIP;
	
	// Constructors
	public MapModel() {
		
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
			this.setSrc(new Point(this.heightMap[0].length / 2, this.heightMap[0].length / 2));
			writer.println((int) src.getY() + "," + (int) src.getX());
			
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
					result[i][0] = result[i - 1][0] - 1;
					result[i][1] = result[i - 1][1];
					break;
				case "Down":
					result[i][0] = result[i - 1][0] + 1;
					result[i][1] = result[i - 1][1];
					break;
				case "Left":
					result[i][0] = result[i - 1][0];
					result[i][1] = result[i - 1][1] - 1;
					break;
				case "Right":
					result[i][0] = result[i - 1][0];
					result[i][1] = result[i - 1][1] + 1;
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
}