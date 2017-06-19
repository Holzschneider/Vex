package de.dualuse.vecmath;

public interface MatrixAlgebra<Q> {
	public Q setZero();
	public Q setIdentity();

	public Q invert();
	public Q setInverse(Q that);
	
	public Q transpose();
	public Q setTransposed(Q that);
	
	public Q concatenate(Q that);
	public Q setConcatenation(Q A, Q B);
	
	public double determinant();
}
