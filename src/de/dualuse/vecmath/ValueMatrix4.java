package de.dualuse.vecmath;

public interface ValueMatrix4 extends ValueMatrix3 {

	public double getM00();
	public double getM10();
	public double getM20();
	public double getM30();
	public double getM01();
	public double getM11();
	public double getM21();
	public double getM31();
	public double getM02();
	public double getM12();
	public double getM22();
	public double getM32();
	public double getM03();
	public double getM13();
	public double getM23();
	public double getM33();
	
	public double get(int row,int col);
	
	public ValueMatrix4 clone();

}
