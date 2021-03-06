package de.dualuse.vecmath;

import static java.lang.Math.*;

import java.io.Serializable;

public class Vector4d	extends Vector<Vector4d>
						implements	Serializable,
									Functionals.Vector4d.Function<Vector4d>,
									Functionals.Vector4d.Consumer,
									Functionals.Vector4d
{
	private static final long serialVersionUID = 1L;
	public double x, y, z, w;

	public Vector4d() {}
	public Vector4d(Vector4d v) { this.set(v); }
	
	public Vector4d(double x, double y, double z, double w) { this.xyzw(x, y, z, w); };
	public static Vector4d of(double x, double y, double z, double w) { return new Vector4d(x,y,z,w); }
	
	
	

	@Override
	public String toString() { return x+" "+y+" "+z+" "+w; };


	@Override
	public Vector4d self() { return this; }
	
	@Override
	public Vector4d clone() { return new Vector4d(x,y,z,w); };
	
	@Override
	public int hashCode() {
		return new Double(x*x+y*y+z*z+w*w).hashCode();
	}
	
	public boolean elementsEqual(Vector4d a) {
		return x==a.x && y==a.y && z==a.z && w==a.w;
	}
	
	public<T> T to(Vector4d.Function<T> v) { return v.apply(this.x, this.y, this.z, this.w); }
	public double[] to(double[] v) { v[0]=x; v[1]=y; v[2]=z; v[3]=w; return v; };
	
	public Vector4d get(Vector4d.Consumer v) { v.accept(x, y, z, w); return this; } 
	
	@Override
	public Vector4d set(Vector4d v) {
		return xyzw(v.x,v.y,v.z,v.w);
	}
	
	public void accept(double x, double y, double z, double w) { this.xyzw(x,y,z,w); }
	public Vector4d apply(double x, double y, double z, double w) { return this.xyzw(x, y, z, w); }
	
	public Vector4d xyzw(double x, double y, double z, double w) { this.x=x; this.y=y; this.z=z; this.w=w;return this;}
	public Vector4d xyz(double x, double y, double z) { this.x=x; this.y=y; this.z=z; return this;}
	public Vector4d xy(double x, double y) { this.x=x; this.y=y; return this;}
	
	public Vector4d x(double x) { this.x=x; return this; }
	public Vector4d y(double y) { this.y=y; return this; }
	public Vector4d z(double z) { this.z=z; return this; }
	public Vector4d w(double w) { this.w=w; return this; }
	
	//////////////////////////////////////////////////////////////////////////////
	
	@Override public Vector4d sum(Vector4d a, Vector4d b) { return this.xyzw(a.x+b.x,a.y+b.y,a.z+b.z, a.w+b.w); }
	@Override public Vector4d difference(Vector4d a, Vector4d b) { return this.xyzw(a.x-b.x,a.y-b.y,a.z-b.z,a.w-b.w); }
	
	public Vector4d add(double x, double y, double z, double w) { return xyzw(x+this.x,y+this.y,z+this.z,w+this.w); }
	
	public Vector4d add(Vector4d v) { return xyzw(x+v.x,y+v.y,z+v.z,w+v.w); }
	public Vector4d adds(Vector4d v, double scale) { return xyzw(x+v.x*scale,y+v.y*scale,z+v.z*scale, w+v.w*scale); }
	
	public Vector4d sub(Vector4d v) { return  xyzw(x-v.x,y-v.y,z-v.z, w-v.w); }
	
	public Vector4d scale(double s) { xyzw(x*s,y*s,z*s,w*s); return this; }
	
	@Override public Vector4d mul(Vector4d v) { return xyzw(x*v.x,y*v.y,z*v.z,w*v.w); }
	@Override public Vector4d div(Vector4d v) { return xyzw(x/v.x,y/v.y,z/v.z,w/v.w); }
	
	public Vector4d normalize() { return scale(1./length()); }
	public double dot(Vector4d v) { return x*v.x+y*v.y+z*v.z+w*v.w; }
	public double length() { return Math.sqrt(dot(this)); }
	
	@Override
	public double norm(double p) {
		return pow(pow(abs(x),p)+pow(abs(y),p)+pow(abs(z),p)+pow(abs(w),p),1.0/p);
	}

	public double distance(Vector4d v) { return Math.sqrt(quadrance(v)); }
	public double distance(double x, double y, double z, double w) { return Math.sqrt(quadrance(x,y,z,w)); }


	public double quadrance(Vector4d v) { return quadrance(v.x,v.y,v.z,v.w); }
	public double quadrance(double vx, double vy, double vz, double vw) {  
		final double dx = vx-this.x, dy = vy-this.y, dz = vz-this.z; 
		return dx*dx+dy*dy+dz*dz; 
	}

	////////// Convenience Methods
	
	public Vector4d augmentation(Vector3d v) { return this.xyzw(v.x, v.y, v.z, 1); }
	public Vector4d transformation(Matrix4d m) { return m.transform(this); }
	
	////////// Interpolatable

	
	public Vector4d lerp(Vector4d b, double alpha) {
		final double oma = 1-alpha;
		return this.xyzw(this.x*oma+b.x*alpha, this.y*oma+b.y*alpha, this.z*oma+b.z*alpha, this.w*oma+b.w*alpha);
	}
	
	public Vector4d line(Vector4d a, Vector4d b, double r) {
		final double omr = 1.-r; 
		return this.xyzw(
				a.x*omr+b.x*r, 
				a.y*omr+b.y*r, 
				a.z*omr+b.z*r, 
				a.w*omr+b.w*r); 
	}
	
	
	@Override 
	public Vector4d spline(Vector4d a, Vector4d da, Vector4d dd, Vector4d d, double r) {
		final double omr = 1-r; 
				
		final double p0x = a.x, p0y = a.y, p0z = a.z, p0w = a.w;
		final double p3x = d.x, p3y = d.y, p3z = d.z, p3w = d.w;
		
		double p1x = (p3x-p0x)/3+p0x, p1y = (p3y-p0y)/3.+p0y, p1z = (p3z-p0z)/3.+p0z, p1w = (p3w-p0w)/3.+p0w;
		double p2x = (p3x-p0x)*2/3+p0x, p2y = (p3y-p0y)*2/3.+p0y, p2z = (p3z-p0z)*2/3.+p0z, p2w = (p3w-p0w)*2/3.+p0w;
		
		if (da!=null) { p1x = da.x; p1y = da.y; p1z = da.z; p1w = da.w; }
		if (dd!=null) { p2x = dd.x; p2y = dd.y; p2z = dd.z; p2w = dd.w; }
		
		double q0x = p0x*r+p1x*omr, q0y = p0y*r+p1y*omr, q0z = p0z*r+p1z*omr, q0w = p0w*r+p1w*omr;
		double q1x = p1x*r+p2x*omr, q1y = p1y*r+p2y*omr, q1z = p1z*r+p2z*omr, q1w = p1w*r+p2w*omr;
		double q2x = p2x*r+p3x*omr, q2y = p2y*r+p3y*omr, q2z = p2z*r+p3z*omr, q2w = p2w*r+p3w*omr;
		
		double r0x = q0x*r+q1x*omr, r0y = q0y*r+q1y*omr, r0z = q0z*r+q1z*omr, r0w = q0w*r+q1w*omr;
		double r1x = q1x*r+q2x*omr, r1y = q1y*r+q2y*omr, r1z = q1z*r+q2z*omr, r1w = q1w*r+q2w*omr;
		
		this.xyzw( r0x*r+r1x*omr, r0y*r+r1y*omr, r0z*r+r1z*omr, r0w*r+r1w*omr );
		
		return this;
	}
	
	
	/////////////////////////////////////////////
	
	
	public static Vector4d add(Vector4d a, Vector4d b) {
		return new Vector4d().set(a).add(b);
	}

	public static Vector4d sub(Vector4d a, Vector4d b) {
		return new Vector4d().set(a).sub(b);
	}
	
	public static Vector4d mul(Vector4d a, Vector4d b) {
		return new Vector4d().set(a).mul(b);
	}

	public static Vector4d div(Vector4d a, Vector4d b) {
		return new Vector4d().set(a).div(b);
	}

	public static double dot(Vector4d a, Vector4d b) {
		return a.dot(b);
	}

	public static Vector4d augment( Vector3d a ) {
		return new Vector4d().augmentation(a); 
	}
	
}
