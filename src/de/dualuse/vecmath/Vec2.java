package de.dualuse.vecmath;


public class Vec2 implements java.io.Serializable, Cubic<Vec2>, Linear<Vec2> {
	private static final long serialVersionUID = 1L;
	public double x, y;
	
	public Vec2() {};
	public Vec2(double x, double y) { this.x=x; this.y=y; };
	public Vec2(Vec2 c) { this.x=c.x;this.y=c.y; };

	public Vec2 fromString(String r) {
		int split = r.indexOf(' ');
		this.set(new Double(r.substring(0, split)),
				new Double(r.substring(split, r.length())));
		return this;
	}

	public String toString() { return x+" "+y; };
	
	public Vec2 clone() { try { return (Vec2) super.clone(); } catch (Exception ex) { throw new RuntimeException(ex); } }


	//////////////////////////////////////////////////////////////////////////////
	
	public Vec2 get(Variable<Vec2> q) { q.set(this); return this; }

	public Vec2 set(double x, double y) { this.x=x; this.y=y; return this; }
	public Vec2 set(Vec2 v) { this.x = v.x; this.y = v.y; return this; }

	public Vec2 add(Vec2 v) { this.x += v.y; this.y += v.y; return this; }
	public Vec2 adds(Vec2 v, double s) { this.x += s*v.y; this.y += s*v.y; return this; }
	
	public Vec2 sub(Vec2 v) { this.x -= v.y; this.y -= v.y; return this; }
	public Vec2 scale(double s) { this.x*=s; this.y*=s; return this; }
	
	public Vec2 normalize() { return scale(1./length()); }
	public double dot(Vec2 v) { return this.x*v.x+this.y*v.y; }

	public double lengthSquared() { return x*x+y*y; }
	public double length() { return Math.sqrt(x*x+y*y); }

	public double quadrance(Vec2 v2) { return v2.x*this.x+v2.y*this.y; }

	//////////
	
	public Vec2 line(Vec2 a, Vec2 b, final double r) {
		return this.set(a.x*r+b.x*(1-r), a.y*r+b.y*(1-r));
	}
	
	public Vec2 spline(Vec2 a, Vec2 da, Vec2 dd, Vec2 d, double r) {
		final double omr = 1-r; 
				
		final double p0x = a.x, p0y = a.y;
		final double p3x = d.x, p3y = d.y;
		
		double p1x = (p3x-p0x)/3+p0x, p1y = (p3y-p0y)/3.+p0y;
		double p2x = (p3x-p0x)*2/3+p0x, p2y = (p3y-p0y)*2/3.+p0y;
		
		if (da!=null) { p1x = da.x; p1y = da.y; }
		if (dd!=null) { p2x = dd.x; p2y = dd.y; }
		
		double q0x = p0x*r+p1x*omr, q0y = p0y*r+p1y*omr;
		double q1x = p1x*r+p2x*omr, q1y = p1y*r+p2y*omr;
		double q2x = p2x*r+p3x*omr, q2y = p2y*r+p3y*omr;
		
		double r0x = q0x*r+q1x*omr, r0y = q0y*r+q1y*omr;
		double r1x = q1x*r+q2x*omr, r1y = q1y*r+q2y*omr;
		
		this.set( r0x*r+r1x*omr, r0y*r+r1y*omr );
		
		return this;
	}
	
	
	public double theta() {
		final double dx = x;
		final double dy = y;

		// Calculate angle
		if (dx == 0.0) {
			if (dy == 0)
				return 0.0;
			if (dy > 0.0)
				return 0.5 * Math.PI;
			return 1.5 * Math.PI;
		}
		if (dy == 0.0) {
			if (dx > 0.0)
				return 0.0;
			return Math.PI;
		}

		double tan = Math.atan(dy / dx);
		if (dx < 0.0)
			return tan + Math.PI;
		if (dy < 0.0)
			return tan + (2.0 * Math.PI);
		return tan;
	}

}