package de.dualuse.vecmath;

public interface ValueMatrix2 {
	
	public double getM00();
	public double getM10();
	public double getM01();
	public double getM11();
	
	public double get(int row,int col);
	
	public ValueMatrix2 clone();

}
