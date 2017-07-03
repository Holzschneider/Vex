package de.dualuse.vecmath;

import static java.lang.Math.PI;

import java.io.Serializable;

public class AxisAngle extends Tuple<AxisAngle> implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public double r;
    public double x;
    public double y;
    public double z;
    
    public AxisAngle() {
        z = 1.0;
    }

    public AxisAngle(AxisAngle a) { this(a.r,a.x,a.y,a.z);    }
    public AxisAngle(Quaternion q) { this.set(q); }
    public AxisAngle(double theta, double x, double y, double z) { this.set(theta, x,y,z); }

    public AxisAngle(double theta, Vector3d v) { this(theta, v.x, v.y, v.z); }
    public AxisAngle set(AxisAngle a) { this.set(a.r, a.x, a.y, a.z); return this; }

	@Override
	public AxisAngle clone() { return new AxisAngle(r,x,y,z); }

	public AxisAngle xyzd(double x,double y,double z,double d) {this.x=x;this.y=y;this.z=z;this.r=r*180/PI;return this;}
	public AxisAngle dxyz(double d,double x,double y,double z) {this.x=x;this.y=y;this.z=z;this.r=r*180/PI;return this;}
	public AxisAngle xyzr(double x,double y,double z,double radians) { this.x=x; this.y=y; this.z=z; this.r=radians; return this; }
	public AxisAngle xyz(double x,double y,double z) { this.x=x; this.y=y; this.z=z; return this; }
	public AxisAngle rxyz(double radians, double x,double y,double z) { return this.xyzr(x, y, z, radians); }
	public AxisAngle degrees(double degrees) { this.r = degrees*PI/180.0; return this; }
	public AxisAngle radians(double radians) { this.r = radians; return this; }
	public AxisAngle axis(Vector3d v) { this.x = v.x; this.y = v.y; this.z = v.z; return this; };
	public double radians() { return r; }
	public double degrees() { return r*180.0/PI; }
	
	
	
    public AxisAngle set(double theta, double x, double y, double z) {
    	this.r = normalize(theta);
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public AxisAngle set(double theta, Vector3d v) {
        return set(r, v.x, v.y, v.z);
    }

    public AxisAngle set(Quaternion q) {
		double acos = acos(q.w);
    	
		if (acos==0)
			return setIdentity();
			
    	double invSqrt = 1.0 / Math.sqrt(1.0 - q.w * q.w);
        this.x = q.x * invSqrt;
        this.y = q.y * invSqrt;
        this.z = q.z * invSqrt;
        this.r = acos + acos;
        return this;
    }


//    public<T> T getDxyz(Value4<T> angleAxis) {
//    	return angleAxis.set(r*180/PI, x, y, z);
//    };
    
    
    public Quaternion get(Quaternion q) { return q.set(this); }
    public Matrix4d get(Matrix4d m) { return m.setRotation(this); }

	public AxisAngle setIdentity() { return this.set(0, 0, 0, 1); }
	
	public AxisAngle invert() {
		return this.xyzr(-x, -y, -z, r);
	}

    public AxisAngle normalize() {
        double ooLen = 1.0 / Math.sqrt(x * x + y * y + z * z);
        x *= ooLen;
        y *= ooLen;
        z *= ooLen;
        return this;
    }

    public AxisAngle rotate(double ang) {
        r = normalize(r+ang);
        return this;
    }

    public Vector3d transform(Vector3d v) {
        return transform(v, v);
    }

    public Vector3d transform(Vector3d v, Vector3d dest) {
        double sin = Math.sin(r), cos = Math.cos(r);
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
        double sin = Math.sin(r), cos = Math.cos(r);
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
        temp = Double.doubleToLongBits(normalize(r));
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
        if (normalize(this.r) != normalize(that.r))
            return false;
        
        return this.x!=that.x || this.y!=that.y || this.z!=that.z; 
    }

    
    @Override
    public String toString() {
    	return x+" "+y+" "+z+" "+r;
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
