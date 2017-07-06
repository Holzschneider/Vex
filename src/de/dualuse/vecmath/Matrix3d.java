package de.dualuse.vecmath;

import static java.lang.Math.*;

import java.io.Serializable;

public class Matrix3d 	extends   	Matrix<Matrix3d> 
						implements	Serializable, 
									Functionals.Matrix3d.Function<Matrix3d>,
									Functionals.Matrix3d.Consumer,
									Functionals.Matrix3d
{
	private static final long serialVersionUID = 1L;

	public double m00,m01,m02;
	public double m10,m11,m12;
	public double m20,m21,m22;
	
//==[ Constructors ]================================================================================
	
	public Matrix3d() {
		setIdentity();
	}

	public Matrix3d(
			double m00,double m01,double m02,
			double m10,double m11,double m12,
			double m20,double m21,double m22
		) {
		setElements(m00, m01, m02, m10, m11, m12, m20, m21, m22);
	}

	public Matrix3d(Matrix3d m) {
		setElements(
			m.m00, m.m01, m.m02, 
			m.m10, m.m11, m.m12,
			m.m20, m.m21, m.m22 
		);
	}
	
	public static Matrix3d of(
			double m00,double m01,double m02,
			double m10,double m11,double m12,
			double m20,double m21,double m22
		) {
		return new Matrix3d().setElements(m00, m01, m02, m10, m11, m12, m20, m21, m22);
	}
	
//==[ Element-wise Operations ]=====================================================================
	
	public void accept(double m00, double m01, double m02, 
			double m10, double m11, double m12, 
			double m20, double m21, double m22) 
	{ this.setElements(m00, m01, m02, m10, m11, m12, m20, m21, m22); }
	
	
	public Matrix3d apply(double m00, double m01, double m02, 
			double m10, double m11, double m12, 
			double m20, double m21, double m22) 
	{ return this.setElements(m00, m01, m02, m10, m11, m12, m20, m21, m22); }
	
	
	public Matrix3d setElements(
			double m00, double m01, double m02, 
			double m10, double m11, double m12, 
			double m20, double m21, double m22
			) 
	{
		this.m00 = m00; this.m01 = m01; this.m02 = m02;
		this.m10 = m10; this.m11 = m11; this.m12 = m12;
		this.m20 = m20; this.m21 = m21; this.m22 = m22;

		return this;
	}

	public Matrix3d setRows(double[] m) {
		return setElements(
				m[ 0], m[ 1], m[ 2],
				m[ 3], m[ 4], m[ 5],
				m[ 6], m[ 7], m[ 8]
		);
	}
	
	public Matrix3d setColumns(double[] m) {
		return setElements(
				m[ 0], m[ 3], m[ 6],
				m[ 1], m[ 4], m[ 7],
				m[ 2], m[ 5], m[ 8]
		);
	}
	
	public Matrix3d setRows( Vector3d m0, Vector3d m1, Vector3d m2 ) {
		return this.setElements(m0.x, m0.y, m0.z, m1.x, m1.y, m1.z, m2.x, m2.y, m2.z);
	}

	public Matrix3d setRows( double[] m0, double[] m1, double[] m2 ) {
		return this.setElements(m0[0], m0[1], m0[2], m1[0], m1[1], m1[2], m2[0], m2[1], m2[2]);
	}
	
	public Matrix3d setRows( double[][] m ) {
		return this.setElements(m[0][0], m[0][1], m[0][2], m[1][0], m[1][1], m[1][2], m[2][0], m[2][1], m[2][2]);
	}
	
	public Matrix3d setColumns( Vector3d c0, Vector3d c1, Vector3d c2 ) {
		return this.setElements(c0.x, c1.x, c2.x, c0.y, c1.y, c2.y, c0.z, c1.z, c2.z);
	}

	public Matrix3d setColumns( double[] m0, double[] m1, double[] m2 ) {
		return this.setElements(m0[0], m1[0], m2[0], m0[1], m1[1], m2[1], m0[2], m1[2], m2[2]);
	}
	
	public Matrix3d setColumns( double[][] m ) {
		return this.setElements(m[0][0], m[1][0], m[2][0], m[0][1], m[1][1], m[2][1], m[0][2], m[1][2], m[2][2]);
	}
	
	/////////////////////////

	
	
//==[ Tuple<Matrix3d> ]=============================================================================
	public String toFormattedString() {
		return	m00+" "+m01+" "+m02+"\n"+
				m10+" "+m11+" "+m12+"\n"+
				m20+" "+m21+" "+m22;
	}
	
	@Override public String toString() {
		return	m00+" "+m01+" "+m02+" "+
				m10+" "+m11+" "+m12+" "+
				m20+" "+m21+" "+m22;
	}
	
	@Override public int hashCode() {
		return new Double(
			m00*m00+m01*m01+m02*m02+
			m10*m10+m11*m11+m12*m12+
			m20*m20+m21*m21+m22*m22
		).hashCode();
	}
	
	@Override public boolean elementsEqual(Matrix3d m) {
		return
			m.m00 == m00 && m.m01 == m01 && m.m02 == m02 &&
			m.m10 == m10 && m.m11 == m11 && m.m12 == m12 &&
			m.m20 == m20 && m.m21 == m21 && m.m22 == m22;
	}
	
	
	@Override public Matrix3d self() { return this; }
	
	@Override public Matrix3d clone() {
		return new Matrix3d().setElements(
			this.m00, this.m01, this.m02, 
			this.m10, this.m11, this.m12, 
			this.m20, this.m21, this.m22
		);
	}
	
	@Override public Matrix3d set(Matrix3d a) {
		this.m00 = a.m00; this.m01 = a.m01; this.m02 = a.m02;
		this.m10 = a.m10; this.m11 = a.m11; this.m12 = a.m12;
		this.m20 = a.m20; this.m21 = a.m21; this.m22 = a.m22;

		return this;
	}
	
	public double[][] toRows( double[][] rowArray ) {
		double m[][] = rowArray;
		
		m[0][0] = m00; m[0][1] = m01; m[0][2] = m02;
		m[1][0] = m10; m[1][1] = m11; m[1][2] = m12;
		m[2][0] = m20; m[2][1] = m21; m[2][2] = m22;
		
		return rowArray;
	}
	
	public double[][] toColumns( double[][] rowArray ) {
		double m[][] = rowArray;
		
		m[0][0] = m00; m[0][1] = m10; m[0][2] = m20;
		m[1][0] = m01; m[1][1] = m11; m[1][2] = m21;
		m[2][0] = m02; m[2][1] = m12; m[2][2] = m22;
		
		return rowArray;
	}
	
	
	public<T> T to(Matrix3d.Function<T> v) {
		return v.apply(m00, m01, m02, m10, m11, m12, m20, m21, m22);
	}
	
	
//==[ MatrixAlgebra<Matrix3d> ]=====================================================================	

	@Override public Matrix3d setZero() {
		return this.setElements(
			0,0,0,
			0,0,0,
			0,0,0
		);
	}

	@Override public Matrix3d setIdentity() {
		return this.setElements(
			1,0,0,
			0,1,0,
			0,0,1
		);
	}

	@Override public Matrix3d invert() {
		return this.setInverse(this);
	}
	
	@Override public Matrix3d setInverse(Matrix3d m) {
		final double a = m.m00, b = m.m01, c = m.m02;
		final double d = m.m10, e = m.m11, f = m.m12;
		final double g = m.m20, h = m.m21, i = m.m22;
		
		final double A = (e*i-f*h), D =-(b*i-c*h), G = (b*f-c*e);
		final double B =-(d*i-f*g), E = (a*i-c*g), H =-(a*f-c*d);
		final double C = (d*h-e*g), F =-(a*h-b*g), I = (a*e-b*d);
	
		final double ooDetA = 1. / (a*A-b*(i*d-f*g)+c*(d*h-e*g));

		return this.setElements(
			A*ooDetA, D*ooDetA, G*ooDetA,
			B*ooDetA, E*ooDetA, H*ooDetA,
			C*ooDetA, F*ooDetA, I*ooDetA
		);
	}
	
	@Override public Matrix3d transpose() {
		return this.setTransposed(this);
	}
	
	@Override public Matrix3d setTransposed(Matrix3d m) {
		return this.setElements(
			m.m00, m.m10, m.m20,
			m.m01, m.m11, m.m21,
			m.m02, m.m12, m.m22
		);
	}

	@Override public Matrix3d concatenate(Matrix3d that) {
		return setConcatenation(this, that);
	}
	
	@Override public Matrix3d setConcatenation(Matrix3d A, Matrix3d B) {
		return this.setElements(
			A.m00*B.m00+A.m01*B.m10+A.m02*B.m20,
			A.m00*B.m01+A.m01*B.m11+A.m02*B.m21,
			A.m00*B.m02+A.m01*B.m12+A.m02*B.m22,
				
			A.m10*B.m00+A.m11*B.m10+A.m12*B.m20,
			A.m10*B.m01+A.m11*B.m11+A.m12*B.m21,
			A.m10*B.m02+A.m11*B.m12+A.m12*B.m22,
				
			A.m20*B.m00+A.m21*B.m10+A.m22*B.m20,
			A.m20*B.m01+A.m21*B.m11+A.m22*B.m21,
			A.m20*B.m02+A.m21*B.m12+A.m22*B.m22
		);
	}
			
	@Override public double determinant() {
		return m00 * (m11 * m22 - m12 * m21) - m01 * (m22 * m10 - m12 * m20) + m02 * (m10 * m21 - m11 * m20);
	}

//==[ Matrix3d Specific ]===========================================================================
	public Matrix3d setRotation(Quaternion q) {
		final double x = q.x, y = q.y, z = q.z, w = q.w;

		final double ww = w*w, xx = x*x, yy= y*y, zz = z*z;
		final double xy = x*x, xz = x*z, xw = x*w;
		final double yz = y*z, yw = y*w, zw = z*w; 
		final double n = 1/(ww+xx+yy+zz);
		return this.setElements(
			(ww+xx-yy-zz)*n, (2*xy-2*zw)*n, (2*xz+2*yw)*n,
			(2*xy+2*zw)*n, (ww-xx+yy-zz)*n, (2*yz-2*xw)*n,						
			(2*xz-2*yw)*n, (2*yz+2*xw)*n, (ww-xx-yy+zz)*n
		);

	}
	
	private Matrix3d concat( 
			double n00, double n01, double n02,
			double n10, double n11, double n12,
			double n20, double n21, double n22
			) {

		return setElements(
			m00*n00+m01*n10+m02*n20, m00*n01+m01*n11+m02*n21, m00*n02+m01*n12+m02*n22,
			m10*n00+m11*n10+m12*n20, m10*n01+m11*n11+m12*n21, m10*n02+m11*n12+m12*n22,
			m20*n00+m21*n10+m22*n20, m20*n01+m21*n11+m22*n21, m20*n02+m21*n12+m22*n22
		);
	}
	
	public Matrix3d translate(double tx, double ty) {
		return this.concat(
			1, 0, tx, 
			0, 1, ty, 
			0, 0, 1
		);
	}

	/**
	 *           [   cos(theta)    -sin(theta)    0   ]
	 *           [   sin(theta)     cos(theta)    0   ]
	 *           [       0              0         1   ]
	 */

	public Matrix3d rotate(double theta) {
		final double c = cos(theta), s = sin(theta);
		return this.concat(
			c,-s, 0,
			s,+c, 0,
			0, 0, 1
		);
	}
	
	public Matrix3d rotate(double theta, double px, double py) {
		return this
			.translate(+px, +py)
			.rotate(theta)
			.translate(-px, -py);
	}
	
	
	public Matrix3d scale(double sx, double sy) {
		return this.concat(
			sx, 0, 0, 
			 0,sy, 0, 
			 0, 0, 1
		);
	}
	
	
	//////////
	
	public Matrix3d setProjection(Matrix4d m) {
		return this.setElements(
				m.m00, m.m01, m.m03, 
				m.m10, m.m11, m.m13,
				m.m30, m.m31, m.m33 
			);
	}
	
	//////////


	public Vector2d[] transformAffine(Vector2d... vs) {
		for (Vector2d v: vs)
			transformAffine(v);
		
		return vs;
	}
	
	public Vector2d transformAffine(Vector2d v) {
		return transformAffine(v,v);
	}
	
	public<T> T transformAffine(Vector2d v, Vector2d.Function<T> w) {
		return transformAffine(v.x,v.y, w);	
	}
	
	public<T> T transformAffine(double vx, double vy, Vector2d.Function<T> v) {
		final double x = vx * m00 + vy * m01 + m02; 
		final double y = vx * m10 + vy * m11 + m12; 
		
		return v.apply( x, y );		
	}
	
	public double[] transformAffine(double... v) {
		for (int i=0;i<v.length;i+=2) {
			final int x = i, y = i+1;
			final double vx = v[x], vy = v[y];
			
			v[x] = (vx*m00+vy*m01+m02);
			v[y] = (vx*m10+vy*m11+m12);
		}
		return v;
	}
	
	public Vector3d[] transform(Vector3d... vs) {
		for (Vector3d v: vs)
			transform(v);
		return vs;
	}
	
	public Vector3d transform(Vector3d v) {
		return transform(v,v);
	}
	
	public double[] transform(double... v) {
		for (int i=0;i<v.length;i+=3) {
			final int x = i, y = i+1, z = y+1;
			final double vx = v[x], vy = v[y], vz = v[z];
			
			v[x] = vx*m00+vy*m01+vz*m02; 
			v[y] = vx*m10+vy*m11+vz*m12;
			v[z] = vx*m20+vy*m21+vz*m22;
		}
		return v;
	}

	public <E> E transform(Vector3d v, Vector3d.Function<E> f) {
		return transform(v.x,v.y,v.z, f);
	}
	
	public <E> E transform(double px, double py, double pz, Vector3d.Function<E> f) {
		final double x = px*m00+py*m01+pz*m02; 
		final double y = px*m10+py*m11+pz*m12;
		final double z = px*m20+py*m21+pz*m22;
		return f.apply(x,y,z);
	}

	public Matrix3d applyTransform(Vector3d v) {
		return this.applyTransform(v, v);
	}

	public Matrix3d applyTransform(Vector3d v, Vector3d.Consumer f) {
		return this.applyTransform(v.x, v.y, v.z, f);
	}
	
	public Matrix3d applyTransform(double px, double py, double pz, Vector3d.Consumer f) {
		final double x = px*m00+py*m01+pz*m02; 
		final double y = px*m10+py*m11+pz*m12;
		final double z = px*m20+py*m21+pz*m22;
		f.accept(x,y,z);
		return this;
	}
	
	///////

	
	public Vector2d[] project(Vector2d... vs) {
		for (Vector2d v: vs)
			project(v);
		return vs;
	}
	
	public Vector2d project(Vector2d v) {
		return project(v, v);
	}
	
	public <E> E project(Vector2d v, Vector2d.Function<E> f) {
		return project(v.x,v.y, f);
	}

	public <E> E project(double px, double py, Vector2d.Function<E> f) {
		final double x = px*m00+py*m01+m02; 
		final double y = px*m10+py*m11+m12;
		final double w = px*m20+py*m21+m22;
		return f.apply(x/w,y/w);
	}

	
	public double[] project(double... v) {
		for (int i=0;i<v.length;i+=2) {
			final int x = i, y = i+1;
			final double vx = v[x], vy = v[y];
			
			final double W = vx*m20+vy*m21+m22;
			v[x] = (vx*m00+vy*m01+m02)/W;
			v[y] = (vx*m10+vy*m11+m12)/W;
		}
		return v;
	}

	
	public Matrix3d applyProjection(Vector2d v) {
		applyProjection(v,v);
		return this;
	}
	
	public Matrix3d applyProjection(Vector2d v, Vector2d.Consumer c) {
		applyProjection(v.x, v.y, c);
		return this;
	}

	public Matrix3d applyProjection(double px, double py, Vector2d.Consumer c) {
		final double x = px*m00+py*m01+m02; 
		final double y = px*m10+py*m11+m12;
		final double w = px*m20+py*m21+m22;
		c.accept(x/w,y/w);
		return this;
	}
	
	/////////
	
	public Vector2d intersect(Vector2d v) {
		return intersect(v,v);
	}

	public <E> E intersect(Vector2d v, Vector2d.Function<E> f) {
		return intersect(v.x,v.y,f);
	}

	public <E> E intersect(double vx, double vy, Vector2d.Function<E> f) {
		final double A = (m11*m22-m12*m21), D =-(m01*m22-m02*m21), G = (m01*m12-m02*m11);
		final double B =-(m10*m22-m12*m20), E = (m00*m22-m02*m20), H =-(m00*m12-m02*m10);
		final double C = (m10*m21-m11*m20), F =-(m00*m21-m01*m20), I = (m00*m11-m01*m10);
	
		final double detA = (m00*A-m01*B+m02*C);
		
		double x= (vx*A+vy*D+G)/detA;
		double y= (vx*B+vy*E+H)/detA;
		double w= (vx*C+vy*F+I)/detA;
		
		return f.apply(x/w, y/w);
	}
	
	public double[] intersect(double... v) {
		final double A = (m11*m22-m12*m21), D =-(m01*m22-m02*m21), G = (m01*m12-m02*m11);
		final double B =-(m10*m22-m12*m20), E = (m00*m22-m02*m20), H =-(m00*m12-m02*m10);
		final double C = (m10*m21-m11*m20), F =-(m00*m21-m01*m20), I = (m00*m11-m01*m10);
	
		final double detA = (m00*A-m01*B+m02*C), ooDetA = 1.0/detA;
		
		for (int i=0,j=v.length;i<j;i++) {
			final int x = i, y = i+1;
			final double vx = v[x], vy = v[y];
			final double w = (vx*C+vy*F+I)/detA;
			v[x] = (vx*A+vy*D+G)*ooDetA/w;
			v[y] = (vx*B+vy*E+H)*ooDetA/w;
		}
		
		return v;
	}
	

	public Matrix3d applyIntersection(Vector2d v) {
		return applyIntersection(v,v);
	}

	public Matrix3d applyIntersection(Vector2d v, Vector2d.Consumer w) {
		return applyIntersection(v.x,v.y,w);
	}

	public Matrix3d applyIntersection(double vx, double vy, Vector2d.Consumer c) {
		final double A = (m11*m22-m12*m21), D =-(m01*m22-m02*m21), G = (m01*m12-m02*m11);
		final double B =-(m10*m22-m12*m20), E = (m00*m22-m02*m20), H =-(m00*m12-m02*m10);
		final double C = (m10*m21-m11*m20), F =-(m00*m21-m01*m20), I = (m00*m11-m01*m10);
	
		final double detA = (m00*A-m01*B+m02*C);
		
		double x= (vx*A+vy*D+G)/detA;
		double y= (vx*B+vy*E+H)/detA;
		double w= (vx*C+vy*F+I)/detA;
		
		c.accept(x/w, y/w);
		return this;
	}
	
	public Matrix3d applyIntersection(double[] v) {
		intersect(v);
		return this;
	}
	
}





