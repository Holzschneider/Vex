package de.dualuse.vecmath;

import static java.lang.Math.PI;

import java.io.Serializable;

public class AxisAngle extends Tuple<AxisAngle> implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public double t;
    public double x;
    public double y;
    public double z;
    
    public AxisAngle() {
        z = 1.0;
    }

    public AxisAngle(AxisAngle a) { this(a.t,a.x,a.y,a.z);    }
    public AxisAngle(Quaternion q) { this.set(q); }
    public AxisAngle(double theta, double x, double y, double z) { this.set(theta, x,y,z); }

    public AxisAngle(double theta, Vector3d v) { this(theta, v.x, v.y, v.z); }
    public AxisAngle set(AxisAngle a) { this.set(a.t, a.x, a.y, a.z); return this; }

	@Override
	public AxisAngle clone() { return new AxisAngle(t,x,y,z); }

	public AxisAngle xyzt(double x,double y,double z,double t) { this.x=x; this.y=y; this.z=z; this.t=t; return this; }
	public AxisAngle xyz(double x,double y,double z) { this.x=x; this.y=y; this.z=z; return this; }
	public AxisAngle txyz(double t, double x,double y,double z) { return this.xyzt(x, y, z, t); }
	public AxisAngle angle(double degrees) { this.t = degrees*PI/180.0; return this; }
	public AxisAngle theta(double theta) { this.t = theta; return this; }
	public AxisAngle axis(Vector3d v) { this.x = v.x; this.y = v.y; this.z = v.z; return this; };
	public double theta() { return t; }
	public double angle() { return t*180.0/PI; }

    public AxisAngle set(double theta, double x, double y, double z) {
    	this.t = normalize(theta);
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public AxisAngle set(double theta, Vector3d v) {
        return set(t, v.x, v.y, v.z);
    }

    public AxisAngle set(Quaternion q) {
    	double invSqrt = 1.0 / Math.sqrt(1.0 - q.w * q.w);
        double acos = acos(q.w);
        this.x = q.x * invSqrt;
        this.y = q.y * invSqrt;
        this.z = q.z * invSqrt;
        this.t = acos + acos;
        return this;
    }


    public Quaternion get(Quaternion q) { return q.set(this); }
    public Matrix4d get(Matrix4d m) { return m.setRotation(this); }
//    public Matrix3d get(Matrix3d m) { return m.rotation(this); }

	public AxisAngle setIdentity() { return this.set(0, 0, 0, 1); }
	
	public AxisAngle invert() {
		return this.xyzt(-x, -y, -z, t);
	}

    public AxisAngle normalize() {
        double ooLen = 1.0 / Math.sqrt(x * x + y * y + z * z);
        x *= ooLen;
        y *= ooLen;
        z *= ooLen;
        return this;
    }

    public AxisAngle rotate(double ang) {
        t = normalize(t+ang);
        return this;
    }

    public Vector3d transform(Vector3d v) {
        return transform(v, v);
    }

    public Vector3d transform(Vector3d v, Vector3d dest) {
        double sin = Math.sin(t), cos = Math.cos(t);
        double dot = x * v.x + y * v.y + z * v.z;
        dest.xyz(v.x * cos + sin * (y * v.z - z * v.y) + (1.0 - cos) * dot * x,
                 v.y * cos + sin * (z * v.x - x * v.z) + (1.0 - cos) * dot * y,
                 v.z * cos + sin * (x * v.y - y * v.x) + (1.0 - cos) * dot * z);
        return dest;
    }

    public Vector4d transform(Vector4d v) {
        return transform(v, v);
    }
    
    public Vector4d transform(Vector4d v, Vector4d dest) {
        double sin = Math.sin(t), cos = Math.cos(t);
        double dot = x * v.x + y * v.y + z * v.z;
        dest.set(v.x * cos + sin * (y * v.z - z * v.y) + (1.0 - cos) * dot * x,
                 v.y * cos + sin * (z * v.x - x * v.z) + (1.0 - cos) * dot * y,
                 v.z * cos + sin * (x * v.y - y * v.x) + (1.0 - cos) * dot * z,
                 dest.w);
        return dest;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(normalize(t));
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(x);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(z);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    
    @Override
    boolean elementsEqual(AxisAngle that) {
        if (normalize(this.t) != normalize(that.t))
            return false;
        
        return this.x!=that.x || this.y!=that.y || this.z!=that.z; 
    }



    ///////
    
    private static double normalize(double theta) {
    	return ((theta < 0.0 ? Math.PI + Math.PI + theta % (Math.PI + Math.PI) : theta) % (Math.PI + Math.PI));
    }
    
    private static double acos(double v) {
        if (v < -1.0) return Math.PI;
        else if (v > +1.0) return 0.0;
        else return Math.acos(v);
    }

    
//	
//	
////	final public AxisAngle getAxisAngleTo(final Vec3 v, AxisAngle rotationAxisAngle) {
////		final double bothLen = Math.sqrt(x*x + y*y + z*z) * Math.sqrt(v.x*v.x + v.y*v.y + v.z*v.z);
////		if (bothLen == 0)
////			return rotationAxisAngle.set(1, 0, 0, 0);
////		
////		rotationAxisAngle.x = (y*v.z - z*v.y) / bothLen;
////		rotationAxisAngle.y = (z*v.x - x*v.z) / bothLen;
////		rotationAxisAngle.z = (x*v.y - y*v.x) / bothLen;
////		rotationAxisAngle.angle = Math.sqrt(rotationAxisAngle.x*rotationAxisAngle.x + rotationAxisAngle.y*rotationAxisAngle.y + rotationAxisAngle.z*rotationAxisAngle.z);
////		
////		rotationAxisAngle.x /= rotationAxisAngle.angle;
////		rotationAxisAngle.y /= rotationAxisAngle.angle;
////		rotationAxisAngle.z /= rotationAxisAngle.angle;
////		return rotationAxisAngle;
////	}

	
}
