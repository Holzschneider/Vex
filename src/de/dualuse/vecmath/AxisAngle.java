package de.dualuse.vecmath;

import static java.lang.Math.PI;

import java.io.Serializable;

public class AxisAngle extends Tuple<AxisAngle> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public double x,y,z,theta;
	
	public AxisAngle(double x, double y, double z, double theta) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.theta = theta;
	}
	
	public AxisAngle fromString(String r) {
		String c[] = r.split("\\s+");
		return this.setElements(new Double(c[0]), new Double(c[1]), new Double(c[2]), new Double(c[3]));
	}
	
	public String toString() {
		return x+" "+y+" "+z+" "+theta;
	}
	
	public AxisAngle clone() {
		return new AxisAngle(x, y, z, theta);
	}
	
	@Override
	public int hashCode() {
		return new Double(x*x+y*y+z*z+theta*theta).hashCode();
	}
	
	@Override
	public AxisAngle set(AxisAngle a) {
		return this.setElements(a.x, a.y, a.z, a.theta);
	}
	
	public boolean elementsEqual(AxisAngle a) {
		return x==a.x && y==a.y && z==a.z && theta==a.theta;
	}


	public AxisAngle xyzt(double x,double y, double z, double t) { this.x=x; this.y=y; this.z=z;theta=t; return this; }
	public AxisAngle xyz(double x,double y, double z) { this.x=x; this.y=y; this.z=z; return this; }
	public AxisAngle theta(double theta) { this.theta = theta; return this; }
	public AxisAngle angle(double degrees) { this.theta = degrees*PI/180.0; return this; }
	
	public double theta() { return theta; }
	public double angle() { return theta*180.0/PI; }
	
	public AxisAngle setElements(double x, double y, double z, double theta) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.theta = theta;
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

	
	
//	final public AxisAngle getAxisAngleTo(final Vec3 v, AxisAngle rotationAxisAngle) {
//		final double bothLen = Math.sqrt(x*x + y*y + z*z) * Math.sqrt(v.x*v.x + v.y*v.y + v.z*v.z);
//		if (bothLen == 0)
//			return rotationAxisAngle.set(1, 0, 0, 0);
//		
//		rotationAxisAngle.x = (y*v.z - z*v.y) / bothLen;
//		rotationAxisAngle.y = (z*v.x - x*v.z) / bothLen;
//		rotationAxisAngle.z = (x*v.y - y*v.x) / bothLen;
//		rotationAxisAngle.angle = Math.sqrt(rotationAxisAngle.x*rotationAxisAngle.x + rotationAxisAngle.y*rotationAxisAngle.y + rotationAxisAngle.z*rotationAxisAngle.z);
//		
//		rotationAxisAngle.x /= rotationAxisAngle.angle;
//		rotationAxisAngle.y /= rotationAxisAngle.angle;
//		rotationAxisAngle.z /= rotationAxisAngle.angle;
//		return rotationAxisAngle;
//	}

	
}
