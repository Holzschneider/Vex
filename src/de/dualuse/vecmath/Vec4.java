package de.dualuse.vecmath;


public abstract class Vec4 implements Value4, Variable<Value4>, Linear<Value4>, Cubic<Value4>, java.io.Serializable {
	private static final long serialVersionUID = 1L;
	public double x, y, z, w;

	
	
	@Override public Linear<Value4> line(Value4 a, Value4 b, double r) {
		return set(a).mix(b, r);
	}
	
	
	public Vec4 spline(Value4 a, Value4 da, Value4 dd, Value4 d, double r) {
		final double omr = 1-r; 
				
		final double p0x = a.getX(), p0y = a.getY(), p0z = a.getZ(), p0w = a.getW();
		final double p3x = d.getX(), p3y = d.getY(), p3z = d.getZ(), p3w = d.getW();
		
		double p1x = (p3x-p0x)/3+p0x, p1y = (p3y-p0y)/3.+p0y, p1z = (p3z-p0z)/3.+p0z, p1w = (p3w-p0w)/3.+p0w;
		double p2x = (p3x-p0x)*2/3+p0x, p2y = (p3y-p0y)*2/3.+p0y, p2z = (p3z-p0z)*2/3.+p0z, p2w = (p3w-p0w)*2/3.+p0w;
		
		if (da!=null) { p1x = da.getX(); p1y = da.getY(); p1z = da.getZ(); p1w = da.getW(); }
		if (dd!=null) { p2x = dd.getX(); p2y = dd.getY(); p2z = dd.getZ(); p2w = dd.getW(); }
		
		double q0x = p0x*r+p1x*omr, q0y = p0y*r+p1y*omr, q0z = p0z*r+p1z*omr, q0w = p0w*r+p1w*omr;
		double q1x = p1x*r+p2x*omr, q1y = p1y*r+p2y*omr, q1z = p1z*r+p2z*omr, q1w = p1w*r+p2w*omr;
		double q2x = p2x*r+p3x*omr, q2y = p2y*r+p3y*omr, q2z = p2z*r+p3z*omr, q2w = p2w*r+p3w*omr;
		
		double r0x = q0x*r+q1x*omr, r0y = q0y*r+q1y*omr, r0z = q0z*r+q1z*omr, r0w = q0w*r+q1w*omr;
		double r1x = q1x*r+q2x*omr, r1y = q1y*r+q2y*omr, r1z = q1z*r+q2z*omr, r1w = q1w*r+q2w*omr;
		
		this.set( r0x*r+r1x*omr, r0y*r+r1y*omr, r0z*r+r1z*omr, r0w*r+r1w*omr );
		
		return this;
	}
	
	
	public Vec4 get(Variable<Value4> q) { q.set(this); return this; }
	
	
	public double getX() { return x; }
	public double getY() { return y; }
	public double getZ() { return z; }
	public double getW() { return w; }

	public Vec4 set(double x, double y, double z, double w) { this.x=x; this.y=y; this.z=z; this.w=w; return this; }

	public Vec4() {};
	public Vec4(double x, double y, double z, double w) { this.x=x; this.y=y; this.z=z; this.w=w; };
	public Vec4(Value4 c) { this.x=c.getX();this.y=c.getY(); this.z=c.getZ(); this.w=c.getW(); };
	
	public Vec4(Vec4 c) { this.x=c.x;this.y=c.y; this.z=c.z; this.w=c.w; };


	public Vec4 set(Value4 v) { return set(v.getX(),v.getY(),v.getZ(),v.getW()); }
	public Vec4 set(Vec4 v) { return set(v.getX(),v.getY(),v.getZ(),v.getW()); }
	
	public Vec4 add(Value4 v) { set(getX()+v.getX(),getY()+v.getY(),getZ()+v.getZ(),getW()+v.getW()); return this; }
	public Vec4 add(Value4 v, double scale) { set(getX()+v.getX()*scale,getY()+v.getY()*scale,getZ()+v.getZ()*scale, getW()+v.getW()*scale); return this; }
	
	public Vec4 sub(Value4 v) { set(getX()-v.getX(),getY()-v.getY(),getZ()-v.getZ(), getW()-v.getW()); return this; }
	public Vec4 sub(Value4 v, double scale) { set(getX()-v.getX()*scale,getY()-v.getY()*scale,getZ()-v.getZ()*scale, getW()-v.getW()*scale); return this; }
	
	public Vec4 scale(double s) { set(getX()*s,getY()*s,getZ()*s,getW()*s); return this; }
	
	public Vec4 normalize() { return scale(1./length()); }

	public double dot(Value4 v) { return getX()*v.getX()+getY()*v.getY()+getZ()*v.getZ()+getW()*v.getW(); }
	public double lengthSq() { final double x = getX(), y = getY(), z = getZ(), w = getW(); return x*x+y*y+z*z+w*w; }
	public double length() { final double x = getX(), y = getY(), z = getZ(), w = getW(); return Math.sqrt(x*x+y*y+z*z+w*w); }
	
	public double distanceSq(Value4 v) {  
		final double dx = v.getX()-this.getX(), dy = v.getY()-this.getY(), dz = v.getZ()-this.getZ(); 
		return dx*dx+dy*dy+dz*dz; 
	}
	
	public Vec4 fromString(String r) { int s1 = r.indexOf(' '); int s2 = r.indexOf(' ',s1+1); int s3= r.indexOf(' ',s2+1); return this.set(java.lang.Double.parseDouble(r.substring(0, s1)), java.lang.Double.parseDouble(r.substring(s1,s2)), java.lang.Double.parseDouble(r.substring(s2,s3)), java.lang.Double.parseDouble(r.substring(s3,r.length()))); }
	public String toString() { return getX()+" "+getY()+" "+getZ()+" "+getW(); };

	public Vec4 clone() { try { return (Vec4) super.clone(); } catch (Exception ex) { throw new RuntimeException(ex); } }
	
	public Vec4 mix( Value4 v, final double r ) { final double omr = 1.-r; this.set(this.getX()*omr+v.getX()*r, this.getY()*omr+v.getY()*r, this.getZ()*omr+v.getZ()*r, this.getW()*omr+v.getW()*r); return this; }
	
	
}
