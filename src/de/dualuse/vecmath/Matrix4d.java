package de.dualuse.vecmath;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

import java.io.Serializable;

public class Matrix4d extends Matrix<Matrix4d> implements Serializable {
	private static final double EPSILON = 1.0E-10;
	private static final long serialVersionUID = 1L;

	public double m00,m01,m02,m03;
	public double m10,m11,m12,m13;
	public double m20,m21,m22,m23;
	public double m30,m31,m32,m33;
	
//==[ Constructors ]================================================================================
	
	public Matrix4d() { 
		identity(); 
	}	
	
	public static Matrix4d from(
			double m00, double m01, double m02, double m03,
			double m10, double m11, double m12, double m13,
			double m20, double m21, double m22, double m23,
			double m30, double m31, double m32, double m33
			) {
		return new Matrix4d().setElements(m00,m01,m02,m03,m10,m11,m12,m13,m20,m21,m22,m23,m30,m31,m32,m33);
	}
	
//==[ Element-wise Operations ]=====================================================================

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

	public Matrix4d addElements(
			double n00, double n01, double n02, double n03,
			double n10, double n11, double n12, double n13,
			double n20, double n21, double n22, double n23,
			double n30, double n31, double n32, double n33
			) {
		return this.setElements(
			m00+n00, m01+n01, m02+n02, m03+n03,
			m10+n10, m11+n11, m12+n12, m13+n13,
			m20+n20, m21+n21, m22+n22, m23+n23,
			m30+n30, m31+n31, m32+n32, m33+n33
		);
	}

	public Matrix4d mulElements(
			double n00, double n01, double n02, double n03,
			double n10, double n11, double n12, double n13,
			double n20, double n21, double n22, double n23,
			double n30, double n31, double n32, double n33
			) {
		return this.setElements(
			m00*n00, m01*n01, m02*n02, m03*n03,
			m10*n10, m11*n11, m12*n12, m13*n13,
			m20*n20, m21*n21, m22*n22, m23*n23,
			m30*n30, m31*n31, m32*n32, m33*n33
		);
	}

	/////
	
	public Matrix4d add(
			double n00, double n01, double n02, double n03,
			double n10, double n11, double n12, double n13,
			double n20, double n21, double n22, double n23,
			double n30, double n31, double n32, double n33
			) {
		return addElements(n00,n01,n02,n03,n10,n11,n12,n13,n20,n21,n22,n23,n30,n31,n32,n33);
	}
	
	public Matrix4d sub(
			double n00, double n01, double n02, double n03,
			double n10, double n11, double n12, double n13,
			double n20, double n21, double n22, double n23,
			double n30, double n31, double n32, double n33
			) {
		return addElements(-n00,-n01,-n02,-n03,-n10,-n11,-n12,-n13,-n20,-n21,-n22,-n23,-n30,-n31,-n32,-n33);
	}

//==[ Tuple<Matrix3d> ]=============================================================================
	
	@Override public Matrix4d fromString(String r) {
		String c[] = r.split("\\s+");

		return this.setElements(
			new Double(c[ 0]), new Double(c[ 1]), new Double(c[ 2]), new Double(c[ 3]),
			new Double(c[ 4]), new Double(c[ 5]), new Double(c[ 6]), new Double(c[ 7]),
			new Double(c[ 8]), new Double(c[ 9]), new Double(c[10]), new Double(c[11]),
			new Double(c[12]), new Double(c[13]), new Double(c[14]), new Double(c[15])
		);
	}
	
