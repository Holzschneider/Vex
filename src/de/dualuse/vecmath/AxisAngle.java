package de.dualuse.vecmath;

import java.io.Serializable;

public abstract class AxisAngle implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public double x,y,z,theta;
	
	public AxisAngle() {
		x=y=theta=0;
		z = 1;
	}
	
	public AxisAngle(double x, double y, double z, double w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.theta = w;
	}
	
	public AxisAngle(Quaternion q) { setToRotation(q); }
	
	public AxisAngle set(double x, double y, double z, double w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.theta = w;
		
		return this;
	}
	
	public double getDegrees() { return theta*180./Math.PI; }
	
	public AxisAngle setToRotation(Quaternion q) {
		final double w = q.w;
		double radians = 0.;
		
//		if((w >= -1.0f) && (w <= 1.0f)) 
		{
			radians = Math.acos(w)*2.;
			final double scale = Math.sin(radians / 2.0);
			
			if (scale!=0.) {
				this.set(q.x/scale, q.y/scale, q.z/scale, radians);
				return this;
			}
		}
		
//		this.set(0., 0., 1., radians);
		return this;
	}
	
}
