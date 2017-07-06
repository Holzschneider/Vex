package de.dualuse.vecmath;

import java.io.Serializable;

public class Scalar extends Number implements Interpolatable<Scalar>, Serializable {
	private static final long serialVersionUID = 1L;
	public double v;
	
	public Scalar(double v) { this.v = v; }
	public Scalar get(Scalar q) { q.set(this); return this; }

	public Scalar clone() { return new Scalar(v); }
	
	public Scalar set(Scalar a) {
		return this.setElement(a.doubleValue());
	}
	
	public Scalar setElement(double v) {
		this.v = v;
		return this;
	}
	
	@Override
	public Scalar spline(Scalar a, Scalar da, Scalar dd, Scalar d, double r) {

		final double omr = 1-r; 
				
		final double p0x = a.doubleValue();
		final double p3x = d.doubleValue();
		
		double p1x = (p3x-p0x)/3+p0x;
		double p2x = (p3x-p0x)*2/3+p0x;
		
		if (da!=null) p1x = da.doubleValue();
		if (dd!=null) p2x = dd.doubleValue();
		
		double q0x = p0x*r+p1x*omr;
		double q1x = p1x*r+p2x*omr;
		double q2x = p2x*r+p3x*omr;
		
		double r0x = q0x*r+q1x*omr;
		double r1x = q1x*r+q2x*omr;
		
		this.setElement( r0x*r+r1x*omr );
		
		return this;
	}
	
	
	public String toString() { return ""+doubleValue(); }	
	
	public double doubleValue() { return v; }
	public float floatValue() { return (float)v; }
	public int intValue() { return (int)v; }
	public long longValue() { return (long)v; }

	public Scalar lerp(Scalar b, double alpha) {
		return this.setElement(this.v*(1-alpha)+b.v*alpha);
	}
	
}
