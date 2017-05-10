package de.dualuse.vecmath;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.io.Serializable;

public class Matrix3d extends Matrix<Matrix3d> implements Serializable {
	private static final long serialVersionUID = 1L;

	public double m00,m01,m02;
	public double m10,m11,m12;
	public double m20,m21,m22;
	
	public Matrix3d() {
		identity();
	}
	
	public static Matrix3d from(
				double m00,double m01,double m02,
				double m10,double m11,double m12,
				double m20,double m21,double m22
			) {
		return new Matrix3d().setElements(m00, m01, m02, m10, m11, m12, m20, m21, m22);
	}
	
//	public Matrix3d(
//			double m00,double m01,double m02,
//			double m10,double m11,double m12,
//			double m20,double m21,double m22
//			)
//	{
//		this.setElements(m00, m01, m02, m10, m11, m12, m20, m21, m22);
//	}

	public Matrix3d fromString(String r) {
		String c[] = r.split("\\s+");

		return this.setElements(
				new Double(c[0]), new Double(c[1]), new Double(c[2]),
				new Double(c[3]), new Double(c[4]), new Double(c[5]),
				new Double(c[6]), new Double(c[7]), new Double(c[8]));
	}
	
	@Override
	public String toString() {
		return	m00+" "+m01+" "+m02+" "+
				m10+" "+m11+" "+m12+" "+
				m20+" "+m21+" "+m22;
	}
	
	@Override
	public int hashCode() {
		return new Double(
				m00*m00+m01*m01+m02*m02+
				m10*m10+m11*m11+m12*m12+
				m20*m20+m21*m21+m22*m22
			).hashCode();
	}
	
	public boolean elementsEqual(Matrix3d m) {
		return
				m.m00 == m00 && m.m01 == m01 && m.m02 == m02 &&
				m.m10 == m10 && m.m11 == m11 && m.m12 == m12 &&
				m.m20 == m20 && m.m21 == m21 && m.m22 == m22;
	}
	
	@Override
	public Matrix3d clone() {
		return new Matrix3d().setElements(
				this.m00, this.m01, this.m02, 
				this.m10, this.m11, this.m12, 
				this.m20, this.m21, this.m22);
	}
	
	//////////////////////////////////////////////////////////////////////////////

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
	
	public Matrix3d set(Matrix3d a) {
		this.m00 = a.m00; this.m01 = a.m01; this.m02 = a.m02;
		this.m10 = a.m10; this.m11 = a.m11; this.m12 = a.m12;
		this.m20 = a.m20; this.m21 = a.m21; this.m22 = a.m22;

		return this;
	}
	

	@Override
	public Matrix3d zero() {
		return this.setElements(
				0,0,0,
				0,0,0,
				0,0,0);
	}

	@Override
	public Matrix3d identity() {
		return this.setElements(
				1,0,0,
				0,1,0,
				0,0,1);
	}

	@Override
	public Matrix3d concatenate(Matrix3d that) {
		return concatenation(this, that);
	}
	
	@Override
	public Matrix3d concatenation(Matrix3d A, Matrix3d B) {
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
	
	@Override
	public Matrix3d transpose() {
		return this.transposition(this);
	}
	
	@Override
	public Matrix3d transposition(Matrix3d m) {
		return this.setElements(
				m.m00, m.m01, m.m02,
				m.m10, m.m11, m.m12,
				m.m20, m.m21, m.m22
			);
	}

	@Override
	public Matrix3d invert() {
		return this.inversion(this);
	}
	
	@Override
	public Matrix3d inversion(Matrix3d m) {
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
	
	
		
	public double determinant() {
		return m00 * (m11 * m22 - m12 * m21) - m01 * (m22 * m10 - m12 * m20) + m02 * (m10 * m21 - m11 * m20);
	}
	

	////////////////////////////////////// Matrix3d Specific
	
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
				0, 0, 1);
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
				0, 0, 1);
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
				 0, 0, 1);
	}
	
	
	//////////
	

	/// UNTESTED
	public Matrix3d setProjection(Matrix4d m, double zPlane) {
//		final double n00 = 1, n01 = 0, n02 = 0, n03 = 0;
//		final double n10 = 0, n11 = 1, n12 = 0, n13 = 0;
//		final double n20 = 0, n21 = 0, n22 = 1, n23 = zPlane;
//		final double n30 = 0, n31 = 0, n32 = 0, n33 = 1;
//		
//		double m00 = n00*m.m00+n10*m.m01+n20*m.m02+n30*m.m03;
//		double m01 = n01*m.m00+n11*m.m01+n21*m.m02+n31*m.m03;
////		double m02 = n02*m.m00+n12*m.m01+n22*m.m02+n32*m.m03;
//		double m03 = n03*m.m00+n13*m.m01+n23*m.m02+n33*m.m03;
//
//		double m10 = n00*m.m10+n10*m.m11+n20*m.m12+n30*m.m13;
//		double m11 = n02*m.m10+n12*m.m11+n22*m.m12+n32*m.m13;
////		double m12 = n01*m.m10+n11*m.m11+n21*m.m12+n31*m.m13;
//		double m13 = n03*m.m10+n13*m.m11+n23*m.m12+n33*m.m13;
//		
////		double m20 = n00*m.m20+n10*m.m21+n20*m.m22+n30*m.m23;
////		double m21 = n01*m.m20+n11*m.m21+n21*m.m22+n31*m.m23;
////		double m22 = n02*m.m20+n12*m.m21+n22*m.m22+n32*m.m23;
////		double m23 = n03*m.m20+n13*m.m21+n23*m.m22+n33*m.m23;
//		
//		double m30 = n00*m.m30+n10*m.m31+n20*m.m32+n30*m.m33;
//		double m31 = n01*m.m30+n11*m.m31+n21*m.m32+n31*m.m33;
////		double m32 = n02*m.m30+n12*m.m31+n22*m.m32+n32*m.m33;
//		double m33 = n03*m.m30+n13*m.m31+n23*m.m32+n33*m.m33;

		return this.setElements(
				m.m00, m.m01, zPlane*m.m02+m.m03, 
				m.m10, m.m11, zPlane*m.m12+m.m13,
				m.m30, m.m31, zPlane*m.m32+m.m33 
			);
	}
	
	//////////


	public Vector3d transform(Vector3d v) {
		return v.xyz(
			v.x*m00+v.y*m01+v.z*m02, 
			v.x*m10+v.y*m11+v.z*m12, 
			v.x*m20+v.y*m21+v.z*m22 
		);
	}
	
	public Vector2d project(Vector2d v) {
		final double x = v.x*m00+v.y*m01+m02; 
		final double y = v.x*m10+v.y*m11+m12;
		final double w = v.x*m20+v.y*m21+m22;
		
		return v.xy( x/w, y/w );
	}
	
	//as in cast a shadow on an object
	public Vector2d intersect(Vector2d v) {
		final double A = (m11*m22-m12*m21), D =-(m01*m22-m02*m21), G = (m01*m12-m02*m11);
		final double B =-(m10*m22-m12*m20), E = (m00*m22-m02*m20), H =-(m00*m12-m02*m10);
		final double C = (m10*m21-m11*m20), F =-(m00*m21-m01*m20), I = (m00*m11-m01*m10);
	
		final double detA = (m00*A-m01*B+m02*C);
		
		double x= (v.x*A+v.y*D+G)/detA;
		double y= (v.x*B+v.y*E+H)/detA;
		double w= (v.x*C+v.y*F+I)/detA;
		
		return v.xy(x/w, y/w);
	}
	
}





