package de.dualuse.vecmath;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

public class Vector3d implements VectorAlgebra<Vector3d>, Interpolatable<Vector3d>, java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	public double x, y, z;

	public Vector3d() {};
	public Vector3d(double x, double y, double z) { this.x=x; this.y=y; this.z=z; };
	public Vector3d(Vector3d c) { this.x=c.x;this.y=c.y; this.z=c.z; };

	public Vector3d fromString(String r) {
		int s1 = r.indexOf(' ');
		int s2 = r.indexOf(' ', s1 + 1);
		
		return this.set(
				new Double(r.substring(0, s1)),
				new Double(r.substring(s1, s2)),
				new Double(r.substring(s2, r.length()))
		);
	}

	public String toString() { return x+" "+y+" "+z; };
	
	public Vector3d clone() {
		return new Vector3d(x,y,z);
	}
	
	//////////////////////////////////////////////////////////////////////////////

	public static interface Value<Q> { Q define(Vector3d v); }
	public<Q> Q get( Value<Q> v )  { return v.define(this); }
	
//	public Vec3 set(Vec3 v) { this.x=v.x; this.y=v.y; this.z=v.z; return this; }
	public Vector3d set(double x, double y, double z) { this.x=x; this.y=y; this.z=z; return this; }
	
	public Vector3d add(Vector3d v) { this.x+=v.x; this.y+=v.y; this.z+=v.z; return this; }
	public Vector3d adds(Vector3d v, double s) { this.x+=s*v.x; this.y+=s*v.y; this.z+=s*v.z; return this; }
	
	public Vector3d sub(Vector3d v) { this.x-=v.x; this.y-=v.y; this.z-=v.z; return this; }

	public Vector3d scale(double s) { this.x*=s; this.y*=s; this.z*=s; return this; }
	public Vector3d normalize() { return scale(1./length()); }

	public double dot(Vector3d v) { return x*v.x+y*v.y+z*v.z; }

	public double length() { return Math.sqrt(x*x+y*y+z*z); }
	public double quadrance(Vector3d v) {  
		final double dx = v.x-this.x, dy = v.y-this.y, dz = v.z-this.z; 
		return dx*dx+dy*dy+dz*dz; 
	}
	
	@Override
	public double norm(double p) { 
		return pow(pow(abs(x),p)+pow(abs(y),p)+pow(abs(z),p),1.0/p);
	}
	
	
	
//	final public AxisAngle getAxisAngleTo(final Vec3 v, AxisAngle rotationAxisAngle) {
//		final double bothLen = Math.sqrt(x*x + y*y + z*z) * Math.sqrt(v.x*v.x + v.y*v.y + v.z*v.z);
//		if (bothLen == 0)
//			return rotationAxisAngle.set(1, 0, 0, 0);
//		
//		rotationAxisAngle.x = (y*v.z - z*v.y) / bothLen;
//		rotationAxisAngle.y = (z*v.x - x*v.z) / bothLen;
//		rotationAxisAngle.z = (x*v.y - y*v.x) / bothLen;
//		rotationAxisAngle.angle = Math.sqrt(rotationAxisAngle.x*rotationAxisAngle.x + rotationAxisAngle.y*rotationAxisAngle.y + rotationAxisAngle.z*rotationAxisAngle.z);
//		
//		rotationAxisAngle.x /= rotationAxisAngle.angle;
//		rotationAxisAngle.y /= rotationAxisAngle.angle;
//		rotationAxisAngle.z /= rotationAxisAngle.angle;
//		return rotationAxisAngle;
//	}
	
//	// transform / rotates this vector by the quaternion
//	public Vec3 transformBy(Quaternion quaternion) {
//		double t2 =   quaternion.w*quaternion.x;
//		double t3 =   quaternion.w*quaternion.y;
//		double t4 =   quaternion.w*quaternion.z;
//		double t5 =  -quaternion.x*quaternion.x;
//		double t6 =   quaternion.x*quaternion.y;
//		double t7 =   quaternion.x*quaternion.z;
//		double t8 =  -quaternion.y*quaternion.y;
//		double t9 =   quaternion.y*quaternion.z;
//		double t10 = -quaternion.z*quaternion.z;
//		return set(
//				2*( (t8 + t10)*this.x + (t6 -  t4)*this.y + (t3 + t7)*this.z ) + this.x,
//				2*( (t4 +  t6)*this.x + (t5 + t10)*this.y + (t9 - t2)*this.z ) + this.y,
//				2*( (t7 -  t3)*this.x + (t2 +  t9)*this.y + (t5 + t8)*this.z ) + this.z
//		);
//	}
	
