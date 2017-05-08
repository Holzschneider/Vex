package de.dualuse.vecmath;

import java.io.Serializable;

public class Quaternion implements Serializable, Linear<Quaternion> {
	static final double FLT_EPSILON = 0.0000000001;

	
	private static final long serialVersionUID = 1L;	
	
	public double x,y,z,w;
	
	public Quaternion() { }
	
	public Quaternion(double x, double y, double z, double w) { set(x,y,z,w); }
	public Quaternion(Quaternion q) { set(q.x,q.y,q.z,q.w); }

	public Quaternion set(double x, double y, double z, double w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		
		return this;
	}
	
	
	//Quaternion Maths
	public Quaternion invert() { 
		final double inv = 1.0 / Math.sqrt(x*x+y*y+z*z+w*w); 
		return this.set(-this.x*inv, -this.y*inv, -this.z*inv, this.w*inv); 
	}

	@Override
	public Linear<Quaternion> line(Quaternion to, Quaternion from, double t) {
		double toSign = 1.;
		double dot = from.x * to.x + from.y * to.y + from.z * to.z + from.w * to.w;
		if (dot < 0.) {
			dot = -dot;
			toSign = -1.;
		}

		// fallback to linear interpolation, in case we run out of floating
		// point precision
		double scale0 = 1.0 - t;
		double scale1 = t;

		if ((1.0f - dot) > FLT_EPSILON) {
			double angle = Math.acos(dot);
			double sinangle = Math.sin(angle);
			if (sinangle > FLT_EPSILON) {
				// calculate spherical interpolation
				scale0 = Math.sin((1.0 - t) * angle) / sinangle;
				scale1 = Math.sin(t * angle) / sinangle;
			}
		}

		this.x = from.x*scale0+to.x*scale1*toSign;
		this.y = from.y*scale0+to.y*scale1*toSign;
		this.z = from.z*scale0+to.z*scale1*toSign;
		this.w = from.w*scale0+to.w*scale1*toSign;

		return this;
	}
	
	public Quaternion setToRotation(AxisAngle aa) {
		final double sineval = Math.sin(aa.theta / 2.);
		final double x = aa.x, y = aa.y, z = aa.z;
		final double l = Math.sqrt(x*x+y*y+z*z);
		if (l == 0)
			return this.set(0, 0, 0, 1);
		return this.set(x*sineval/l, y*sineval/l, z*sineval/l, Math.cos(aa.theta / 2.));
	}	

	//TODO Verify this
	public Quaternion setToTransform(Matrix3 t) {
		double  T = t.m00 + t.m11 + t.m22 + 1.;

		// If the trace of the t.matrix is greater than zero, then the result is:
		if (T>0.) {
		      double S = 0.5 / Math.sqrt(T);
		      
		      return this.set(( t.m21 - t.m12 ) * S,( t.m02 - t.m20 ) * S,( t.m10 - t.m01 ) * S,0.25 / S);
		} else { 
			//If the trace of the t.matrix is less than or equal to zero then identify which t.major diagonal elet.ment has the greatest value.
			if ((t.m00 > t.m11)&&(t.m00 > t.m22)) { 
			   final double S = Math.sqrt( 1.0 + t.m00 - t.m11 - t.m22 ) * 2; // S=4*qx 
			   final double qw = (t.m21 - t.m12) / S;
			   final double qx = 0.25 * S;
			   final double qy = (t.m01 + t.m10) / S; 
			   final double qz = (t.m02 + t.m20) / S;
			   return this.set(qx,qy,qz,qw);
			} else if (t.m11 > t.m22) { 
			   final double S = Math.sqrt( 1.0 + t.m11 - t.m00 - t.m22 ) * 2; // S=4*qy
			   final double qw = (t.m02 - t.m20) / S;
			   final double qx = (t.m01 + t.m10) / S; 
			   final double qy = 0.25 * S;
			   final double qz = (t.m12 + t.m21) / S;
			   return this.set(qx,qy,qz,qw);
			} else { 
			   final double S = Math.sqrt( 1.0 + t.m22 - t.m00 - t.m11 ) * 2; // S=4*qz
			   final double qw = (t.m10 - t.m01) / S;
			   final double qx = (t.m02 + t.m20) / S; 
			   final double qy = (t.m12 + t.m21) / S; 
			   final double qz = 0.25 * S;
			   return this.set(qx,qy,qz,qw);
			}
		}
	}
	

	
	// transform / rotates this vector by the quaternion
	public Vec3 transform(Vec3 v) {
		final double t2 =   w*x, t3 =   w*y, t4 =   w*z;
		final double t5 =  -x*x, t6 =   x*y, t7 =   x*z;
		final double t8 =  -y*y, t9 =   y*z, t10 = -z*z;
		return v.set(
				2*( (t8 + t10)*v.x + (t6 -  t4)*v.y + (t3 + t7)*v.z ) + v.x,
				2*( (t4 +  t6)*v.x + (t5 + t10)*v.y + (t9 - t2)*v.z ) + v.y,
				2*( (t7 -  t3)*v.x + (t2 +  t9)*v.y + (t5 + t8)*v.z ) + v.z
		);
	}
	
	
	/**
	 * Transforms this quaternion by the given quaternion
	 */
	public Quaternion concatenate(Quaternion q) {
		return set(
				x*q.x - y*q.y - z*q.z - w*q.w,
				x*q.y + y*q.x + z*q.w - w*q.z,
				x*q.z + z*q.x + w*q.y - y*q.w,
				x*q.w + w*q.x + y*q.z - z*q.y);
	}
	
	public Quaternion concatenation(Quaternion a, Quaternion q) {
		return set(
				a.x*q.x - a.y*q.y - a.z*q.z - a.w*q.w,
				a.x*q.y + a.y*q.x + a.z*q.w - a.w*q.z,
				a.x*q.z + a.z*q.x + a.w*q.y - a.y*q.w,
				a.x*q.w + a.w*q.x + a.y*q.z - a.z*q.y);
	}
	
	
	public double lengthSquared() {
		return x*x+y*y+z*z+w*w;
	}
	
	public double length() {
		return Math.sqrt(x*x+y*y+z*z+w*w);
	}
	
	public Quaternion scale(double s) {
		this.x*=s;
		this.y*=s;
		this.z*=s;
		this.w*=s;
		return this;
	}
	
	public Quaternion normalize() { return scale(1./length()); }

	@Override
	public Quaternion set(Quaternion q) {
		return this.set(q.x,q.y,q.z,q.w);
	}

	@Override
	public Quaternion get(Variable<Quaternion> q) {
		q.set(this);
		return this;
	}

	@Override
	public Quaternion clone() {
		return new Quaternion(x,y,z,w);
	}



//	public Quaternion rotate(Vec3 v, double scale) {
//		final double x = getX(), y = getY(), z = getZ(), w = getW();
//		return this.set(v.x*scale, v.y*scale, v.z*scale, 0).transformBy(x,y,z,w).scale(0.5);
//		
//		/*
//		Quaternion q = new Quaternion.Double(v.x * scale, v.y * scale, v.z * scale, 0);
//		q.transformBy(this);
//		x += q.x * 0.5; y += q.y * 0.5; z += q.z * 0.5; w += q.w * 0.5;
//		
//		return this.set(x,y,z,w);
//		*/
//	}


	

	
	
}