	@Override public String toString() {
		return	m00+" "+m01+" "+m02+" "+m03+
				m10+" "+m11+" "+m12+" "+m13+
				m20+" "+m21+" "+m22+" "+m23+
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

//==[ MatrixAlgebra<Matrix4d> ]=====================================================================

	@Override public Matrix4d zero() { 
		return setElements(
			0.0,0.0,0.0,0.0,
			0.0,0.0,0.0,0.0,
			0.0,0.0,0.0,0.0,
			0.0,0.0,0.0,0.0
		);
	}
	
	@Override public Matrix4d identity() { 
		return setElements(
			1.0,0.0,0.0,0.0,
			0.0,1.0,0.0,0.0,
			0.0,0.0,1.0,0.0,
			0.0,0.0,0.0,1.0
		);
	}

	@Override public Matrix4d invert() {
		return this.inversion(this);
	}
	
	@Override public Matrix4d inversion(Matrix4d m) {
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

		return this.setElements( 
			im0 * iDet, im1 * iDet, im2 * iDet, im3 * iDet, 
			im4 * iDet, im5 * iDet, im6 * iDet, im7 * iDet,
			im8 * iDet, im9 * iDet, im10 * iDet, im11 * iDet,
			im12 * iDet, im13 * iDet, im14 * iDet, im15 * iDet
		);
	}

	@Override public Matrix4d transpose() {
		return this.transposition(this);
	}
	
	@Override public Matrix4d transposition(Matrix4d m) {
		return this.setElements(
			m.m00, m.m10, m.m20, m.m30,
			m.m01, m.m11, m.m21, m.m31,
			m.m02, m.m12, m.m22, m.m32,
			m.m03, m.m13, m.m23, m.m33
		);
	}

	@Override public Matrix4d concatenate(Matrix4d that) {
		return concatenation(this, that);
	}
	
	@Override public Matrix4d concatenation(Matrix4d A, Matrix4d B) {
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
	
//==[ Matrix<Matrix4d> ]============================================================================
	
	@Override public Matrix4d add(Matrix4d a) {
		return addElements(
			a.m00,a.m01,a.m02,a.m03,
			a.m10,a.m11,a.m12,a.m13,
			a.m20,a.m21,a.m22,a.m23,
			a.m30,a.m31,a.m32,a.m33
		);	
	}

	@Override public Matrix4d sub(Matrix4d a) {
		return addElements(
			-a.m00,-a.m01,-a.m02,-a.m03,
			-a.m10,-a.m11,-a.m12,-a.m13,
			-a.m20,-a.m21,-a.m22,-a.m23,
			-a.m30,-a.m31,-a.m32,-a.m33
		);
	}
	
	@Override public Matrix4d mul(Matrix4d a) {
		return mulElements(
			a.m00,a.m01,a.m02,a.m03,
			a.m10,a.m11,a.m12,a.m13,
			a.m20,a.m21,a.m22,a.m23,
			a.m30,a.m31,a.m32,a.m33
		);	
	}
	
//==[ Matrix4d Specific ]===========================================================================

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

	Matrix4d concat( 
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
	
	
	public Matrix4d rotate(Quaternion q) {
		final double x = q.x, y = q.y, z = q.z, w = q.w;

		final double ww = w*w, xx = x*x, yy= y*y, zz = z*z;
		final double xy = x*x, xz = x*z, xw = x*w;
		final double yz = y*z, yw = y*w, zw = z*w; 
		
		return this.concat(
			ww+xx-yy-zz, 2*xy-2*zw, 2*xz+2*yw, 0.,
			2*xy+2*zw, ww-xx+yy-zz, 2*yz-2*xw, 0.,						
			2*xz-2*yw, 2*yz+2*xw, ww-xx-yy+zz, 0.,
			0.0, 0.0, 0.0, 	ww+xx+yy+zz
		);
	}

	public Matrix4d rotate(AxisAngle aa) { return this.rotate(aa.x,aa.y,aa.z,aa.t); }
	
	public Matrix4d rotate(double ax, double ay, double az, double theta) {
//		// compare '$ man glRotate' or 'javax.vecmath.Matrix4d.set(AxisAngle4d a1)'
		
		final double s = sin(theta), c = cos(theta), t = 1-c, l = sqrt(ax*ax+ay*ay+az*az);
		final double x = (ax/l), y = (ay/l), z= (az/l);
		final double xz = x*z, xy = x*y, yz = y*z, xx=x*x, yy=y*y, zz=z*z;
		
		return this.concat(
				t*xx+c  , t*xy-s*z, t*xz+s*y, 0,
				t*xy+s*z, t*yy+c  , t*yz-s*x, 0,
				t*xz-s*y, t*yz+s*x, t*zz+c  , 0,
				       0,        0,      0,   1);

	}

//	public Matrix4d translate(Vector3d v);
	public Matrix4d translate(double tx, double ty, double tz) {
		return this.concat(
			1,0,0,tx,
			0,1,0,ty,
			0,0,1,tz,
			0,0,0, 1
		);
	}

//	public Matrix4d scale(Vector3d v);
	public Matrix4d scale(double sx, double sy, double sz) {
		return this.concat(
			sx, 0, 0, 0,
			 0,sy, 0, 0,
			 0, 0,sz, 0,
			 0, 0, 0, 1
		);
	}
	
	
	/////////
	public static Matrix4d fromTransformation( Matrix3d m ) { return new Matrix4d().setTransformation( m ); }
	public Matrix4d setTransformation(Matrix3d m) {
		return this.setElements(
			m.m00, m.m01, m.m02,   0, 
			m.m10, m.m11, m.m12,   0,
			m.m20, m.m21, m.m22,   0,
			0,     0,         0,   1
		);
	}

	public static Matrix4d fromProjection( Matrix3d m ) { return new Matrix4d().setProjection( m ); }
	public Matrix4d setProjection(Matrix3d m) {
		return this.setElements(
			m.m00, m.m01,   0, m.m02, 
			m.m10, m.m11,   0, m.m12,
			    0,     0,   0,     0,
			m.m20, m.m21,   0, m.m22 
		);
	}

	public static Matrix4d fromRotation( Quaternion q ) { return new Matrix4d().setRotation(q); }
	public Matrix4d setRotation(Quaternion quat) {
		final double x = quat.x, y = quat.y, z = quat.z, w = quat.w;

		final double ww = w*w, xx = x*x, yy= y*y, zz = z*z;
		final double xy = x*x, xz = x*z, xw = x*w;
		final double yz = y*z, yw = y*w, zw = z*w; 
		
		return this.setElements(
			ww+xx-yy-zz, 2*xy-2*zw, 2*xz+2*yw, 0.,
			2*xy+2*zw, ww-xx+yy-zz, 2*yz-2*xw, 0.,						
			2*xz-2*yw, 2*yz+2*xw, ww-xx-yy+zz, 0.,
			0.0, 0.0, 0.0, 	ww+xx+yy+zz
		);
				
	}

	public static Matrix4d fromTranslation( Vector3d v ) { return new Matrix4d().setTranslation( v ); }
	public Matrix4d setTranslation(Vector3d translation) {
		final double tx = translation.x, ty = translation.y, tz = translation.z;
		return setElements(	
			1.0,0.0,0.0, tx,
			0.0,1.0,0.0, ty,
			0.0,0.0,1.0, tz,
			0.0,0.0,0.0, 1
		);		
	}
	
	public static Matrix4d fromScale( Vector3d v ) { return new Matrix4d().setScale( v ); }
	public Matrix4d setScale(Vector3d scaling) {
		final double scx = scaling.x, scy = scaling.y, scz = scaling.y; 
		return setElements(	
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
		
		return this.setElements(
			(2*near)/(right-left),                     0, A, 0,
			0                    , (2*near)/(top-bottom), B, 0,
			0                    ,                     0, C, D,
			0                    ,                     0,-1, 0
		);
	}
	
	//////////

//	public Vector2d project(Vector2d v, double z);
//	public Vector2d intersect(Vector2d v, double z);

	public Vector3d project(Vector3d v) {
		final double x = v.x * m00 + v.y * m01 + v.z * m02 + m03; 
		final double y = v.x * m10 + v.y * m11 + v.z * m12 + m13; 
		final double z = v.x * m20 + v.y * m21 + v.z * m22 + m23;
		final double w = v.x * m30 + v.y * m31 + v.z * m32 + m33;
		
		return v.xyz( x/w, y/w, z/w );
	}
	
	public<T> T project(double vx, double vy, double vz, Value3<T> v) {
		final double x = vx * m00 + vy * m01 + vz * m02 + m03; 
		final double y = vx * m10 + vy * m11 + vz * m12 + m13; 
		final double z = vx * m20 + vy * m21 + vz * m22 + m23;
		final double w = vx * m30 + vy * m31 + vz * m32 + m33;
		
		return v.set( x/w, y/w, z/w );		
	}
	
	public double[] project(double[] v) {
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

	
	public Vector4d transform(Vector4d v) {
		return v.xyzw(	
			v.x * m00 + v.y * m01 + v.z * m02 + v.w * m03, 
			v.x * m10 + v.y * m11 + v.z * m12 + v.w * m13, 
			v.x * m20 + v.y * m21 + v.z * m22 + v.w * m23,
			v.x * m30 + v.y * m31 + v.z * m32 + v.w * m33
		);
	}
	
	public<T> T transform(double vx, double vy, double vz, double vw, Value4<T> v) {
		final double x = vx * m00 + vy * m01 + vz * m02 + vw * m03; 
		final double y = vx * m10 + vy * m11 + vz * m12 + vw * m13; 
		final double z = vx * m20 + vy * m21 + vz * m22 + vw * m23;
		final double w = vx * m30 + vy * m31 + vz * m32 + vw * m33;
		
		return v.set( x, y, z, w );		
	}
	
	public double[] transform(double[] v) {
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
//	//TODO Verify this
//	public Quaternion setToTransform(Matrix3d t) {
//		double  T = t.m00 + t.m11 + t.m22 + 1.;
//
//		// If the trace of the t.matrix is greater than zero, then the result is:
//		if (T>0.) {
//		      double S = 0.5 / Math.sqrt(T);
//		      
//		      return this.set(( t.m21 - t.m12 ) * S,( t.m02 - t.m20 ) * S,( t.m10 - t.m01 ) * S,0.25 / S);
//		} else { 
//			//If the trace of the t.matrix is less than or equal to zero then identify which t.major diagonal elet.ment has the greatest value.
//			if ((t.m00 > t.m11)&&(t.m00 > t.m22)) { 
//			   final double S = Math.sqrt( 1.0 + t.m00 - t.m11 - t.m22 ) * 2; // S=4*qx 
//			   final double qw = (t.m21 - t.m12) / S;
//			   final double qx = 0.25 * S;
//			   final double qy = (t.m01 + t.m10) / S; 
//			   final double qz = (t.m02 + t.m20) / S;
//			   return this.set(qx,qy,qz,qw);
//			} else if (t.m11 > t.m22) { 
//			   final double S = Math.sqrt( 1.0 + t.m11 - t.m00 - t.m22 ) * 2; // S=4*qy
//			   final double qw = (t.m02 - t.m20) / S;
//			   final double qx = (t.m01 + t.m10) / S; 
//			   final double qy = 0.25 * S;
//			   final double qz = (t.m12 + t.m21) / S;
//			   return this.set(qx,qy,qz,qw);
//			} else { 
//			   final double S = Math.sqrt( 1.0 + t.m22 - t.m00 - t.m11 ) * 2; // S=4*qz
//			   final double qw = (t.m10 - t.m01) / S;
//			   final double qx = (t.m02 + t.m20) / S; 
//			   final double qy = (t.m12 + t.m21) / S; 
//			   final double qz = 0.25 * S;
//			   return this.set(qx,qy,qz,qw);
//			}
//		}
//	}	
	
	
}