//	// transform / rotates this vector by the quaternion inverse
//	public Vec3 transformByInverse(Quaternion quaternion) {
//		double inv = 1.0 / quaternion.length();
//		inv *=inv;
//		double t2 =  -quaternion.w*quaternion.x*inv;
//		double t3 =  -quaternion.w*quaternion.y*inv;
//		double t4 =  -quaternion.w*quaternion.z*inv;
//		double t5 =  -quaternion.x*quaternion.x*inv;
//		double t6 =   quaternion.x*quaternion.y*inv;
//		double t7 =   quaternion.x*quaternion.z*inv;
//		double t8 =  -quaternion.y*quaternion.y*inv;
//		double t9 =   quaternion.y*quaternion.z*inv;
//		double t10 = -quaternion.z*quaternion.z*inv;
//		return set(
//				2*( (t8 + t10)*this.x + (t6 -  t4)*this.y + (t3 + t7)*this.z ) + this.x,
//				2*( (t4 +  t6)*this.x + (t5 + t10)*this.y + (t9 - t2)*this.z ) + this.y,
//				2*( (t7 -  t3)*this.x + (t2 +  t9)*this.y + (t5 + t8)*this.z ) + this.z
//		);
//	}
//	
////		public Vec3.Double add(Vec3 v) { set(x+v.x,y+v.y,z+v.z); return this; }
//	public boolean isValid() {
//		return !(java.lang.Double.isNaN(x) || java.lang.Double.isNaN(y) || java.lang.Double.isNaN(z) ||
//				java.lang.Double.isInfinite(x) || java.lang.Double.isInfinite(y) || java.lang.Double.isInfinite(z));
//	}

	@Override
	public Vector3d point(Vector3d a) {
		return this.set(a.x,a.y,a.z);
	}

	public Vector3d line(Vector3d a, Vector3d b, double r) {
		final double omr = 1.-r; 
		final double x = a.x*omr+b.x*r;
		final double y = a.y*omr+b.y*r;
		final double z = a.z*omr+b.z*r;
		
		this.x=x;
		this.y=y;
		this.z=z;
		return this;
	}
	
	public Vector3d spline(Vector3d a, Vector3d da, Vector3d dd, Vector3d d, double r) {
		final double omr = 1-r; 
				
		final double p0x = a.x, p0y = a.y, p0z = a.z;
		final double p3x = d.x, p3y = d.y, p3z = d.z;
		
		double p1x = (p3x-p0x)/3+p0x, p1y = (p3y-p0y)/3.+p0y, p1z = (p3z-p0z)/3.+p0z;
		double p2x = (p3x-p0x)*2/3+p0x, p2y = (p3y-p0y)*2/3.+p0y, p2z = (p3z-p0z)*2/3.+p0z;
		
		if (da!=null) { p1x = da.x; p1y = da.y; p1z = da.z; }
		if (dd!=null) { p2x = dd.x; p2y = dd.y; p2z = dd.z; }
		
		final double q0x = p0x*r+p1x*omr, q0y = p0y*r+p1y*omr, q0z = p0z*r+p1z*omr;
		final double q1x = p1x*r+p2x*omr, q1y = p1y*r+p2y*omr, q1z = p1z*r+p2z*omr;
		final double q2x = p2x*r+p3x*omr, q2y = p2y*r+p3y*omr, q2z = p2z*r+p3z*omr;
		
		final double r0x = q0x*r+q1x*omr, r0y = q0y*r+q1y*omr, r0z = q0z*r+q1z*omr;
		final double r1x = q1x*r+q2x*omr, r1y = q1y*r+q2y*omr, r1z = q1z*r+q2z*omr;
		
		this.x = r0x*r+r1x*omr;
		this.y = r0y*r+r1y*omr;
		this.z = r0z*r+r1z*omr; 
		
		return this;
	}
	
	
	////////////////////////////////////// Vector3d Specific

	public Vector3d cross(Vector3d a, Vector3d b) {
		this.x = a.y*b.z - a.z*b.y; 
		this.y = a.z*b.x - a.x*b.z;
		this.z = a.x*b.y - a.y*b.x;
		return this;
	}
	
	
}


