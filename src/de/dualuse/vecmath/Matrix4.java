package de.dualuse.vecmath;

import java.util.Locale;

public class Matrix4 implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	public double m00,m01,m02,m03;
	public double m10,m11,m12,m13;
	public double m20,m21,m22,m23;
	public double m30,m31,m32,m33;
	
	public Matrix4 set(
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
	
	public Matrix4 setColumns(Vec4 x, Vec4 y, Vec4 z, Vec4 w) {
		return set(	//X Axis
				x.x, y.x, z.x, w.x,
				x.y, y.y, z.y, w.y,
				x.z, y.z, z.z, w.z,
				x.w, y.w, z.w, w.w
			);
				
	}

	public Matrix4 setRows(Vec4 x, Vec4 y, Vec4 z, Vec4 w) {
		return set(	//X Axis
				x.x, x.y, x.z, x.w, 
				y.x, y.y, y.z, y.w,
				z.x, z.y, z.z, z.w,
				w.x, w.y, w.z, w.w
			);
				
	}
	
	public Matrix4 setToQuaternion(Quaternion quat) {
		final double qx = quat.x, qy = quat.y, qz = quat.z, qw = quat.w;
		
		m00 = qw*qw + qx*qx - qy*qy - qz*qz; 
		m10 = 2*qx*qy + 2*qw*qz;
		m20 = 2*qx*qz - 2*qw*qy; 
		m30 = 0.0;
		
		//Y Axis
		m01 = 2*qx*qy-2*qw*qz;
		m11 = qw*qw - qx*qx + qy*qy - qz*qz;
		m21 = 2*qy*qz + 2*qw*qx;
		m31 = 0.0;
		
		//Z Axis
		m02 = 2*qx*qz + 2*qw*qy;
		m12 = 2*qy*qz - 2*qw*qx;
		m22 = qw*qw - qx*qx - qy*qy + qz*qz;
		m32 = 0.0;
		
		//?????????
		m03 = 0.;
		m13 = 0.;
		m23 = 0.;
		m33 = qw*qw + qx*qx + qy*qy + qz*qz;

		return this;
	}

	public Matrix4 setToTranslation(Vec3 translation) {
		final double tx = translation.x, ty = translation.y, tz = translation.z;
		return set(	
				1.0,0.0,0.0, tx,
				0.0,1.0,0.0, ty,
				0.0,0.0,1.0, tz,
				0.0,0.0,0.0, 1
			);		
	}
	

	public Matrix4 setToScale(Vec3 scaling) {
		final double scx = scaling.x, scy = scaling.y, scz = scaling.y; 
		return set(	scx,0.0,0.0,0.0,
					0.0,scy,0.0,0.0,
					0.0,0.0,scz,0.0,
					0.0,0.0,0.0,1.0 );
		
	}
		
	
//	public Matrix4 decompose(Vec3 translation, Quaternion rotation, Vec3 scaling) {
//		//TODO throw runtimeException if Matrix4 describes a non decomposable transform 
//		//this decomposition works only if the 3x3 core of M is a product of a diagonal scaling Matrix4 and a rotation
//		
//		translation.set(m03, m13, m23);
//		
//		final double lx = Math.sqrt(m00*m00+m10*m10+m20*m20);
//		final double ly = Math.sqrt(m01*m01+m11*m11+m21*m21);
//		final double lz = Math.sqrt(m02*m02+m12*m12+m22*m22);
//
//		scaling.set(lx,ly,lz);
//		
//		rotation.setToTransformation(
//				m00/lx,m10/lx,m20/lx, 
//				m01/ly,m11/ly,m21/ly,
//				m02/lz,m12/lz,m22/lz
//		);
//		
//		return this;			
//	}
	
	
	public Matrix4 zero() { return set(0.,0.,0.,0.,0.,0.,0.,0.,0.,0.,0.,0.,0.,0.,0.,0.); }
	
	public Matrix4 identity() { return set(1., 0., 0., 0., 0., 1., 0., 0., 0., 0., 1., 0., 0., 0., 0., 1.); }

	public Matrix4 invert() {
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

		final double det = fA0 * fB5 - fA1 * fB4 + fA2 * fB3 + fA3 * fB2 - fA4 * fB1 + fA5 * fB0;

		if (Math.abs(det) == 0)
			throw new RuntimeException("Non invertible transform");

		final double im0 = +m11 * fB5 - m12 * fB4 + m13 * fB3;
		final double im4 = -m10 * fB5 + m12 * fB2 - m13 * fB1;
		final double im8 = +m10 * fB4 - m11 * fB2 + m13 * fB0;
		final double im12 = -m10 * fB3 + m11 * fB1 - m12 * fB0;
		final double im1 = -m01 * fB5 + m02 * fB4 - m03 * fB3;
		final double im5 = +m00 * fB5 - m02 * fB2 + m03 * fB1;
		final double im9 = -m00 * fB4 + m01 * fB2 - m03 * fB0;
		final double im13 = +m00 * fB3 - m01 * fB1 + m02 * fB0;
		final double im2 = +m31 * fA5 - m32 * fA4 + m33 * fA3;
		final double im6 = -m30 * fA5 + m32 * fA2 - m33 * fA1;
		final double im10 = +m30 * fA4 - m31 * fA2 + m33 * fA0;
		final double im14 = -m30 * fA3 + m31 * fA1 - m32 * fA0;
		final double im3 = -m21 * fA5 + m22 * fA4 - m23 * fA3;
		final double im7 = +m20 * fA5 - m22 * fA2 + m23 * fA1;
		final double im11 = -m20 * fA4 + m21 * fA2 - m23 * fA0;
		final double im15 = +m20 * fA3 - m21 * fA1 + m22 * fA0;

		final double iDet = 1. / det;

		return this.set( 
				im0 * iDet, im1 * iDet, im2 * iDet, im3 * iDet, 
				im4 * iDet, im5 * iDet, im6 * iDet, im7 * iDet,
				im8 * iDet, im9 * iDet, im10 * iDet, im11 * iDet,
				im12 * iDet, im13 * iDet, im14 * iDet, im15 * iDet );
		
	}

	public double determinant() {
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

	
	public Matrix4 fromString(String r) {
		String c[] = r.split("\\s+");

		return this.set(
				new Double(c[0]), 
				new Double(c[1]), 
				new Double(c[2]), 
				new Double(c[3]),
				
				new Double(c[4]), 
				new Double(c[5]), 
				new Double(c[6]), 
				new Double(c[7]),
				
				new Double(c[8]), 
				new Double(c[9]), 
				new Double(c[10]), 
				new Double(c[11]),
				
				new Double(c[12]), 
				new Double(c[13]), 
				new Double(c[14]),
				new Double(c[15]));
	}
	

	public String toString() {
		final int width = 9, precision = 9;
		final String format = "%"+width+"."+precision+"g";
		
		StringBuilder sb = new StringBuilder();

		double[] numbers = {m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33};
		
		for (int i = 0; i<numbers.length; i++) {
			String s = String.format(Locale.US, format, numbers[i]);
			
			if (s.length()>precision) for (int pprecision = (2*precision-s.length()); s.length()>precision; pprecision--) {
				s = String.format(Locale.US, "%"+width+"."+pprecision+"g", numbers[i]);
			}
			sb.append(s);
			sb.append(i>0 && (i+1)%4==0?"\n":", ");
		}
		
		return sb.toString();
	}

	public Matrix4 concatenate(Matrix4 that) {
		return concatenation(this, that);
	}
	
	public Matrix4 concatenation(Matrix4 A, Matrix4 B) {
		final double a00 = A.m00, a01 = A.m01, a02 = A.m02, a03 = A.m03;
		final double a10 = A.m10, a11 = A.m11, a12 = A.m12, a13 = A.m13;
		final double a20 = A.m20, a21 = A.m21, a22 = A.m22, a23 = A.m23;
		final double a30 = A.m30, a31 = A.m31, a32 = A.m32, a33 = A.m33;
		
		final double b00 = B.m00, b01 = B.m01, b02 = B.m02, b03 = B.m03;
		final double b10 = B.m10, b11 = B.m11, b12 = B.m12, b13 = B.m13;
		final double b20 = B.m20, b21 = B.m21, b22 = B.m22, b23 = B.m23;
		final double b30 = B.m30, b31 = B.m31, b32 = B.m32, b33 = B.m33;
		
		this.m00 = a00 * b00 + a10 * b01 + a20 * b02 + a30 * b03;
		this.m01 = a00 * b10 + a10 * b11 + a20 * b12 + a30 * b13;
		this.m02 = a00 * b20 + a10 * b21 + a20 * b22 + a30 * b23;
		this.m03 = a00 * b30 + a10 * b31 + a20 * b32 + a30 * b33;
		
		this.m10 = a01 * b00 + a11 * b01 + a21 * b02 + a31 * b03;
		this.m11 = a01 * b10 + a11 * b11 + a21 * b12 + a31 * b13;
		this.m12 = a01 * b20 + a11 * b21 + a21 * b22 + a31 * b23;
		this.m13 = a01 * b30 + a11 * b31 + a21 * b32 + a31 * b33;
		
		this.m20 = a02 * b00 + a12 * b01 + a22 * b02 + a32 * b03;
		this.m21 = a02 * b10 + a12 * b11 + a22 * b12 + a32 * b13;
		this.m22 = a02 * b20 + a12 * b21 + a22 * b22 + a32 * b23;
		this.m23 = a02 * b30 + a12 * b31 + a22 * b32 + a32 * b33;

		this.m30 = a03 * b00 + a13 * b01 + a23 * b02 + a33 * b03;
		this.m31 = a03 * b10 + a13 * b11 + a23 * b12 + a33 * b13;
		this.m32 = a03 * b20 + a13 * b21 + a23 * b22 + a33 * b23;
		this.m33 = a03 * b30 + a13 * b31 + a23 * b32 + a33 * b33;
		
		return this;
	}

	public Matrix4 rotate(double ax, double ay, double az) {
		
		return this;
	}
	
	public Matrix4 translate(double tx, double ty, double tz) {
		this.m30 += tx;
		this.m31 += ty;
		this.m32 += tz;
		
		return this;
	}
	
	public Matrix4 scale(double scX, double scY, double scZ) {
		return set(
				m00 * scX, m01 * scY, m02 * scZ, m03,
				m10 * scX, m11 * scY, m12 * scZ, m13,
				m20 * scX, m21 * scY, m22 * scZ, m23,
				m30, m31, m32, m33
		);
	}
	
	//////////
	
	
	public Vec3 project(Vec3 v) {
		final double w = v.x * m30 + v.y * m31 + v.z * m32 + m33;
		return v.set(	
				(v.x * m00 + v.y * m01 + v.z * m02 + m03)/w, 
				(v.x * m10 + v.y * m11 + v.z * m12 + m13)/w, 
				(v.x * m20 + v.y * m21 + v.z * m22 + m23)/w
			);
	}
	
	public Vec4 transform(Vec4 v) {
		return v.set(	v.x * m00 + v.y * m01 + v.z * m02 + v.w * m03, 
						v.x * m10 + v.y * m11 + v.z * m12 + v.w * m13, 
						v.x * m20 + v.y * m21 + v.z * m22 + v.w * m23,
						v.x * m30 + v.y * m31 + v.z * m32 + v.w * m33
					);
	}

	
	
	
}
