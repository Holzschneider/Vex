package de.dualuse.vecmath;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

import java.util.Arrays;
import java.util.regex.Matcher;

public class Vector3d	extends Vector<Vector3d>
						implements	Value3<Vector3d>,
									VectorAlgebra<Vector3d>, 
									Interpolatable<Vector3d>,
									java.io.Serializable 
{
	private static final long serialVersionUID = 1L;
	
	public double x, y, z;

//==[ Constructors ]================================================================================
	public Vector3d() {}
	
	public Vector3d(double x, double y, double z) {
		this.x=x; this.y=y; this.z=z;
	}

	
	public static Vector3d of(double x, double y, double z) {
		return new Vector3d(x,y,z);
	}
	
	public static Vector3d fromElements(double x, double y, double z) {
		return new Vector3d(x,y,z);
	}

//==[ Getter & Setter ]=============================================================================
	
	public Vector3d set(double x, double y, double z) { return this.xyz(x, y, z); } 
	public Vector3d xyz(double x, double y, double z) { this.x=x; this.y=y; this.z=z; return this; }
	public Vector3d xy(double x, double y) { this.x=x; this.y=y; return this; }
	public Vector3d xz(double x, double z) { this.x=x; this.z=z; return this; }
	public Vector3d yz(double y, double z) { this.y=y; this.z=z; return this; }
	
//==[ Tuple<Vector3d> ]=============================================================================
	
	static public Vector3d from(Object... objs) {
		StringBuilder b = new StringBuilder(16*3);
		for(Object o: objs)
			b.append(o).append(' ');
		
		return fromString(b.toString());
	}
	
	static public Vector3d fromString(String r) {
		Matcher m = Scalar.DECIMAL.matcher(r);
		m.find(); double x = Double.parseDouble(m.group());
		m.find(); double y = Double.parseDouble(m.group());
		m.find(); double z = Double.parseDouble(m.group());
		
		return new Vector3d().xyz(x, y, z);
	}

	@Override public String toString() {
		return x+" "+y+" "+z;
	}
	
	@Override public Vector3d clone() {
		return new Vector3d(x,y,z);
	}
	
	@Override public int hashCode() {
		return new Double(x*x+y*y+z*z).hashCode();
	}
	
	@Override public boolean elementsEqual(Vector3d a) {
		return x==a.x && y==a.y && z==a.z;
	}
	
	@Override public Vector3d set(Vector3d a) {
		return this.xyz(a.x,a.y,a.z);
	}
	
	
	public interface ConsumerDouble3 {
		void set(double x, double y, double z);
	}
	

	public Vector3d read(ConsumerDouble3 cd3) { cd3.set(this.x, this.y, this.z); return this; }

	
	public<T> T get(Value3<T> v) { return v.set(this.x, this.y, this.z); }

	public double[] get(double[] v) { v[0]=x; v[1]=y; v[2]=z; return v; };


//==[ VectorAlgebra<Vector3d> ]=====================================================================
	
	public Vector3d addVector(Vector3d v) { return this.addElements(v.x, v.y, v.z); }
	public Vector3d addElements(double x, double y, double z) { this.x+=x; this.y+=y; this.z+=z; return this; }
	
	
	@Override public Vector3d sum(Vector3d a, Vector3d b) { return this.xyz(a.x+b.x,a.y+b.y,a.z+b.z); }
	@Override public Vector3d difference(Vector3d a, Vector3d b) { return this.xyz(a.x-b.x, a.y-b.y, a.z-b.z); }

	
	@Override public Vector3d add(Vector3d v) { return this.addElements(v.x,v.y,v.z); }
	public Vector3d add(double x, double y, double z) { return this.addElements(x,y,z); }
	
	@Override public Vector3d adds(Vector3d v, double s) { return this.addElements(v.x*s,v.y*s,v.z*s); }
	public Vector3d adds(double x, double y, double z, double s) { return this.addElements(x*s, y*s, z*s); }
	
	@Override public Vector3d sub(Vector3d v) { return this.addElements(-v.x, -v.y, -v.z); }
	public Vector3d sub(double x, double y, double z) { return this.addElements(-x, -y, -z); }
	
	@Override public Vector3d scale(double s) { this.x*=s; this.y*=s; this.z*=s; return this; }
	@Override public Vector3d normalize() { return scale(1./length()); }

	public double dot(double x, double y, double z) { return this.x*x+this.y*y+this.z*z; } 
	@Override public double dot(Vector3d v) { return dot(v.x,v.y,v.z); }

	@Override public double length() { return Math.sqrt(x*x+y*y+z*z); }

	@Override public double distance(Vector3d v) { return Math.sqrt(quadrance(v)); }
	public double distance(double x, double y, double z) { return Math.sqrt(quadrance(x,y,z)); }
	
	@Override public double quadrance(Vector3d v) { return quadrance(v.x,v.y,v.z); }
	public double quadrance(double x, double y, double z) { 
		final double dx = x-this.x, dy = y-this.y, dz = z-this.z; 
		return dx*dx+dy*dy+dz*dz; 
	}
	
	@Override public double norm(double p) { 
		return pow(pow(abs(x),p)+pow(abs(y),p)+pow(abs(z),p),1.0/p);
	}
	
//==[ Interpolatable<Vector3d> ]====================================================================	

	public Vector3d lerp(Vector3d b, double alpha) {
		final double oma = 1-alpha;
		return this.xyz(this.x*oma+b.x*alpha, this.y*oma+b.y*alpha, this.z*oma+b.z*alpha);
	}

//	@Override public Vector3d line(Vector3d a, Vector3d b, double r) {
//		final double omr = 1.-r; 
//		final double x = a.x*omr+b.x*r;
//		final double y = a.y*omr+b.y*r;
//		final double z = a.z*omr+b.z*r;
//		
//		this.x=x;
//		this.y=y;
//		this.z=z;
//		return this;
//	}
	
	@Override public Vector3d spline(Vector3d a, Vector3d da, Vector3d dd, Vector3d d, double t) {
		final double omr = t, r = 1-omr;
				
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

//==[ Convenience Methods ]=========================================================================
	
	public Vector3d augment(Vector2d v) { return this.xyz(v.x, v.y, 1); }
	public Vector3d transformation(Matrix3d m) { return m.transform(this); }
	public Vector3d projection(Matrix4d m) { return m.project(this); }
	
//==[ Vector3d Specific ]===========================================================================

	public Vector3d cross(Vector3d that) {
		return cross(this,that);
	}

	public Vector3d cross(Vector3d a, Vector3d b) {
		double ax = a.x, bx = b.x;
		double ay = a.y, by = b.y;
		double az = a.z, bz = b.z;
		
		this.x = ay*bz - az*by; 
		this.y = az*bx - ax*bz;
		this.z = ax*by - ay*bx;
		return this;
	}
	
}


