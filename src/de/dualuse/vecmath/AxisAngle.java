package de.dualuse.vecmath;

import java.io.Serializable;

public class AxisAngle implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public double x,y,z,theta;
	
	public AxisAngle() {
		x=y=theta=0;
		z = 1;
	}
	
	public AxisAngle(double x, double y, double z, double theta) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.theta = theta;
	}
	
	public AxisAngle set(double x, double y, double z, double theta) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.theta = theta;
		return this;
	}
	
	
	public AxisAngle fromString(String r) {
		String c[] = r.split("\\s+");
		return this.set(new Double(c[0]), new Double(c[1]), new Double(c[2]), new Double(c[3]));
	}
	
	public String toString() {
		return x+" "+y+" "+z+" "+theta;
	}
	
	public AxisAngle clone() {
		return new AxisAngle().set(x, y, z, theta);
	}
	
	@Override
	public int hashCode() {
		return new Double(x*x+y*y+z*z+theta*theta).hashCode();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		else
		if (o instanceof AxisAngle)
			return equals((AxisAngle)o);
		else
			return false;
	}
	
	public boolean equals(AxisAngle a) {
		return x==a.x && y==a.y && z==a.z && theta==a.theta;
		
	}

	//////////////////////////////////////////////////////////////////////////////
	
	public double getDegrees() { return theta*180./Math.PI; }
	
	public AxisAngle identity() { return this.set(0, 0, 0, 0); }
	
	public AxisAngle setRotation(Quaternion q) {
		double theta = 2 * Math.acos(q.w), norm = Math.sqrt(1-q.w*q.w);
		return this.set(-q.x / norm, -q.y / norm, -q.z / norm, theta);
		
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
