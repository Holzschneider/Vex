package de.dualuse.vecmath;


public abstract class Scalar extends Number implements Value, Cubic<Number>, Linear<Number> {
	private static final long serialVersionUID = 1L;
	public double v;
	
	public Scalar get(Variable<Number> q) { q.set(this); return this; }

	public Scalar clone() { try { return (Scalar)super.clone(); } catch (Exception e) { throw new RuntimeException(e); } }

	
//	@Override
	public Scalar line(Number a, Number b, double r) {
		this.set(a.doubleValue()*(1.-r)+b.doubleValue()*r);
		
		return this;
	}
	
//	@Override
	public Scalar spline(Number a, Number da, Number dd, Number d, double r) {

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
		
		this.set( r0x*r+r1x*omr );
		
		return this;
	}
	
	public double distance(Scalar s) { return Math.abs(s.doubleValue()-this.doubleValue()); }
	public double distance(double s) { return Math.abs(s-this.doubleValue()); }
	
	public String toString() { return ""+doubleValue(); }
	
	
	public double doubleValue() { return v; }
	public float floatValue() { return (float)v; }
	public int intValue() { return (int)v; }
	public long longValue() { return (long)v; }
	
	public Scalar set(Scalar sv) { this.v=sv.doubleValue(); return this; }
	public Scalar set(Number s) { this.v=s.doubleValue(); return this; }
	public Scalar set(double s) { this.v=s; return this; }
	public Scalar set(float s) { this.v=s; return this; }
	public Scalar set(int s) { this.v=s; return this; }

	
	
}
