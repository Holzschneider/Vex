package de.dualuse.vecmath;

import static java.lang.Math.*;

import java.io.Serializable;

public class Vector2d 	extends Vector<Vector2d> 
						implements	Serializable,
									Functionals.Vector2d.Function<Vector2d>,
									Functionals.Vector2d.Consumer,
									Functionals.Vector2d
{
	private static final long serialVersionUID = 1L;
	public double x, y;
	
	public Vector2d() {};

	public Vector2d(Vector2d v) {
		this.x=v.x; this.y=v.y;
	}

	public Vector2d(double x, double y) { this.x=x; this.y=y; };
	
	public static Vector2d of(double x, double y) { return new Vector2d(x,y); }

	public String toString() { return x+" "+y; };
	
	
	@Override
	public Vector2d self() { return this; }

	@Override
	public Vector2d clone() { return new Vector2d(x,y); }

	@Override
	public int hashCode() {
		return new Double(x*x+y*y).hashCode();
	}
	
	public boolean elementsEqual(Vector2d a) {
		return x==a.x && y==a.y;
	}
	
	@Override
	public Vector2d set(Vector2d t) { return this.xy(t.x,t.y); }

	public void accept(double x, double y) { this.xy(x, y); }

	@Override
	public Vector2d apply(double x, double y) {
		return this.xy(x, y);
	}
	
	public Vector2d xy(double x, double y) { this.x=x; this.y=y; return this; }
	
	public Vector2d x(double x) { this.x=x; return this; }
	public Vector2d y(double y) { this.y=y; return this; }
	
	public<T> T to(Vector2d.Function<T> v) { return v.apply(this.x, this.y); }
	public double[] to(double[] v) { v[0]=x; v[1]=y; return v; };

	public Vector2d get(Vector2d.Consumer v) { v.accept(this.x, this.y); return this; }
	public Vector2d get(double[] v) { v[0]=x; v[1]=y; return this; };

	//////////////////////////////////////////////////////////////////////////////

	@Override public Vector2d sum(Vector2d a, Vector2d b) { return this.xy(a.x+b.x,a.y+b.y); }
	@Override public Vector2d difference(Vector2d a, Vector2d b) { return this.xy(a.x-b.x, a.y-b.y); }
	
	public Vector2d add(double x, double y) { this.x+=x; this.y+=y; return this; }
	public Vector2d add(Vector2d v) { return this.add(v.x,v.y); }
	public Vector2d adds(Vector2d v, double s) { return this.add(v.x*s,v.y*s); }
	
	@Override public Vector2d sub(Vector2d v) { return this.add(-v.x, -v.y); }
	public Vector2d sub(double x, double y) { return this.add(-x, -y); }
	public Vector2d scale(double s) { this.x*=s; this.y*=s; return this; }
	
	public Vector2d normalize() { return scale(1./length()); }
	public double dot(Vector2d v) { return this.x*v.x+this.y*v.y; }

	public double length() { return Math.sqrt(x*x+y*y); }
	public double distance(Vector2d v) { return Math.sqrt(quadrance(v)); }
	public double distance(double x, double y) { return Math.sqrt(quadrance(x,y)); }

	public double quadrance(Vector2d v2) { return quadrance(v2.x,v2.y); }
	public double quadrance(double x, double y) { 
		final double dx = x-this.x, dy = y-this.y;
		return dx*dx+dy*dy;
	}

	public double norm(double p) {
		return pow(pow(abs(x),p)+pow(abs(y),p),1.0/p);
	}
	
	
	////////// Convenience Methods
	
	public Vector2d projection(Matrix3d m) { return m.project(this); }
	public Vector2d intersection(Matrix3d m) { return m.intersect(this); } 

	//////////

	public Vector2d lerp(Vector2d b, double alpha) {
		final double oma = 1-alpha;
		return this.xy(this.x*oma+b.x*alpha, this.y*oma+b.y*alpha);
	}
	
	public Vector2d line(Vector2d a, Vector2d b, double t) {
		return this.set(a).lerp(b,t);
	}
	
	@Override
	public Vector2d spline(Vector2d a, Vector2d da, Vector2d dd, Vector2d d, double t) {
		final double omr = t, r = 1-omr; 
				
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
		
		this.xy(r0x*r+r1x*omr, r0y*r+r1y*omr);
		
		return this;
	}
	
	//////////
	
	public double angle(Vector2d v) {
		throw new RuntimeException("Not implemented yet");
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

