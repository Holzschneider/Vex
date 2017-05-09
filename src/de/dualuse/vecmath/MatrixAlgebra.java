package de.dualuse.vecmath;

public interface MatrixAlgebra<Q> {
	public Q zero();
	public Q identity();

	public Q invert(Q q);
	public Q transpose(Q q);
		
	public double determinant();
	public Q concatenate(Q that);
	
	public Q concatenation(Q A, Q B);
}
