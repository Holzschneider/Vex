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
	
	public Quaternion setConcatenation(Quaternion a, Quaternion q) {
		return xyzw(
				a.x*q.x - a.y*q.y - a.z*q.z - a.w*q.w,
				a.x*q.y + a.y*q.x + a.z*q.w - a.w*q.z,
				a.x*q.z + a.z*q.x + a.w*q.y - a.y*q.w,
				a.x*q.w + a.w*q.x + a.y*q.z - a.z*q.y);
	}
	
	
	public Quaternion setRotation(Matrix4d m) {
		return setRotationFromTransposeMatrix(m.m00,m.m10,m.m20,m.m01,m.m11,m.m21,m.m02,m.m12,m.m22);
	}

	public Quaternion setRotation(double m00, double m01, double m02, double m10, double m11, double m12, double m20, double m21, double m22) {
		return setRotationFromTransposeMatrix(m00,m10,m20, m01, m11, m21, m02,m12,m22);
	}
	
    private Quaternion setRotationFromTransposeMatrix(double m00, double m01, double m02, double m10, double m11, double m12, double m20, double m21, double m22) {
        double nm00 = m00, nm01 = m01, nm02 = m02;
        double nm10 = m10, nm11 = m11, nm12 = m12;
        double nm20 = m20, nm21 = m21, nm22 = m22;
        double lenX = 1.0 / Math.sqrt(m00 * m00 + m01 * m01 + m02 * m02);
        double lenY = 1.0 / Math.sqrt(m10 * m10 + m11 * m11 + m12 * m12);
        double lenZ = 1.0 / Math.sqrt(m20 * m20 + m21 * m21 + m22 * m22);
        nm00 *= lenX; nm01 *= lenX; nm02 *= lenX;
        nm10 *= lenY; nm11 *= lenY; nm12 *= lenY;
        nm20 *= lenZ; nm21 *= lenZ; nm22 *= lenZ;
        setFromNormalizedTransposedMatrix(nm00, nm01, nm02, nm10, nm11, nm12, nm20, nm21, nm22);
        
        return this;
    }

    private void setFromNormalizedTransposedMatrix(
    		double m00, double m01, double m02, 
    		double m10, double m11, double m12, 
    		double m20, double m21, double m22)
    {
        double t;
        double tr = m00 + m11 + m22;
        if (tr >= 0.0) {
            t = Math.sqrt(tr + 1.0);
            w = t * 0.5;
            t = 0.5 / t;
            x = (m12 - m21) * t;
            y = (m20 - m02) * t;
            z = (m01 - m10) * t;
        } else {
            if (m00 >= m11 && m00 >= m22) {
                t = Math.sqrt(m00 - (m11 + m22) + 1.0);
                x = t * 0.5;
                t = 0.5 / t;
                y = (m10 + m01) * t;
                z = (m02 + m20) * t;
                w = (m12 - m21) * t;
            } else if (m11 > m22) {
                t = Math.sqrt(m11 - (m22 + m00) + 1.0);
                y = t * 0.5;
                t = 0.5 / t;
                z = (m21 + m12) * t;
                x = (m10 + m01) * t;
                w = (m20 - m02) * t;
            } else {
                t = Math.sqrt(m22 - (m00 + m11) + 1.0);
                z = t * 0.5;
                t = 0.5 / t;
                x = (m02 + m20) * t;
                y = (m21 + m12) * t;
                w = (m01 - m10) * t;
            }
        }
    }

	public Quaternion invert() { 
		return setInverse(this); 
	}
	
	public Quaternion setInverse(Quaternion q) {
        double invNorm = 1.0 / (q.x * q.x + q.y * q.y + q.z * q.z + q.w * q.w);
        this.x = -q.x * invNorm;
        this.y = -q.y * invNorm;
        this.z = -q.z * invNorm;
        this.w = q.w * invNorm;
        return this;
	}

	
	////////////////////////////////////// Quaternion Specific
	
	
	
	public Quaternion concatenate(AxisAngle aa) { return this.rotate( aa.r, aa.x, aa.y, aa.z ); }
	
	public Quaternion rotate(double theta, double x, double y, double z) {
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
		final double s = sin(aa.r / 2.), c= cos(aa.r / 2.);
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

	public Quaternion interpolate(Quaternion target, double alpha) {
		return interpolate(target, alpha);
	}
	
	
	public Quaternion slerp(Quaternion target, double alpha) {
        double cos = x * target.x + y * target.y + z * target.z + w * target.w;
        double abscos = Math.abs(cos);
        double u, v;
        if (1.0 - abscos > 1E-6) {
            double sinsqr = 1.0 - abscos * abscos;
            double sin = 1.0 / sqrt(sinsqr);
            double theta = atan2(sinsqr * sin, abscos);
            u = sin((1.0 - alpha) * theta) * sin;
            v = sin(alpha * theta) * sin;
        } else {
            u = 1.0 - alpha;
            v = alpha;
        }
        v = cos >= 0.0 ? v : -v;
        this.x = u * x + v * target.x;
        this.y = u * y + v * target.y;
        this.z = u * z + v * target.z;
        this.w = u * w + v * target.w;
        
		return this;
	}

	
	///XXX Untested
	public Quaternion spline(Quaternion a, Quaternion da, Quaternion dd, Quaternion d, double r) {
		this.set(a).slerp(d, r);
		
		
		
		return this;
	}
	
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
