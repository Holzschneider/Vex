package de.dualuse.vecmath;

import java.util.Locale;

public class Matrix3 implements MatrixAlgebra<Matrix3>, java.io.Serializable {
	private static final long serialVersionUID = 1L;

	public double m00,m01,m02;
	public double m10,m11,m12;
	public double m20,m21,m22;
	
	public Matrix3 set(Matrix3 a) {
		this.m00 = a.m00; this.m01 = a.m01; this.m02 = a.m02;
		this.m10 = a.m10; this.m11 = a.m11; this.m12 = a.m12;
		this.m20 = a.m20; this.m21 = a.m21; this.m22 = a.m22;

		return this;
	}
	
	public Matrix3 setElements(
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
	

	@Override
	public Matrix3 zero() {
		return this.setElements(
				0,0,0,
				0,0,0,
				0,0,0);
	}

	@Override
	public Matrix3 identity() {
		return this.setElements(
				1,0,0,
				0,1,0,
				0,0,1);
	}

	@Override
	public Matrix3 concatenate(Matrix3 that) {
		return concatenation(this, that);
	}

	@Override
	public Matrix3 concatenation(Matrix3 A, Matrix3 B) {
		final double m00 = A.m00*B.m00+A.m01*B.m10+A.m02*B.m20;
		final double m01 = A.m00*B.m01+A.m01*B.m11+A.m02*B.m21;
		final double m02 = A.m00*B.m02+A.m01*B.m12+A.m02*B.m22;
		
		final double m10 = A.m10*B.m00+A.m11*B.m10+A.m12*B.m20;
		final double m11 = A.m10*B.m01+A.m11*B.m11+A.m12*B.m21;
		final double m12 = A.m10*B.m02+A.m11*B.m12+A.m12*B.m22;
		
		final double m20 = A.m20*B.m00+A.m21*B.m10+A.m22*B.m20;
		final double m21 = A.m20*B.m01+A.m21*B.m11+A.m22*B.m21;
		final double m22 = A.m20*B.m02+A.m21*B.m12+A.m22*B.m22;
		
		return this.setElements(
				m00, m01, m02,
				m10, m11, m12,
				m20, m21, m22
				);
	}


	@Override
	public Matrix3 invert(Matrix3 m) {
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
	

	@Override
	public Matrix3 transpose(Matrix3 m) {
		return this.setElements(
				m.m00, m.m01, m.m02,
				m.m10, m.m11, m.m12,
				m.m20, m.m21, m.m22
			);
	}
	
	public Vec3 transform(Vec3 v) {
		return v.set(
			v.x*m00+v.y*m01+v.z*m02, 
			v.x*m10+v.y*m11+v.z*m12, 
			v.x*m20+v.y*m21+v.z*m22 
		);
	}
	
	public Vec2 project(Vec2 v) {
		double x = v.x*m00+v.y*m01+m02; 
		double y = v.x*m10+v.y*m11+m12;
		double w = v.x*m20+v.y*m21+m22;
		
		return v.set( x/w, y/w );
	}
		
	public double determinant() {
		return m00 * (m11 * m22 - m12 * m21) - m01 * (m22 * m10 - m12 * m20) + m02 * (m10 * m21 - m11 * m20);
	}
	
	public String toString() {
		final int width = 9, precision = 9;
		final String format = "%"+width+"."+precision+"g";
		
		StringBuilder sb = new StringBuilder();
		
		final double[] numbers = {m00, m01, m02, m10, m11, m12, m20, m21, m22};
		
		for (int i = 0; i<numbers.length; i++) {
			String s = String.format(Locale.US, format, numbers[i]);
			
			if (s.length()>precision) for (int pprecision = (2*precision-s.length()); s.length()>precision; pprecision--) {
				s = String.format(Locale.US, "%"+width+"."+pprecision+"g", numbers[i]);
			}
			sb.append(s);
			sb.append(i>0 && (i+1)%3==0?"\n":", ");
		}
		
		return sb.toString();
	}
	
}


