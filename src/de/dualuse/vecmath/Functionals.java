package de.dualuse.vecmath;

public class Functionals {

	public static interface Vector2d {
		static public interface Function<T> {
			public T apply(double x, double y);
		}
		
		static public interface Consumer {
			public void accept(double x, double y);
		}

	}
	
	public static interface Vector3d {
		public static interface Consumer {
			public void accept(double x, double y, double z);
		}

		public static interface Function<T> {
			public T apply(double x, double y, double z);
		}
	}
	
	public static interface Vector4d {
		public static interface Consumer {
			public void accept(double x, double y, double z, double w);
		}

		public static interface Function<T> {
			public T apply(double x, double y, double z, double w);
		}
	}

	public static interface Quaternion {
		public static interface Consumer {
			public void accept(double x, double y, double z, double w);
		}

		public static interface Function<T> {
			public T apply(double x, double y, double z, double w);
		}
	}
	

	public static interface Matrix3d {
		public static interface Consumer {
			public void accept( double m00, double m01, double m02,
								double m10, double m11, double m12,
								double m20, double m21, double m22
								);
		}

		public interface Function<T> {
			public T apply( double m00, double m01, double m02,
							double m10, double m11, double m12,
							double m20, double m21, double m22
							);
		}
	}

	public static interface Matrix4d {
		public static interface Consumer {
			public void accept(	double m00, double m01, double m02, double m03,
								double m10, double m11, double m12, double m13,
								double m20, double m21, double m22, double m23,
								double m30, double m31, double m32, double m33
								);
		}

		public interface Function<T> {
			public T apply(	double m00, double m01, double m02, double m03,
							double m10, double m11, double m12, double m13,
							double m20, double m21, double m22, double m23,
							double m30, double m31, double m32, double m33
							);
		}
	}
}
