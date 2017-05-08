package de.dualuse.vecmath;

import java.util.Locale;

public class Matrix3 implements java.io.Serializable {
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

	public Matrix3 invert() {
		final double a = m00, b = m01, c = m02;
		final double d = m10, e = m11, f = m12;
		final double g = m20, h = m21, i = m22;
		
		final double A = (e*i-f*h), D =-(b*i-c*h), G = (b*f-c*e);
		final double B =-(d*i-f*g), E = (a*i-c*g), H =-(a*f-c*d);
		final double C = (d*h-e*g), F =-(a*h-b*g), I = (a*e-b*d);
	
		final double ooDetA = 1. / (a*A-b*(i*d-f*g)+c*(d*h-e*g));

		this.m00 = A*ooDetA; this.m01 = D*ooDetA; this.m02 = G*ooDetA;
		this.m10 = B*ooDetA; this.m11 = E*ooDetA; this.m12 = H*ooDetA;
		this.m20 = C*ooDetA; this.m21 = F*ooDetA; this.m22 = I*ooDetA;
		
		return this;
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


