package de.dualuse.vecmath;


public class Vec4 implements Interpolatable<Vec4>, java.io.Serializable {
	private static final long serialVersionUID = 1L;
	public double x, y, z, w;


	public Vec4() {};
	public Vec4(double x, double y, double z, double w) { this.x=x; this.y=y; this.z=z; this.w=w; };
	public Vec4(Vec4 c) { this.x=c.x;this.y=c.y; this.z=c.z; this.w=c.w; };

	public Vec4 fromString(String r) {
		int s1 = r.indexOf(' ');
		int s2 = r.indexOf(' ', s1 + 1);
		int s3 = r.indexOf(' ', s2 + 1);
		return this.set(
				new Double(r.substring(0, s1)),
				new Double(r.substring(s1, s2)), 
				new Double(r.substring(s2, s3)),
				new Double(r.substring(s3, r.length())));
	}

	public String toString() { return x+" "+y+" "+z+" "+w; };

	public Vec4 clone() { return new Vec4(this); };
	
	//////////////////////////////////////////////////////////////////////////////

	public Vec4 get(Vec4 q) { q.point(this); return this; }
	public Vec4 set(double x, double y, double z, double w) { this.x=x; this.y=y; this.z=z; this.w=w; return this; }
	
	public Vec4 add(Vec4 v) { set(x+v.x,y+v.y,z+v.z,w+v.w); return this; }
	public Vec4 adds(Vec4 v, double scale) { set(x+v.x*scale,y+v.y*scale,z+v.z*scale, w+v.w*scale); return this; }
	
	public Vec4 sub(Vec4 v) { set(x-v.x,y-v.y,z-v.z, w-v.w); return this; }
	
	public Vec4 scale(double s) { set(x*s,y*s,z*s,w*s); return this; }
	
	public Vec4 normalize() { return scale(1./length()); }
	public double dot(Vec4 v) { return x*v.x+y*v.y+z*v.z+w*v.w; }
	public double lengthSquared() { return x*x+y*y+z*z+w*w; }
	public double length() { return Math.sqrt(lengthSquared()); }
	
	public double quadrance(Vec4 v) {  
		final double dx = v.x-this.x, dy = v.y-this.y, dz = v.z-this.z; 
		return dx*dx+dy*dy+dz*dz; 
	}
	
	////////// 
	@Override
	public Vec4 point(Vec4 v) {
		return set(v.x,v.y,v.z,v.w);
	}
	
	@Override 
	public Vec4 line(Vec4 a, Vec4 b, double r) {
		final double omr = 1.-r; 
		return this.set(
				a.x*omr+b.x*r, 
				a.y*omr+b.y*r, 
				a.z*omr+b.z*r, 
				a.w*omr+b.w*r); 
	}
	
	
	public Vec4 spline(Vec4 a, Vec4 da, Vec4 dd, Vec4 d, double r) {
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
		
		this.set( r0x*r+r1x*omr, r0y*r+r1y*omr, r0z*r+r1z*omr, r0w*r+r1w*omr );
		
		return this;
	}
	
}
