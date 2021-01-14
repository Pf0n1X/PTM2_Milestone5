// Tomer Erusalimsky 208667162 & Ron Avraham 206910663
package server_side;

import java.util.Objects;

public class Node implements Comparable {
	
	// Data Members
	private int row;
	private int column;
	private int value;
	private String movedSide;
	    
	// Constructors
	public Node(int row, int column, int value, String movedSide) {
		this.row = row;
	    this.column = column;
	    this.value = value;
	    this.movedSide = movedSide;
	}

	// Methods
	public int getRow() {
	    return row;
	}

	public void setRow(int row) {
	    this.row = row;
	}

	public int getCol() {
		return column;
	}

	public void setCol(int column) {
	    this.column = column;
	}

	public int getValue() {
	    return value;
	}

	public void setValue(int value) {
	    this.value = value;
	}

	public String getMovedSide() {
	    return movedSide;
	}

	public void setMovedSide(String movedSide) {
	    this.movedSide = movedSide;
	}

	@Override
	public int compareTo(Object obj) {
	    Node node = (Node)obj;

	    return (this.value - node.value);
	}

	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    Node node = (Node)obj;
	    return row == node.row &&
	            column == node.column;
	}

	@Override
	public int hashCode() {
	    return Objects.hash(row, column);
	}
}