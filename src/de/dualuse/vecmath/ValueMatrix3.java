package de.dualuse.vecmath;

public interface ValueMatrix3 extends ValueMatrix2 {
	
	public double getM00();
	public double getM10();
	public double getM20();
	
	public double getM01();
	public double getM11();
	public double getM21();
	
	public double getM02();
	public double getM12();
	public double getM22();
	
	public double get(int row,int col);
	
	public ValueMatrix3 clone();

}
