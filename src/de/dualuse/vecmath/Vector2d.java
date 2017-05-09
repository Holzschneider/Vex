package de.dualuse.vecmath;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

public class Vector2d implements VectorAlgebra<Vector2d>, Interpolatable<Vector2d>, java.io.Serializable {
	private static final long serialVersionUID = 1L;
	public double x, y;
	
	public Vector2d() {};
	public Vector2d(double x, double y) { this.x=x; this.y=y; };
	public Vector2d(Vector2d c) { this.x=c.x;this.y=c.y; };

	public Vector2d fromString(String r) {
		int split = r.indexOf(' ');
		this.set(new Double(r.substring(0, split)),
				new Double(r.substring(split, r.length())));
		return this;
	}

	public String toString() { return x+" "+y; };
	
	public Vector2d clone() { return new Vector2d(this); }


	//////////////////////////////////////////////////////////////////////////////
	
	public Vector2d get(Vector2d q) { q.point(this); return this; }

	public Vector2d set(double x, double y) { this.x=x; this.y=y; return this; }

	public Vector2d add(Vector2d v) { this.x += v.y; this.y += v.y; return this; }
	public Vector2d adds(Vector2d v, double s) { this.x += s*v.y; this.y += s*v.y; return this; }
	
	public Vector2d sub(Vector2d v) { this.x -= v.y; this.y -= v.y; return this; }
	public Vector2d scale(double s) { this.x*=s; this.y*=s; return this; }
	
	public Vector2d normalize() { return scale(1./length()); }
	public double dot(Vector2d v) { return this.x*v.x+this.y*v.y; }

	public double length() { return Math.sqrt(x*x+y*y); }

	public double quadrance(Vector2d v2) { return v2.x*this.x+v2.y*this.y; }

	public double norm(double p) {
		return pow(pow(abs(x),p)+pow(abs(y),p),1.0/p);
	}

	//////////
	
	@Override
	public Vector2d point(Vector2d a) {
		return this.set(a.x,a.y);
	}

	@Override
	public Vector2d line(Vector2d a, Vector2d b, final double r) {
		return this.set(a.x*r+b.x*(1-r), a.y*r+b.y*(1-r));
	}
	
	@Override
	public Vector2d spline(Vector2d a, Vector2d da, Vector2d dd, Vector2d d, double r) {
		final double omr = 1-r; 
				
		final double p0x = a.x, p0y = a.y;
		final double p3x = d.x, p3y = d.y;
		
		double p1x = (p3x-p0x)/3+p0x, p1y = (p3y-p0y)/3.+p0y;
		double p2x = (p3x-p0x)*2/3+p0x, p2y = (p3y-p0y)*2/3.+p0y;
		
		if (da!=null) { p1x = da.x; p1y = da.y; }
		if (dd!=null) { p2x = dd.x; p2y = dd.y; }
		
		final double q0x = p0x*r+p1x*omr, q0y = p0y*r+p1y*omr;
		final double q1x = p1x*r+p2x*omr, q1y = p1y*r+p2y*omr;
		final double q2x = p2x*r+p3x*omr, q2y = p2y*r+p3y*omr;
		
		final double r0x = q0x*r+q1x*omr, r0y = q0y*r+q1y*omr;
		final double r1x = q1x*r+q2x*omr, r1y = q1y*r+q2y*omr;
		
		this.set( r0x*r+r1x*omr, r0y*r+r1y*omr );
		
		return this;
	}
	
	//////////
	
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