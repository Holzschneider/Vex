package de.dualuse.vecmath;

import static java.lang.Math.*;

import java.io.Serializable;

public class Matrix4d 	extends   	Matrix<Matrix4d> 
						implements	Serializable, 
									Functionals.Matrix4d.Function<Matrix4d>,
									Functionals.Matrix4d.Consumer,
									Functionals.Matrix4d 
{
	private static final double EPSILON = 1.0E-10;
	private static final long serialVersionUID = 1L;
	
	public double m00,m01,m02,m03;
	public double m10,m11,m12,m13;
	public double m20,m21,m22,m23;
	public double m30,m31,m32,m33;
	
//==[ Constructors ]================================================================================
	
	public Matrix4d() { 
		setIdentity(); 
	}
	
	public Matrix4d(
			double m00, double m01, double m02, double m03,
			double m10, double m11, double m12, double m13,
			double m20, double m21, double m22, double m23,
			double m30, double m31, double m32, double m33
			) { 
		setElements(m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33);
	}

	public static Matrix4d of(
			double m00, double m01, double m02, double m03,
			double m10, double m11, double m12, double m13,
			double m20, double m21, double m22, double m23,
			double m30, double m31, double m32, double m33
			) {
		return new Matrix4d().setElements(m00,m01,m02,m03,m10,m11,m12,m13,m20,m21,m22,m23,m30,m31,m32,m33);
	}


//==[ Element-wise Operations ]=====================================================================
	public double get(int row, int col) { return getElement(row, col); }
	public double getElement(int row, int col) {
		switch ((row<<2)|col) {
		case  0|0: return m00;
		case  0|1: return m01;
		case  0|2: return m02;
		case  0|3: return m03;
		case  4|0: return m10;
		case  4|1: return m11;
		case  4|2: return m12;
		case  4|3: return m13;
		case  8|0: return m20;
		case  8|1: return m21;
		case  8|2: return m22;
		case  8|3: return m23;
		case 12|0: return m30;
		case 12|1: return m31;
		case 12|2: return m32;
		case 12|3: return m33;
		}
		throw new IndexOutOfBoundsException("("+row+","+col+") must be in [0,4)x[0,4).");
	}
	

	public Matrix4d apply(double m00, double m01, double m02, double m03, 
			double m10, double m11, double m12, double m13, 
			double m20, double m21, double m22, double m23, 
			double m30, double m31, double m32, double m33 ) {
		
		this.m00 = m00; this.m01 = m01; this.m02 = m02; this.m03 = m03;
		this.m10 = m10; this.m11 = m11; this.m12 = m12; this.m13 = m13;
		this.m20 = m20; this.m21 = m21; this.m22 = m22; this.m23 = m23;
		this.m30 = m30; this.m31 = m31; this.m32 = m32; this.m33 = m33;
		return this;
	}
	

	public void accept(double m00, double m01, double m02, double m03, 
			double m10, double m11, double m12, double m13, 
			double m20, double m21, double m22, double m23, 
			double m30, double m31, double m32, double m33 ) {
		
		this.m00 = m00; this.m01 = m01; this.m02 = m02; this.m03 = m03;
		this.m10 = m10; this.m11 = m11; this.m12 = m12; this.m13 = m13;
		this.m20 = m20; this.m21 = m21; this.m22 = m22; this.m23 = m23;
		this.m30 = m30; this.m31 = m31; this.m32 = m32; this.m33 = m33;
	}

	
	public Matrix4d setElements(
			double m00, double m01, double m02, double m03, 
			double m10, double m11, double m12, double m13, 
			double m20, double m21, double m22, double m23, 
			double m30, double m31, double m32, double m33 ) {
		
		this.m00 = m00; this.m01 = m01; this.m02 = m02; this.m03 = m03;
		this.m10 = m10; this.m11 = m11; this.m12 = m12; this.m13 = m13;
		this.m20 = m20; this.m21 = m21; this.m22 = m22; this.m23 = m23;
		this.m30 = m30; this.m31 = m31; this.m32 = m32; this.m33 = m33;
		
		return this;
	}

//==[ Tuple<Matrix3d> ]=============================================================================
	
	
	public String toFormattedString() {
		return	m00+" "+m01+" "+m02+" "+m03+"\n"+
				m10+" "+m11+" "+m12+" "+m13+"\n"+
				m20+" "+m21+" "+m22+" "+m23+"\n"+
				m30+" "+m31+" "+m32+" "+m33;
	}
	
	
	@Override public String toString() {
		return	m00+" "+m01+" "+m02+" "+m03+" "+
				m10+" "+m11+" "+m12+" "+m13+" "+
				m20+" "+m21+" "+m22+" "+m23+" "+
				m30+" "+m31+" "+m32+" "+m33;
	}

	@Override public int hashCode() {
		return new Double(
			m00*m00+m01*m01+m02*m02+m03*m03+
			m10*m10+m11*m11+m12*m12+m13*m13+
			m20*m20+m21*m21+m22*m22+m23*m23+
			m30*m30+m31*m31+m32*m32+m33*m33
		).hashCode();
	}
	
	@Override public boolean elementsEqual(Matrix4d m) {
		return
			m.m00 == m00 && m.m01 == m01 && m.m02 == m02 && m.m03 == m03 && 
			m.m10 == m10 && m.m11 == m11 && m.m12 == m12 && m.m13 == m13 && 
			m.m20 == m20 && m.m21 == m21 && m.m22 == m22 && m.m23 == m23 && 
			m.m30 == m30 && m.m31 == m31 && m.m32 == m32 && m.m33 == m33;
	}
	
	@Override public Matrix4d self() { return this; }

	@Override public Matrix4d clone() {
		return new Matrix4d().setElements(
			this.m00, this.m01, this.m02, this.m03, 
			this.m10, this.m11, this.m12, this.m13, 
			this.m20, this.m21, this.m22, this.m23, 
			this.m30, this.m31, this.m32, this.m33
		);
	}
	
	@Override public Matrix4d set(Matrix4d a) {
		return setElements(
			a.m00,a.m01,a.m02,a.m03,
			a.m10,a.m11,a.m12,a.m13,
			a.m20,a.m21,a.m22,a.m23,
			a.m30,a.m31,a.m32,a.m33
		);
	}

	public double[][] toRows( double[][] rowArray ) {
		double m[][] = rowArray;
		
		m[0][0] = m00; m[0][1] = m01; m[0][2] = m02; m[0][3] = m03;
		m[1][0] = m10; m[1][1] = m11; m[1][2] = m12; m[1][3] = m13;
		m[2][0] = m20; m[2][1] = m21; m[2][2] = m22; m[2][3] = m23;
		m[3][0] = m30; m[3][1] = m31; m[3][2] = m32; m[3][3] = m33;
		
		return rowArray;
	}
	
	public double[][] toColumns( double[][] rowArray ) {
		double m[][] = rowArray;
		
		m[0][0] = m00; m[0][1] = m10; m[0][2] = m20; m[0][3] = m30;
		m[1][0] = m01; m[1][1] = m11; m[1][2] = m21; m[1][3] = m31;
		m[2][0] = m02; m[2][1] = m12; m[2][2] = m22; m[2][3] = m32;
		m[3][0] = m03; m[3][1] = m13; m[3][2] = m23; m[3][3] = m33;
		
		return rowArray;
	}
	
	public<T> T to(Matrix4d.Function<T> v) {
		return v.apply(	m00, m01, m02, m03, 
						m10, m11, m12, m13,
						m20, m21, m22, m23,
						m30, m31, m32, m33 );
	}
	
	
//==[ MatrixAlgebra<Matrix4d> ]=====================================================================

	@Override public Matrix4d setZero() { 
		return setElements(
			0.0,0.0,0.0,0.0,
			0.0,0.0,0.0,0.0,
			0.0,0.0,0.0,0.0,
			0.0,0.0,0.0,0.0
		);
	}
	
	@Override public Matrix4d setIdentity() { 
		return setElements(
			1.0,0.0,0.0,0.0,
			0.0,1.0,0.0,0.0,
			0.0,0.0,1.0,0.0,
			0.0,0.0,0.0,1.0
		);
	}

	@Override public Matrix4d invert() {
		return this.setInverse(this);
	}
	
	@Override public Matrix4d setInverse(Matrix4d m) {
		final double fA0 = m.m00 * m.m11 - m.m01 * m.m10;
		final double fA1 = m.m00 * m.m12 - m.m02 * m.m10;
		final double fA2 = m.m00 * m.m13 - m.m03 * m.m10;
		final double fA3 = m.m01 * m.m12 - m.m02 * m.m11;
		final double fA4 = m.m01 * m.m13 - m.m03 * m.m11;
		final double fA5 = m.m02 * m.m13 - m.m03 * m.m12;
		final double fB0 = m.m20 * m.m31 - m.m21 * m.m30;
		final double fB1 = m.m20 * m.m32 - m.m22 * m.m30;
		final double fB2 = m.m20 * m.m33 - m.m23 * m.m30;
		final double fB3 = m.m21 * m.m32 - m.m22 * m.m31;
		final double fB4 = m.m21 * m.m33 - m.m23 * m.m31;
		final double fB5 = m.m22 * m.m33 - m.m23 * m.m32;

		final double det = fA0 * fB5 - fA1 * fB4 + fA2 * fB3 + fA3 * fB2 - fA4 * fB1 + fA5 * fB0;

		if (EPSILON < det && det < EPSILON )
			throw new RuntimeException("Non invertible transform");

		final double im0 = +m.m11 * fB5 -m.m12 * fB4 +m.m13 * fB3;
		final double im4 = -m.m10 * fB5 +m.m12 * fB2 -m.m13 * fB1;
		final double im8 = +m.m10 * fB4 -m.m11 * fB2 +m.m13 * fB0;
		final double im12 = -m.m10 * fB3 +m.m11 * fB1 -m.m12 * fB0;
		final double im1 = -m.m01 * fB5 +m.m02 * fB4 -m.m03 * fB3;
		final double im5 = +m.m00 * fB5 -m.m02 * fB2 +m.m03 * fB1;
		final double im9 = -m.m00 * fB4 +m.m01 * fB2 -m.m03 * fB0;
		final double im13 = +m.m00 * fB3 -m.m01 * fB1 +m.m02 * fB0;
		final double im2 = +m.m31 * fA5 -m.m32 * fA4 +m.m33 * fA3;
		final double im6 = -m.m30 * fA5 +m.m32 * fA2 -m.m33 * fA1;
		final double im10 = +m.m30 * fA4 -m.m31 * fA2 +m.m33 * fA0;
		final double im14 = -m.m30 * fA3 +m.m31 * fA1 -m.m32 * fA0;
		final double im3 = -m.m21 * fA5 +m.m22 * fA4 -m.m23 * fA3;
		final double im7 = +m.m20 * fA5 -m.m22 * fA2 +m.m23 * fA1;
		final double im11 = -m.m20 * fA4 +m.m21 * fA2 -m.m23 * fA0;
		final double im15 = +m.m20 * fA3 -m.m21 * fA1 +m.m22 * fA0;

		final double iDet = 1. / det;

		return this.setElements( 
			im0 * iDet, im1 * iDet, im2 * iDet, im3 * iDet, 
			im4 * iDet, im5 * iDet, im6 * iDet, im7 * iDet,
			im8 * iDet, im9 * iDet, im10 * iDet, im11 * iDet,
			im12 * iDet, im13 * iDet, im14 * iDet, im15 * iDet
		);
	}

	@Override public Matrix4d transpose() {
		return this.setTransposed(this);
	}
	
	@Override public Matrix4d setTransposed(Matrix4d m) {
		return this.setElements(
			m.m00, m.m10, m.m20, m.m30,
			m.m01, m.m11, m.m21, m.m31,
			m.m02, m.m12, m.m22, m.m32,
			m.m03, m.m13, m.m23, m.m33
		);
	}

	@Override public Matrix4d concatenate(Matrix4d that) {
		return setConcatenation(this, that);
	}


	@Override public Matrix4d setConcatenation(Matrix4d A, Matrix4d B) {
		return this.setElements(
			A.m00 * B.m00 + A.m01 * B.m10 + A.m02 * B.m20 + A.m03 * B.m30,
			A.m00 * B.m01 + A.m01 * B.m11 + A.m02 * B.m21 + A.m03 * B.m31,
			A.m00 * B.m02 + A.m01 * B.m12 + A.m02 * B.m22 + A.m03 * B.m32,
			A.m00 * B.m03 + A.m01 * B.m13 + A.m02 * B.m23 + A.m03 * B.m33,

			A.m10 * B.m00 + A.m11 * B.m10 + A.m12 * B.m20 + A.m13 * B.m30,
			A.m10 * B.m01 + A.m11 * B.m11 + A.m12 * B.m21 + A.m13 * B.m31,
			A.m10 * B.m02 + A.m11 * B.m12 + A.m12 * B.m22 + A.m13 * B.m32,
			A.m10 * B.m03 + A.m11 * B.m13 + A.m12 * B.m23 + A.m13 * B.m33,
			
			
			A.m20 * B.m00 + A.m21 * B.m10 + A.m22 * B.m20 + A.m23 * B.m30,
			A.m20 * B.m01 + A.m21 * B.m11 + A.m22 * B.m21 + A.m23 * B.m31,
			A.m20 * B.m02 + A.m21 * B.m12 + A.m22 * B.m22 + A.m23 * B.m32,
			A.m20 * B.m03 + A.m21 * B.m13 + A.m22 * B.m23 + A.m23 * B.m33,
			
			
			A.m30 * B.m00 + A.m31 * B.m10 + A.m32 * B.m20 + A.m33 * B.m30,
			A.m30 * B.m01 + A.m31 * B.m11 + A.m32 * B.m21 + A.m33 * B.m31,
			A.m30 * B.m02 + A.m31 * B.m12 + A.m32 * B.m22 + A.m33 * B.m32,
			A.m30 * B.m03 + A.m31 * B.m13 + A.m32 * B.m23 + A.m33 * B.m33
		);
	}

	@Override public double determinant() {
		final double fA0 = m00 * m11 - m01 * m10;
		final double fA1 = m00 * m12 - m02 * m10;
		final double fA2 = m00 * m13 - m03 * m10;
		final double fA3 = m01 * m12 - m02 * m11;
		final double fA4 = m01 * m13 - m03 * m11;
		final double fA5 = m02 * m13 - m03 * m12;
		final double fB0 = m20 * m31 - m21 * m30;
		final double fB1 = m20 * m32 - m22 * m30;
		final double fB2 = m20 * m33 - m23 * m30;
		final double fB3 = m21 * m32 - m22 * m31;
		final double fB4 = m21 * m33 - m23 * m31;
		final double fB5 = m22 * m33 - m23 * m32;

		return fA0 * fB5 - fA1 * fB4 + fA2 * fB3 + fA3 * fB2 - fA4 * fB1 + fA5 * fB0;
	}
	
	
//==[ Matrix4d Specific ]===========================================================================
	public Matrix4d setRows(double[] m) {
		return setElements(
				m[ 0], m[ 1], m[ 2], m[ 3],
				m[ 4], m[ 5], m[ 6], m[ 7],
				m[ 8], m[ 9], m[10], m[11],
				m[12], m[13], m[14], m[15]
		);
	}
	
	public Matrix4d setColumns(double[] m) {
		return setElements(
				m[ 0], m[ 4], m[ 8], m[12],
				m[ 1], m[ 5], m[ 9], m[13],
				m[ 2], m[ 6], m[10], m[14],
				m[ 3], m[ 7], m[11], m[15]
		);
	}
	
	public Matrix4d setColumns(Vector4d x, Vector4d y, Vector4d z, Vector4d w) {
		return setElements(
			x.x, y.x, z.x, w.x,
			x.y, y.y, z.y, w.y,
			x.z, y.z, z.z, w.z,
			x.w, y.w, z.w, w.w
		);
	}

	public Matrix4d setRows(Vector4d x, Vector4d y, Vector4d z, Vector4d w) {
		return setElements(
			x.x, x.y, x.z, x.w, 
			y.x, y.y, y.z, y.w,
			z.x, z.y, z.z, z.w,
			w.x, w.y, w.z, w.w
		);
	}

	public Matrix4d setColumns(double[] c0, double[] c1, double[] c2, double[] c3) {
		return setElements(
			c0[0], c1[0], c2[0], c3[0], 
			c0[1], c1[1], c2[1], c3[1], 
			c0[2], c1[2], c2[2], c3[2], 
			c0[3], c1[3], c2[3], c3[3] 
		);
	}

	public Matrix4d setRows(double[] m0, double[] m1, double[] m2, double[] m3) {
		return setElements(
			m0[0], m0[1], m0[2], m0[3], 
			m1[0], m1[1], m1[2], m1[3], 
			m2[0], m2[1], m2[2], m2[3], 
			m3[0], m3[1], m3[2], m3[3] 
		);
	}
	
	public Matrix4d setColumns(double[][] c) {
		return setElements(
			c[0][0], c[1][0], c[2][0], c[3][0], 
			c[0][1], c[1][1], c[2][1], c[3][1], 
			c[0][2], c[1][2], c[2][2], c[3][2], 
			c[0][3], c[1][3], c[2][3], c[3][3] 
		);
	}

	public Matrix4d setRows(double[][] m) {
		return setElements(
			m[0][0], m[0][1], m[0][2], m[0][3], 
			m[1][0], m[1][1], m[1][2], m[1][3], 
			m[2][0], m[2][1], m[2][2], m[2][3], 
			m[3][0], m[3][1], m[3][2], m[3][3] 
		);
	}

	public Matrix4d concatenate( 
			double n00, double n01, double n02, double n03,
			double n10, double n11, double n12, double n13,
			double n20, double n21, double n22, double n23,
			double n30, double n31, double n32, double n33
			) {

		return setElements(
			m00*n00+m01*n10+m02*n20+m03*n30,
			m00*n01+m01*n11+m02*n21+m03*n31,
			m00*n02+m01*n12+m02*n22+m03*n32,
			m00*n03+m01*n13+m02*n23+m03*n33,

			m10*n00+m11*n10+m12*n20+m13*n30,
			m10*n01+m11*n11+m12*n21+m13*n31,
			m10*n02+m11*n12+m12*n22+m13*n32,
			m10*n03+m11*n13+m12*n23+m13*n33,
				
			m20*n00+m21*n10+m22*n20+m23*n30,
			m20*n01+m21*n11+m22*n21+m23*n31,
			m20*n02+m21*n12+m22*n22+m23*n32,
			m20*n03+m21*n13+m22*n23+m23*n33,
				
			m30*n00+m31*n10+m32*n20+m33*n30,
			m30*n01+m31*n11+m32*n21+m33*n31,
			m30*n02+m31*n12+m32*n22+m33*n32,
			m30*n03+m31*n13+m32*n23+m33*n33
		);
	}
	
	

	public Matrix4d rotateQuaternion(double x, double y, double z, double w) {
		final double ww = w*w, xx = x*x, yy= y*y, zz = z*z;
		final double xy = x*y, xz = x*z, xw = x*w;
		final double yz = y*z, yw = y*w, zw = z*w; 
		
		return this.concatenate(
			ww+xx-yy-zz, 2*xy-2*zw, 2*xz+2*yw, 0.,
			2*xy+2*zw, ww-xx+yy-zz, 2*yz-2*xw, 0.,						
			2*xz-2*yw, 2*yz+2*xw, ww-xx-yy+zz, 0.,
			0.0, 0.0, 0.0, 	ww+xx+yy+zz
		);
	}
	
	public Matrix4d rotate(Quaternion q) {
		return rotateQuaternion(q.x,q.y,q.z,q.w);
	}

	public Matrix4d rotateInverse(Quaternion q) {
		final double invNorm = 1.0 / (q.x * q.x + q.y * q.y + q.z * q.z + q.w * q.w);
		return rotateQuaternion(-q.x * invNorm,-q.y * invNorm, -q.z * invNorm, q.w * invNorm);
	}
	
	public Matrix4d rotate(AxisAngle aa) { return this.rotate(aa.r,aa.x,aa.y,aa.z); }
	public Matrix4d rotate(double theta, Vector3d axis) { return rotate(theta, axis.x, axis.y, axis.z); }
	
	public Matrix4d rotateDegrees( double degrees, double x, double y, double z) {
		return this.rotate(degrees*PI/180,x,y,z);
	}
	
	public Matrix4d rotate(double theta, double ax, double ay, double az) {
//		// compare '$ man glRotate' or 'javax.vecmath.Matrix4d.set(AxisAngle4d a1)'
		
		final double s = sin(theta), c = cos(theta), t = 1-c, l = sqrt(ax*ax+ay*ay+az*az);
		final double x = (ax/l), y = (ay/l), z= (az/l);
		final double xz = x*z, xy = x*y, yz = y*z, xx=x*x, yy=y*y, zz=z*z;
		
		return this.concatenate(
				t*xx+c  , t*xy-s*z, t*xz+s*y, 0,
				t*xy+s*z, t*yy+c  , t*yz-s*x, 0,
				t*xz-s*y, t*yz+s*x, t*zz+c  , 0,
				       0,        0,      0,   1);

	}

	public Matrix4d translate(Vector3d v) {
		return translate(v.x,v.y,v.z);
	}

	public Matrix4d translate(Vector3d v, double times) {
		return translate(v.x*times,v.y*times,v.z*times);
	}
	
	public Matrix4d translate(double tx, double ty, double tz) {
		return this.concatenate(
			1,0,0,tx,
			0,1,0,ty,
			0,0,1,tz,
			0,0,0, 1
		);
	}

	public Matrix4d scale(double s) {
		return this.scale(s,s,s);
	}
	
	
	public Matrix4d scale(Vector3d v) {
		return this.scale(v.x,v.y,v.z);
	}
	
	public Matrix4d scale(double sx, double sy, double sz) {
		return this.concatenate(
			sx, 0, 0, 0,
			 0,sy, 0, 0,
			 0, 0,sz, 0,
			 0, 0, 0, 1
		);
	}
	
	
	/////////
	public Matrix4d augment(Matrix3d m) {
		return this.setElements(
			m.m00, m.m01, m.m02,   0, 
			m.m10, m.m11, m.m12,   0,
			m.m20, m.m21, m.m22,   0,
			0,     0,         0,   1
		);
	}

	public Matrix4d setRotation(AxisAngle a) {
		double theta = a.r, ax = a.x, ay = a.y, az = a.z;
		final double s = sin(theta), c = cos(theta), t = 1-c, l = sqrt(ax*ax+ay*ay+az*az);
		final double x = (ax/l), y = (ay/l), z= (az/l);
		final double xz = x*z, xy = x*y, yz = y*z, xx=x*x, yy=y*y, zz=z*z;
		
		return this.setElements(
				t*xx+c  , t*xy-s*z, t*xz+s*y, 0,
				t*xy+s*z, t*yy+c  , t*yz-s*x, 0,
				t*xz-s*y, t*yz+s*x, t*zz+c  , 0,
				       0,        0,      0,   1);

	}

	public Matrix4d setRotation(Quaternion q) {
		final double x = q.x, y = q.y, z = q.z, w = q.w;

		final double ww = w*w, xx = x*x, yy= y*y, zz = z*z;
		final double xy = x*y, xz = x*z, xw = x*w;
		final double yz = y*z, yw = y*w, zw = z*w; 
		
		return this.setElements(
			ww+xx-yy-zz, 2*xy-2*zw, 2*xz+2*yw, 0.,
			2*xy+2*zw, ww-xx+yy-zz, 2*yz-2*xw, 0.,						
			2*xz-2*yw, 2*yz+2*xw, ww-xx-yy+zz, 0.,
			0.0, 0.0, 0.0, 	ww+xx+yy+zz
		);
	}

	public Matrix4d setTranslation(Vector3d translation) {
		final double tx = translation.x, ty = translation.y, tz = translation.z;
		return setElements(	
			1.0,0.0,0.0, tx,
			0.0,1.0,0.0, ty,
			0.0,0.0,1.0, tz,
			0.0,0.0,0.0, 1
		);		
	}
	
	public Matrix4d setScale(Vector3d scaling) {
		final double scx = scaling.x, scy = scaling.y, scz = scaling.y; 
		return setElements(	
			scx,0.0,0.0,0.0,
			0.0,scy,0.0,0.0,
			0.0,0.0,scz,0.0,
			0.0,0.0,0.0,1.0
		);
	}
	
//	public Matrix4d viewport(double x, double y, double width, double height) {
//		return this
//			.translate(x, y, 0)
//			.scale(width,height,1)
//			.scale(0.5,-0.5,1)
//			.translate(1,-1,0);
//	}
	
	public Matrix4d ortho(double left, double right, double bottom, double top, double near, double far) {
        float tx = (float) (- (right + left) / (right - left));
        float ty = (float) (- (top + bottom) / (top - bottom));
		float tz = (float) (- (far + near) / (far - near));
		
		return this.concatenate(
				 2/(right-left), 0,0, tx ,
				 0, 2/(top-bottom), 0, ty,
				 0, 0, -2/(far-near), tz,
				0,0,0,1
				);
	}
	
	public Matrix4d frustum(double left, double right, double bottom, double top, double near, double far) {
		// see '$ man glFrustum'
		final double A = (right + left)/(right - left);
		final double B = (top + bottom)/(top - bottom);
		final double C = -(far + near)/(far - near);
		final double D = -(2 * far * near) /( far -near);
		
		return this.concatenate(
//		return this.setElements(
			(2*near)/(right-left),                     0, A, 0,
			0                    , (2*near)/(top-bottom), B, 0,
			0                    ,                     0, C, D,
			0                    ,                     0,-1, 0
		);
	}
	
	//////////


	public Vector3d[] transformAffine(Vector3d... vs) {
		for (Vector3d v: vs)
			transformAffine(v);
		return vs;
	}
	
	public Vector3d transformAffine(Vector3d v) {
		return transformAffine(v,v);
	}
	
	public<T> T transformAffine(Vector3d v, Vector3d.Function<T> w) {
		return transformAffine(v.x,v.y,v.z, w);	
	}
	
	public<T> T transformAffine(double vx, double vy, double vz, Vector3d.Function<T> v) {
		final double x = vx * m00 + vy * m01 + vz * m02 + m03; 
		final double y = vx * m10 + vy * m11 + vz * m12 + m13; 
		final double z = vx * m20 + vy * m21 + vz * m22 + m23;
		
		return v.apply( x, y, z );		
	}
	
	public double[] transformAffine(double... v) {
		for (int i=0;i<v.length;i+=3) {
			final int x = i, y = i+1, z = y+1;
			final double vx = v[x], vy = v[y], vz = v[z];
			
			v[x] = vx*m00+vy*m01+vz*m02+m03; 
			v[y] = vx*m10+vy*m11+vz*m12+m13;
			v[z] = vx*m20+vy*m21+vz*m22+m23;
		}
		return v;
	}
	
	/////////////
	
	public Vector4d transform(Vector4d v) {
		return transform(v,v);
	}
	
	public<T> T transform(Vector4d v, Vector4d.Function<T> w) {
		return transform(v.x,v.y,v.z,v.w, w);	
	}
	
	public<T> T transform(double vx, double vy, double vz, double vw, Vector4d.Function<T> v) {
		final double x = vx * m00 + vy * m01 + vz * m02 + vw * m03; 
		final double y = vx * m10 + vy * m11 + vz * m12 + vw * m13; 
		final double z = vx * m20 + vy * m21 + vz * m22 + vw * m23;
		final double w = vx * m30 + vy * m31 + vz * m32 + vw * m33;
		
		return v.apply( x, y, z, w );		
	}
	
	public double[] transform(double... v) {
		for (int i=0,I=v.length;i<I;i+=4) {
			final int x = i, y = i+1, z = i+2, w = i+3;
			final double vx = v[x], vy = v[y], vz = v[z], vw = v[w];
			
			v[x] = vx * m00 + vy * m01 + vz * m02 + vw * m03; 
			v[y] = vx * m10 + vy * m11 + vz * m12 + vw * m13; 
			v[z] = vx * m20 + vy * m21 + vz * m22 + vw * m23;
			v[w] = vx * m30 + vy * m31 + vz * m32 + vw * m33;
		}
		
		return v;
	}


	
	public Matrix4d applyTransform(Vector4d v) {
		return applyTransform(v,v);
	}
	
	public Matrix4d applyTransform(Vector4d v, Vector4d.Consumer w) {
		applyTransform(v.x,v.y,v.z,v.w, w);
		return this;
	}
	
	public Matrix4d applyTransform(double vx, double vy, double vz, double vw, Vector4d.Consumer v) {
		final double x = vx * m00 + vy * m01 + vz * m02 + vw * m03; 
		final double y = vx * m10 + vy * m11 + vz * m12 + vw * m13; 
		final double z = vx * m20 + vy * m21 + vz * m22 + vw * m23;
		final double w = vx * m30 + vy * m31 + vz * m32 + vw * m33;
		
		v.accept( x, y, z, w );
		return this;
	}
	
	public Matrix4d applyTransform(double[] v) {
		applyTransform(v);
		return this;
	}

	////

	public Vector3d[] project(Vector3d... vs) {
		for (Vector3d v: vs)
			project(v);
		return vs;
	}
	
	public Vector3d project(Vector3d v) {
		return project(v,v);
	}
	
	public<T> T project(Vector3d v, Vector3d.Function<T> w) {
		return project(v.x,v.y,v.z,w);
	}
	
	public<T> T project(double vx, double vy, double vz, Vector3d.Function<T> v) {
		final double x = vx * m00 + vy * m01 + vz * m02 + m03; 
		final double y = vx * m10 + vy * m11 + vz * m12 + m13; 
		final double z = vx * m20 + vy * m21 + vz * m22 + m23;
		final double w = vx * m30 + vy * m31 + vz * m32 + m33;
		
		return v.apply( x/w, y/w, z/w );		
	}
	
	public double[] project(double... v) {
		for (int i=0,I=v.length;i<I;i+=3) {
			final int x = i, y = i+1, z = i+2;
			final double vx = v[x], vy = v[y], vz = v[z];
			
			double W = vx * m30 + vy * m31 + vz * m32 +  m33;
			v[x] = (vx * m00 + vy * m01 + vz * m02 + m03)/W;
			v[y] = (vx * m10 + vy * m11 + vz * m12 + m13)/W;
			v[z] = (vx * m20 + vy * m21 + vz * m22 + m23)/W;
		}
		
		return v;
	}

	
	public Matrix4d applyProjection(double[] v) {
		project(v);
		return this;
	}
	
	public Matrix4d applyProjection(Vector3d v) {
		return applyProjection(v,v);
	}
	
	public Matrix4d applyProjection(Vector3d v, Vector3d.Consumer w) {
		return applyProjection(v.x,v.y,v.z,w);
	}
	
	public Matrix4d applyProjection(double vx, double vy, double vz, Vector3d.Consumer v) {
		final double x = vx * m00 + vy * m01 + vz * m02 + m03; 
		final double y = vx * m10 + vy * m11 + vz * m12 + m13; 
		final double z = vx * m20 + vy * m21 + vz * m22 + m23;
		final double w = vx * m30 + vy * m31 + vz * m32 + m33;
		
		v.accept( x/w, y/w, z/w );
		return this;
	}
	
	////////////////
	
	
	
	public static Matrix4d outer(Vector4d a, Vector4d b) {
		return Matrix4d.of( 
				a.x*b.x, a.x*b.y, a.x*b.z, a.x*b.w,
				a.y*b.x, a.y*b.y, a.y*b.z, a.y*b.w,
				a.z*b.x, a.z*b.y, a.z*b.z, a.z*b.w,
				a.w*b.x, a.w*b.y, a.w*b.z, a.w*b.w
			);
	}
	
	public static Vector4d mul(Matrix4d a, Vector4d b) {
		return a.transform(new Vector4d(b));
	}
	
	public static Matrix4d mul(Matrix4d a, Matrix4d b) {
		return new Matrix4d().setConcatenation(a,b);
	}
	
	
	public static Vector3d project(Matrix4d a, Vector3d b) {
		return a.project(new Vector3d(b));
	}
	
	public static Matrix3d minor(Matrix4d a, int row, int col) {
		return new Matrix3d().setMinor(a,row,col);
	}

	public static Matrix4d invert( Matrix4d a ) {
		return new Matrix4d().setInverse(a);
	}
	
	public static Matrix4d transpose( Matrix4d a ) {
		return new Matrix4d().setTransposed(a);
	}
	
	/////////////
	

	public static Matrix4d rotate(Matrix4d a, Quaternion b) {
		return new Matrix4d().set(a).rotate(b);
	}

	public static Matrix4d rotate(Quaternion a, Matrix4d b) {
		return new Matrix4d().setRotation(a).concatenate(b);
	}

	public static Matrix4d rotate(Matrix4d a, AxisAngle b) {
		return new Matrix4d().set(a).rotate(b);
	}

	public static Matrix4d rotate(AxisAngle a, Matrix4d b) {
		return new Matrix4d().setRotation(a).concatenate(b);
	}
	
	public static Matrix4d translate(Matrix4d a, Vector3d b) {
		return new Matrix4d().set(a).translate(b);
	}

	public static Matrix4d translate(Vector3d a, Matrix4d b) {
		return new Matrix4d().setTranslation(a).concatenate(b);
	}
	
	
}











