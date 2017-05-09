package de.dualuse.vecmath;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.util.Locale;

public class Matrix3d implements MatrixAlgebra<Matrix3d>, java.io.Serializable {
	private static final long serialVersionUID = 1L;

	public double m00,m01,m02;
	public double m10,m11,m12;
	public double m20,m21,m22;
	
	public Matrix3d() {}
	public Matrix3d(
			double m00, double m01, double m02,
			double m10, double m11, double m12,
			double m20, double m21, double m22) {
		this.elements(m00, m01, m02, m10, m11, m12, m20, m21, m22);
	}
	
	public Matrix3d(Matrix3d c) {
		this.elements(c.m00, c.m01, c.m02, c.m10, c.m11, c.m12, c.m20, c.m21, c.m22);
	}
	

	public Matrix3d fromString(String r) {
		String c[] = r.split("\\s+");

		return this.elements(
				new Double(c[0]), new Double(c[1]), new Double(c[2]),
				new Double(c[3]), new Double(c[4]), new Double(c[5]),
				new Double(c[6]), new Double(c[7]), new Double(c[8]));
	}
	
	public String toString() {
		return	m00+" "+m01+" "+m02+
				m10+" "+m11+" "+m12+
				m20+" "+m21+" "+m22;
	}

	
	@Override
	public Matrix3d clone() throws CloneNotSupportedException {
		return new Matrix3d().elements(
				this.m00, this.m01, this.m02, 
				this.m10, this.m11, this.m12, 
				this.m20, this.m21, this.m22);
	}
	
	//////////////////////////////////////////////////////////////////////////////

	private Matrix3d elements(
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
		return this.elements(
				0,0,0,
				0,0,0,
				0,0,0);
	}

	@Override
	public Matrix3d identity() {
		return this.elements(
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
		return this.elements(
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
	public Matrix3d transpose(Matrix3d m) {
		return this.elements(
				m.m00, m.m01, m.m02,
				m.m10, m.m11, m.m12,
				m.m20, m.m21, m.m22
			);
	}

	@Override
	public Matrix3d invert(Matrix3d m) {
		final double a = m.m00, b = m.m01, c = m.m02;
		final double d = m.m10, e = m.m11, f = m.m12;
		final double g = m.m20, h = m.m21, i = m.m22;
		
		final double A = (e*i-f*h), D =-(b*i-c*h), G = (b*f-c*e);
		final double B =-(d*i-f*g), E = (a*i-c*g), H =-(a*f-c*d);
		final double C = (d*h-e*g), F =-(a*h-b*g), I = (a*e-b*d);
	
		final double ooDetA = 1. / (a*A-b*(i*d-f*g)+c*(d*h-e*g));

		return this.elements(
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

		return elements(
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
	

	public Vector3d transform(Vector3d v) {
		return v.set(
			v.x*m00+v.y*m01+v.z*m02, 
			v.x*m10+v.y*m11+v.z*m12, 
			v.x*m20+v.y*m21+v.z*m22 
		);
	}
	
	public Vector2d project(Vector2d v) {
		final double x = v.x*m00+v.y*m01+m02; 
		final double y = v.x*m10+v.y*m11+m12;
		final double w = v.x*m20+v.y*m21+m22;
		
		return v.set( x/w, y/w );
	}
	
}





