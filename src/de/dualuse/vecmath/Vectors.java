package de.dualuse.vecmath;


/**
 * Proof that there's no name aliasing
 * @author pholzschneider
 */


public final class Vectors {
	
	/// Quaternion & AxisAngle
	public static Vector3d mul(Quaternion a, Vector3d b) { return Quaternion.mul(a, b); }
	public static Quaternion mul(Quaternion a, Quaternion b) { return Quaternion.mul(a, b); }

    public static Vector3d mul(AxisAngle a, Vector3d b) { return AxisAngle.mul(a, b); }
	public static Quaternion mul(AxisAngle a, AxisAngle b) { return AxisAngle.mul(a, b); } 
	
	/// Matrix4d
	public static Matrix4d outer(Vector4d a, Vector4d b) { return Matrix4d.outer(a, b); }
	public static Vector4d mul(Matrix4d a, Vector4d b) { return Matrix4d.mul(a, b); }
	public static Matrix4d mul(Matrix4d a, Matrix4d b) { return Matrix4d.mul(a,b); }
	public static Vector3d project(Matrix4d a, Vector3d b) { return Matrix4d.project(a,b); }
	
	public static Matrix3d minor(Matrix4d a, int row, int col) { return Matrix4d.minor(a, row, col); }
	public static Matrix4d transpose( Matrix4d a ) { return Matrix4d.transpose(a); }
	public static Matrix4d invert( Matrix4d a ) { return Matrix4d.invert(a); }

	
	public static Matrix4d rotate(Matrix4d a, Quaternion b) { return Matrix4d.rotate(a, b); }
	public static Matrix4d rotate(Quaternion a, Matrix4d b) { return Matrix4d.rotate(a, b); }
	public static Matrix4d rotate(Matrix4d a, AxisAngle b) { return Matrix4d.rotate(a, b); }
	public static Matrix4d rotate(AxisAngle a, Matrix4d b) { return Matrix4d.rotate(a, b); }
	public static Matrix4d translate(Matrix4d a, Vector3d b) { return Matrix4d.translate(a, b); }
	public static Matrix4d translate(Vector3d a, Matrix4d b) { return Matrix4d.translate(a, b); }
	
	/// Matrix3d
	public static Matrix3d outer(Vector3d a, Vector3d b) { return Matrix3d.outer(a, b); }
	public static Vector3d mul(Matrix3d a, Vector3d b) { return Matrix3d.mul(a,b); }
	public static Matrix3d mul(Matrix3d a, Matrix3d b) { return Matrix3d.mul(a, b); }
	
	public static Matrix3d invert( Matrix3d a ) { return Matrix3d.invert(a); }
	public static Matrix3d transpose( Matrix3d a ) { return Matrix3d.transpose(a); }

	
	public static Matrix3d rotate(Matrix3d a, Quaternion b) { return Matrix3d.rotate(a, b); }
	public static Matrix3d rotate(Quaternion a, Matrix3d b) { return Matrix3d.rotate(a, b); }
	public static Matrix3d rotate(Matrix3d a, AxisAngle b) { return Matrix3d.rotate(a, b); }
	public static Matrix3d rotate(AxisAngle a, Matrix3d b) { return Matrix3d.rotate(a, b); }
	public static Matrix3d translate(Matrix3d a, Vector2d b) { return Matrix3d.translate(a, b); }
	public static Matrix3d translate(Vector2d a, Matrix3d b) { return Matrix3d.translate(a, b); }
	
	/// Vector2d 
	public static Vector2d add(Vector2d a, Vector2d b) { return Vector2d.add(a, b); }
	public static Vector2d sub(Vector2d a, Vector2d b) { return Vector2d.sub(a, b); }
	public static Vector2d mul(Vector2d a, Vector2d b) { return Vector2d.mul(a, b); }
	public static Vector2d div(Vector2d a, Vector2d b) { return Vector2d.div(a, b); }
	public static double dot(Vector2d a, Vector2d b) { return Vector2d.dot(a, b); }
	public static Vector2d project( Vector3d a ) { return Vector2d.project(a); }
	
	/// Vector3d 
	public static Vector3d add(Vector3d a, Vector3d b) { return Vector3d.add(a, b); }
	public static Vector3d sub(Vector3d a, Vector3d b) { return Vector3d.sub(a, b); }
	public static Vector3d mul(Vector3d a, Vector3d b) { return Vector3d.mul(a, b); }
	public static Vector3d div(Vector3d a, Vector3d b) { return Vector3d.div(a, b); }
	public static double dot(Vector3d a, Vector3d b) { return Vector3d.dot(a, b); }
	public static Vector3d project( Vector4d a ) { return Vector3d.project(a); }
	public static Vector3d cross(Vector3d a, Vector3d b) { return Vector3d.cross(a, b); }
	public static Vector3d augment( Vector2d a ) { return Vector3d.augment(a); }


	/// Vector4d 
	public static Vector4d add(Vector4d a, Vector4d b) { return Vector4d.add(a, b); }
	public static Vector4d sub(Vector4d a, Vector4d b) { return Vector4d.sub(a, b); }
	public static Vector4d mul(Vector4d a, Vector4d b) { return Vector4d.mul(a, b); }
	public static Vector4d div(Vector4d a, Vector4d b) { return Vector4d.div(a, b); }
	public static double dot(Vector4d a, Vector4d b) { return Vector4d.dot(a, b); }
	public static Vector4d augment( Vector3d a ) { return Vector4d.augment(a); }


}







