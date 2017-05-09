package de.dualuse.vecmath;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class Matrix4d implements MatrixAlgebra<Matrix4d>, java.io.Serializable {
	private static final double EPSILON = 1.0E-10;
	private static final long serialVersionUID = 1L;

	public double m00,m01,m02,m03;
	public double m10,m11,m12,m13;
	public double m20,m21,m22,m23;
	public double m30,m31,m32,m33;
	
	public Matrix4d() {}
//	public Matrix4d(
//			double m00, double m01, double m02, double m03,
//			double m10, double m11, double m12, double m13,
//			double m20, double m21, double m22, double m23,
//			double m30, double m31, double m32, double m33 ) 
//	{
//		
//		this.elements(
//				m00, m01, m02, m03,
//				m10, m11, m12, m13, 
//				m20, m21, m22, m23, 
//				m30, m31, m32, m33);
//	}
//	
//	public Matrix4d(Matrix4d c) {
//		this.elements(
//				c.m00, c.m01, c.m02, c.m03, 
//				c.m10, c.m11, c.m12, c.m13, 
//				c.m20, c.m21, c.m22, c.m23, 
//				c.m30, c.m31, c.m32, c.m33	); 
//	}

	public Matrix4d fromString(String r) {
		String c[] = r.split("\\s+");

		return this.elements(
				new Double(c[ 0]), new Double(c[ 1]), new Double(c[ 2]), new Double(c[ 3]),
				new Double(c[ 4]), new Double(c[ 5]), new Double(c[ 6]), new Double(c[ 7]),
				new Double(c[ 8]), new Double(c[ 9]), new Double(c[10]), new Double(c[11]),
				new Double(c[12]), new Double(c[13]), new Double(c[14]), new Double(c[15]));
	}
	
	public String toString() {
		return	m00+" "+m01+" "+m02+" "+m03+
				m10+" "+m11+" "+m12+" "+m13+
				m20+" "+m21+" "+m22+" "+m23+
				m30+" "+m31+" "+m32+" "+m33;
	}

	@Override
	public Matrix4d clone() {
		return new Matrix4d().elements(
				this.m00, this.m01, this.m02, this.m03, 
				this.m10, this.m11, this.m12, this.m13, 
				this.m20, this.m21, this.m22, this.m23, 
				this.m30, this.m31, this.m32, this.m33);
	}
	

	//////////////////////////////////////////////////////////////////////////////
	
	private Matrix4d elements(
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
	
	public Matrix4d setColumns(Vector4d x, Vector4d y, Vector4d z, Vector4d w) {
		return elements(	//X Axis
				x.x, y.x, z.x, w.x,
				x.y, y.y, z.y, w.y,
				x.z, y.z, z.z, w.z,
				x.w, y.w, z.w, w.w
			);
				
	}

	public Matrix4d setRows(Vector4d x, Vector4d y, Vector4d z, Vector4d w) {
		return elements(	//X Axis
				x.x, x.y, x.z, x.w, 
				y.x, y.y, y.z, y.w,
				z.x, z.y, z.z, z.w,
				w.x, w.y, w.z, w.w
			);
				
	}
	
	@Override
	public Matrix4d zero() { 
		return elements(
				0.0,0.0,0.0,0.0,
				0.0,0.0,0.0,0.0,
				0.0,0.0,0.0,0.0,
				0.0,0.0,0.0,0.0
				);
	}
	
	@Override
	public Matrix4d identity() { 
		return elements(
				1.0,0.0,0.0,0.0,
				0.0,1.0,0.0,0.0,
				0.0,0.0,1.0,0.0,
				0.0,0.0,0.0,1.0
				);
	}
	

	@Override	
	public Matrix4d concatenate(Matrix4d that) {
		return concatenation(this, that);
	}
	
	@Override	
	public Matrix4d concatenation(Matrix4d A, Matrix4d B) {
		return this.elements(
				A.m00 * B.m00 + A.m10 * B.m01 + A.m20 * B.m02 + A.m30 * B.m03,
				A.m00 * B.m10 + A.m10 * B.m11 + A.m20 * B.m12 + A.m30 * B.m13,
				A.m00 * B.m20 + A.m10 * B.m21 + A.m20 * B.m22 + A.m30 * B.m23,
				A.m00 * B.m30 + A.m10 * B.m31 + A.m20 * B.m32 + A.m30 * B.m33,
				
				A.m01 * B.m00 + A.m11 * B.m01 + A.m21 * B.m02 + A.m31 * B.m03,
				A.m01 * B.m10 + A.m11 * B.m11 + A.m21 * B.m12 + A.m31 * B.m13,
				A.m01 * B.m20 + A.m11 * B.m21 + A.m21 * B.m22 + A.m31 * B.m23,
				A.m01 * B.m30 + A.m11 * B.m31 + A.m21 * B.m32 + A.m31 * B.m33,

				A.m02 * B.m00 + A.m12 * B.m01 + A.m22 * B.m02 + A.m32 * B.m03,
				A.m02 * B.m10 + A.m12 * B.m11 + A.m22 * B.m12 + A.m32 * B.m13,
				A.m02 * B.m20 + A.m12 * B.m21 + A.m22 * B.m22 + A.m32 * B.m23,
				A.m02 * B.m30 + A.m12 * B.m31 + A.m22 * B.m32 + A.m32 * B.m33,

				A.m03 * B.m00 + A.m13 * B.m01 + A.m23 * B.m02 + A.m33 * B.m03,
				A.m03 * B.m10 + A.m13 * B.m11 + A.m23 * B.m12 + A.m33 * B.m13,
				A.m03 * B.m20 + A.m13 * B.m21 + A.m23 * B.m22 + A.m33 * B.m23,
				A.m03 * B.m30 + A.m13 * B.m31 + A.m23 * B.m32 + A.m33 * B.m33 );
	}
	


	@Override
	public Matrix4d transpose(Matrix4d m) {
		return this.elements(
				m.m00, m.m10, m.m20, m.m30,
				m.m01, m.m11, m.m21, m.m31,
				m.m02, m.m12, m.m22, m.m32,
				m.m03, m.m13, m.m23, m.m33
			);
	}
	
	@Override	
	public Matrix4d invert(Matrix4d m) {
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

		return this.elements( 
				im0 * iDet, im1 * iDet, im2 * iDet, im3 * iDet, 
				im4 * iDet, im5 * iDet, im6 * iDet, im7 * iDet,
				im8 * iDet, im9 * iDet, im10 * iDet, im11 * iDet,
				im12 * iDet, im13 * iDet, im14 * iDet, im15 * iDet );
		
	}

	@Override
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


	////////////////////////////////////// Matrix4d Specific

	private Matrix4d concat( 
			double n00, double n01, double n02, double n03,
			double n10, double n11, double n12, double n13,
			double n20, double n21, double n22, double n23,
			double n30, double n31, double n32, double n33
			) {

		return elements(
				m00*n00+m10*n01+m20*n02+m30*n03,
				m00*n10+m10*n11+m20*n12+m30*n13,
				m00*n20+m10*n21+m20*n22+m30*n23,
				m00*n30+m10*n31+m20*n32+m30*n33,
				
				m01*n00+m11*n01+m21*n02+m31*n03,
				m01*n10+m11*n11+m21*n12+m31*n13,
				m01*n20+m11*n21+m21*n22+m31*n23,
				m01*n30+m11*n31+m21*n32+m31*n33,

				m02*n00+m12*n01+m22*n02+m32*n03,
				m02*n10+m12*n11+m22*n12+m32*n13,
				m02*n20+m12*n21+m22*n22+m32*n23,
				m02*n30+m12*n31+m22*n32+m32*n33,

				m03*n00+m13*n01+m23*n02+m33*n03,
				m03*n10+m13*n11+m23*n12+m33*n13,
				m03*n20+m13*n21+m23*n22+m33*n23,
				m03*n30+m13*n31+m23*n32+m33*n33
			);
	}


	public Matrix4d rotate(double theta, double ax, double ay, double az) {
		// compare '$ man glRotate' or 'javax.vecmath.Matrix4d.set(AxisAngle4d a1)'
		final double s = sin(theta), c = cos(theta), t = 1-c, l = sqrt(ax*ax+ay*ay+az*az);
		final double x = ax/l, y = ay/l, z= az/l, xz = x*z, xy = x*y, yz = y*z, xx=x*x, yy=y*y, zz=z*z;
		
		return this.concat(
				t*xx+c  , t*xy-s*z, t*xz+s*y, 0,
				t*xy+s*z, t*yy+c  , t*yz-s*x, 0,
				t*xz-s*y, t*yz+s*x, t*zz+c  , 0,
				       0,        0,      0,   1);
	}
	
	public Matrix4d translate(double tx, double ty, double tz) {
		return this.concat(
				1,0,0,tx,
				0,1,0,ty,
				0,0,1,tz,
				0,0,0, 1 );
	}
	
	public Matrix4d scale(double sx, double sy, double sz) {
		return this.concat(
				sx, 0, 0, 0,
				 0,sy, 0, 0,
				 0, 0,sz, 0,
				 0, 0, 0, 1);
	}
	

	
	/////////
	
	public Matrix4d setToTransformation(Matrix3d m) {
		return this.elements(
				m.m00, m.m01, m.m02,   0, 
				m.m10, m.m11, m.m12,   0,
				m.m20, m.m21, m.m22,   0,
				0,     0,         0,   1
			);
	}

	public Matrix4d setToProjection(Matrix3d m) {
		return this.elements(
				m.m00, m.m01,   0, m.m02, 
				m.m10, m.m11,   0, m.m12,
				    0,     0,   0,     0,
				m.m20, m.m21,   0, m.m22 
			);
	}

	public Matrix4d setToRotation(Quaternion quat) {
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

	public Matrix4d setToTranslation(Vector3d translation) {
		final double tx = translation.x, ty = translation.y, tz = translation.z;
		return elements(	
				1.0,0.0,0.0, tx,
				0.0,1.0,0.0, ty,
				0.0,0.0,1.0, tz,
				0.0,0.0,0.0, 1
			);		
	}
	

	public Matrix4d setToScale(Vector3d scaling) {
		final double scx = scaling.x, scy = scaling.y, scz = scaling.y; 
		return elements(	
				scx,0.0,0.0,0.0,
				0.0,scy,0.0,0.0,
				0.0,0.0,scz,0.0,
				0.0,0.0,0.0,1.0
			);
	}
	
	public Matrix4d viewport(double x, double y, double width, double height) {
		return this
				.translate(x, y, 0)
				.scale(width,height,1)
				.scale(0.5,-0.5,1)
				.translate(1,-1,0);
	}
	
	public Matrix4d frustum(double left, double right, double bottom, double top, double near, double far) {
		// see '$ man glFrustum'
		final double A = (right + left)/(right - left);
		final double B = (top + bottom)/(top - bottom);
		final double C = -(far + near)/(far - near);
		final double D = -(2 * far * near) /( far -near);
		
		return this.elements(
				(2*near)/(right-left),                     0, A, 0,
				0                    , (2*near)/(top-bottom), B, 0,
				0                    ,                     0, C, D,
				0                    ,                     0,-1, 0
			);
	}
	
	//////////
	
	public Vector3d project(Vector3d v) {
		final double x = v.x * m00 + v.y * m01 + v.z * m02 + m03; 
		final double y = v.x * m10 + v.y * m11 + v.z * m12 + m13; 
		final double z = v.x * m20 + v.y * m21 + v.z * m22 + m23;
		final double w = v.x * m30 + v.y * m31 + v.z * m32 + m33;
		
		return v.set( x/w, y/w, z/w );
	}
	
	public Vector4d transform(Vector4d v) {
		return v.set(	
				v.x * m00 + v.y * m01 + v.z * m02 + v.w * m03, 
				v.x * m10 + v.y * m11 + v.z * m12 + v.w * m13, 
				v.x * m20 + v.y * m21 + v.z * m22 + v.w * m23,
				v.x * m30 + v.y * m31 + v.z * m32 + v.w * m33
			);
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
	
	
	
}
