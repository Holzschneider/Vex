package de.dualuse.vecmath;

import static java.lang.Math.PI;

import java.io.Serializable;

public class AxisAngle extends Tuple<AxisAngle> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public double x,y,z,t;
	
	public AxisAngle(double x, double y, double z, double theta) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.t = theta;
	}
	
	public AxisAngle fromString(String r) {
		String c[] = r.split("\\s+");
		return this.setElements(new Double(c[0]), new Double(c[1]), new Double(c[2]), new Double(c[3]));
	}
	
	public String toString() {
		return x+" "+y+" "+z+" "+t;
	}
	
	public AxisAngle clone() {
		return new AxisAngle(x, y, z, t);
	}
	
	@Override
	public int hashCode() {
		return new Double(x*x+y*y+z*z+t*t).hashCode();
	}
	
	@Override
	public AxisAngle set(AxisAngle a) {
		return this.setElements(a.x, a.y, a.z, a.t);
	}
	
	public boolean elementsEqual(AxisAngle a) {
		return x==a.x && y==a.y && z==a.z && t==a.t;
	}


	public AxisAngle xyzt(double x,double y,double z,double t) { this.x=x; this.y=y; this.z=z; this.t=t; return this; }
	public AxisAngle xyz(double x,double y,double z) { this.x=x; this.y=y; this.z=z; return this; }
	public AxisAngle angle(double degrees) { this.t = degrees*PI/180.0; return this; }
	public AxisAngle theta(double theta) { this.t = theta; return this; }
	
	public double theta() { return t; }
	public double angle() { return t*180.0/PI; }
	
	public AxisAngle setElements(double x, double y, double z, double theta) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.t = theta;
		return this;
	}
	
	
	//////////////////////////////////////////////////////////////////////////////
	
	public AxisAngle identity() { return this.setElements(0, 0, 0, 0); }
	
	public AxisAngle setRotation(Quaternion q) {
		double theta = 2 * Math.acos(q.w), norm = Math.sqrt(1-q.w*q.w);
		return this.setElements(-q.x / norm, -q.y / norm, -q.z / norm, theta);
		
//		final double w = q.w;
//		double radians = 0.;
//		if((w >= -1.0f) && (w <= 1.0f)) 
//		{
//			radians = Math.acos(w)*2.;
//			final double scale = Math.sin(radians / 2.0);
//			
//			if (scale!=0.) {
//				this.set(q.x/scale, q.y/scale, q.z/scale, radians);
//				return this;
//			}
//		}
//		
////		this.set(0., 0., 1., radians);
//		return this;
	}

	
}
