package de.dualuse.vecmath;

import static java.lang.Math.*;

import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.util.regex.Matcher;

public class Quaternion extends Tuple<Quaternion> implements Interpolatable<Quaternion>, Serializable {
	static final double FLT_EPSILON = 0.0000000001;
	
	private static final long serialVersionUID = 1L;	
	
	public double x,y,z,w;
	
	public Quaternion() { identity(); }
	public Quaternion(double x, double y, double z, double w) { 
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	static public Quaternion from(Object... objs) {
		StringBuilder b = new StringBuilder(16*3);
		for(Object o: objs)
			b.append(o).append(' ');
		
		return fromString(b.toString());
	}
	
	static public Quaternion fromString(String r) {
		Matcher m = Scalar.DECIMAL.matcher(r);
		m.find(); double x = Double.parseDouble(m.group());
		m.find(); double y = Double.parseDouble(m.group());
		m.find(); double z = Double.parseDouble(m.group());
		m.find(); double w = Double.parseDouble(m.group());
		
		return new Quaternion().xyzw(x, y, z, w);
	}

	@Override 
	public String toString() {
		return x+" "+y+" "+z+" "+w;
	}
	
	
	@Override
	public Quaternion clone() {
		return new Quaternion().xyzw(x,y,z,w);
	}
	

	@Override
	public int hashCode() {
		return new Double(x*x+y*y+z*z+w*w).hashCode();
	}
	
	@Override
	public boolean elementsEqual(Quaternion a) {
		return x==a.x && y==a.y && z==a.z && w==a.w;
	}


	public Quaternion setElements(double x, double y, double z, double w) { return this.xyzw(x, y, z, w); }
	public Quaternion xyzw(double x, double y, double z, double w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		
		return this;
	}
	
	public Quaternion wxyz(double w, double x, double y, double z) {
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
		
		return this;
	}
	

	public<T> T get(Value4<T> v) { return v.set(this.x, this.y, this.z, this.w); }

	
	///////////////////////////////////////////////////////
	
	public Quaternion identity() {
		return this.xyzw(0, 0, 0, 1);
	}
	
	
	/**
	 * Transforms this quaternion by the given quaternion
	 */
	public Quaternion concatenate(Quaternion q) {
		return xyzw(
				x*q.x - y*q.y - z*q.z - w*q.w,
				x*q.y + y*q.x + z*q.w - w*q.z,
				x*q.z + z*q.x + w*q.y - y*q.w,
				x*q.w + w*q.x + y*q.z - z*q.y);
	}
	
	public Quaternion concatenation(Quaternion a, Quaternion q) {
		return xyzw(
				a.x*q.x - a.y*q.y - a.z*q.z - a.w*q.w,
				a.x*q.y + a.y*q.x + a.z*q.w - a.w*q.z,
				a.x*q.z + a.z*q.x + a.w*q.y - a.y*q.w,
				a.x*q.w + a.w*q.x + a.y*q.z - a.z*q.y);
	}
	

	public Quaternion invert() { 
		return inversion(this); 
	}
	
	public Quaternion inversion(Quaternion q) {
        double invNorm = 1.0 / (q.x * q.x + q.y * q.y + q.z * q.z + q.w * q.w);
        this.x = -q.x * invNorm;
        this.y = -q.y * invNorm;
        this.z = -q.z * invNorm;
        this.w = q.w * invNorm;
        return this;
	}

	
	////////////////////////////////////// Quaternion Specific
	public Quaternion concatenate(AxisAngle aa) { return this.rotate( aa.t, aa.x, aa.y, aa.z ); }
	
	private Quaternion rotate(double theta, double x, double y, double z) {
		final double s = sin(theta / 2.), c = cos(theta / 2.), l = sqrt(x*x+y*y+z*z);
		final double qx = x*s/l, qy = y*s/l, qz = z*s/l, qw = c;
		
		//concatenate the quaternion (qx,qy,qz,qw) to this quaternion
		return this.xyzw( 
				x*qx - y*qy - z*qz - w*qw,
				x*qy + y*qx + z*qw - w*qz,
				x*qz + z*qx + w*qy - y*qw,
				x*qw + w*qx + y*qz - z*qy);
	}
	
	
	public Quaternion set(AxisAngle aa) {
		final double s = sin(aa.t / 2.), c= cos(aa.t / 2.);
		final double x = aa.x, y = aa.y, z = aa.z, l = sqrt(x*x+y*y+z*z);
		
		return this.xyzw(x*s/l, y*s/l, z*s/l, c);
	}	

	// transform / rotates this vector by the quaternion
	public Vector3d transform(Vector3d v) {
		final double t2 =   w*x, t3 =   w*y, t4 =   w*z;
		final double t5 =  -x*x, t6 =   x*y, t7 =   x*z;
		final double t8 =  -y*y, t9 =   y*z, t10 = -z*z;
		return v.xyz(
				2*( (t8 + t10)*v.x + (t6 -  t4)*v.y + (t3 + t7)*v.z ) + v.x,
				2*( (t4 +  t6)*v.x + (t5 + t10)*v.y + (t9 - t2)*v.z ) + v.y,
				2*( (t7 -  t3)*v.x + (t2 +  t9)*v.y + (t5 + t8)*v.z ) + v.z
		);
	}

	// transform / rotates this vector by the quaternion
	public Vector3d transformInverse(Vector3d v) {
//		double invNorm = 1.0 / (this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w);
//        double x = -this.x * invNorm, y = -this.y * invNorm, z = -this.z * invNorm, w = this.w * invNorm;
        double x = -this.x, y = -this.y, z = -this.z , w = this.w;

		final double t2 =   w*x, t3 =   w*y, t4 =   w*z;
		final double t5 =  -x*x, t6 =   x*y, t7 =   x*z;
		final double t8 =  -y*y, t9 =   y*z, t10 = -z*z;
		return v.xyz(
				2*( (t8 + t10)*v.x + (t6 -  t4)*v.y + (t3 + t7)*v.z ) + v.x,
				2*( (t4 +  t6)*v.x + (t5 + t10)*v.y + (t9 - t2)*v.z ) + v.y,
				2*( (t7 -  t3)*v.x + (t2 +  t9)*v.y + (t5 + t8)*v.z ) + v.z
		);
	}

	
	////////////////////////////////////// Interpolatable Specific

	@Override
	public Quaternion set(Quaternion a) {
		return xyzw(a.x,a.y,a.z,a.w);
	}
	
	///XXX Untested
	@Override
	public Quaternion spline(Quaternion a, Quaternion da, Quaternion dd, Quaternion d, double r) {
		double ax = a.x, ay = a.y, az = a.z, aw = a.w;
		double dx = d.x, dy = d.y, dz = a.z, dw = d.w;
		
		a.line(a, da, r); //XXX sux, since it overwrites a & d for a brief moment -> so it's not thread-safe
		d.line(dd, d,r);
		
		this.line(a,d,r);
		
		a.xyzw(ax, ay, az, aw);
		d.xyzw(dx, dy, dz, dw);
		
		return this;
	}
	
	@Override
	public Quaternion line(Quaternion from, Quaternion to, double t) {
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
	

	
}
