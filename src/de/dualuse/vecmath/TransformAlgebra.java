package de.dualuse.vecmath;


public interface TransformAlgebra<Q> {
	public Q identity();

	public Q inversion(Q q);
		
	public Q concatenate(Q that);
	public Q concatenation(Q A, Q B);
}
